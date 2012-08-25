package jp.mailmanager.constant;

/**
 * メッセージ定数クラス
 */
public class Messages {

    /**
     * プライベートコンストラクタ
     */
    private Messages() {
    }

    /** 必須エラーメッセージ */
    public static final String ERROR_REQUIRED = "Message.Error.Required";

    /** 存在チェックエラーメッセージ */
    public static final String ERROR_NOT_EXISTS = "Message.Error.NotExists";

    /** ディレクトリ存在チェックエラーメッセージ */
    public static final String ERROR_NOT_EXISTS_DIR = "Message.Error.NotExistsDir";

    /** ディレクトリ作成エラーメッセージ */
    public static final String ERROR_NOT_MAKE_DIR = "Message.Error.NotMakeDir";

    /** 処理実行確認メッセージ */
    public static final String CONFIRM_BEFORE_EXECUTE = "Message.Confirm.BeforeExecute";

    /** 処理完了メッセージ */
    public static final String INFO_COMPLETE = "Message.Info.Complete";

}
