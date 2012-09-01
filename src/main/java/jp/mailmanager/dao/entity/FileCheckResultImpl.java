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

    /** 入力ディレクトリパス */
    private String inputDirPath;

    /** 入力ファイル名 */
    private String inputFileName;

    /** 出力ディレクトリパス */
    private String outputDirPath;

    /** 出力ファイル名 */
    private String outputFileName;

    @Override
    public FileCheckResultPK getPK() {
        return this.pk;
    }

    @Override
    public void setPK(FileCheckResultPK pk) {
        this.pk = pk;
    }

    @Override
    public Integer getExecutionId() {
        return this.pk.getExecutionId();
    }

    @Override
    public void setExecutionId(Integer executionId) {
        this.pk.setExecutionId(executionId);
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
    public String getInputDirPath() {
        return this.inputDirPath;
    }

    @Override
    public void setInputDirPath(String inputDirPath) {
        this.inputDirPath = inputDirPath;
    }

    @Override
    public String getInputFileName() {
        return this.inputFileName;
    }

    @Override
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    @Override
    public String getOutputDirPath() {
        return this.outputDirPath;
    }

    @Override
    public void setOutputDirPath(String outputDirPath) {
        this.outputDirPath = outputDirPath;
    }

    @Override
    public String getOutputFileName() {
        return this.outputFileName;
    }

    @Override
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
