package command.state;

import gui.GraphicNode;
import gui.UserInterfacePanel;
import node.CompositeNode;

public class NewStateCommand extends AbstractStateCommand{

    public NewStateCommand(UserInterfacePanel panel) {
        super(panel);
    }

    @Override
    public void execute() {
        new ClearStateCommand(panel).execute();

        panel.setRoot(new CompositeNode(0,new GraphicNode("Nuovo organigramma")));
        panel.ridisegna();
    }
}
