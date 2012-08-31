package jp.mailmanager.dao.entity.base;

import java.sql.Timestamp;

/**
 * エンティティ基底インタフェース
 * 
 * @param <PK> 主キーのデータ型
 */
public interface EntityBase<PK> {

    /**
     * 主キーを取得する。
     * 
     * @return
     */
    PK getPK();

    /**
     * 主キーを設定する。
     * 
     * @param pk
     */
    void setPK(PK pk);

    /**
     * 登録日時を取得する。
     * 
     * @return 登録日時
     */
    Timestamp getRegisterDatetime();

    /**
     * 登録日時を設定する。
     * 
     * @param registerDatetime 登録日時
     */
    void setRegisterDatetime(Timestamp registerDatetime);

    /**
     * 登録ユーザIDを取得する。
     * 
     * @return 登録ユーザID
     */
    String getRegisterUserId();

    /**
     * 登録ユーザIDを設定する。
     * 
     * @param registerUserId 登録ユーザID
     */
    void setRegisterUserId(String registerUserId);

    /**
     * 更新日時を取得する。
     * 
     * @return 更新日時
     */
    Timestamp getUpdatedDatetime();

    /**
     * 更新日時を設定する。
     * 
     * @param updatedDatetime 更新日時
     */
    void setUpdatedDatetime(Timestamp updatedDatetime);

    /**
     * 更新ユーザIDを取得する。
     * 
     * @return 更新ユーザID
     */
    String getUpdatedUserId();

    /**
     * 更新ユーザIDを設定する。
     * 
     * @param updatedUserId 更新ユーザID
     */
    void setUpdatedUserId(String updatedUserId);

    /**
     * バージョンを取得する。
     * 
     * @return バージョン
     */
    Integer getVersion();

    /**
     * バージョンを設定する。
     * 
     * @param version バージョン
     */
    void setVersion(Integer version);

}
