package jp.mailmanager.dao.entity;

import java.io.Serializable;

/**
 * ファイルチェック結果主キークラス
 */
public class FileCheckResultPKImpl implements FileCheckResultPK, Serializable {

    private static final long serialVersionUID = 1L;

    /** ファイルハッシュ値 */
    private String fileHash;

    /** ファイル内連番 */
    private Integer fileSeq;

    @Override
    public String getFileHash() {
        return this.fileHash;
    }

    @Override
    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    @Override
    public Integer getFileSeq() {
        return this.fileSeq;
    }

    @Override
    public void setFileSeq(Integer fileSeq) {
        this.fileSeq = fileSeq;
    }
}
