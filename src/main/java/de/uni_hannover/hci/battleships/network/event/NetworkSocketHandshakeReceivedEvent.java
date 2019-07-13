package de.uni_hannover.hci.battleships.network.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class NetworkSocketHandshakeReceivedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<NetworkSocketHandshakeReceivedEvent> EVENT_TYPE = new EventType<>("server-client-connected");


    /* LIFECYCLE */

    /**
     * TODO
     */
    public NetworkSocketHandshakeReceivedEvent()
    {
        super(EVENT_TYPE);
    }
}