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
        JButton button1 = new JButton("button1");
        JButton button2 = new JButton("button2");


        button1.addActionListener(evt -> System.out.println("Butt1"));
        button2.addActionListener(evt -> System.out.println("Butt2"));

        toolbar.add(button1);
        toolbar.add(button2);

        this.add(toolbar, BorderLayout.NORTH);

        //inizializzazione panel
        UserInterfacePanel uip = new UserInterfacePanel(this);
        //uip.setPreferredSize(new Dimension(WIDTH,(int) (0.9*HEIGHT)));

        this.add(uip, BorderLayout.CENTER);


    }

    private void salvataggio() {
        System.out.println("Vuoi salvare?");
        //TODO SALVATAGGIO
    }
}
