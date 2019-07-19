package de.uni_hannover.hci.battleships.ui.board.cell;

// JavaFX
import javafx.scene.paint.Color;


public class BoardViewCellColor
{
    public static final Color WATER = new Color(0.27, 0.51, 0.7, 1);
    public static final Color SHIP = new Color(0.5, 0.5, 0.5, 1);
    public static final Color HIT = new Color(0.8, 0, 0, 1);
    public static final Color MISS = new Color(0.16, 0.3, 0.42, 1);
    public static final Color GHOST_SHIP = WATER.deriveColor(0.0, 0.7, 0.9, 1.0);
}