package gui;

import command.NewNodeCommand;
import command.RemoveSelectedCommand;
import exceptions.NothingSelectedException;
import exceptions.RootNotRemovableException;
import gui.command.CommandJButton;
import gui.command.CommandJMenuItem;

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

        UserInterfacePanel panel = panelSetup();
        toolbarSetup(panel);
        menuSetup(panel);
    }

    private UserInterfacePanel panelSetup() {
        //inizializzazione panel
        UserInterfacePanel panel = new UserInterfacePanel(this);
        panel.setPreferredSize(new Dimension(GraphicNode.WIDTH, GraphicNode.HEIGHT));

        JScrollPane scrollPane = new CustomScrollPane(panel);
        this.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void toolbarSetup(UserInterfacePanel panel) {
        //inizializzazione della toolbar
        JToolBar toolbar = new JToolBar();


        JButton addChildButton = new CommandJButton("Aggiungi Figlio", new NewNodeCommand(panel));
        JButton removeNodeButton = new JButton("Rimuovi");
        toolbar.add(addChildButton);
        toolbar.add(removeNodeButton);
        this.add(toolbar, BorderLayout.NORTH);
        //aggiunta dei listeners ai bottoni
        removeNodeButton.addActionListener(evt -> {
            try {
//                panel.removeSelectedNode();
                new RemoveSelectedCommand(panel).execute();
            } catch (NothingSelectedException e) {
                JOptionPane.showMessageDialog(null, "Selezionare un nodo da rimuovere", "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (RootNotRemovableException e) {
                JOptionPane.showMessageDialog(null, "Impossibile rimuovere il nodo radice", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void menuSetup(UserInterfacePanel panel) {
        JMenuBar menuBar = new JMenuBar();


        JMenu menu = new JMenu("File");
        JMenuItem item1 = new CommandJMenuItem("Nuovo",null);
        JMenuItem item2 = new CommandJMenuItem("Apri", null);
        JMenuItem item3 = new CommandJMenuItem("Salva", null);
        JMenuItem item4 = new CommandJMenuItem("Chiudi", null);
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menuBar.add(menu);

        menu = new JMenu("Nodo");
        item1 = new CommandJMenuItem("Aggiungi figlio",new NewNodeCommand(panel));
        item2 = new CommandJMenuItem("Rimuovi nodo", null);
        menu.add(item1);
        menu.add(item2);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }


    private void salvataggio() {
        System.out.println("Vuoi salvare?");
        //TODO SALVATAGGIO
    }
}
