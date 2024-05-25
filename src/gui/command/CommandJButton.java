package gui.command;

import command.Command;

import javax.swing.*;
import java.awt.*;

public class CommandJButton extends JButton {
    public CommandJButton(Command command) {
        addActionListener(e -> command.execute());
    }

    public CommandJButton(String text, Command command) {
        super(text);
        addActionListener(e -> command.execute());
    }
}
