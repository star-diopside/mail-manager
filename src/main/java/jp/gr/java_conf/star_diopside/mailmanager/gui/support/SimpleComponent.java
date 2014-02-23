package jp.gr.java_conf.star_diopside.mailmanager.gui.support;

import java.awt.Component;
import java.awt.Container;

public class SimpleComponent implements ContainerComponent {

    private Component component;

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public void addTo(Container container) {
        container.add(component);
    }
}
