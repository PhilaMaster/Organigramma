import javax.swing.*;
import java.awt.*;

public class TestGrafica extends JComponent {
    private int rectWidth;
    private int rectHeight;
    private String text;

    // Costruttore per impostare le dimensioni del rettangolo e il testo
    public TestGrafica(int rectWidth, int rectHeight, String text) {
        this.rectWidth = rectWidth;
        this.rectHeight = rectHeight;
        this.text = text;
        setPreferredSize(new Dimension(rectWidth, rectHeight)); // Imposta la dimensione preferita
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna il rettangolo nero
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, rectWidth, rectHeight);

        // Imposta il colore del testo a bianco
        g.setColor(Color.WHITE);

        // Calcola la posizione del testo per centrarlo nel rettangolo
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (rectWidth - metrics.stringWidth(text)) / 2;
        int y = ((rectHeight - metrics.getHeight()) / 2) + metrics.getAscent();

        // Disegna il testo al centro del rettangolo
        g.drawString(text, x, y);
    }

    public static void main(String[] args) {
        // Creazione del JFrame
        JFrame frame = new JFrame("Rettangolo Nero con Testo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Creazione del componente con le dimensioni desiderate e il testo
        TestGrafica rectComponent = new TestGrafica(200, 100, "Testo al Centro");

        // Aggiungere il componente al frame
        frame.add(rectComponent, BorderLayout.CENTER); // Aggiungi il componente al centro del frame

        // Renderlo visibile
        frame.pack(); // Dimensiona il frame secondo le dimensioni preferite del suo contenuto
        frame.setVisible(true);
    }
}