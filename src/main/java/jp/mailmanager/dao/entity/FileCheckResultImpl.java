package jp.mailmanager.dao.entity;

import java.io.Serializable;

import jp.mailmanager.dao.entity.base.EntityBaseImpl;

/**
 * ファイルチェック結果エンティティクラス
 */
public class FileCheckResultImpl extends EntityBaseImpl<FileCheckResultPK> implements FileCheckResult, Serializable {

    private static final long serialVersionUID = 1L;

    /** 主キー */
    private FileCheckResultPK pk = new FileCheckResultPKImpl();

    /** 入力ファイルパス */
    private String inputFilePath;

    /** 出力ファイルパス */
    private String outputFilePath;

    @Override
    public FileCheckResultPK getPK() {
        return this.pk;
    }

    @Override
    public void setPK(FileCheckResultPK pk) {
        this.pk = pk;
    }

    @Override
    public String getFileHash() {
        return this.pk.getFileHash();
    }

    @Override
    public void setFileHash(String fileHash) {
        this.pk.setFileHash(fileHash);
    }

    @Override
    public Integer getFileSeq() {
        return this.pk.getFileSeq();
    }

    @Override
    public void setFileSeq(Integer fileSeq) {
        this.pk.setFileSeq(fileSeq);
    }

    @Override
    public String getInputFilePath() {
        return this.inputFilePath;
    }

    @Override
    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    @Override
    public String getOutputFilePath() {
        return this.outputFilePath;
    }

    @Override
    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }
}
