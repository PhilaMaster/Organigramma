package gui;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;

public class CustomScrollPane extends JScrollPane {
    private static final int VERTICAL_SCROLLING_INTENSITY = 8, HORIZONTAL_SCROLLING_INTENSITY=8;

    public CustomScrollPane(JComponent component) {
        super(component);
        customizeMouseWheelScrolling();
    }

    private void customizeMouseWheelScrolling() {
        this.addMouseWheelListener(e -> {
            if (e.isShiftDown() || e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && e.getPreciseWheelRotation() != 0) {
                JScrollBar horizontalScrollBar = this.getHorizontalScrollBar();
                JScrollBar verticalScrollBar = this.getVerticalScrollBar();

                //Cliccando lo shift si può spostare la orizzontale
                if (e.isShiftDown()) {
                    horizontalScrollBar.setValue(horizontalScrollBar.getValue() + e.getUnitsToScroll()*HORIZONTAL_SCROLLING_INTENSITY);
                } else {
                    verticalScrollBar.setValue(verticalScrollBar.getValue() + e.getUnitsToScroll()*VERTICAL_SCROLLING_INTENSITY);
                }
                e.consume();
            }
        });
    }
}
