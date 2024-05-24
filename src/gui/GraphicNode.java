package gui;

import node.Node;
import visitor.NodeVisitor;

import javax.swing.*;
import java.awt.*;

public class GraphicNode extends JComponent {
    //costanti relative alla struttura dei diagrammi nella gui
    public static final int HEIGHT = 65, WIDTH = (int) (HEIGHT*2.5),
            CHARACTER_LIMIT = 30, BORDER_WIDTH=2,
            VERTICAL_SPACE = HEIGHT, VERTICAL_OFFSET = 20,//Spazio e offset lasciato tra un nodo e un altro in verticale
            HORIZONTAL_SPACE = WIDTH/3, HORIZONTAL_OFFSET = 20;//Spazio e offset lasciato tra un nodo e un altro in orizzontale

    private final String nome;
    private final UserInterfacePanel uip;
    //private final Node node;
    //private int x = HORIZONTAL_OFFSET;
    private boolean selected = false;

    public GraphicNode(UserInterfacePanel uip, String nome) {
        this.uip = uip;
        this.nome = nome;
//        this.setBounds(x, VERTICAL_OFFSET, WIDTH, HEIGHT);
        //this.setLocation(x, getY(node));
        //this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setSize(WIDTH, HEIGHT);//poichè sarà il visitor a sistemare la posizione, non chiamo il metodo setLocation (o bounds) qui
    }

    //public Node getNode() {return node;}

    public void setSelected(boolean selected){
        this.selected = selected;
//        uip.setSelected(this);TODO potevo commentare davvero sto codice?
        repaint();
    }

//    private static int getY(Node n) {
//        return n.getAltezza() * VERTICAL_SPACE + VERTICAL_OFFSET;
//    }

    //public void setX(int x) {this.x = x;}

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
        Font font = g2d.getFont();
        int fontSize = font.getSize();
        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(nome);
        int textHeight = metrics.getHeight();
        while (textWidth > WIDTH || textHeight > HEIGHT) {
            fontSize--;
            font = new Font(font.getName(), font.getStyle(), fontSize);
            g2d.setFont(font);
            metrics = g2d.getFontMetrics(font);
            textWidth = metrics.stringWidth(nome);
            textHeight = metrics.getHeight();
        }

        //disegno il testo al centro del rettangolo
        g2d.setColor(Color.BLACK);

        //FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        textWidth = metrics.stringWidth(nome);
        textHeight = metrics.getHeight();

        int x_str =   (WIDTH - textWidth) / 2;
        int y_str =  ((HEIGHT - textHeight) / 2) + metrics.getAscent();

        // Disegna la stringa
        g2d.drawString(nome, x_str, y_str);
    }
}