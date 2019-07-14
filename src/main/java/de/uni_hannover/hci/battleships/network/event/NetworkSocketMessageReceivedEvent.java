package de.uni_hannover.hci.battleships.network.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class NetworkSocketMessageReceivedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<NetworkSocketMessageReceivedEvent> EVENT_TYPE = new EventType<>("network-socket-message-received");


    /* ATTRIBUTES */

    private final String _message;


    /* LIFECYCLE */

    /**
     * TODO
     * @param message
     */
    public NetworkSocketMessageReceivedEvent(String message)
    {
        super(EVENT_TYPE);
        this._message = message;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public String getMessage()
    {
        return this._message;
    }
}