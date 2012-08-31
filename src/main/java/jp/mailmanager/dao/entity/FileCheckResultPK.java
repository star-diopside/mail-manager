package jp.mailmanager.dao.entity;

/**
 * ファイルチェック結果主キーインタフェース
 */
public interface FileCheckResultPK {

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
