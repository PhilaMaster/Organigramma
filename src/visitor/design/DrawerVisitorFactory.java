package visitor.design;

import visitor.NodeVisitor;

import java.awt.*;

public interface DrawerVisitorFactory {
    NodeVisitor createDrawerVisitor(Graphics2D g2d);
}
