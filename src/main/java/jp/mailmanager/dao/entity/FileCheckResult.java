package jp.mailmanager.dao.entity;

import jp.mailmanager.dao.entity.base.EntityBase;

/**
 * ファイルチェック結果エンティティインタフェース
 */
public interface FileCheckResult extends FileCheckResultPK, EntityBase<FileCheckResultPK> {

    /**
     * 入力ファイルパスを取得する。
     * 
     * @return 入力ファイルパス
     */
    String getInputFilePath();

    /**
     * 入力ファイルパスを設定する。
     * 
     * @param inputFilePath 入力ファイルパス
     */
    void setInputFilePath(String inputFilePath);

    /**
     * 出力ファイルパスを取得する。
     * 
     * @return 出力ファイルパス
     */
    String getOutputFilePath();

    /**
     * 出力ファイルパスを設定する。
     * 
     * @param outputFilePath 出力ファイルパス
     */
    void setOutputFilePath(String outputFilePath);

}
