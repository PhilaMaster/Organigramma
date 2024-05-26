package command;

import gui.GraphicNode;
import node.Node;

import javax.swing.*;

public class RenameNodeCommand implements Command{
    private final Node selected;
    public RenameNodeCommand(Node selected) {
        this.selected = selected;
    }

    public void execute(){
        String nome = JOptionPane.showInputDialog(null, "Inserisci il nome del nodo:", "Inserisci nome", JOptionPane.QUESTION_MESSAGE);
        if (nome != null && !nome.isBlank()) {
            if(nome.length() <= GraphicNode.CHARACTER_LIMIT){
                selected.getGraphic().setName(nome);
                selected.getGraphic().repaint();
            }else
                JOptionPane.showMessageDialog(null, "La stringa inserita è troppo lunga, il massimo è "+GraphicNode.CHARACTER_LIMIT+"."
                        , "Inserisci nome", JOptionPane.ERROR_MESSAGE);
        }
    }
}
