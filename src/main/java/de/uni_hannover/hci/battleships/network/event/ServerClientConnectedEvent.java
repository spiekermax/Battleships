package de.uni_hannover.hci.battleships.network.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class ServerClientConnectedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<ServerClientConnectedEvent> EVENT_TYPE = new EventType<>("server-client-connected");


    /* LIFECYCLE */

    /**
     * TODO
     */
    public ServerClientConnectedEvent()
    {
        super(EVENT_TYPE);
    }
}