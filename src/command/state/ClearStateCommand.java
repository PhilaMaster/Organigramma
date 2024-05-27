package command.state;

import command.Command;
import gui.GraphicNode;
import gui.UserInterfacePanel;
import node.CompositeNode;

public class ClearStateCommand extends AbstractStateCommand {

    public ClearStateCommand(UserInterfacePanel panel) {
        super(panel);
    }

    @Override
    public void execute() {
        panel.removeAll();
    }
}
