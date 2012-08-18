package jp.mailmanager.controller.support;

public interface ModelAccessor<T> {

    T getModel();

    void setModel(T model);

}
