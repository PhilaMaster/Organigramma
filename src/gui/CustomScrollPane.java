package gui;

import javax.swing.*;

public class CustomScrollPane extends JScrollPane {
    private static final int VERTICAL_SCROLLING_INTENSITY = 8, HORIZONTAL_SCROLLING_INTENSITY=8;
    private static final boolean HORIZONTAL_SCROLLING = false;
    public CustomScrollPane(JComponent component) {
        super(component);
        customizeMouseWheelScrolling();
    }

    private void customizeMouseWheelScrolling() {
        this.addMouseWheelListener(e -> {
            JScrollBar verticalBar = getVerticalScrollBar();
            int scrollAmount = e.getUnitsToScroll() * verticalBar.getUnitIncrement();
            verticalBar.setValue(verticalBar.getValue() + scrollAmount*VERTICAL_SCROLLING_INTENSITY);
            JScrollBar horizontalBar = getHorizontalScrollBar();
            if(HORIZONTAL_SCROLLING) {
                scrollAmount = e.getUnitsToScroll() * horizontalBar.getUnitIncrement();
                horizontalBar.setValue(horizontalBar.getValue() + scrollAmount*HORIZONTAL_SCROLLING_INTENSITY);
            }
            else    horizontalBar.setValue(0);
        });
    }
}
