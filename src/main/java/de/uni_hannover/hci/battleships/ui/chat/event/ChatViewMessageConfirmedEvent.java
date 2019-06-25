package de.uni_hannover.hci.battleships.ui.chat.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class ChatViewMessageConfirmedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<ChatViewMessageConfirmedEvent> EVENT_TYPE = new EventType<>("chat-view-message-confirmed");


    /* ATTRIBUTES */

    private final String _message;


    /* LIFECYCLE */

    /**
     * Erzeugt ein "chat-view-message-confirmed" Event.
     * @param message Die eingegebene Nachricht.
     */
    public ChatViewMessageConfirmedEvent(String message)
    {
        super(EVENT_TYPE);
        this._message = message;
    }


    /* METHODS */

    /**
     * Gibt die eingegebene Nachricht zurück.
     * @return Die eingegebene Nachricht.
     */
    public String getMessage()
    {
        return this._message;
    }
}