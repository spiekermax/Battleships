package de.uni_hannover.hci.battleships.ui.dialog.networkconfig.model;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocketType;


public class NetworkConfig
{
    /* ATTRIBUTES */

    private final String _ipAdress;
    private final int _port;
    private final NetworkSocketType _socketType;


    /* LIFECYCLE */

    /**
     * TODO
     * @param ipAdress
     * @param port
     * @param socketType
     */
    public NetworkConfig(String ipAdress, int port, NetworkSocketType socketType)
    {
        this._ipAdress = ipAdress;
        this._port = port;
        this._socketType = socketType;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public String getIpAdress()
    {
        return this._ipAdress;
    }

    /**
     * TODO
     * @return
     */
    public int getPort()
    {
        return this._port;
    }

    /**
     * TODO
     * @return
     */
    public NetworkSocketType getSocketType()
    {
        return this._socketType;
    }
}