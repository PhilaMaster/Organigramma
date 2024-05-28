package command.node_management;

import command.Command;
import exceptions.NothingSelectedException;
import gui.GraphicNode;
import gui.UserInterfacePanel;
import node.Node;

import javax.swing.*;

public class RenameNodeCommand implements Command {
    private final Node selected;
    public RenameNodeCommand(Node selected) {
        if (selected == null) throw new NothingSelectedException();
        this.selected = selected;
    }

    public void execute(){
        String nome = JOptionPane.showInputDialog(null, "Inserisci il nome del nodo:", "Inserisci nome", JOptionPane.QUESTION_MESSAGE);
        if (nome != null && !nome.isBlank()) {
            if(nome.length() <= GraphicNode.CHARACTER_LIMIT){
                selected.getGraphic().setName(nome);
                ((UserInterfacePanel)selected.getGraphic().getParent()).modificato();
                selected.getGraphic().repaint();
            }else
                JOptionPane.showMessageDialog(null, "La stringa inserita è troppo lunga, il massimo è "+GraphicNode.CHARACTER_LIMIT+"."
                        , "Inserisci nome", JOptionPane.ERROR_MESSAGE);
        }
    }
}
