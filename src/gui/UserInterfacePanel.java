package gui;

import diagram.CompositeDiagram;
import diagram.Diagram;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class UserInterfacePanel extends javax.swing.JPanel {
    //private final UserInterfaceFrame uif;
    private int treeHeight = 1, treeWidth = 1;
    private final CompositeDiagram root;
    private final GraphicDiagram graphicRoot;
    private GraphicDiagram selected;
    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (Component component : getComponents())
                if (component instanceof GraphicDiagram diagram)
                    diagram.setSelected(diagram == e.getSource());//setto true solo quello su cui clicco, false tutti gli altri
            //TODO if doppioclick -> apri menù
        }
    };

    public UserInterfacePanel(UserInterfaceFrame uif) {
        super(null);//layoutManager nullo, quindi manuale
        setBackground(Color.GRAY);

        //istanzio nodo radice
        root = new CompositeDiagram(0, uif.getTitle());
        root.setRemovable(false);
        graphicRoot = new GraphicDiagram(this, root);
        this.add(graphicRoot);
        graphicRoot.addMouseListener(mouseAdapter);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //calcolo posizione nodi con visita, li disegno e disegno anche collegamenti con pattern visitor
        //TODO
    }

    public void addDiagram() {
        //TODO scegli nome con popup
        String nome = "nome esempio";

        Diagram selectedDiagram = selected.getDiagram();
        Diagram diagram = new CompositeDiagram(selectedDiagram.getAltezza()+1, nome);
        GraphicDiagram d = new GraphicDiagram(this, diagram);

        selectedDiagram.add(diagram);
        this.add(d);
        d.addMouseListener(mouseAdapter);

        //verifica possibile aggiunta di scrollbar al panel (se i figli sono troppi e non entrano)
        setPreferredSize(new Dimension(
                GraphicDiagram.HORIZONTAL_OFFSET*5 + (GraphicDiagram.WIDTH+GraphicDiagram.HORIZONTAL_SPACE)*treeWidth,
                GraphicDiagram.VERTICAL_OFFSET*5 + (GraphicDiagram.HEIGHT+GraphicDiagram.VERTICAL_SPACE)*treeHeight));

        //TODO solo per debug, poi bisogna davvero calcolare l'altezza dell'albero
        treeHeight++;

        this.repaint();
        this.revalidate();
    }

    public void setSelected(GraphicDiagram gd) {
        this.selected = gd;
    }
}
