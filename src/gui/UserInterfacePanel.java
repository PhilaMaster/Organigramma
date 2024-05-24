package gui;

import node.CompositeNode;
import node.Node;
import visitor.PositionerVisitor;
import visitor.SelectedVisitor;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInterfacePanel extends javax.swing.JPanel {
    //private final UserInterfaceFrame uif;
    private int treeHeight = 1, treeWidth = 1;
    private final CompositeNode root;
    //private final GraphicNode graphicRoot;
    private Node selected;
    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() instanceof GraphicNode g) {
                SelectedVisitor selectedVisitor = new SelectedVisitor(g);
                root.accept(selectedVisitor);
                selected = selectedVisitor.getSelectedNode();
            }

            //TODO if doppioclick -> apri menù
        }
    };

    public UserInterfacePanel(UserInterfaceFrame uif) {
        super(null);//layoutManager nullo, quindi manuale
        setBackground(Color.GRAY);

        //istanzio nodo radice (TODO se uso command, mettere in un command?
        GraphicNode graphicRoot = new GraphicNode(this, uif.getTitle());
        root = new CompositeNode(0, graphicRoot);
        root.setRemovable(false);
        this.add(graphicRoot);
        graphicRoot.addMouseListener(mouseAdapter);
    }

//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        //calcolo posizione nodi con visita, li disegno e disegno anche collegamenti con pattern visitor
//        //TODO
//    }

    public void addNode() {
        //TODO scegli nome con popup
        String nomeNodo = "nome esempio";


        GraphicNode gNode = new GraphicNode(this, nomeNodo);
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

//    public void setSelected(GraphicNode gd) {
//        this.selected = gd;
//    }
}
