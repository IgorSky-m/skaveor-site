package com.skachko.shop.account.service.entities.message.email.service;

import com.skachko.shop.account.service.config.AsyncConfig;
import com.skachko.shop.account.service.entities.message.api.IMessageService;
import com.skachko.shop.account.service.entities.message.dto.CustomUserMessage;
import com.skachko.shop.account.service.entities.message.dto.api.EMessageStatus;
import com.skachko.shop.account.service.entities.message.dto.api.EMessageType;
import com.skachko.shop.account.service.entities.message.email.dto.EmailMessageRequest;
import com.skachko.shop.account.service.entities.message.email.service.api.IEmailService;
import com.skachko.shop.account.service.exceptions.MsgServiceException;
import com.skachko.shop.account.service.support.utils.IsEmptyUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessageService implements IMessageService<EmailMessageRequest> {

    private static final String FORMAT = "%s\nResponse email: %s";

    private final Executor executor = Executors.newSingleThreadExecutor();
    @Value("${msg.emails.default}")
    private String defaultEmail;

    private final IEmailService service;
    private final JavaMailSender mailSender;

    @Async(AsyncConfig.ASYNC_EXECUTOR_BEAN_NAME)
    @Override
    public void sendAndSave(EmailMessageRequest request, Map<String, String> headers) {

        CustomUserMessage msg = CustomUserMessage.builder()
                .sender(defaultEmail)
                .receiver(request.getReceiver())
                .text(createText(request))
                .messageType(EMessageType.EMAIL)
                .subject(request.getSubject())
                .dtCreate(new Date())
                .userId(getUserId(headers))
                .status(EMessageStatus.CREATED)
                .build();

        try {
            msg = service.save(msg);
            this.mailSender.send(createMessage(request));
            msg.setStatus(EMessageStatus.SENT);
        } catch (Exception e) {
            msg.setStatus(EMessageStatus.ERROR);
        }

        service.save(msg);

    }


    @Override
    public List<CustomUserMessage> getListByUserId(UUID userId) {
        return service.getByUserId(userId);
    }

    @Override
    public CustomUserMessage getMessage(UUID id) {
        return service.getById(id);
    }

    private UUID getUserId(Map<String, String> headers) {

        UUID id = null;

        if (headers != null) {
            String str = headers.getOrDefault("id", null);
            if (str != null) {
                id = UUID.fromString(str);
            }
        }

        return id;
    }

    private MimeMessage createMessage(EmailMessageRequest request) {
        MimeMessage msg = mailSender.createMimeMessage();
        boolean multipart = IsEmptyUtil.isNotNullOrEmpty(request.getFiles());
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, multipart);
            helper.setFrom(defaultEmail);
            helper.setTo(defaultEmail);
            helper.setSubject(request.getSubject());
            helper.setText(createText(request));
            if (multipart) {
                for (EmailMessageRequest.FileProps e : request.getFiles()) {
                    helper.addAttachment(e.getName(), new ByteArrayDataSource(e.getBytes(), e.getType()));
                }
            }
            return helper.getMimeMessage();
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new MsgServiceException(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new MsgServiceException();
        }


    }

    private String createText(EmailMessageRequest request) {
        return String.format(FORMAT, request.getText(), request.getReceiver());
    }

}
