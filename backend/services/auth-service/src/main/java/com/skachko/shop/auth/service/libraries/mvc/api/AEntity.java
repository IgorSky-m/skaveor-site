package com.skachko.shop.auth.service.libraries.mvc.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class AEntity implements IIdentifiable<UUID> {
    public static final String ID = "id";
    public static final String DT_CREATE = "dt_create";
    public static final String DT_UPDATE = "dt_update";
    public static final String DT_DELETE = "dt_delete";
    public static final String META = "meta";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ID)
    private UUID id;
    @Column(name = DT_CREATE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date dtCreate;
    @Column(name = DT_UPDATE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date dtUpdate;
    @Column(name = DT_DELETE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date dtDelete;
    @Column(name = META)
    private String meta;


    public AEntity(){}
    public AEntity(AEntity entity){
        this.id = entity.getId();
        this.dtCreate = entity.getDtCreate();
        this.dtUpdate = entity.getDtUpdate();
        this.dtDelete = entity.getDtDelete();
        this.meta = entity.getMeta();
    }


    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
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
