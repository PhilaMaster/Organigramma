package visitor;

import static gui.GraphicNode.*;

import node.Node;

public class PositionerVisitor implements NodeVisitor {
    /**
     * Visita la gerarchia di nodi dell'organigramma, calcolando la posizione di ognuno di essi.
     * Calcola inoltre altezza e larghezza dell'albero, possono essere acceduti con i metodi getters.
     */

    int height,width;
    int xLeaf = HORIZONTAL_OFFSET;

    @Override
    public void visit(Node node) {
        //POST-ORDER DEPTH FIRST TRAVERSAL
        for(Node n : node.getChildren())
            n.accept(this);

        //CALCOLO ALTEZZA E SPESSORE
        if(node.isLeaf()) width++;
        height = Math.max(node.getHeight(), height);

        //CALCOLO POSIZIONE
        if (node.isLeaf()){
            node.getGraphic().setLocation(xLeaf,getY(node));
            xLeaf += WIDTH + HORIZONTAL_SPACE;//posizione prossimo nodo foglia
        }else
            if(node.getChildrenCount()==1){
                //se ha un figlio imposto la sua stessa posizione
                int xParent = node.getChild(0).getGraphic().getX();
                node.getGraphic().setLocation(xParent,getY(node));
            }else
                node.getGraphic().setLocation(getXParent(node),getY(node));
    }

    private int getXParent(Node parent) {
        int x_first = parent.getChild(0).getGraphic().getX();
        int x_last = parent.getChild(parent.getChildrenCount()-1).getGraphic().getX();
        return x_first + (x_last-x_first)/2;
    }

    private static int getY(Node n) {
        return n.getHeight() * (VERTICAL_SPACE+HEIGHT) + VERTICAL_OFFSET;
    }

    public int getHeight() {return height;}
    public int getWidth() {return width;}
}
