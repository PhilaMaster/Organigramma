package gui;

import diagram.CompositeDiagram;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class UserInterfacePanel extends javax.swing.JPanel {
    private final UserInterfaceFrame uif;//TODO serve questo riferimento al frame?
    private final CompositeDiagram root;
    private final GraphicDiagram graphicRoot;

    public UserInterfacePanel(UserInterfaceFrame uif) {
        super(null);
        this.uif = uif;
        setBackground(Color.GRAY);
        //istanzio nodo radice
        root = new CompositeDiagram(0, uif.getTitle());
        graphicRoot = new GraphicDiagram(uif, root);
        this.add(graphicRoot);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //calcolo posizione nodi con visita, li disegno e disegno anche collegamenti con pattern visitor
        //TODO
        }
}
