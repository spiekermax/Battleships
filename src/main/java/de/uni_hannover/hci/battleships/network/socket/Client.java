package de.uni_hannover.hci.battleships.network.socket;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocket;
import de.uni_hannover.hci.battleships.network.event.NetworkSocketMessageReceivedEvent;
import de.uni_hannover.hci.battleships.network.event.NetworkSocketVectorReceivedEvent;
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

    private int _port;
    private Socket _socket;
    private BufferedReader _inputStreamReader;
    private BufferedWriter _outputStreamWriter;


    /* LIFECYCLE */

    /**
     * TODO
     * @param port
     */
    public Client(int port, String ipAddress)
    {
        this._port = port;

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
     * TODO
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
     * TODO
     * @param message
     */
    public void sendMessage(String message)
    {
        this.sendString("m: " + message);
    }

    /**
     * TODO
     * @param vector
     */
    public void sendVector(Vector2i vector)
    {
        this.sendString("v: " + vector);
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
                        if(fetchedString.startsWith("m: "))
                        {
                            String fetchedMessage = fetchedString.substring("m: ".length());
                            this.getEventEmitter().fireEvent(new NetworkSocketMessageReceivedEvent( fetchedMessage ));
                        }
                        else if(fetchedString.startsWith("v: "))
                        {
                            Vector2i fetchedVector = Vector2i.fromString( fetchedString.substring("v: ".length()) );
                            this.getEventEmitter().fireEvent(new NetworkSocketVectorReceivedEvent( fetchedVector ));
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
     * Methode die den Port zurückgibt
     * @return
     */
    public int getPort()
    {
        return this._port;
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
}