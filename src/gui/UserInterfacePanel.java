package gui;

import static gui.GraphicNode.*;

import exceptions.NothingSelectedException;
import node.CompositeNode;
import node.Node;
import visitor.PositionerVisitor;
import visitor.RemoveChildrenVisitor;
import visitor.SelectedVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class UserInterfacePanel extends javax.swing.JPanel {
    private static int idNode = 1;
    private int treeHeight = 1, treeWidth = 1;
    private final CompositeNode root;
    private Node selected;
    public final MouseAdapter mouseAdapter = new MouseAdapter() {
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

    public Node getSelected(){return selected;}
    public void setSelected(Node selected){this.selected = selected;}

    public UserInterfacePanel(UserInterfaceFrame uif) {
        super(null);//layoutManager nullo, poichè li gestisco manualmente
        setBackground(Color.GRAY);

        GraphicNode graphicRoot = new GraphicNode(uif.getTitle());
        root = new CompositeNode(0, graphicRoot);
        root.setRemovable(false);
        this.add(graphicRoot);
        graphicRoot.addMouseListener(mouseAdapter);
    }

    /**
     * Aggiunge un nuovo nodo al panel come figlio del nodo selezionato con un nome di default.
     * Il nome potrà essere modificato in seguito facendo doppio click sul nodo.
     */
//    public void newNode() {
//        String nomeNodo = "Nodo "+idNode++;
//        if (selected==null) throw new NothingSelectedException();
//
//        GraphicNode gNode = new GraphicNode(nomeNodo);
//        Node node = new CompositeNode(selected.getHeight()+1, gNode);
//
//        selected.add(node);
//        this.add(gNode);
//        node.setParent(selected);
//        gNode.addMouseListener(mouseAdapter);
//
//        ridisegna();
//    }

    public void ridisegna() {
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


//    public void removeSelectedNode(){
//        if (selected==null) throw new NothingSelectedException();
//        if (selected.isRoot()) throw new UnsupportedOperationException("Impossibile rimuovere il nodo radice");
//
//        if (selected.getChildrenCount()>0){
//            Object[] options = { "OK", "CANCEL" };
//            int risposta = JOptionPane.showOptionDialog(null,
//                    "Attenzione, il nodo selezionato ha dei figli, continuare con la rimozione?", "Rimozione nodo",
//                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
//                    null, options, options[1]);
//            if(risposta == JOptionPane.CLOSED_OPTION || risposta == 1) return;
//            selected.accept(new RemoveChildrenVisitor(this));
//        }
//
//        this.remove(selected.getGraphic());
//        selected.getParent().remove(selected);
//        selected = null;
//
//
//        ridisegna();
//    }


    /**
     * Disegna i nodi sul frame. Il metodo in questione disegna le linee che collegano i nodi nella gerarchia.
     * Il disegno dei nodi stessi è rimandato alla paint della superclasse poichè gli ogetti di classe
     * GraphicNode sono figli del panel; la politica di disegno è definito nella classe GraphicNode.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //TODO cambiare politica di disegnamento a runtime con qualche pattern?
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

    public int getIdAndUpdate() {return idNode++;    }
}
