package command;

import exceptions.NothingSelectedException;
import exceptions.RootNotRemovableException;
import gui.UserInterfacePanel;
import node.Node;
import visitor.nodes_management.RemoveChildrenVisitor;

import javax.swing.*;

public class RemoveSelectedCommand implements Command {
    private final UserInterfacePanel panel;

    public RemoveSelectedCommand(UserInterfacePanel panel) {
        this.panel = panel;
    }

    public void execute() {
        Node selected = panel.getSelected();
        if (selected==null) throw new NothingSelectedException();
        if (selected.isRoot()) throw new RootNotRemovableException();

        if (selected.getChildrenCount()>0){
            Object[] options = { "OK", "CANCEL" };
            int risposta = JOptionPane.showOptionDialog(null,
                    "Attenzione, il nodo selezionato ha dei figli, continuare con la rimozione?", "Rimozione nodo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[1]);
            if(risposta == JOptionPane.CLOSED_OPTION || risposta == 1) return;
            selected.accept(new RemoveChildrenVisitor(panel));
        }

        panel.remove(selected.getGraphic());
        selected.getParent().remove(selected);
        panel.setSelected(null);

        panel.modificato();
        panel.ridisegna();
    }
}
