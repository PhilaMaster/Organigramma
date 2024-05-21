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
    private final CompositeDiagram root;
    private final GraphicDiagram graphicRoot;
    private GraphicDiagram selected;
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (Component component : getComponents()) {
                if (component instanceof GraphicDiagram) {//non dovrebbe servire attualmente, in quanto all'interno del panel ci sono soltanto GraphicDiagrams
                    GraphicDiagram diagram = (GraphicDiagram) component;
                    if (diagram == e.getSource()) {
                        diagram.setSelected(true);
                    } else {
                        diagram.setSelected(false);
                    }
                }

            }
        }
    };

    public UserInterfacePanel(UserInterfaceFrame uif) {
        super(null);
        //this.uif = uif;
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

        CompositeDiagram selectedDiagram = selected.getDiagram();
        CompositeDiagram diagram = new CompositeDiagram(selectedDiagram.getAltezza()+1, nome);
        GraphicDiagram d = new GraphicDiagram(this, diagram);

        selectedDiagram.add(diagram);
        this.add(d);
        d.addMouseListener(mouseAdapter);

        d.repaint();
        d.revalidate();
    }

    public void setSelected(GraphicDiagram gd) {
        this.selected = gd;
    }
}
