package gui;

import command.state.LoadStateCommand;
import command.node.NewNodeCommand;
import command.node.RemoveSelectedNodeCommand;
import command.state.NewStateCommand;
import command.state.SaveStateCommand;
import exceptions.NothingSelectedException;
import exceptions.RootNotRemovableException;
import visitor.design.LineDrawerVisitorFactory;
import visitor.design.NaiveDrawerVisitorFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInterfaceFrame extends javax.swing.JFrame {

    public static final int WIDTH = 1280, HEIGHT = 720;
    private final UserInterfacePanel panel;
    public UserInterfaceFrame() {
        panel = panelSetup();
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
                salvaSeModificatoAnnulla();
            }
        });

        toolbarSetup();
        menuSetup();
    }

    private UserInterfacePanel panelSetup() {
        //inizializzazione panel
        UserInterfacePanel panel = new UserInterfacePanel();
        panel.setPreferredSize(new Dimension(GraphicNode.WIDTH, GraphicNode.HEIGHT));

        JScrollPane scrollPane = new CustomScrollPane(panel);
        this.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void toolbarSetup() {
        //inizializzazione della toolbar
        JToolBar toolbar = new JToolBar();


        JButton addChildButton = new JButton("Aggiungi Figlio");
        addChildButton.addActionListener(evt -> {
            try {
                new NewNodeCommand(panel).execute();
            } catch (NothingSelectedException e) {
                JOptionPane.showMessageDialog(this, "Selezionare un nodo a cui aggiungere un figlio.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        //noinspection ExtractMethodRecommender
        JButton removeNodeButton = new JButton("Rimuovi");
        removeNodeButton.addActionListener(evt -> {
            try {
                new RemoveSelectedNodeCommand(panel).execute();
            } catch (NothingSelectedException e) {
                JOptionPane.showMessageDialog(this, "Selezionare un nodo da rimuovere", "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (RootNotRemovableException e) {
                JOptionPane.showMessageDialog(this, "Impossibile rimuovere il nodo radice", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        ButtonGroup group = new ButtonGroup();
        JRadioButton lineDrawer = new JRadioButton("Organigramma classico");
        lineDrawer.setSelected(true);
        lineDrawer.addActionListener(e -> {
            panel.setFactory(LineDrawerVisitorFactory.INSTANCE);
            panel.repaint();
                });
        group.add(lineDrawer);
        JRadioButton naiveDrawer = new JRadioButton("Dritte");
        naiveDrawer.addActionListener(e -> {
            panel.setFactory(NaiveDrawerVisitorFactory.INSTANCE);
            panel.repaint();
                });
        group.add(naiveDrawer);

        toolbar.add(new JLabel("Quickbar"));
        toolbar.add(addChildButton);
        toolbar.add(removeNodeButton);
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(new JLabel("Seleziona stile linee:"));
        toolbar.add(lineDrawer);
        toolbar.add(naiveDrawer);
        this.add(toolbar, BorderLayout.SOUTH);
    }

    private void menuSetup() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("Nuovo");
        item1.addActionListener(e -> {
            salvaSeModificato();
            new NewStateCommand(panel).execute();
        });
        JMenuItem item2 = new JMenuItem("Apri");
        item2.addActionListener(e -> {
            salvaSeModificato();
            new LoadStateCommand(panel).execute();
        });
        JMenuItem item3 = new CommandJMenuItem("Salva", new SaveStateCommand(panel));
        JMenuItem item4 = new JMenuItem("Chiudi");
        item4.addActionListener(e -> salvaSeModificatoAnnulla());
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menuBar.add(menu);

        menu = new JMenu("Nodo");
        item1 = new JMenuItem("Aggiungi figlio");
        item1.addActionListener(e -> {
            try {
                new NewNodeCommand(panel).execute();
            } catch (NothingSelectedException ex) {
                JOptionPane.showMessageDialog(this, "Selezionare un nodo a cui aggiungere un figlio.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        item2 = new JMenuItem("Rimuovi nodo");
        item2.addActionListener(e -> {
            try {
                new RemoveSelectedNodeCommand(panel).execute();
            } catch (NothingSelectedException exc) {
                JOptionPane.showMessageDialog(this, "Selezionare un nodo da rimuovere", "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (RootNotRemovableException exc) {
                JOptionPane.showMessageDialog(this, "Impossibile rimuovere il nodo radice", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        menu.add(item1);
        menu.add(item2);
        menuBar.add(menu);


        menu = new JMenu("Aiuto");
        item1 = new JMenuItem("Informazioni");
        item1.addActionListener(e -> showInfoDialog());
        menu.add(item1);
        menuBar.add(menu);

        this.setJMenuBar(menuBar);
    }

    private void salvaSeModificato() {
        if(panel.getModificato()){
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Vuoi salvare lo stato corrente?",
                    "Salva Stato",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if(option == JOptionPane.YES_OPTION){
                new SaveStateCommand(panel).execute();
            }
        }
    }

    private void showInfoDialog() {
        JDialog infoDialog = new JDialog(this, "Informazioni di Aiuto", true);
        infoDialog.setSize(WIDTH/3, HEIGHT/3);
        infoDialog.setLocationRelativeTo(null);
        infoDialog.setResizable(false);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel tab1 = new JPanel();
        tab1.add(new JLabel("<html><h3>Generale</h3>" +
                "<p>L'applicazione permette di gestire un organigramma aziendale. " +
                "<br>Non c'è un limite in altezza o larghezza per l'organigramma." +
                "<br>I nodi devono avere un nome con un numero di caratteri minore di "+GraphicNode.CHARACTER_LIMIT+".</p></html>"));
        tabbedPane.addTab("Generale", tab1);

        JPanel tab2 = new JPanel();
        tab2.add(new JLabel(
                "<html>" +
                    "<h3>Scorciatoie</h3>" +
                        "<ol>"+
                            "<li><b>Doppio click</b>, permette di aggiungere un<br> nodo figlio al nodo cliccato.</li>" +
                            "<li><b>Tasto centrale del mouse</b>, permette di rinominare<br> il nodo selezionato.</li>" +
                        "</ol>"+
                    "</html>"));
        tabbedPane.addTab("Scorciatoie", tab2);

        JPanel tab3 = new JPanel();
        tab3.add(new JLabel("<html><h3>About</h3><p>Creato da Pasquale Papalia.</p></html>"));
        tabbedPane.addTab("About", tab3);

        infoDialog.add(tabbedPane);
        infoDialog.setVisible(true);
    }

    private void salvaSeModificatoAnnulla() {
        if (panel.getModificato()) {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Vuoi salvare lo stato corrente prima di uscire?",
                    "Salva Stato",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                //salvo e chiudo
                new SaveStateCommand(panel).execute();
                System.out.println("Salvato");
                System.exit(0);
            } else if (option == JOptionPane.NO_OPTION) {
                //chiudo senza salvare
                System.out.println("Non salvato");
                System.exit(0);
            } else if (option == JOptionPane.CANCEL_OPTION) {
                //annullo chiusura
                System.out.println("Annullata chiusura");
            }
        }
        else System.exit(0);
    }
}
