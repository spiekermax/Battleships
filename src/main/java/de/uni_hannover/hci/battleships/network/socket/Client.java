package de.uni_hannover.hci.battleships.network.socket;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocket;
import de.uni_hannover.hci.battleships.network.NetworkSocketType;
import de.uni_hannover.hci.battleships.network.event.*;
import de.uni_hannover.hci.battleships.util.Vector2i;

// JavaFX
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

// Java
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client implements NetworkSocket
{
    /* ATTRIBUTES */

    private boolean _isRunning = false;

    private final AnchorPane _eventEmitter = new AnchorPane();

    private final int _port;
    private final String _serverIpAdress;
    private Socket _socket;
    private BufferedReader _inputStreamReader;
    private BufferedWriter _outputStreamWriter;

    private boolean _isServerReady = false;


    /* LIFECYCLE */

    /**
     * Der Client wird mit einem angegebenen Port und IP-Adresse erstellt
     * @param port Die Portnummer
     * @param ipAddress Die IP-Adresse
     */
    public Client(int port, String ipAddress)
    {
        this._port = port;
        this._serverIpAdress = ipAddress;

        try
        {
            this._socket = new Socket(ipAddress, this.getPort());
            this._inputStreamReader = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
            this._outputStreamWriter = new BufferedWriter(new OutputStreamWriter(this.getSocket().getOutputStream()));

            this.setIsRunning(true);
            this.runFetchLoop();
        }
        catch(IOException e)
        {
            // TODO: Besseres ERROR-Handling
            e.printStackTrace();
        }
    }


    /* METHODS */

    /**
     * Der Handshake wird benötigt um zu signalisieren, dass beide Spieler bereit sind
     */
    public void sendHandshake()
    {
        this.sendString("handshake");
    }

    /**
     * Methode zum Versenden von Nachrichten
     * @param string
     */
    private void sendString(String string)
    {
        try
        {
            this.getOutputStreamWriter().write(string + "\n");
            this.getOutputStreamWriter().flush();
        }
        catch(IOException e)
        {
            // TODO: Besseres ERROR-Handling
            e.printStackTrace();
        }
    }

    /**
     * Eine Nachricht wird als String an den Server versendet
     * @param message
     */
    public void sendMessage(String message)
    {
        this.sendString("m: " + message);
    }

    /**
     * Die Vektorkoordinaten werden an den Server geschickt
     * @param vector
     */
    public void sendVector(Vector2i vector)
    {
        this.sendString("v: " + vector);
    }

    /**
     * Der Nutzername des Spielers wird an den Server versendet
     * @param userName
     */
    public void sendUserName(String userName)
    {
        this.sendString("u: " + userName);
    }

    /**
     * Sendet die Orientierung der Schiffe an den Server
     */
    public void sendOrientationSwitch()
    {
        this.sendString("os");
    }

    /**
     * TODO
     */
    private void runFetchLoop()
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                while(this.isRunning())
                {
                    String fetchedString = this.getInputStreamReader().readLine();

                    if(fetchedString != null)
                    {
                        if(fetchedString.equals("handshake"))
                        {
                            this._isServerReady = true;
                            this.getEventEmitter().fireEvent(new NetworkSocketHandshakeReceivedEvent());
                        }
                        else if(fetchedString.equals("os"))
                        {
                            this.getEventEmitter().fireEvent(new NetworkSocketOrientationSwitchReceivedEvent());
                        }
                        else if(fetchedString.startsWith("m: "))
                        {
                            String fetchedMessage = fetchedString.substring("m: ".length());
                            this.getEventEmitter().fireEvent(new NetworkSocketMessageReceivedEvent( fetchedMessage ));
                        }
                        else if(fetchedString.startsWith("v: "))
                        {
                            Vector2i fetchedVector = Vector2i.fromString( fetchedString.substring("v: ".length()) );
                            this.getEventEmitter().fireEvent(new NetworkSocketVectorReceivedEvent( fetchedVector ));
                        }
                        else if(fetchedString.startsWith("u: "))
                        {
                            String fetchedName = fetchedString.substring(("u: ".length()));
                            this.getEventEmitter().fireEvent(new NetworkSocketUserNameReceivedEvent( fetchedName ));
                        }
                    }
                }
            }
            catch(IOException e)
            {
                // TODO: Besseres ERROR-Handling
                e.printStackTrace();
            }
            finally
            {
                this.shutdown();
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    /**
     * TODO
     */
    private void shutdown()
    {
        try
        {
            this.getSocket().close();
        }
        catch(IOException e)
        {
            // TODO: Besseres ERROR-Handling
            e.printStackTrace();
        }
        finally
        {
            this.setIsRunning(false);
        }
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public NetworkSocketType getType()
    {
        return NetworkSocketType.CLIENT;
    }

    /**
     * Methode die den Port zurückgibt
     * @return
     */
    public int getPort()
    {
        return this._port;
    }

    /**
     * Methode die die IP-Adresse zurückgibt
     * @return
     */
    public String getServerIpAdress()
    {
        return this._serverIpAdress;
    }

    /**
     * TODO
     * @return
     * @throws UnknownHostException
     */
    public InetAddress getIpAdress() throws UnknownHostException
    {
        return InetAddress.getLocalHost();
    }

    /**
     * TODO
     * @return
     * @throws UnknownHostException
     */
    public String getIpAdressString() throws UnknownHostException
    {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * TODO
     * @return
     */
    public boolean isRunning()
    {
        return this._isRunning;
    }

    /**
     * TODO
     * @param newIsRunning
     */
    private void setIsRunning(boolean newIsRunning)
    {
        this._isRunning = newIsRunning;
    }

    /**
     * TODO
     * @return
     */
    public Node getEventEmitter()
    {
        return this._eventEmitter;
    }

    /**
     * TODO
     * @return
     */
    private Socket getSocket()
    {
        return this._socket;
    }

    /**
     * TODO
     * @return
     */
    private BufferedReader getInputStreamReader()
    {
        return this._inputStreamReader;
    }

    /**
     * TODO
     * @return
     */
    private BufferedWriter getOutputStreamWriter()
    {
        return this._outputStreamWriter;
    }

    /**
     * TODO
     * @return
     */
    public boolean isServerReady()
    {
        return this._isServerReady;
    }
}