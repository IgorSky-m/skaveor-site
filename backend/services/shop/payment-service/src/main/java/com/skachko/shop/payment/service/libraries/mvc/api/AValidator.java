package com.skachko.shop.payment.service.libraries.mvc.api;

import com.skachko.shop.payment.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.shop.payment.service.libraries.mvc.exceptions.StructuredError;
import com.skachko.shop.payment.service.libraries.mvc.exceptions.StructuredFieldError;
import com.skachko.shop.payment.service.libraries.mvc.exceptions.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class AValidator<T extends AEntity, ID > implements IValidator<T, ID>{

    private static final String GROUP_FIELD_PATTERN = "%s[%s]";

    private final MessageSource messageSource;

    public AValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void validateCreate(T t) throws ValidationException {
        if (t == null) {
            throw new ValidationException(new StructuredError(getMessageSource().getMessage("error.main.entity.empty", null, LocaleContextHolder.getLocale())));
        }

        List<StructuredError> errors = validateFields(t);

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateGroupBeforeCreate(Collection<T> t) throws ValidationException {
        List<StructuredError> errors = new ArrayList<>();

        int index = 0;
        for (T entity : t) {
            errors.addAll(validateFields(entity, index++, true));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private List<StructuredError> validateFields(T t){
        return validateFields(t, 0, false);
    }

    private List<StructuredError> validateFields(T t, int entityNum, boolean isMultiple){

        List<StructuredError> errors = new ArrayList<>();

        if (t.getDtCreate() == null) {
            errors.add(new StructuredFieldError(getFieldName(AEntity.DT_CREATE, entityNum, isMultiple),getMessageSource().getMessage("error.main.field.empty", null, LocaleContextHolder.getLocale())));
        }

        if (t.getDtUpdate() == null) {
            errors.add(new StructuredFieldError(getFieldName(AEntity.DT_UPDATE, entityNum, isMultiple),getMessageSource().getMessage("error.main.field.empty", null, LocaleContextHolder.getLocale())));
        }

        return errors;
    }


    private String getFieldName(String fieldName, int num, boolean usePattern) {

        if (usePattern) {
            return String.format(GROUP_FIELD_PATTERN, fieldName, num);
        }

        return fieldName;

    }

    @Override
    public void validateBeforeUpdate(ID id, Date version, T oldEntity, T newEntity) throws ValidationException {
        checkVersionsIdentity(oldEntity.getDtUpdate().getTime(),  version.getTime());
    }

    private void checkVersionsIdentity(long time1, long time2) {
        if (time1 != time2){
            throw new EntityNotFoundException();
        }
    }


    @Override
    public void validateBeforeDelete(ID id, Date version, T old) {
        checkVersionsIdentity(old.getDtUpdate().getTime(),  version.getTime());
    }

    protected MessageSource getMessageSource(){
        return messageSource;
    }

}
