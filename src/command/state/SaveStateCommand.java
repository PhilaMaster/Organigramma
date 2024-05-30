package command.state;

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
        //apre dialog di scelta file
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                //salva su file
                out.writeObject(panel.getRoot());
                JOptionPane.showMessageDialog(panel, "Organigramma salvato con successo.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                panel.setModificato(false);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(panel, "C'è stato un errore nel salvataggio.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
