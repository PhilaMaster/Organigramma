package command.state;

import gui.UserInterfacePanel;
import node.Node;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadStateCommand extends AbstractStateCommand {

    public LoadStateCommand(UserInterfacePanel panel) {
        super(panel);
    }

    @Override
    public void execute() {
        new ClearStateCommand(panel).execute();

        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                // Carica lo stato dell'applicazione
                Node root = (Node) in.readObject();
                panel.setRoot(root);
                panel.setSelected(root);
                panel.setModificato(false);
                //JOptionPane.showMessageDialog(null, "Organigramma caricato con successo.",
                //        "Success", JOptionPane.INFORMATION_MESSAGE);
                panel.ridisegna();
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(panel, "Impossibile caricare l'organigramma.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
