package visitor.nodes_management;

import node.CompositeNode;
import node.Node;
import visitor.NodeVisitor;

import javax.swing.*;

public class RemoveChildrenVisitor implements NodeVisitor {
    /**
     * Rimuove tutti gli oggetti <code>GraphicNode</code> dei nodi figli del node su cui si invoca la accept() dal panel.
     */
    private final JPanel panel;
    public RemoveChildrenVisitor(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void visit(CompositeNode node) {
        for(Node child : node.getChildren())
            child.accept(this);
        panel.remove(node.getGraphic());
    }
}
