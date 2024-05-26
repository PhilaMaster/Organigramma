package gui;

import command.RenameNodeCommand;
import node.CompositeNode;
import node.Node;
import visitor.design.LineDrawerVisitor;
import visitor.design.PositionerVisitor;
import visitor.nodes_management.SelectedVisitor;

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

                if (e.getClickCount() == 2){new RenameNodeCommand(selected).execute();}
//                    String nome = JOptionPane.showInputDialog(null, "Inserisci il nome del nodo:", "Inserisci nome", JOptionPane.QUESTION_MESSAGE);
//                    if (nome != null && !nome.isBlank()) {
//                        if(nome.length() <= GraphicNode.CHARACTER_LIMIT){
//                            selected.getGraphic().setName(nome);
//                            selected.getGraphic().repaint();
//                        }else
//                            JOptionPane.showMessageDialog(null, "La stringa inserita è troppo lunga, il massimo è "+GraphicNode.CHARACTER_LIMIT+"!"
//                                    , "Inserisci nome", JOptionPane.ERROR_MESSAGE);
//                    }

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

//TODO cambiare politica di disegnamento a runtime con qualche pattern?
    /**
     * Disegna i nodi sul frame. Il metodo in questione disegna le linee che collegano i nodi nella gerarchia.
     * Il disegno dei nodi stessi è rimandato alla paint della superclasse poichè gli ogetti di classe
     * GraphicNode sono figli del panel; la politica di disegno è definito nella classe GraphicNode.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        //Disegna i nodi
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        root.accept(new LineDrawerVisitor(g2d));
        //disegna i bordi
    }

    public int getIdAndUpdate() {return idNode++;}
}
