package visitor.nodes_management;

import node.Node;
import visitor.NodeVisitor;

import javax.swing.*;

public class RemoveChildrenVisitor implements NodeVisitor {
    /**
     * Rimuove tutti gli oggetti <code>GraphicNode</code> dei nodi figli di node dal panel.
     * @param node il nodo di cui si vogliono rimuovere gli oggetti <code>GraphicNode</code> dei figli dal panel
     */
    private final JPanel panel;
    public RemoveChildrenVisitor(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void visit(Node node) {
        for(Node child : node.getChildren()) {
            child.accept(this);
            panel.remove(child.getGraphic());
        }
    }
}
