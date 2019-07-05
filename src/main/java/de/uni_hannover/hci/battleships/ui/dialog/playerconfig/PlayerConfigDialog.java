package de.uni_hannover.hci.battleships.ui.dialog.playerconfig;

// Internal dependencies
import de.uni_hannover.hci.battleships.ui.dialog.playerconfig.model.PlayerConfigDialogResponse;
import de.uni_hannover.hci.battleships.ui.dialog.playerconfig.model.PlayerConfigDialogResponseType;
import de.uni_hannover.hci.battleships.util.resource.R;

// Java
import java.io.IOException;

// JavaFX
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;


public class PlayerConfigDialog extends Dialog<PlayerConfigDialogResponse>
{
    /* COMPONENTS */

    private final ButtonType _confirmButton = new ButtonType("OK");
    private final ButtonType _cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);


    /* LIFECYCLE */

    public PlayerConfigDialog()
    {
        super();

        this.setTitle("Party Wizard");
        this.setHeaderText("Configure your character.");

        try
        {
            Node dialogContent = FXMLLoader.load( R.layout("dialog/playerconfig.fxml") );
            this.getDialogPane().setContent(dialogContent);
            this.getDialogPane().getButtonTypes().setAll(this.getConfirmButton(), this.getCancelButton());

            this.setResultConverter(buttonType ->
            {
                if(buttonType == this.getConfirmButton())
                    return new PlayerConfigDialogResponse(PlayerConfigDialogResponseType.VALID, "TODO");
                else
                    return new PlayerConfigDialogResponse(PlayerConfigDialogResponseType.ABORT, null);
            });
        }
        catch(IOException e)
        {
            throw new RuntimeException("ERROR: PlayerConfigDialog(): Failed to load main layout!", e);
        }
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    private ButtonType getConfirmButton()
    {
        return this._confirmButton;
    }

    /**
     * TODO
     * @return
     */
    private ButtonType getCancelButton()
    {
        return this._cancelButton;
    }
}