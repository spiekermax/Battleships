package de.uni_hannover.hci.battleships.ui.dialog.networkconfig;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocketType;
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.model.NetworkConfig;
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


public class NetworkConfigDialog extends Dialog<NetworkConfig>
{
    /* COMPONENTS */

    private final ButtonType _hostButton = new ButtonType("Host");
    private final ButtonType _joinButton = new ButtonType("Join");
    private final ButtonType _cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    private final TextField _ipAdressTextField;
    private final TextField _portTextField;


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

            this._ipAdressTextField = (TextField) dialogContent.lookup( R.id("_ipAdressInputField") );
            this._portTextField = (TextField) dialogContent.lookup( R.id("_portInputField") );

            this.setResultConverter(buttonType ->
            {
                if(buttonType == this.getHostButton())
                    return new NetworkConfig(true, this.getIpAdressTextFieldText(), this.getPortTextFieldNum(), NetworkSocketType.SERVER);
                else if(buttonType == this.getJoinButton())
                    return new NetworkConfig(true, this.getIpAdressTextFieldText(), this.getPortTextFieldNum(), NetworkSocketType.CLIENT);
                else
                    return new NetworkConfig(false, null, 0, null);
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

    /**
     * TODO
     * @return
     */
    private TextField getIpAdressTextField()
    {
        return this._ipAdressTextField;
    }

    /**
     * TODO
     * @return
     */
    private String getIpAdressTextFieldText()
    {
        return this.getIpAdressTextField().getText();
    }

    /**
     * TODO
     * @return
     */
    private TextField getPortTextField()
    {
        return this._portTextField;
    }

    /**
     * TODO
     * @return
     */
    private int getPortTextFieldNum()
    {
        return Integer.parseInt( this.getPortTextField().getText() );
    }
}