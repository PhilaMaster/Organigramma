package gui;

import command.NewNodeCommand;
import command.RemoveSelectedCommand;
import command.RenameNodeCommand;
import gui.command.CommandJMenuItem;

import javax.swing.*;

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

        item = new JMenuItem("Visualizza ruoli");
        add(item);
        item.addActionListener(e -> new NewNodeCommand(panel).execute());

        item = new JMenuItem("Aggiungi figlio");
        add(item);
        item.addActionListener(e -> new NewNodeCommand(panel).execute());
        // Aggiungi un sottomenu
        JMenu subMenu = new JMenu("Ruoli");
        JMenuItem subMenuItem1 = new JMenuItem("Aggiungi");
        JMenuItem subMenuItem2 = new JMenuItem("Rimuovi");
        subMenu.add(subMenuItem1);
        subMenu.add(subMenuItem2);
        this.add(subMenu);
    }
}
