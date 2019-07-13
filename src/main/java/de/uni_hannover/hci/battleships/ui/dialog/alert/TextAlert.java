package de.uni_hannover.hci.battleships.ui.dialog.alert;

// JavaFX
import javafx.scene.control.Alert;


public class TextAlert extends Alert
{
    /* LIFECYCLE */

    public TextAlert(String title, String text)
    {
        super(AlertType.INFORMATION);

        this.setTitle(title);
        this.setHeaderText(null);
        this.setContentText(text);

        this.showAndWait();
    }
}