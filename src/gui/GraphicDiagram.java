package gui;

import diagram.Diagram;

import javax.swing.*;
import java.awt.*;

public class GraphicDiagram extends JComponent {
    private static final int HEIGHT = 40, WIDTH = 200, CHARACTER_LIMIT = 20;

    private final UserInterfaceFrame uif;
    private final Diagram diagram;

    public GraphicDiagram(UserInterfaceFrame uif, Diagram diagram) {
        this.uif = uif;
        this.diagram = diagram;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.setColor(Color.white);
    }
}