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
import javafx.scene.control.TextField;


public class PlayerConfigDialog extends Dialog<PlayerConfigDialogResponse>
{
    /* COMPONENTS */

    private final ButtonType _confirmButton = new ButtonType("OK");
    private final ButtonType _cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    private final TextField _nameInputField;


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

            this._nameInputField = (TextField) dialogContent.lookup( R.id("_nameInputField") );

            this.setResultConverter(buttonType ->
            {
                if(buttonType == this.getConfirmButton())
                {
                    if(this.checkNameInput())
                        return new PlayerConfigDialogResponse(PlayerConfigDialogResponseType.VALID, this.getNameInputField().getText());
                    else
                        return new PlayerConfigDialogResponse(PlayerConfigDialogResponseType.INVALID, this.getNameInputField().getText());
                }
                else
                    return new PlayerConfigDialogResponse(PlayerConfigDialogResponseType.ABORT, null);
            });
        }
        catch(IOException e)
        {
            throw new RuntimeException("ERROR: PlayerConfigDialog(): Failed to load main layout!", e);
        }
    }


    /* METHODS */

    /**
     * TODO
     * @return
     */
    private boolean checkNameInput()
    {
        return !this.getNameInputField().getText().trim().equals("");
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

    /**
     * TODO
     * @return
     */
    private TextField getNameInputField()
    {
        return this._nameInputField;
    }
}