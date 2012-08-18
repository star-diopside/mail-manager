package jp.mailmanager.controller.support;

import java.awt.event.ActionListener;

public interface ActionModelDriven<T> extends ActionListener, ModelAccessor<T> {

}
