import gui.UserInterfaceFrame;

import java.awt.*;

public class Applicazione {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new UserInterfaceFrame().setVisible(true));
    }
}
