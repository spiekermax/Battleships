package de.uni_hannover.hci.battleships;

// Internal dependencies
import de.uni_hannover.hci.battleships.data.Player;
import de.uni_hannover.hci.battleships.network.NetworkSocket;
import de.uni_hannover.hci.battleships.network.event.NetworkSocketMessageReceivedEvent;
import de.uni_hannover.hci.battleships.network.event.NetworkSocketVectorReceivedEvent;
import de.uni_hannover.hci.battleships.network.socket.Client;
import de.uni_hannover.hci.battleships.network.socket.Server;
import de.uni_hannover.hci.battleships.ui.board.BoardView;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewCellClickedEvent;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewRightClickedEvent;
import de.uni_hannover.hci.battleships.ui.chat.ChatView;
import de.uni_hannover.hci.battleships.ui.chat.event.ChatViewMessageConfirmedEvent;
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.NetworkConfigDialog;
import de.uni_hannover.hci.battleships.ui.dialog.playerconfig.PlayerConfigDialog;
import de.uni_hannover.hci.battleships.util.resource.R;
import de.uni_hannover.hci.battleships.network.event.NetworkSocketNameReceivedEvent;

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

    private Player _user;
    private Player _enemy;

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

        // UI-Komponenten
        ChatView chatView = (ChatView) root.lookup( R.id("chat") );
        BoardView userBoardView = (BoardView) root.lookup( R.id("player_board") );
        BoardView enemyBoardView = (BoardView) root.lookup( R.id("enemy_board") );
        enemyBoardView.setShipsVisible(false);
        enemyBoardView.setIsEnabled(false); // TODO: Optional: if board is disabled fire no events?


        // Ermittle gewünschte Netzwerkkonfiguration
        this.showNetworkConfigDialog();

        // Ermittle gewünschte Charakterkonfiguration
        this.showPlayerConfigDialog();


        // Handle Chat-Eingaben
        chatView.addEventHandler(ChatViewMessageConfirmedEvent.EVENT_TYPE, event ->
        {
            if(event.getMessage().trim().equals("")) return;

            this.getNetworkSocket().sendMessage(event.getMessage());
            chatView.addMessage(this.getUserPlayer(), event.getMessage());
        });


        // Zeige empfangene Chat-Nachrichten
        this.getNetworkSocket().getEventEmitter().addEventHandler(NetworkSocketMessageReceivedEvent.EVENT_TYPE, event ->
        {
            chatView.addMessage(new Player("TODO"), event.getMessage()); // TODO: retrieve enemy player name
        });


        // Handle Board-Clicks
        userBoardView.addEventHandler(BoardViewCellClickedEvent.EVENT_TYPE, event ->
        {
            /*
             * if( this.getUserPlayer().isReady() ) return;
             *
             * if( this.getUserPlayer().getBoard().addShip(event.getCoords(), this.getUserPlayer().getAvailableShips().get(0)) )    //Braucht keinen Parameter availableShip, wird automatisch übergeben
             * {
             *     this.getNetworkSocket().sendVector(event.getCoords());
             *
             *     this.getUserPlayer().removeFirstAvailableShip(); //Löscht sich automatisch beim hinzufügen eines Schiffes
             *     userBoardView.display( this.getUserPlayer().getBoard() );
             * }
             *
             * if( this.getUserPlayer().getAvailableShips().size() == 0 ) // Possible replacement: this.getUserPlayer().hasAvailableShips()
             * {
             *     this.getUserPlayer().setIsReady(true);
             *     userBoardView.setIsEnabled(false);
             *     enemyBoardView.setIsEnabled(true);
             * }
             */
        });
        userBoardView.addEventHandler(BoardViewRightClickedEvent.EVENT_TYPE, event ->
        {
            /*
             * this.getUserPlayer().toggleDefaultShipOrientation();
             */
        });

        enemyBoardView.addEventHandler(BoardViewCellClickedEvent.EVENT_TYPE, event ->
        {
            /*
             * if( !this.getUserPlayer().isReady() || !this.getEnemyPlayer().isReady() ) return;
             * if( !this.getUserPlayer().turn() ) return;
             *
             * this.getEnemyPlayer().hasBeenShot(event.getCoords());
             * enemyBoardView.display(this.getEnemyPlayer().getBoard());
             *
             * this.getNetworkSocket().sendVector(event.getCoords());
             * this.getUserPlayer().switchTurn()); // TODO: Initialwerte für 'hasTheMove' setzen (einer true, einer false)
             * //this.getEnemyPlayer().setHasTheMove(true);
             */
        });

        // Handle empfangene Board-Koordinaten
        this.getNetworkSocket().getEventEmitter().addEventHandler(NetworkSocketVectorReceivedEvent.EVENT_TYPE, event ->
        {
            /*
             * if( !this.getEnemyPlayer().isReady() )
             * {
             *     this.getEnemyPlayer().getBoard().addShip(event.getCoords(), this.getEnemyPlayer().getAvailableShips().get(0)); // Legal-check is redundant
             *     this.getEnemyPlayer().removeFirstAvailableShip(); //Macht von allein(siehe oben)
             *
             *     if ( this.getUserPlayer().getAvailableShips().size() == 0 ) this.getEnemyPlayer().setIsReady(true);
             * }
             * else
             * {
             *     this.getUserPlayer().hasBeenShot(event.getCoords());
             *     userBoardView.display(this.getUserPlayer().getBoard()));
             *
             *     this.getUserPlayer().switchTurn();
             *     this.getEnemyPlayer().switchTurn();
             * }
             */
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

            this._user = new Player(playerConfigResponse.getName());
        });
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    private Player getUserPlayer()
    {
        return this._user;
    }

    /**
     * TODO
     * @return
     */
    private Player getEnemyPlayer()
    {
        return this._enemy;
    }

    /**
     * TODO
     * @return
     */
    private NetworkSocket getNetworkSocket()
    {
        return this._networkSocket;
    }
}