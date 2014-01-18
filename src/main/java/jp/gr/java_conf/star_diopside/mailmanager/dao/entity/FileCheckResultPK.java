package jp.gr.java_conf.star_diopside.mailmanager.dao.entity;

/**
 * ファイルチェック結果主キーインタフェース
 */
public interface FileCheckResultPK {

    /**
     * 実行IDを取得する。
     * 
     * @return 実行ID
     */
    Integer getExecutionId();

    /**
     * 実行IDを設定する。
     * 
     * @param executionId 実行ID
     */
    void setExecutionId(Integer executionId);

    /**
     * ファイルハッシュ値を取得する。
     * 
     * @return ファイルハッシュ値
     */
    String getFileHash();

    /**
     * ファイルハッシュ値を設定する。
     * 
     * @param fileHash ファイルハッシュ値
     */
    void setFileHash(String fileHash);

    /**
     * ファイル内連番を取得する。
     * 
     * @return ファイル内連番
     */
    Integer getFileSeq();

    /**
     * ファイル内連番を設定する。
     * 
     * @param fileSeq ファイル内連番
     */
    void setFileSeq(Integer fileSeq);

}
