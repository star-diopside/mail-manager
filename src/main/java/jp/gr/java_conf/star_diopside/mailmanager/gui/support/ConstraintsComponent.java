package jp.gr.java_conf.star_diopside.mailmanager.gui.support;

import java.awt.Component;
import java.awt.Container;

public class ConstraintsComponent implements ContainerComponent {

    private Component component;
    private Object constraints;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Object getConstraints() {
        return constraints;
    }

    public void setConstraints(Object constraints) {
        this.constraints = constraints;
    }

    @Override
    public void addTo(Container container) {
        container.add(component, constraints);
    }
}
