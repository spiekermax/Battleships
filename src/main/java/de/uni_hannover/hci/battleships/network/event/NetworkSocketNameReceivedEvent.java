package de.uni_hannover.hci.battleships.network.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class NetworkSocketNameReceivedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<NetworkSocketNameReceivedEvent> EVENT_TYPE = new EventType<>("network-socket-name-received");


    /* ATTRIBUTES */

    private final String _name;


    /* LIFECYCLE */

    /**
     * TODO
     * @param name
     */
    public NetworkSocketNameReceivedEvent(String name)
    {
        super(EVENT_TYPE);
        this._name = name;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public String getName()
    {
        return this._name;
    }
}
