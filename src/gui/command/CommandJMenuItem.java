package gui.command;

import command.Command;

import javax.swing.*;

public class CommandJMenuItem extends JMenuItem {
    public CommandJMenuItem(String nome, Command command) {
        super(nome);
        addActionListener(e -> command.execute());
    }
}
