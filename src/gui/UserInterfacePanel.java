package gui;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class UserInterfacePanel extends javax.swing.JPanel {
    private final UserInterfaceFrame uif;//serve questo riferimento al frame?
    //private final List<GraphicDiagram> objects = new LinkedList<GraphicDiagram>();

    public UserInterfacePanel(UserInterfaceFrame uif) {
        this.uif = uif;

        setBackground(Color.CYAN);
    }


}
