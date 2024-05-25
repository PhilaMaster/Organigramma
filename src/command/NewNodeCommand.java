package command;

import exceptions.NothingSelectedException;
import gui.GraphicNode;
import gui.UserInterfacePanel;
import node.CompositeNode;
import node.Node;

public class NewNodeCommand implements Command {
    private final UserInterfacePanel panel;

    public NewNodeCommand(UserInterfacePanel panel) {
        this.panel = panel;
    }

    public void execute() {
//        panel.newNode();
        String nomeNodo = "Nodo "+panel.getIdAndUpdate();
        Node selected = panel.getSelected();
        if (selected==null) throw new NothingSelectedException();

        GraphicNode gNode = new GraphicNode(nomeNodo);
        Node node = new CompositeNode(selected.getHeight()+1, gNode);

        selected.add(node);
        panel.add(gNode);
        node.setParent(selected);
        gNode.addMouseListener(panel.mouseAdapter);

        panel.ridisegna();
    }
}
