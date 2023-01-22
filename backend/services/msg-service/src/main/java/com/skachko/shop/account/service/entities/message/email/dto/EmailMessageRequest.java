package com.skachko.shop.account.service.entities.message.email.dto;

import com.skachko.shop.account.service.entities.message.dto.api.EMessageType;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessageRequest {

    private EMessageType messageType;

    private String subject;

    private String text;

    private String receiver;

    List<FileProps> files;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileProps{
        private String name;
        private String type;
        private byte[] bytes;
    }
}
