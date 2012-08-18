package jp.mailmanager.controller.support;

import java.awt.Component;
import java.awt.event.ActionEvent;

public abstract class AbstractModelActionListener<T> implements ActionModelDriven<T> {

    private ActionModelDriven<T> action;

    public AbstractModelActionListener(ActionModelDriven<T> action) {
        this.action = action;
    }

    public abstract Component getParent();

    @Override
    public void actionPerformed(ActionEvent e) {

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
