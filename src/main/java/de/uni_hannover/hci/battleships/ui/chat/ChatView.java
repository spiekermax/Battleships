package de.uni_hannover.hci.battleships.ui.chat;

// Internal dependencies
import de.uni_hannover.hci.battleships.resources.R;
import de.uni_hannover.hci.battleships.ui.chat.events.ChatViewMessageConfirmedEvent;

// Java
import java.io.IOException;

// JavaFX
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;


public class ChatView extends VBox
{
    /* COMPONENTS */

    /**
     * Das Eingabefeld des Chats.
     */
    @FXML
    private TextField _inputField;


    /* LIFECYCLE */

    /**
     * Instanziiert ein Chat-Objekt mit UI.
     */
    public ChatView()
    {
        // Load component layout
        FXMLLoader loader = new FXMLLoader(R.layout("component/chat.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try { loader.load(); }
        catch(IOException e) { throw new RuntimeException("ERROR: Failed to load 'ChatView' layout!", e); }

        // Append callbacks
        this.getInputField().addEventHandler(KeyEvent.KEY_PRESSED, this::onInputFieldKeyPressed);
    }


    /* CALLBACKS */

    /**
     * Wird augerufen, wann immer eine Taste im Eingabefeld gedrückt wird.
     * Löst das 'ChatViewMessageConfirmedEvent' aus, sobald 'Enter' gedrückt wird
     * und leert das Eingabefeld.
     * @param keyEvent Das zum Tastendruck zugehörige Event.
     */
    private void onInputFieldKeyPressed(KeyEvent keyEvent)
    {
        switch(keyEvent.getCode())
        {
            case ENTER:
                this.fireEvent(new ChatViewMessageConfirmedEvent(this.getInputField().getText()));
                this.getInputField().clear();
                break;
            default:
                break;
        }
    }


    /* GETTERS & SETTERS */

    private TextField getInputField()
    {
        return this._inputField;
    }
}