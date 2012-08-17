package jp.mailmanager.main;

/**
 * 処理実行インタフェース
 */
public interface Launcher {

    /**
     * 処理を実行する。
     * 
     * @param args 引数
     * @return 終了ステータスコード
     */
    int run(String[] args);

}
