package jp.mailmanager.controller.action;

import jp.mailmanager.controller.model.MailManagerModel;
import jp.mailmanager.controller.support.ActionModelDriven;
import jp.mailmanager.controller.support.ParentComponentAccessor;

/**
 * MailManagerコピー先ディレクトリ選択イベントインタフェース
 */
public interface MailManagerSelectDestDirAction extends ActionModelDriven<MailManagerModel>, ParentComponentAccessor {

}
