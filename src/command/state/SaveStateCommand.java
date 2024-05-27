package command.state;

import command.Command;
import gui.UserInterfacePanel;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveStateCommand extends AbstractStateCommand {

    public SaveStateCommand(UserInterfacePanel panel) {
        super(panel);
    }

    @Override
    public void execute() {
        // Apri un file chooser per selezionare il file di salvataggio
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                // Salva lo stato dell'applicazione
                out.writeObject(panel.getRoot());
                JOptionPane.showMessageDialog(null, "Organigramma salvato con successo.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();//TODO debug
                JOptionPane.showMessageDialog(null, "C'è stato un errore nel salvataggio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
