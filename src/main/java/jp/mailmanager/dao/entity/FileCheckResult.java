package jp.mailmanager.dao.entity;

import jp.mailmanager.dao.entity.base.EntityBase;

/**
 * ファイルチェック結果エンティティインタフェース
 */
public interface FileCheckResult extends FileCheckResultPK, EntityBase<FileCheckResultPK> {

    /**
     * 入力ディレクトリパスを取得する。
     * 
     * @return 入力ディレクトリパス
     */
    String getInputDirPath();

    /**
     * 入力ディレクトリパスを設定する。
     * 
     * @param inputDirPath 入力ディレクトリパス
     */
    void setInputDirPath(String inputDirPath);

    /**
     * 入力ファイル名を取得する。
     * 
     * @return 入力ファイル名
     */
    String getInputFileName();

    /**
     * 入力ファイル名を設定する。
     * 
     * @param inputFileName 入力ファイル名
     */
    void setInputFileName(String inputFileName);

    /**
     * 出力ディレクトリパスを取得する。
     * 
     * @return 出力ディレクトリパス
     */
    String getOutputDirPath();

    /**
     * 出力ディレクトリパスを設定する。
     * 
     * @param outputDirPath 出力ディレクトリパス
     */
    void setOutputDirPath(String outputDirPath);

    /**
     * 出力ファイル名を取得する。
     * 
     * @return 出力ファイル名
     */
    String getOutputFileName();

    /**
     * 出力ファイル名を設定する。
     * 
     * @param outputFileName 出力ファイル名
     */
    void setOutputFileName(String outputFileName);

}
