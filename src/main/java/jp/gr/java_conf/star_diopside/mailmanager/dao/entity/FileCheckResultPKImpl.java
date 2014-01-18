package jp.gr.java_conf.star_diopside.mailmanager.dao.entity;

import java.io.Serializable;

/**
 * ファイルチェック結果主キークラス
 */
public class FileCheckResultPKImpl implements FileCheckResultPK, Serializable {

    private static final long serialVersionUID = 1L;

    /** 実行ID */
    private Integer executionId;

    /** ファイルハッシュ値 */
    private String fileHash;

    /** ファイル内連番 */
    private Integer fileSeq;

    @Override
    public Integer getExecutionId() {
        return this.executionId;
    }

    @Override
    public void setExecutionId(Integer executionId) {
        this.executionId = executionId;
    }

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
