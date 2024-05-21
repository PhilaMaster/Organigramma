import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TestGrafica2 {
    private static JFrame f;
    private static Box box;
    static JScrollPane  scrollPane;
    static Random rand = new Random();

    public static void main(String[] args) {
        box = new Box(BoxLayout.Y_AXIS);

        JLabel label = new JLabel("Possible Paths and Total Distances");
        label.setForeground(Color.RED);

        for (int i = 0; i < 200; i++) {
            box.add(Box.createRigidArea(new Dimension(0, 2)));// creates space between the components
            box.add(new JLabel(i + " : " + rand.nextInt(10000)));
        }

        scrollPane = new JScrollPane(box);
        Dimension dim = new Dimension(box.getComponent(0).getPreferredSize());
        scrollPane.getVerticalScrollBar().setUnitIncrement(dim.height * 2); // adjusts scrolling speed
        //scrollPane.getViewport().setBackground(Color.WHITE);

        f = new JFrame();
        f.getContentPane().add(label, BorderLayout.NORTH);
        f.getContentPane().add(scrollPane, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(640, 480);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }



//    public static class ScrollablePanelWithNullLayout {
//        public static void main(String[] args) {
//            // Creazione del JFrame principale
//            JFrame frame = new JFrame("Esempio di JScrollPane con Layout Null");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(400, 300); // Dimensione della finestra
//
//            // Creazione del JPanel con layout null
//            JPanel panel = new JPanel();
//            panel.setLayout(null); // Layout null
//
//            // Aggiunta di componenti al JPanel
//            for (int i = 0; i < 30; i++) {
//                JLabel label = new JLabel("Label " + i);
//                label.setBounds(10, i * 30, 200, 25); // Posizionamento manuale dei componenti
//                panel.add(label);
//            }
//
//            // Impostare le dimensioni preferite del pannello per far apparire le scrollbar
//            panel.setPreferredSize(new Dimension(300, 600));
//
//            // Avvolgimento del JPanel in un JScrollPane
//            JScrollPane scrollPane = new JScrollPane(panel);
//
//            // Aggiunta del JScrollPane al JFrame
//            frame.add(scrollPane);
//
//            // Visualizzazione del JFrame
//            frame.setVisible(true);
//        }
//    }
}