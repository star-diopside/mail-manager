package jp.gr.java_conf.star_diopside.mailmanager.gui.support;

import java.awt.Component;
import java.awt.Container;

public class ComponentUtils {

    public static <T> T findParent(Component component, Class<T> clazz) {
        Container parent = component.getParent();
        if (parent == null) {
            return null;
        } else if (clazz.isInstance(parent)) {
            return clazz.cast(parent);
        } else {
            return findParent(parent, clazz);
        }
    }
}
