package gui;

import command.node.NewNodeCommand;
import command.node.RenameNodeCommand;
import node.CompositeNode;
import node.Node;
import visitor.design.*;
import visitor.nodes_management.ComponentAdderVisitor;
import visitor.nodes_management.SelectedVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInterfacePanel extends javax.swing.JPanel {
    private static int idNode = 1;

    private DrawerVisitorFactory factory = LineDrawerVisitorFactory.INSTANCE;//factory di default
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

    public void setFactory(DrawerVisitorFactory factory) {this.factory = factory;}

    public Node getSelected(){return selected;}
    public void setSelected(Node selected){
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

    public UserInterfacePanel() {
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


    /**
     * Disegna i nodi sul frame. Il metodo in questione richiama il visitor che disegna le linee che collegano
     * i nodi nella gerarchia seguendo la politica decisa dall'abstract factory <code>DrawerVisitorFactory</code> utilizzato.
     * Il disegno dei nodi stessi è rimandato alla paint della superclasse poichè gli ogetti di classe
     * GraphicNode sono figli del panel; la loro politica di disegno è definito nella classe GraphicNode.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        //Riposiziona i nodi
        PositionerVisitor visitor = new PositionerVisitor();
        root.accept(visitor);

        //Disegna i nodi
        super.paint(g);

        //Disegna i bordi
        Graphics2D g2d = (Graphics2D) g;
        root.accept(factory.createDrawerVisitor(g2d));

        //Verifica eventuale aggiunta di scrollbar al panel (se i figli sono troppi e non entrano)
        setPreferredSize(new Dimension(
                GraphicNode.HORIZONTAL_OFFSET + GraphicNode.WIDTH*2 + (GraphicNode.WIDTH+ GraphicNode.HORIZONTAL_SPACE)* visitor.getWidth(),
                GraphicNode.VERTICAL_OFFSET + GraphicNode.HEIGHT*2 +(GraphicNode.HEIGHT+ GraphicNode.VERTICAL_SPACE)* visitor.getHeight()));
    }

    public int getIdAndUpdate() {return idNode++;}
    public void resetId(){idNode=1;}

    public boolean getModificato(){return modificato;}
    public void setModificato(boolean mod){modificato = mod;}
}
