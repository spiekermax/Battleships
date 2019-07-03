package de.uni_hannover.hci.battleships.ui.dialog.networkconfig;

// Internal dependencies
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.model.NetworkConfig;
import de.uni_hannover.hci.battleships.network.NetworkSocketType;
import de.uni_hannover.hci.battleships.util.resource.R;

// Java
import java.io.IOException;

// JavaFX
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;


public class NetworkConfigDialog extends Dialog<NetworkConfig>
{
    /* COMPONENTS */

    private final ButtonType _hostButton = new ButtonType("Host");
    private final ButtonType _joinButton = new ButtonType("Join");
    private final ButtonType _cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);


    /* LIFECYCLE */

    /**
     * TODO
     */
    public NetworkConfigDialog()
    {
        super();

        this.setTitle("Party Wizard");
        this.setHeaderText("Host a new game or join an existing one?");

        try
        {
            Node dialogContent = FXMLLoader.load( R.layout("dialog/networkconfig.fxml") );
            this.getDialogPane().setContent(dialogContent);
            this.getDialogPane().getButtonTypes().setAll(this.getHostButton(), this.getJoinButton(), this.getCancelButton());

            this.setResultConverter(buttonType ->
            {
                if(buttonType == this.getHostButton())
                    return new NetworkConfig("", 123, NetworkSocketType.SERVER);
                else if(buttonType == this.getJoinButton())
                    return new NetworkConfig("", 123, NetworkSocketType.CLIENT);
                else
                    return new NetworkConfig("", 0, NetworkSocketType.INVALID);
            });
        }
        catch(IOException e)
        {
            throw new RuntimeException("ERROR: NetworkConfigDialog(): Failed to load main layout!", e);
        }
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    private ButtonType getHostButton()
    {
        return this._hostButton;
    }

    /**
     * TODO
     * @return
     */
    private ButtonType getJoinButton()
    {
        return this._joinButton;
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