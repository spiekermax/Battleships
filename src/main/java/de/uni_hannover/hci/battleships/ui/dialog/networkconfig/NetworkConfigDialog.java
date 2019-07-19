package de.uni_hannover.hci.battleships.ui.dialog.networkconfig;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocketType;
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.model.NetworkConfigDialogResponse;
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.model.NetworkConfigDialogResponseType;
import de.uni_hannover.hci.battleships.util.NetworkSocketConnectionValidator;
import de.uni_hannover.hci.battleships.util.resource.R;

// Java
import java.io.IOException;

// JavaFX
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;


public class NetworkConfigDialog extends Dialog<NetworkConfigDialogResponse>
{
    /* COMPONENTS */

    private ComboBox _networkSocketDropdown;

    private final ButtonType _confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
    private final ButtonType _cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    private TextField _ipAdressTextField;
    private TextField _portTextField;


    /* LIFECYCLE */

    /**
     * TODO
     */
    public NetworkConfigDialog()
    {
        super();

        this.setTitle("Party Wizard");
        this.setHeaderText("Host a new game or join an existing one?");
        this.getDialogPane().getButtonTypes().setAll(this.getConfirmButton(), this.getCancelButton());

        this.loadHostView();

        this.setResultConverter(buttonType ->
        {
            if(buttonType == this.getCancelButton()) return new NetworkConfigDialogResponse(NetworkConfigDialogResponseType.ABORT, null, 0, null);

            if(buttonType == this.getConfirmButton())
            {
                switch(this.getNetworkSocketDropdownSelection())
                {
                    case SERVER:
                        if(!NetworkSocketConnectionValidator.validatePort(this.getPortTextFieldNum())) break;
                        return new NetworkConfigDialogResponse(NetworkConfigDialogResponseType.VALID, null, this.getPortTextFieldNum(), NetworkSocketType.SERVER);
                    case CLIENT:
                        if(!NetworkSocketConnectionValidator.validateActiveServer(this.getPortTextFieldNum(), this.getIpAdressTextFieldText())) break;
                        return new NetworkConfigDialogResponse(NetworkConfigDialogResponseType.VALID, this.getIpAdressTextFieldText(), this.getPortTextFieldNum(), NetworkSocketType.CLIENT);
                }
            }

            return new NetworkConfigDialogResponse(NetworkConfigDialogResponseType.INVALID, null, 0, null);
        });
    }

    /* METHODS */

    /**
     * TODO
     */
    private void loadHostView()
    {
        try
        {
            Node dialogContent = FXMLLoader.load( R.layout("dialog/networkconfig_host.fxml") );
            this.getDialogPane().setContent( dialogContent );

            this._networkSocketDropdown = (ComboBox) dialogContent.lookup( R.id("_networkSocketDropdown") );
            this.getNetworkSocketDropdown().getSelectionModel().select(0);

            this._ipAdressTextField = (TextField) dialogContent.lookup( R.id("_ipAdressInputField") );

            this._portTextField = (TextField) dialogContent.lookup( R.id("_portInputField") );
            Platform.runLater(() -> this.getPortTextField().requestFocus());

            this.getNetworkSocketDropdown().valueProperty().addListener((observableValue, oldState, newState) ->
            {
                switch(this.getNetworkSocketDropdownSelection())
                {
                    case SERVER:
                        break;
                    case CLIENT:
                        this.loadJoinView();
                        break;
                }
            });
        }
        catch(IOException e)
        {
            throw new RuntimeException("ERROR: NetworkConfigDialog(): Failed to load main layout!", e);
        }
    }

    /**
     * TODO
     */
    private void loadJoinView()
    {
        try
        {
            Node dialogContent = FXMLLoader.load( R.layout("dialog/networkconfig_join.fxml") );
            this.getDialogPane().setContent( dialogContent );
            this.getDialogPane().getScene().getWindow().sizeToScene();

            this._networkSocketDropdown = (ComboBox) dialogContent.lookup( R.id("_networkSocketDropdown") );
            this.getNetworkSocketDropdown().getSelectionModel().select(1);

            this._ipAdressTextField = (TextField) dialogContent.lookup( R.id("_ipAdressInputField") );
            Platform.runLater(() -> this.getIpAdressTextField().requestFocus());

            this._portTextField = (TextField) dialogContent.lookup( R.id("_portInputField") );

            this.getNetworkSocketDropdown().valueProperty().addListener((observableValue, oldState, newState) ->
            {
                switch(this.getNetworkSocketDropdownSelection())
                {
                    case SERVER:
                        this.loadHostView();
                        break;
                    case CLIENT:
                        break;
                }
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
    private ComboBox getNetworkSocketDropdown()
    {
        return this._networkSocketDropdown;
    }

    /**
     * TODO
     * @return
     */
    private NetworkSocketType getNetworkSocketDropdownSelection()
    {
        switch(this.getNetworkSocketDropdown().getSelectionModel().getSelectedIndex())
        {
            case 0:
                return NetworkSocketType.SERVER;
            case 1:
                return NetworkSocketType.CLIENT;
        }

        return null;
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
    private Integer getPortTextFieldNum()
    {
        try
        {
            return Integer.parseInt( this.getPortTextField().getText() );
        }
        catch(NumberFormatException e)
        {
            return null;
        }
    }
}