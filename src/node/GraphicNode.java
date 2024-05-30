package node;

import javax.swing.*;
import java.awt.*;

public class GraphicNode extends JComponent {
    //costanti relative alla struttura dei diagrammi nella gui
    public static final int
            HEIGHT = 66, WIDTH = (int) (HEIGHT*2.5),
            CHARACTER_LIMIT = 27, BORDER_WIDTH=2,
            VERTICAL_SPACE = HEIGHT, VERTICAL_OFFSET = 20,//Spazio e offset lasciato tra un nodo e un altro in verticale
            HORIZONTAL_SPACE = WIDTH/3, HORIZONTAL_OFFSET = 20;//Spazio e offset lasciato tra un nodo e un altro in orizzontale

    //private final String nome;
    private boolean selected = false;

    public GraphicNode(String nome) {
        this.setName(nome);
        this.setSize(WIDTH, HEIGHT);//poichè sarà il visitor a sistemare la posizione, mi basta impostare la dimensione
    }

    public void setSelected(boolean selected){
        this.selected = selected;
        repaint();
    }

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
        //noinspection SuspiciousNameCombination
        g2d.fillRect(BORDER_WIDTH, BORDER_WIDTH, WIDTH-BORDER_WIDTH*2, HEIGHT-BORDER_WIDTH*2);

        //ridimensiono scritta in modo che entri all'interno del rettangolo
        Font font = g2d.getFont();
        font = new Font(font.getFamily(), font.getStyle(), 15);
        g2d.setFont(font);
        int fontSize = font.getSize();
        FontMetrics metrics = g2d.getFontMetrics(font);
        int textWidth = metrics.stringWidth(getName());
        int textHeight = metrics.getHeight();
        while (textWidth > WIDTH-BORDER_WIDTH*5 || textHeight > HEIGHT-BORDER_WIDTH*5) {
            fontSize--;
            font = new Font(font.getName(), font.getStyle(), fontSize);
            g2d.setFont(font);
            metrics = g2d.getFontMetrics(font);
            textWidth = metrics.stringWidth(getName());
            textHeight = metrics.getHeight();
        }

        //disegno il testo al centro del rettangolo
        g2d.setColor(Color.BLACK);
        textWidth = metrics.stringWidth(getName());
        textHeight = metrics.getHeight();
        int x_str =  (WIDTH - textWidth) / 2;
        int y_str = ((HEIGHT - textHeight) / 2) + metrics.getAscent();
        g2d.drawString(getName(), x_str, y_str);
    }
}