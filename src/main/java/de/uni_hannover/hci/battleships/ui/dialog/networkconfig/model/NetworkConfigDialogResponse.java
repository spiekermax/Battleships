package de.uni_hannover.hci.battleships.ui.dialog.networkconfig.model;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocketType;


public class NetworkConfigDialogResponse
{
    /* ATTRIBUTES */

    private final NetworkConfigDialogResponseType _type;

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
    public NetworkConfigDialogResponse(NetworkConfigDialogResponseType type, String ipAdress, int port, NetworkSocketType socketType)
    {
        this._type = type;

        this._ipAdress = ipAdress;
        this._port = port;
        this._socketType = socketType;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public boolean isAborted()
    {
        return this.getType() == NetworkConfigDialogResponseType.ABORT;
    }

    /**
     * TODO
     * @return
     */
    public boolean isValid()
    {
        return this.getType() == NetworkConfigDialogResponseType.VALID;
    }

    /**
     * TODO
     * @return
     */
    public NetworkConfigDialogResponseType getType()
    {
        return this._type;
    }

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