package visitor.nodes_management;

import gui.GraphicNode;
import gui.UserInterfacePanel;
import node.Node;
import visitor.NodeVisitor;

public class ComponentAdderVisitor implements NodeVisitor {

    private final UserInterfacePanel panel;
    public ComponentAdderVisitor(UserInterfacePanel panel) {
        this.panel = panel;
    }

    public void visit(Node node) {
        for(Node child : node.getChildren()) {
            child.accept(this);
        }

        if(!panel.getRoot().equals(node)) {
            GraphicNode gnode = node.getGraphic();
            panel.add(gnode);
            gnode.addMouseListener(panel.mouseAdapter);
        }
    }
}
