package gui;

import static gui.GraphicNode.*;
import node.CompositeNode;
import node.Node;
import visitor.PositionerVisitor;
import visitor.SelectedVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UserInterfacePanel extends javax.swing.JPanel {
    private int treeHeight = 1, treeWidth = 1;
    private final CompositeNode root;
    private Node selected;
    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() instanceof GraphicNode g) {
                SelectedVisitor selectedVisitor = new SelectedVisitor(g);
                root.accept(selectedVisitor);
                selected = selectedVisitor.getSelectedNode();

                if (e.getClickCount() == 2){
                    String nome = JOptionPane.showInputDialog(null, "Inserisci il nome del nodo:", "Inserisci nome", JOptionPane.QUESTION_MESSAGE);
                    if (nome != null && !nome.isBlank()) {
                        if(nome.length() <= GraphicNode.CHARACTER_LIMIT){
                            selected.getGraphic().setName(nome);
                            selected.getGraphic().repaint();
                        }else
                            JOptionPane.showMessageDialog(null, "La stringa inserita è troppo lunga, il massimo è "+GraphicNode.CHARACTER_LIMIT+"!"
                                    , "Inserisci nome", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    };

    public UserInterfacePanel(UserInterfaceFrame uif) {
        super(null);//layoutManager nullo, poichè li gestisco manualmente
        setBackground(Color.GRAY);

        GraphicNode graphicRoot = new GraphicNode(uif.getTitle());
        root = new CompositeNode(0, graphicRoot);
        root.setRemovable(false);
        this.add(graphicRoot);
        graphicRoot.addMouseListener(mouseAdapter);
    }

    public Node getSelected(){
        return selected;
    }

    public Node getRoot(){
        return root;
    }
    public void newNode() {
        //TODO scegli nome con popup
        String nomeNodo = "nome esempio";


        GraphicNode gNode = new GraphicNode(nomeNodo);
        Node node = new CompositeNode(selected.getHeight()+1, gNode);

        selected.add(node);
        this.add(gNode);
        node.setParent(selected);
        gNode.addMouseListener(mouseAdapter);


        PositionerVisitor visitor = new PositionerVisitor();
        root.accept(visitor);
        treeHeight = visitor.getHeight();
        treeWidth = visitor.getWidth();

        //verifica possibile aggiunta di scrollbar al panel (se i figli sono troppi e non entrano)
        setPreferredSize(new Dimension(//TODO formula un attimo da rivedere
                GraphicNode.HORIZONTAL_OFFSET + GraphicNode.WIDTH*2 + (GraphicNode.WIDTH+ GraphicNode.HORIZONTAL_SPACE)*treeWidth,
                GraphicNode.VERTICAL_OFFSET + GraphicNode.HEIGHT*2 +(GraphicNode.HEIGHT+ GraphicNode.VERTICAL_SPACE)*treeHeight));


        this.repaint();
        this.revalidate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Node n : getNodes()) {
            int xMean = n.getGraphic().getX() + GraphicNode.WIDTH / 2;
            int y = n.getGraphic().getY();

            //Se non è il nodo radice, disegno la linea sopra il nodo
            if(!n.isRoot())
                g2d.drawLine(xMean, y, xMean, y - GraphicNode.VERTICAL_SPACE / 2);

            //Se non è un nodo foglia, disegno la linea sotto il nodo
            if (!n.isLeaf()) {
                g2d.drawLine(xMean, y + GraphicNode.HEIGHT,
                        xMean, y + GraphicNode.HEIGHT + GraphicNode.VERTICAL_SPACE / 2);

                int nChildren = n.getChildrenCount();
                if (nChildren > 1){
                    int xFirst = n.getChild(0).getGraphic().getX();
                    int xLast = n.getChild(nChildren-1).getGraphic().getX();
                    int yLine = n.getGraphic().getY()+VERTICAL_SPACE/2+GraphicNode.HEIGHT;
                    g2d.drawLine(xFirst+ GraphicNode.WIDTH/2,yLine,
                            xLast+GraphicNode.WIDTH/2,yLine);
                }


//                g2d.drawLine();
            }
        }
    }

    private List<Node> getNodes() {
        List<Node> nodes = new LinkedList<>();
        getLeafNodesRec(root, nodes);
        return nodes;
    }

    private void getLeafNodesRec(Node node, List<Node> nodes){
        nodes.add(node);
        for(Node n: node.getChildren()) {
            getLeafNodesRec(n,nodes);
        }
    }


//        for(Component c : this.getComponents())
//            if (c instanceof GraphicNode gNode){
//                int xMean = gNode.getX() + GraphicNode.WIDTH/2;
//                int y = gNode.getY();
//
//                //Se non è il nodo radice, disegno la linea sopra il nodo
//                if (!this.getRoot().getGraphic().equals(gNode))
//                    g2d.drawLine(xMean,y,xMean,y-GraphicNode.VERTICAL_SPACE/2);
//
//                //Se non è un nodo foglia, disegno la linea sotto il nodo
//                if (!leafs.contains(gNode))
//                    g2d.drawLine(xMean,y+GraphicNode.HEIGHT,
//                            xMean,y+GraphicNode.HEIGHT+GraphicNode.VERTICAL_SPACE/2);
//            }

//    }
//
//    private Set<GraphicNode> getLeafNodes() {//Si potrebbe usare un proxy per ritardare l'esecuzione del metodo a quando è davvero necessario
//        Set<GraphicNode> leafs = new HashSet<>();
//        getLeafNodesRec(root, leafs);
//        return leafs;
//    }
//
//    private void getLeafNodesRec(Node node, Set<GraphicNode> leafs){
//        for(Node n: node.getChildren())
//            if(n.isLeaf()) leafs.add(n.getGraphic());
//    }
}
