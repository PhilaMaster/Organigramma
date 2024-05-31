package visitor.nodes_management;

import node.GraphicNode;
import node.Node;
import visitor.NodeVisitor;

public class SelectorVisitor implements NodeVisitor {
    /**
     * Visitor che ha il compito di fare una visita dell'albero e settare a true il booleano Selected relativo
     * al Node selezionato col tasto sinistro, false a tutti gli altri.
     */
    private Node selectedNode;
    private final GraphicNode selectedGraphicNode;

    public SelectorVisitor(GraphicNode selectedGraphicNode) {
        this.selectedGraphicNode = selectedGraphicNode;
    }

    public void visit(Node node){
        for(Node child : node.getChildren()){
            child.accept(this);
        }
        if(node.getGraphic() == selectedGraphicNode){
            selectedNode = node;
            node.getGraphic().setSelected(true);
        }
        else
            node.getGraphic().setSelected(false);
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

}
