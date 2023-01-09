package com.skachko.news.service.libraries.mvc.api;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public abstract class AEntity<ID> implements IIdentifiable<ID> {
    public static final String ID = "id";
    public static final String DT_CREATE = "dt_create";
    public static final String DT_UPDATE = "dt_update";
    public static final String DT_DELETE = "dt_delete";
    public static final String META = "meta";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ID)
    private ID id;
    @Column(name = DT_CREATE)
    private Date dtCreate;
    @Column(name = DT_UPDATE)
    private Date dtUpdate;
    @Column(name = DT_DELETE)
    private Date dtDelete;
    @Column(name = META)
    private String meta;


    public ID getId() {
        return id;
    }


    public void setId(ID id) {
        this.id = id;
    }


    public Date getDtCreate() {
        return dtCreate;
    }


    public void setDtCreate(Date dtCreate) {
        this.dtCreate = dtCreate;
    }


    public Date getDtUpdate() {
        return dtUpdate;
    }


    public void setDtUpdate(Date dtUpdate) {
        this.dtUpdate = dtUpdate;
    }


    public Date getDtDelete() {
        return dtDelete;
    }


    public void setDtDelete(Date dtDelete) {
        this.dtDelete = dtDelete;
    }


    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }


}
