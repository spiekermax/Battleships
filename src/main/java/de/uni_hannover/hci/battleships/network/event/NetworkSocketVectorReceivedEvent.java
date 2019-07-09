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

    private final Vector2i _vector;


    /* LIFECYCLE */

    /**
     * TODO
     * @param vector
     */
    public NetworkSocketVectorReceivedEvent(Vector2i vector)
    {
        super(EVENT_TYPE);
        this._vector = vector;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public Vector2i getVector()
    {
        return this._vector;
    }
}