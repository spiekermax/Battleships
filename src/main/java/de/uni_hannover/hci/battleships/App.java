package de.uni_hannover.hci.battleships;

// Internal dependencies
import de.uni_hannover.hci.battleships.data.Board;
import de.uni_hannover.hci.battleships.data.BoardCell;
import de.uni_hannover.hci.battleships.data.Player;
import de.uni_hannover.hci.battleships.network.NetworkSocket;
import de.uni_hannover.hci.battleships.network.event.*;
import de.uni_hannover.hci.battleships.network.socket.Client;
import de.uni_hannover.hci.battleships.network.socket.Server;
import de.uni_hannover.hci.battleships.ui.board.BoardView;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewCellClickedEvent;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewCellHoveredEvent;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewMouseExitedEvent;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewRightClickedEvent;
import de.uni_hannover.hci.battleships.ui.chat.ChatView;
import de.uni_hannover.hci.battleships.ui.chat.event.ChatViewMessageConfirmedEvent;
import de.uni_hannover.hci.battleships.ui.dialog.alert.TextAlert;
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
import javafx.scene.text.Text;
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
    private Server _server;
    private Client _client;


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
        Text connectionInfoText = (Text) root.lookup( R.id("connection_info_text") );
        ChatView chatView = (ChatView) root.lookup( R.id("chat") );
        BoardView userBoardView = (BoardView) root.lookup( R.id("player_board") );

        BoardView enemyBoardView = (BoardView) root.lookup( R.id("enemy_board") );
        enemyBoardView.setShipsVisible(false);
        enemyBoardView.setIsEnabled(false);


        // Ermittle gewünschte Netzwerkkonfiguration
        this.showNetworkConfigDialog();

        // Gebe IP-Adresse und Port an
        switch(this.getNetworkSocket().getType())
        {
            case CLIENT:
                this._client = (Client) this.getNetworkSocket();
                connectionInfoText.setText("IP: " + this.getClient().getServerIpAdress() + " Port: " + this.getClient().getPort());
                break;
            case SERVER:
                this._server = (Server) this.getNetworkSocket();
                connectionInfoText.setText("IP: " + this.getServer().getIpAdressString() + " Port: " + this.getServer().getPort());

                userBoardView.setIsEnabled(false);
                break;
        }


        // Ermittle gewünschte Charakterkonfiguration
        this.showPlayerConfigDialog();

        // Empfange User-Konfiguration des Gegners
        this.getNetworkSocket().getEventEmitter().addEventHandler(NetworkSocketUserNameReceivedEvent.EVENT_TYPE, event ->
        {
            if(this.getEnemyPlayer() != null) throw new IllegalStateException("ERROR: App.start(): Player name has already been set!");

            switch(this.getNetworkSocket().getType())
            {
                case CLIENT:
                    this._enemy = new Player(event.getUserName(), true);
                    break;
                case SERVER:
                    this._enemy = new Player(event.getUserName(), false);
                    break;
            }
        });


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
            chatView.addMessage(this.getEnemyPlayer(), event.getMessage());
        });


        // Handle Board-Mausinteraktionen
        userBoardView.addEventHandler(BoardViewCellHoveredEvent.EVENT_TYPE, event ->
        {
            if(!userBoardView.isEnabled() || this.getUserPlayer().isReady()) return;
            if(!Board.isInBounds(event.getCoords())) return;

            Board ghostBoard = new Board(this.getUserPlayer().getBoard());
            if( ghostBoard.addShip(event.getCoords(), BoardCell.GHOST_SHIP, this.getUserPlayer().getFirstAvailableShip(), this.getUserPlayer().getShipOrientation()) )
            {
                userBoardView.display(ghostBoard);
            }
            else
            {
                userBoardView.display(this.getUserPlayer().getBoard());
            }
        });
        userBoardView.addEventHandler(BoardViewMouseExitedEvent.EVENT_TYPE, event -> userBoardView.display(this.getUserPlayer().getBoard()));

        userBoardView.addEventHandler(BoardViewCellClickedEvent.EVENT_TYPE, event ->
        {
            if(!userBoardView.isEnabled())
            {
                new TextAlert("Info", "Bitte warte, bis sich dein Gegner verbindet!");
                return;
            }
            if(this.getUserPlayer().isReady()) return;

            if( this.getUserPlayer().getBoard().addShip(event.getCoords(), this.getUserPlayer().getFirstAvailableShip(), this.getUserPlayer().getShipOrientation()) )
            {
                this.getNetworkSocket().sendVector(event.getCoords());
                this.getUserPlayer().removeFirstAvailableShip();

                userBoardView.display(this.getUserPlayer().getBoard());
            }

            if(!this.getUserPlayer().hasAvailableShips())
            {
                this.getUserPlayer().setIsReady(true);
                userBoardView.setIsEnabled(false);
                enemyBoardView.setIsEnabled(true);
            }
        });
        userBoardView.addEventHandler(BoardViewRightClickedEvent.EVENT_TYPE, event ->
        {
            this.getUserPlayer().toggleShipOrientation();
            this.getNetworkSocket().sendOrientationSwitch();

            // Update Ghostship
            userBoardView.fireEvent(new BoardViewCellHoveredEvent(event.getCoords()));
        });

        enemyBoardView.addEventHandler(BoardViewCellClickedEvent.EVENT_TYPE, event ->
        {
            if(!this.getUserPlayer().isReady() || !this.getEnemyPlayer().isReady())
            {
                new TextAlert("Info", "Ein User hat noch nicht all seine Schiffe platziert!");
                return;
            }
            if(!this.getUserPlayer().hasTurn())
            {
                new TextAlert("Info", "Du bist gerade nicht am Zug!");
                return;
            }

            if(!this.getEnemyPlayer().getBoard().shoot(event.getCoords()))
            {
                this.getUserPlayer().setHasTurn(false);
                this.getEnemyPlayer().setHasTurn(true);
            }
            this.getNetworkSocket().sendVector(event.getCoords());
            enemyBoardView.display(this.getEnemyPlayer().getBoard());

            // Überprüfe, ob das Spiel vorbei ist
            if(this.getEnemyPlayer().getBoard().areAllShipsHit())
            {
                new TextAlert("Sieg", "Du hast gegen " + this.getEnemyPlayer().getName() + " gewonnen!");
                userBoardView.setIsEnabled(false);
                enemyBoardView.setIsEnabled(false);
            }
        });

        // Handle empfangene Board-Koordinaten
        this.getNetworkSocket().getEventEmitter().addEventHandler(NetworkSocketVectorReceivedEvent.EVENT_TYPE, event ->
        {
            if(!this.getEnemyPlayer().isReady())
            {
                this.getEnemyPlayer().getBoard().addShip(event.getCoords(), this.getEnemyPlayer().getFirstAvailableShip(), this.getEnemyPlayer().getShipOrientation());
                this.getEnemyPlayer().removeFirstAvailableShip();

                if(!this.getEnemyPlayer().hasAvailableShips()) this.getEnemyPlayer().setIsReady(true);
            }
            else
            {
                if(!this.getUserPlayer().getBoard().shoot(event.getCoords()))
                {
                    this.getUserPlayer().setHasTurn(true);
                    this.getEnemyPlayer().setHasTurn(false);
                }
                // Überprüfe, ob das Spiel vorbei ist
                else if(this.getUserPlayer().getBoard().areAllShipsHit())
                {
                    // Spawn from FX-Thread
                    Platform.runLater(() -> new TextAlert("Niederlage", "Du hast gegen " + this.getEnemyPlayer().getName() + " verloren!"));
                    userBoardView.setIsEnabled(false);
                    enemyBoardView.setIsEnabled(false);
                }
                userBoardView.display(this.getUserPlayer().getBoard());
            }
        });
        this.getNetworkSocket().getEventEmitter().addEventHandler(NetworkSocketOrientationSwitchReceivedEvent.EVENT_TYPE, event -> this.getEnemyPlayer().toggleShipOrientation());


        // Schalte das Spielbrett frei, sobald ein User gejoint ist
        this.getNetworkSocket().getEventEmitter().addEventHandler(NetworkSocketHandshakeReceivedEvent.EVENT_TYPE, event -> userBoardView.setIsEnabled(true));


        if(this.getClient() != null) this.getClient().sendHandshake();
    }

    public static void main(String[] args)
    {
        App.launch(args);
    }

    /**
     * TODO
     */
    public static void terminate()
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
            if(networkConfigResponse.isAborted()) App.terminate();
            if(!networkConfigResponse.isValid())
            {
                new TextAlert("ERROR", "Ungültige Eingabe!");
                this.showNetworkConfigDialog();
                return;
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
            if(playerConfigResponse.isAborted()) App.terminate();
            if(!playerConfigResponse.isValid())
            {
                new TextAlert("ERROR", "Ungültige Eingabe!");
                this.showPlayerConfigDialog();
                return;
            }

            switch(this.getNetworkSocket().getType())
            {
                case CLIENT:
                    this._user = new Player(playerConfigResponse.getName(), false);

                    this.getNetworkSocket().sendUserName(playerConfigResponse.getName());
                    break;
                case SERVER:
                    this._user = new Player(playerConfigResponse.getName(), true);

                    this.getNetworkSocket().getEventEmitter().addEventHandler(NetworkSocketHandshakeReceivedEvent.EVENT_TYPE, event ->
                            this.getNetworkSocket().sendUserName(playerConfigResponse.getName()));
                    break;
            }
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

    /**
     * TODO
     * @return
     */
    private Server getServer()
    {
        return this._server;
    }

    /**
     * TODO
     * @return
     */
    private Client getClient()
    {
        return this._client;
    }
}