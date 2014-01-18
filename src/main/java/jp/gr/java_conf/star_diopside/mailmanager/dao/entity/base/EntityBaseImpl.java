package jp.gr.java_conf.star_diopside.mailmanager.dao.entity.base;

import java.sql.Timestamp;

/**
 * エンティティ基底クラス
 */
public abstract class EntityBaseImpl<PK> implements EntityBase<PK> {

    /** 登録日時 */
    private Timestamp registerDatetime;

    /** 登録ユーザID */
    private String registerUserId;

    /** 更新日時 */
    private Timestamp updatedDatetime;

    /** 更新ユーザID */
    private String updatedUserId;

    /** バージョン */
    private Integer version;

    @Override
    public Timestamp getRegisterDatetime() {
        return this.registerDatetime;
    }

    @Override
    public void setRegisterDatetime(Timestamp registerDatetime) {
        this.registerDatetime = registerDatetime;
    }

    @Override
    public String getRegisterUserId() {
        return this.registerUserId;
    }

    @Override
    public void setRegisterUserId(String registerUserId) {
        this.registerUserId = registerUserId;
    }

    @Override
    public Timestamp getUpdatedDatetime() {
        return this.updatedDatetime;
    }

    @Override
    public void setUpdatedDatetime(Timestamp updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    @Override
    public String getUpdatedUserId() {
        return this.updatedUserId;
    }

    @Override
    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
