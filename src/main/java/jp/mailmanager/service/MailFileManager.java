package jp.mailmanager.service;

import java.io.File;
import java.util.LinkedHashMap;

import jp.mailmanager.exception.BusinessException;

/**
 * メールファイル管理処理インタフェース
 */
public interface MailFileManager {

    /**
     * メールファイル(EML)の名前を変更してコピーする。
     * 
     * @param inputDirectory コピー元メールファイル格納ディレクトリパス名
     * @param outputDirectory コピー先ディレクトリパス名
     * @return エラーが発生したファイルをキー、エラー情報を値に持つマップオブジェクト
     * @throws BusinessException 
     *             inputDirectoryがディレクトリではない、またはoutputDirectoryのディレクトリが生成できない場合
     */
    LinkedHashMap<File, Exception> copyMailFiles(String inputDirectory, String outputDirectory)
            throws BusinessException;

    /**
     * メールファイル(EML)の名前を変更してコピーする。
     * 
     * @param inputDirectory コピー元メールファイル格納ディレクトリパス
     * @param outputDirectory コピー先ディレクトリパス
     * @return エラーが発生したファイルをキー、エラー情報を値に持つマップオブジェクト
     * @throws BusinessException 
     *             inputDirectoryがディレクトリではない、またはoutputDirectoryのディレクトリが生成できない場合
     */
    LinkedHashMap<File, Exception> copyMailFiles(File inputDirectory, File outputDirectory) throws BusinessException;

}
