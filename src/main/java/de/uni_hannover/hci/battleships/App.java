package de.uni_hannover.hci.battleships;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocket;
import de.uni_hannover.hci.battleships.network.socket.Client;
import de.uni_hannover.hci.battleships.network.socket.Server;
import de.uni_hannover.hci.battleships.ui.chat.ChatView;
import de.uni_hannover.hci.battleships.ui.chat.event.ChatViewMessageConfirmedEvent;
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.NetworkConfigDialog;
import de.uni_hannover.hci.battleships.ui.dialog.playerconfig.PlayerConfigDialog;
import de.uni_hannover.hci.battleships.util.resource.R;

// Java
import java.io.IOException;

// JavaFX
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application
{
    /* CONSTANTS */

    private static final String APP_TITLE = "Battleships";
    private static final int DEFAULT_WINDOW_WIDTH = 800;
    private static final int DEFAULT_WINDOW_HEIGHT = 600;
    private static final int MIN_WINDOW_WIDTH = 400;
    private static final int MIN_WINDOW_HEIGHT = 300;


    /* ATTRIBUTES */

    private NetworkSocket _networkSocket;


    /* LIFECYCLE */

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        // Lade, konfiguriere und zeige das Hauptlayout
        Parent root = FXMLLoader.load( R.layout("app.fxml") );

        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(new Scene(root, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        primaryStage.setMinWidth(MIN_WINDOW_WIDTH);
        primaryStage.setMinHeight(MIN_WINDOW_HEIGHT);
        primaryStage.show();

        // Ermittle gewünschte Netzwerkkonfiguration
        this.showNetworkConfigDialog();

        // Ermittle gewünschte Charakterkonfiguration
        this.showPlayerConfigDialog();

        // Handle Chat-Eingaben
        ChatView chatView = (ChatView) root.lookup( R.id("chat") );
        chatView.addEventHandler(ChatViewMessageConfirmedEvent.EVENT_TYPE, event ->
        {
            if(event.getMessage().trim().equals("")) return;

            this.getNetworkSocket().sendMessage(event.getMessage());
            chatView.addMessage(null, event.getMessage());
        });
    }

    public static void main(String[] args)
    {
        App.launch(args);
    }

    /**
     * TODO
     */
    private void terminate()
    {
        Platform.exit();
        System.exit(0);
    }


    /* METHODS */

    /**
     * TODO
     */
    private void showNetworkConfigDialog()
    {
        NetworkConfigDialog networkConfigDialog = new NetworkConfigDialog();
        networkConfigDialog.showAndWait().ifPresent(networkConfigResponse ->
        {
            if(networkConfigResponse.isAborted()) this.terminate();
            if(!networkConfigResponse.isValid())
            {
                // TODO: Error-Signal
                System.out.println("Invalid input!");
                this.showNetworkConfigDialog();
            }

            switch(networkConfigResponse.getSocketType())
            {
                case SERVER:
                    this._networkSocket = new Server(networkConfigResponse.getPort());
                    break;
                case CLIENT:
                    this._networkSocket = new Client(networkConfigResponse.getPort(), networkConfigResponse.getIpAdress());
                    break;
            }
        });
    }

    /**
     * TODO
     */
    private void showPlayerConfigDialog()
    {
        PlayerConfigDialog playerConfigDialog = new PlayerConfigDialog();
        playerConfigDialog.showAndWait().ifPresent(playerConfigResponse ->
        {
            if(playerConfigResponse.isAborted()) this.terminate();
            if(!playerConfigResponse.isValid())
            {
                // TODO: Error-Signal
                System.out.println("Invalid input!");
                this.showPlayerConfigDialog();
            }

            // Create player with name
        });
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    private NetworkSocket getNetworkSocket()
    {
        return this._networkSocket;
    }
}