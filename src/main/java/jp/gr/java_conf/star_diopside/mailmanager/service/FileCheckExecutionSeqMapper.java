package jp.gr.java_conf.star_diopside.mailmanager.service;

/**
 * ファイルチェック実行シーケンスMapperインタフェース
 */
public interface FileCheckExecutionSeqMapper {

    /**
     * 新しいファイルチェック実行シーケンス値を取得する。
     * 
     * @return 新しいファイルチェック実行シーケンス値
     */
    Integer getNextVal();

}
