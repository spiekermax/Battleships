package de.uni_hannover.hci.battleships.network.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class NetworkSocketOrientationSwitchReceivedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<NetworkSocketOrientationSwitchReceivedEvent> EVENT_TYPE = new EventType<>("network-socket-orientation-switch-received");


    /* LIFECYCLE */

    /**
     * TODO
     */
    public NetworkSocketOrientationSwitchReceivedEvent()
    {
        super(EVENT_TYPE);
    }
}