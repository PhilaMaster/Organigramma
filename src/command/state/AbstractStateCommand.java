package command.state;

import command.Command;
import gui.UserInterfacePanel;

public abstract class AbstractStateCommand implements Command {

    protected final UserInterfacePanel panel;
    public AbstractStateCommand(UserInterfacePanel panel) {
        this.panel = panel;
    }
}
