package visitor.design;

import gui.GraphicNode;
import node.Node;
import visitor.NodeVisitor;

import java.awt.*;

import static gui.GraphicNode.VERTICAL_SPACE;

public class LineDrawerVisitor implements NodeVisitor {
    private final Graphics2D g2d;

    public LineDrawerVisitor(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void visit(Node n) {
        for(Node child : n.getChildren()) {child.accept(this);}

        int xMean = n.getGraphic().getX() + GraphicNode.WIDTH / 2;
        int y = n.getGraphic().getY();

        //Se non è il nodo radice, disegno la linea sopra il nodo
        if(!n.isRoot())
            g2d.drawLine(xMean, y, xMean, y - GraphicNode.VERTICAL_SPACE / 2);


        if (!n.isLeaf()) {
            //Se non è un nodo foglia, disegno la linea sotto il nodo
            g2d.drawLine(xMean, y + GraphicNode.HEIGHT,
                    xMean, y + GraphicNode.HEIGHT + GraphicNode.VERTICAL_SPACE / 2);

            int nChildren = n.getChildrenCount();
            //Per ogni nodo foglia che abbia più di un figlio devo disegnare la linea che congiunge il primo e l'ultimo figlio
            if (nChildren > 1){
                int xFirst = n.getChild(0).getGraphic().getX();
                int xLast = n.getChild(nChildren-1).getGraphic().getX();
                int yLine = n.getGraphic().getY()+VERTICAL_SPACE/2+GraphicNode.HEIGHT;
                g2d.drawLine(xFirst+ GraphicNode.WIDTH/2,yLine,
                        xLast+GraphicNode.WIDTH/2,yLine);
            }
        }
    }
}
