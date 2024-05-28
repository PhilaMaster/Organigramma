package gui;

import command.node_management.NewNodeCommand;
import command.node_management.RenameNodeCommand;
import node.CompositeNode;
import node.Node;
import visitor.design.LineDrawerVisitor;
import visitor.design.PositionerVisitor;
import visitor.nodes_management.ComponentAdderVisitor;
import visitor.nodes_management.SelectedVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInterfacePanel extends javax.swing.JPanel {
    private static int idNode = 1;
    private int treeHeight = 1, treeWidth = 1;
    private Node root,selected;
    private boolean modificato = false;
    public final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() instanceof GraphicNode g) {
                setSelected(g);
                if (SwingUtilities.isMiddleMouseButton(e)) new RenameNodeCommand(selected).execute();
                if (e.getClickCount() == 2) new NewNodeCommand(UserInterfacePanel.this).execute();
            }
        }

        private void setSelected(GraphicNode g) {
            SelectedVisitor selectedVisitor = new SelectedVisitor(g);
            root.accept(selectedVisitor);
            selected = selectedVisitor.getSelectedNode();
        }


        /**
         * Per una questione di compatibilità questo metodo verrà richiamato sia nel metodo <code>mouseReleased</code> che
         * in <code>mousePressed</code>. Mostra il menù contestuale sul nodo su cui si clicca tasto destro (per Windows)
         */
        private void menuPop(MouseEvent e) {
            new NodeJPopupMenu(UserInterfacePanel.this).show(e.getComponent(), e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()){
                setSelected((GraphicNode) e.getSource());
                menuPop(e);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()){
                setSelected((GraphicNode) e.getSource());
                menuPop(e);
            }
        }
    };

    public Node getSelected(){return selected;}
    public void setSelected(Node selected){
        //TODO Lanciare eccezione qui?
        this.selected = selected;
    }
    public Node getRoot(){return root;}
    public void setRoot(Node root){
        //rimuovo vecchia radice
        this.remove(this.root.getGraphic());
        //assegno la nuova
        this.root=root;

        assert root != null;
        rootSetup(root);
        root.accept(new ComponentAdderVisitor(this));
    }

    public UserInterfacePanel(UserInterfaceFrame uif) {
        super(null);//layoutManager nullo, poichè li gestisco manualmente
        setBackground(Color.GRAY);

        GraphicNode graphicRoot = new GraphicNode("Nuovo organigramma");
        root = new CompositeNode(0, graphicRoot);
        rootSetup(root);
    }

    private void rootSetup(Node root){
        root.setRemovable(false);
        GraphicNode graphicRoot = root.getGraphic();
        this.add(graphicRoot);
        graphicRoot.addMouseListener(mouseAdapter);
    }


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

    @Override
    public void removeAll(){
        super.removeAll();
    }

    public int getIdAndUpdate() {return idNode++;}

    public boolean getModificato(){return modificato;}
    public void modificato(){modificato = true;}
}
