package command.state;

import gui.UserInterfacePanel;

public class ClearStateCommand extends AbstractStateCommand {

    public ClearStateCommand(UserInterfacePanel panel) {
        super(panel);
    }

    @Override
    public void execute() {
        panel.removeAll();
    }
}
