package com.skachko.libraries.mvc.api;

import java.util.Date;

public interface IBaseEntity<ID> extends IIdentifiable<ID> {


    Date getDtCreate();

    void setDtCreate(Date dtCreate);

    Date getDtUpdate();

    void setDtUpdate(Date dtUpdate);

    Date getDtDelete();
    void setDtDelete(Date dtDelete);

    String getMeta();
    void setMeta(String meta);
}
