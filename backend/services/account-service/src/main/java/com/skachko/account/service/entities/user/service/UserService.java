package com.skachko.account.service.entities.user.service;


import com.google.common.base.CaseFormat;
import com.skachko.account.service.entities.user.api.EUserRole;
import com.skachko.account.service.entities.user.dto.CustomUser;
import com.skachko.account.service.entities.user.dto.UserRequest;
import com.skachko.account.service.entities.user.repository.api.ICustomUserRepository;
import com.skachko.account.service.entities.user.service.api.IUserService;
import com.skachko.account.service.entities.user.validator.api.IUserValidator;
import com.skachko.account.service.exceptions.AccountServiceException;
import com.skachko.account.service.exceptions.AccountServiceValidationException;
import com.skachko.account.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.account.service.support.utils.IsEmptyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final ICustomUserRepository repository;
    private final MessageSource messageSource;
    private final IUserValidator validator;

    @Override
    public CustomUser getUserByEmail(String email) {
        try {
            return repository.findOneByEmail(email).orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AccountServiceException(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
        }
    }

    @Transactional
    @Override
    public CustomUser save(CustomUser customUser) {
        Date date = new Date();

        try {
            validator.validateBeforeCreate(customUser);
            customUser.setDtCreate(date);
            customUser.setDtUpdate(date);
            customUser.setPassword(encodePassword(customUser.getPassword()));
            return repository.save(customUser);
        } catch (AccountServiceValidationException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AccountServiceException(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public CustomUser getOne(UUID id) {
        try {
            return repository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AccountServiceException(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public CustomUser getByRequest(UserRequest request) {
        try {
            validator.validateRequest(request);
            request.setPassword(encodePassword(request.getPassword()));
            return repository.findOneByEmailAndPassword(request.getEmail(), request.getPassword())
                    .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException | AccountServiceValidationException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AccountServiceException(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
        }
    }

    @Transactional
    @Override
    public CustomUser update(UUID id, CustomUser newUser, Date version) {

        validator.validateBeforeUpdate(newUser);

        CustomUser existedUser = getOne(id);

        if (existedUser.getDtUpdate().getTime() != version.getTime()){
            throw new EntityNotFoundException();
        }

        if(existedUser.getDtDelete() != null) {
            throw new AccountServiceException(messageSource.getMessage("update.deactivated.error", null, LocaleContextHolder.getLocale()));
        }

        existedUser.setDtUpdate(new Date());
        existedUser.setEmail(newUser.getEmail());
        if (IsEmptyUtil.isNotNullOrEmpty(newUser.getPassword())){
            existedUser.setPassword(encodePassword(newUser.getPassword()));
        }
        existedUser.setName(newUser.getName());
        existedUser.setRoles(newUser.getRoles());

        return repository.save(existedUser);
    }


    @Transactional
    @Override
    public CustomUser updateRoles(UUID id, Date version, Set<EUserRole> newRoles) {

        CustomUser existedUser = getOne(id);

        if (IsEmptyUtil.isNullOrEmpty(newRoles)){
            throw new AccountServiceException(messageSource.getMessage("roles.empty", null, LocaleContextHolder.getLocale()));
        }

        if (existedUser.getDtUpdate().getTime() != version.getTime()){
            throw new EntityNotFoundException();
        }

        if(existedUser.getDtDelete() != null) {
            throw new AccountServiceException(messageSource.getMessage("update.deactivated.error", null, LocaleContextHolder.getLocale()));
        }

        existedUser.setDtUpdate(new Date());
        existedUser.setRoles(newRoles);

        return repository.save(existedUser);
    }


    @Transactional
    @Override
    public CustomUser deactivate(UUID id, Date version) {
        CustomUser existedUser = getOne(id);

        if (existedUser.getDtUpdate().getTime() != version.getTime()){
            throw new EntityNotFoundException();
        }

        if(existedUser.getDtDelete() != null) {
            throw new AccountServiceException(messageSource.getMessage("already.deactivated.error", null, LocaleContextHolder.getLocale()));
        }

        existedUser.setDtUpdate(new Date());
        existedUser.setDtDelete(existedUser.getDtUpdate());

        return repository.save(existedUser);
    }

    @Transactional
    @Override
    public CustomUser activate(UUID id, Date version) {
        CustomUser existedUser = getOne(id);

        if (existedUser.getDtUpdate().getTime() != version.getTime()){
            throw new EntityNotFoundException();
        }

        if(existedUser.getDtDelete() == null) {
            throw new AccountServiceException(messageSource.getMessage("already.activated.error", null, LocaleContextHolder.getLocale()));
        }

        existedUser.setDtUpdate(new Date());
        existedUser.setDtDelete(null);

        return repository.save(existedUser);
    }

    @Override
    public Boolean isEmailExist(String email) {
        return repository.findOneByEmail(email)
                .isPresent();
    }

    @Override
    public List<CustomUser> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<CustomUser> getPage(int size, int page, String sortField, Sort.Direction direction) {
        try {
            String formattedField = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, sortField);
            return repository.findAll(PageRequest.of(page, size, Sort.by(direction, formattedField)));
        } catch (Exception e) {
            throw new AccountServiceException(
                    messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale())
            );
        }
    }

    private String encodePassword(String password) {
        return DigestUtils.sha3_256Hex(password);
    }

}