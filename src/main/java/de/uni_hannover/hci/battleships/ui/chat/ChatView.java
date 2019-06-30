package de.uni_hannover.hci.battleships.ui.chat;

// Internal dependencies
import de.uni_hannover.hci.battleships.data.Player;
import de.uni_hannover.hci.battleships.util.resource.R;
import de.uni_hannover.hci.battleships.ui.chat.event.ChatViewMessageConfirmedEvent;

// Java
import java.io.IOException;

// JavaFX
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;


public class ChatView extends VBox
{
    /* COMPONENTS */

    /**
     * TODO
     */
    @FXML
    private TextArea _messageView;

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


    /* METHODS */

    /**
     * TODO
     * @param source
     * @param message
     */
    public void addMessage(Player source, String message)
    {
        // TODO: Waiting for player.getName()
        this.getMessageView().setText(this.getMessageView().getText() + "Player: " + message + "\n");
        this.getMessageView().setScrollTop(Double.MAX_VALUE);
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    private TextArea getMessageView()
    {
        return this._messageView;
    }

    /**
     * TODO
     * @return
     */
    private TextField getInputField()
    {
        return this._inputField;
    }
}