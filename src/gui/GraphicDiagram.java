package gui;

import diagram.CompositeDiagram;
import diagram.Diagram;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GraphicDiagram extends JComponent {
    //costanti relative alla struttura dei diagrammi nella gui
    static final int HEIGHT = 65, WIDTH = HEIGHT*3,
            CHARACTER_LIMIT = 30, BORDER_WIDTH=2,
            VERTICAL_SPACE = HEIGHT*2, VERTICAL_OFFSET = 20,
            HORIZONTAL_SPACE = WIDTH*2, HORIZONTAL_OFFSET = 20;


    private final UserInterfacePanel uip;
    private final Diagram diagram;
    private int x = HORIZONTAL_OFFSET;
    private boolean selected = false;

    public GraphicDiagram(UserInterfacePanel uip, Diagram diagram) {
        this.uip = uip;
        this.diagram = diagram;
        this.setBounds(x, getY(diagram), WIDTH, HEIGHT);
        //this.setLocation(x, getY(diagram));
        //this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //this.setSize(WIDTH, HEIGHT);
    }

    public Diagram getDiagram() {return diagram;}

    void setSelected(boolean selected){
        this.selected = selected;
        uip.setSelected(this);
        repaint();
    }

    private static int getY(Diagram diagram) {
        return diagram.getAltezza() * VERTICAL_SPACE + VERTICAL_OFFSET;
    }

    public void setX(int x) {this.x = x;}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //disegno rettangolo nero (sarà il contorno)
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        //disegno rettangolo più piccolo (sarà l'interno)
        if (!selected)
            g2d.setColor(Color.white);
        else
            g2d.setColor(new Color(18, 188, 255));
        g2d.fillRect(BORDER_WIDTH, BORDER_WIDTH, WIDTH-BORDER_WIDTH*2, HEIGHT-BORDER_WIDTH*2);

        //ridimensiono scritta in modo che entri
        String text = diagram.getNome();
        Font font = g2d.getFont();
        int fontSize = font.getSize();
        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();
        while (textWidth > WIDTH || textHeight > HEIGHT) {
            fontSize--;
            font = new Font(font.getName(), font.getStyle(), fontSize);
            g2d.setFont(font);
            metrics = g2d.getFontMetrics(font);
            textWidth = metrics.stringWidth(text);
            textHeight = metrics.getHeight();
        }

        //disegno il testo al centro del rettangolo
        g2d.setColor(Color.BLACK);

        //FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        textWidth = metrics.stringWidth(text);
        textHeight = metrics.getHeight();

        int x_str =   (WIDTH - textWidth) / 2;
        int y_str =  ((HEIGHT - textHeight) / 2) + metrics.getAscent();

        // Disegna la stringa
        g2d.drawString(text, x_str, y_str);
    }
}