package jp.mailmanager.controller.support;

import java.awt.Component;
import java.awt.event.ActionEvent;

/**
 * アクションモデルの共通処理を実装したクラス
 * 
 * @param <T> モデル型
 */
public abstract class AbstractModelActionListener<T> implements ActionModelDriven<T> {

    /** アクションモデル */
    private ActionModelDriven<T> action;

    /**
     * コンストラクタ
     * 
     * @param action アクションモデル
     */
    public AbstractModelActionListener(ActionModelDriven<T> action) {
        this.action = action;
    }

    /**
     * 親コンポーネントを取得する。
     * 
     * @return 親コンポーネント
     */
    public abstract Component getParent();

    /**
     * モデル設定後にアクションを実行し、実行結果を呼び出し元に反映する。
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // 親コンポーネントアクセスインタフェースを実装している場合、親コンポーネントを設定する。
        if (action instanceof ParentComponentAccessor) {
            ((ParentComponentAccessor) action).setParent(getParent());
        }

        // ソースから取得したモデルをアクションリスナーに引き渡す。
        this.action.setModel(this.getModel());

        // アクションリスナーの処理を実行する。
        this.action.actionPerformed(e);

        // 編集後のモデルをソースに反映する。
        this.setModel(this.action.getModel());
    }
}
