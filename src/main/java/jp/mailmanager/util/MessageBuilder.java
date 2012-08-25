package jp.mailmanager.util;

/**
 * メッセージビルダーインタフェース
 */
public interface MessageBuilder {

    /**
     * 初期化する。
     */
    void init();

    /**
     * メッセージコードを設定する。
     * 
     * @param code メッセージのコード値
     */
    void setMessageCode(String code);

    /**
     * コード指定で引数を追加する。
     * 
     * @param code 追加引数のコード値
     */
    void addArgumentCode(String code);

    /**
     * 指定されたオブジェクトを引数に追加する。
     * 
     * @param arg 引数オブジェクト
     */
    void addArgument(Object arg);

    /**
     * 組み立てたメッセージを取得する。
     */
    String getMessage();

}
