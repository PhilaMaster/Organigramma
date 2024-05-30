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
        //reinizializzo lo stato del panel
        new ClearStateCommand(panel).execute();

        //imposto una nuova radice e aggiorno il disegno
        panel.setRoot(new CompositeNode(0,new GraphicNode("Nuovo organigramma")));
        panel.repaint();
        panel.revalidate();
    }
}
