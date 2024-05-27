package gui;

import command.NewNodeCommand;
import command.RemoveSelectedCommand;
import command.RenameNodeCommand;
import node.Node;

import javax.swing.*;

public class CustomJPopupMenu extends JPopupMenu {
    private final UserInterfacePanel panel;
    public CustomJPopupMenu(UserInterfacePanel panel) {
        this.panel=panel;
        itemsSetUp();
    }

    private void itemsSetUp() {
        JMenuItem item;
        item = new JMenuItem("Aggiungi figlio");
        add(item);
        item.addActionListener(e -> new NewNodeCommand(panel).execute());

        item = new JMenuItem("Rimuovi nodo");
        add(item);
        item.addActionListener(e -> new RemoveSelectedCommand(panel).execute());

        item = new JMenuItem("Rinomina nodo");
        add(item);
        item.addActionListener(e -> new RenameNodeCommand(panel.getSelected()).execute());

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
