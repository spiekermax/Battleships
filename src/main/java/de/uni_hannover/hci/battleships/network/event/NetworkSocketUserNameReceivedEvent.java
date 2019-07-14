package de.uni_hannover.hci.battleships.network.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class NetworkSocketUserNameReceivedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<NetworkSocketUserNameReceivedEvent> EVENT_TYPE = new EventType<>("network-socket-user-name-received");


    /* ATTRIBUTES */

    private final String _userName;


    /* LIFECYCLE */

    /**
     * TODO
     * @param userName
     */
    public NetworkSocketUserNameReceivedEvent(String userName)
    {
        super(EVENT_TYPE);
        this._userName = userName;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public String getUserName()
    {
        return this._userName;
    }
}