package gui;

import command.node.NewNodeCommand;
import command.node.RemoveSelectedNodeCommand;
import command.node.RenameNodeCommand;
import exceptions.NothingSelectedException;
import exceptions.RootNotRemovableException;
import gui.dialogs.ManageEmployeeDialog;
import gui.dialogs.ManageRolesDialog;

import javax.swing.*;

public class NodeJPopupMenu extends JPopupMenu {
    private final UserInterfacePanel panel;
    public NodeJPopupMenu(UserInterfacePanel panel) {
        this.panel=panel;
        itemsSetUp();
    }

    private void itemsSetUp() {
        add(new CommandJMenuItem("Aggiungi figlio",new NewNodeCommand(panel)));
        JMenuItem item = new JMenuItem("Rimuovi nodo");
        item.addActionListener(e1 -> {
            try {
                new RemoveSelectedNodeCommand(panel).execute();
            } catch (
                    NothingSelectedException e) {
                JOptionPane.showMessageDialog(this, "Selezionare un nodo da rimuovere", "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (
                    RootNotRemovableException e) {
                JOptionPane.showMessageDialog(this, "Impossibile rimuovere il nodo radice", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(item);



        add(new CommandJMenuItem("Rinomina nodo",new RenameNodeCommand(panel.getSelected())));

        addSeparator();

        item = new JMenuItem("Gestisci utenti");
        add(item);
        item.addActionListener(e -> new ManageEmployeeDialog(null,panel.getSelected()).setVisible(true));

        item = new JMenuItem("Gestisci ruoli");
        add(item);
        item.addActionListener(e -> new ManageRolesDialog(null,panel.getSelected()).setVisible(true));
    }
}
