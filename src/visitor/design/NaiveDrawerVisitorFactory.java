package visitor.design;

import visitor.NodeVisitor;

import java.awt.*;

public enum NaiveDrawerVisitorFactory implements DrawerVisitorFactory {
    INSTANCE;
    @Override
    public NodeVisitor createDrawerVisitor(Graphics2D g2d) {
        return new NaiveDrawerVisitor(g2d);
    }
}