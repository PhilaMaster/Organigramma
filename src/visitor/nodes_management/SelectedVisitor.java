package visitor.nodes_management;

import gui.GraphicNode;
import node.Node;
import visitor.NodeVisitor;

public class SelectedVisitor implements NodeVisitor {
    /**
     * Visitor che ha il compito di fare una visita dell'albero e settare a true il booleano Selected relativo
     * al Node selezionato col tasto sinistro, false a tutti gli altri.
     */
    private Node selectedNode;
    private final GraphicNode selectedGraphicNode;

    public SelectedVisitor(GraphicNode selectedGraphicNode) {
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
