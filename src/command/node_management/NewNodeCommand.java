package command.node_management;

import command.Command;
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
    /**
     * Aggiunge un nuovo nodo al panel come figlio del nodo selezionato con un nome di default.
     * Il nome potrà essere modificato in seguito.
     */
    public void execute() {
        Node selected = panel.getSelected();
        if (selected==null) throw new NothingSelectedException();

        String nomeNodo = "Nodo "+panel.getIdAndUpdate();
        GraphicNode gNode = new GraphicNode(nomeNodo);
        Node node = new CompositeNode(selected.getHeight()+1, gNode);

        selected.add(node);
        panel.add(gNode);
        node.setParent(selected);
        gNode.addMouseListener(panel.mouseAdapter);

        panel.setModificato(true);
        panel.ridisegna();
    }
}
