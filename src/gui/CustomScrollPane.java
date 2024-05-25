package gui;

import javax.swing.*;

public class CustomScrollPane extends JScrollPane {
    public static final int VERTICAL_SCROLLING_INTENSITY = 8, HORIZONTAL_SCROLLING_INTENSITY=8;
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
            scrollAmount = e.getUnitsToScroll() * horizontalBar.getUnitIncrement();
            horizontalBar.setValue(horizontalBar.getValue() + scrollAmount*HORIZONTAL_SCROLLING_INTENSITY);
        });
    }
}
