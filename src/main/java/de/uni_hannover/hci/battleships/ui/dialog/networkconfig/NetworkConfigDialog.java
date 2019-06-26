package de.uni_hannover.hci.battleships.ui.dialog.networkconfig;

// Internal dependencies
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.event.NetworkConfigDialogResponseEvent;

// Java
import java.util.Optional;

// JavaFX
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.model.NetworkConfigOption;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;


public class NetworkConfigDialog extends Alert
{
    /* COMPONENTS */

    private ButtonType _hostButton = new ButtonType("Host");
    private ButtonType _joinButton = new ButtonType("Join");
    private ButtonType _cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);


    /* LIFECYCLE */

    /**
     * TODO
     */
    public NetworkConfigDialog()
    {
        super(AlertType.CONFIRMATION);

        this.setTitle("Party Wizard");
        this.setHeaderText("Host a new game or join an existing one?");

        this.getButtonTypes().setAll(this._hostButton, this._joinButton, this._cancelButton);
    }


    /* METHODS */

    /**
     * TODO
     */
    public void present()
    {
        Optional<ButtonType> result = this.showAndWait();
        if(result.get() == this._hostButton)
        {
            this.getEventTarget().fireEvent(new NetworkConfigDialogResponseEvent(NetworkConfigOption.HOST));
        }
        else if(result.get() == this._joinButton)
        {
            this.getEventTarget().fireEvent(new NetworkConfigDialogResponseEvent(NetworkConfigOption.JOIN));
        }
        else
        {
           this.getEventTarget().fireEvent(new NetworkConfigDialogResponseEvent(NetworkConfigOption.EXIT));
        }
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public DialogPane getEventTarget()
    {
        return this.getDialogPane();
    }
}