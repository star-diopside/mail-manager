package jp.mailmanager.controller.model;

/**
 * MailManagerモデルクラス
 */
public class MailManagerModel {

    /** コピー元ディレクトリパス */
    private String origDir;

    /** コピー先ディレクトリパス */
    private String destDir;

    /**
     * コピー元ディレクトリパスを取得する。
     * 
     * @return コピー元ディレクトリパス
     */
    public String getOrigDir() {
        return origDir;
    }

    /**
     * コピー元ディレクトリパスを設定する。
     * 
     * @param origDir コピー元ディレクトリパス
     */
    public void setOrigDir(String origDir) {
        this.origDir = origDir;
    }

    /**
     * コピー先ディレクトリパスを取得する。
     * 
     * @return コピー先ディレクトリパス
     */
    public String getDestDir() {
        return destDir;
    }

    /**
     * コピー先ディレクトリパスを設定する。
     * 
     * @param destDir コピー先ディレクトリパス
     */
    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }
}
