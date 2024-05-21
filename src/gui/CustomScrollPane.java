package gui;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class CustomScrollPane extends JScrollPane {
    public static int VERTICAL_SCROLLING_INTENSITY = 8;
    public CustomScrollPane(JComponent component) {
        super(component);
        customizeMouseWheelScrolling();
    }

    private void customizeMouseWheelScrolling() {
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JScrollBar verticalBar = getVerticalScrollBar();
                int scrollAmount = e.getUnitsToScroll() * verticalBar.getUnitIncrement();
                verticalBar.setValue(verticalBar.getValue() + scrollAmount*VERTICAL_SCROLLING_INTENSITY);
            }
        });
    }
}
