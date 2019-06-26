package de.uni_hannover.hci.battleships.ui.dialog.networkconfig.event;

// Internal dependencies
import de.uni_hannover.hci.battleships.ui.dialog.networkconfig.model.NetworkConfigOption;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class NetworkConfigDialogResponseEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<NetworkConfigDialogResponseEvent> EVENT_TYPE = new EventType<>("network-config-dialog-response");


    /* ATTRIBUTES */

    private final NetworkConfigOption _config;


    /* LIFECYCLE */

    /**
     * TODO
     * @param config
     */
    public NetworkConfigDialogResponseEvent(NetworkConfigOption config)
    {
        super(EVENT_TYPE);
        this._config = config;
    }


    /* METHODS */

    /**
     * TODO
     * @return
     */
    public NetworkConfigOption getConfig()
    {
        return this._config;
    }
}