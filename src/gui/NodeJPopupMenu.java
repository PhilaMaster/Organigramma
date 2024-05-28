package gui;

import command.node_management.NewNodeCommand;
import command.node_management.RemoveSelectedCommand;
import command.node_management.RenameNodeCommand;
import gui.command.CommandJMenuItem;
import gui.dialogs.ManageEmployeeDialog;
import gui.dialogs.ManageRolesDialog;

import javax.swing.*;
import java.awt.*;

public class NodeJPopupMenu extends JPopupMenu {
    private final UserInterfacePanel panel;
    public NodeJPopupMenu(UserInterfacePanel panel) {
        this.panel=panel;
        itemsSetUp();
    }

    private void itemsSetUp() {
        JMenuItem item;
        add(new CommandJMenuItem("Aggiungi figlio",new NewNodeCommand(panel)));
        add(new CommandJMenuItem("Rimuovi nodo",new RemoveSelectedCommand(panel)));
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
