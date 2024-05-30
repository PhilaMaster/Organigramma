package command.state;

import gui.UserInterfacePanel;

public class ClearStateCommand extends AbstractStateCommand {
    public ClearStateCommand(UserInterfacePanel panel) {
        super(panel);
    }

    /**
     *  Cancella lo stato del panel. Attenzione: lascia lo stato in uno stato inconsistente, questo Command è infatti solo
     *  di supporto ad altri, non andrebbe utilizzato da solo.
     */
    @Override
    public void execute() {
        panel.resetId();
        panel.removeAll();
    }
}
