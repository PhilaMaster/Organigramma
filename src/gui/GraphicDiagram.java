package gui;

import diagram.CompositeDiagram;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GraphicDiagram extends JComponent {
    private static final int HEIGHT = 65, WIDTH = HEIGHT*3, CHARACTER_LIMIT = 20, VERTICAL_SPACE = 20, VERTICAL_OFFSET = 20, BORDER_WIDTH=2;

    private final UserInterfaceFrame uif;
    private final CompositeDiagram diagram;
    private int x = 0;

    public GraphicDiagram(UserInterfaceFrame uif, CompositeDiagram diagram) {
        this.uif = uif;
        this.diagram = diagram;
        this.setBounds(x, getY(diagram), WIDTH, HEIGHT);
        //this.setLocation(x, getY(diagram));
        //this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.PINK);

    }

    private static int getY(CompositeDiagram diagram) {
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
        g2d.setColor(getBackground());
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