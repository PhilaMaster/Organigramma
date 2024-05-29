package visitor.design;

import gui.GraphicNode;
import node.Node;
import visitor.NodeVisitor;

import java.awt.*;

public class NaiveDrawerVisitor implements NodeVisitor {
    /**
     * Visitor che disegna le linee che congiungono i nodi con delle linee dritte.
     */

    private final Graphics2D g2d;

    public NaiveDrawerVisitor(Graphics2D g2d) {
        this.g2d = g2d;
    }
    @Override
    public void visit(Node n) {
        for(Node child : n.getChildren()) {child.accept(this);}

        //Se non è il nodo radice, disegna la linea che lo congiunge il padre
        if(!n.isRoot()){
            int xParent = n.getParent().getGraphic().getX()+ GraphicNode.WIDTH/2;
            int yParent = n.getParent().getGraphic().getY()+ GraphicNode.HEIGHT;
            int xChild = n.getGraphic().getX()+ GraphicNode.WIDTH/2;
            g2d.drawLine(xParent, yParent, xChild, n.getGraphic().getY());
        }
    }
}
