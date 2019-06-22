package de.uni_hannover.hci.battleships.util;

// JavaFX
import javafx.scene.paint.Color;


public class ColorCodeConverter
{
    /* FUNCTIONS */

    /**
     * TODO
     * @param color
     * @return
     */
    public static String parseHex(Color color)
    {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed()   * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue()  * 255)
        );
    }
}