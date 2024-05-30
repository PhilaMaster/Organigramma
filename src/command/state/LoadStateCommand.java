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
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                Node root = (Node) in.readObject();

                //nel caso in cui ho caricato correttamente il file procedo a resettare lo stato
                new ClearStateCommand(panel).execute();

                //carico il nuovo
                panel.setRoot(root);
                panel.setSelected(root);
                panel.setModificato(false);
                panel.repaint();
                panel.revalidate();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(panel, "Impossibile caricare l'organigramma, il file selezionato non è valido o" +
                                "è relativo a una versione precedente dell'applicazione.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException | ClassCastException e) {
                JOptionPane.showMessageDialog(panel, "File selezionato non valido.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
