package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInterfaceFrame extends javax.swing.JFrame {

    public static final int WIDTH = 1280, HEIGHT = 720;
    public UserInterfaceFrame() {
        setUp();
        System.out.println("UserInterfaceFrame created");
    }

    private void setUp() {
        //setup generico
        setTitle("Organigramma aziendale");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                salvataggio();
                System.exit(0);
            }
        });

        //inizializzazione della toolbar
        JToolBar toolbar = new JToolBar();

        JButton button1 = new JButton("Nuovo Organigramma");
        JButton button2 = new JButton("Salva Organigramma");

        JButton addChildButton = new JButton("Aggiungi Figlio");
        //addChildButton.setEnabled(false);//finchè non clicco su un nodo non ne posso aggiungere
        JButton removeNodeButton = new JButton("Rimuovi");
        //removeNodeButton.setEnabled(false);//all'inizio non posso rimuovere nodi



        JButton showRolesButton = new JButton("Mostra ruoli");
        showRolesButton.setEnabled(false);
        JButton showUsersButton = new JButton("Mostra utenti");
        showUsersButton.setEnabled(false);





        toolbar.add(addChildButton);
        toolbar.add(removeNodeButton);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(button1);
        toolbar.add(button2);

        this.add(toolbar, BorderLayout.NORTH);

        //inizializzazione panel
        UserInterfacePanel panel = new UserInterfacePanel(this);
        panel.setPreferredSize(new Dimension(GraphicNode.WIDTH, GraphicNode.HEIGHT));

        JScrollPane scrollPane = new CustomScrollPane(panel);
        this.add(scrollPane, BorderLayout.CENTER);

        addChildButton.addActionListener(evt -> panel.newNode());
        removeNodeButton.addActionListener(evt -> {
            System.out.println(panel.getPreferredSize());
            });
    }

    private void salvataggio() {
        System.out.println("Vuoi salvare?");
        //TODO SALVATAGGIO
    }
}
