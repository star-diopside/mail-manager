package jp.mailmanager.util;

import java.util.ArrayList;

import org.springframework.context.support.MessageSourceAccessor;

/**
 * メッセージの構築を行うビルダーインタフェース
 */
public class MessageBuilder {

    /** メッセージソース */
    private MessageSourceAccessor accessor;

    /** メッセージコード */
    private String msg;

    /** 引数 */
    private ArrayList<Object> args = new ArrayList<>();

    /**
     * コンストラクタ
     * 
     * @param accessor メッセージソースへのアクセスを行うMessageSourceAccessor
     */
    public MessageBuilder(MessageSourceAccessor accessor) {
        this.accessor = accessor;
    }

    /**
     * 初期化する。
     */
    public void init() {
        this.msg = null;
        this.args.clear();
    }

    /**
     * メッセージコードを設定する。
     * 
     * @param code メッセージのコード値
     */
    public void setMessageCode(String code) {
        this.msg = code;
    }

    /**
     * コード指定で引数を追加する。
     * 
     * @param code 追加引数のコード値
     */
    public void addArgumentCode(String code) {
        this.args.add(this.accessor.getMessage(code));
    }

    /**
     * 指定されたオブジェクトを引数に追加する。
     * 
     * @param arg 引数オブジェクト
     */
    public void addArgument(Object arg) {
        this.args.add(arg);
    }

    /**
     * 構築したメッセージを取得する。
     */
    public String getMessage() {
        return this.accessor.getMessage(this.msg, this.args.toArray());
    }
}
