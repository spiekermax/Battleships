package de.uni_hannover.hci.battleships.network.event;

// Internal dependencies
import de.uni_hannover.hci.battleships.util.Vector2i;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class NetworkSocketVectorReceivedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<NetworkSocketVectorReceivedEvent> EVENT_TYPE = new EventType<>("network-socket-vector-received");


    /* ATTRIBUTES */

    private final Vector2i _coords;


    /* LIFECYCLE */

    /**
     * TODO
     * @param coords
     */
    public NetworkSocketVectorReceivedEvent(Vector2i coords)
    {
        super(EVENT_TYPE);
        this._coords = coords;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public Vector2i getCoords()
    {
        return this._coords;
    }
}