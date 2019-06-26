package de.uni_hannover.hci.battleships;

// Internal dependencies
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.NetworkConfigDialog;
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.event.NetworkConfigDialogResponseEvent;
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
        NetworkConfigDialog networkConfigDialog = new NetworkConfigDialog();
        networkConfigDialog.getEventTarget().addEventHandler(NetworkConfigDialogResponseEvent.EVENT_TYPE, event ->
        {
            switch(event.getConfig())
            {
                case HOST:
                    System.out.println("User möchte hosten");
                    break;
                case JOIN:
                    System.out.println("User möchte joinen");
                    break;
                case EXIT:
                    Platform.exit();
                    System.exit(0);
                default:
                    throw new IllegalArgumentException("ERROR: App.start(): Illegal user response!");
            }
        });
        networkConfigDialog.present();
    }

    public static void main(String[] args)
    {
        App.launch(args);
    }
}