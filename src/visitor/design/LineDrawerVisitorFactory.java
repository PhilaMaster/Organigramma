package visitor.design;

import visitor.NodeVisitor;

import java.awt.*;

public enum LineDrawerVisitorFactory implements DrawerVisitorFactory{
    INSTANCE;
    @Override
    public NodeVisitor createDrawerVisitor(Graphics2D g2d) {
        return new LineDrawerVisitor(g2d);
    }
}
