package de.uni_hannover.hci.battleships.network.socket;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocket;
import de.uni_hannover.hci.battleships.network.event.NetworkSocketMessageReceivedEvent;
import de.uni_hannover.hci.battleships.network.event.NetworkSocketNameReceivedEvent;
import de.uni_hannover.hci.battleships.network.event.NetworkSocketVectorReceivedEvent;
import de.uni_hannover.hci.battleships.util.Vector2i;

// JavaFX
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

// Java
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server implements NetworkSocket
{
    /* ATTRIBUTES */

    private boolean _isRunning = false;

    private final AnchorPane _eventEmitter = new AnchorPane();

    private int _port;
    private ServerSocket _serverSocket;
    private Socket _socket;
    private BufferedReader _inputStreamReader;
    private BufferedWriter _outputStreamWriter;


    /* LIFECYCLE */

    /**
     * TODO
     * @param port
     */
    public Server(int port)
    {
        this._port = port;

        try
        {
            this._serverSocket = new ServerSocket(this.getPort(), 1, this.getIpAdress());
            System.out.println(this.getIpAdress());

            this.setIsRunning(true);
            this.acceptConnections();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    /* METHODS */

    /**
     * Methode zum Versenden von Nachrichten
     * @param string
     */
    private void sendString(String string)
    {
        if(this.getOutputStreamWriter() == null)
        {
            System.out.println("INFO: Server(): No client connected!");
            return;
        }

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

    public void sendName(String name) {
        this.sendString("u: " + name);
    }

    /**
     * TODO
     */
    private void acceptConnections()
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                this._socket = this.getServerSocket().accept();
                this._inputStreamReader = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
                this._outputStreamWriter = new BufferedWriter(new OutputStreamWriter(this.getSocket().getOutputStream()));

                this.runFetchLoop();
            }
            catch(IOException e)
            {
                // TODO: Besseres ERROR-Handling
                e.printStackTrace();
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    /**
     * TODO
     */
    private void runFetchLoop()
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
                        System.out.print(fetchedString);
                        Vector2i fetchedVector = Vector2i.fromString( fetchedString.substring("v: ".length()) );
                        this.getEventEmitter().fireEvent(new NetworkSocketVectorReceivedEvent( fetchedVector ));
                    }
                    else if(fetchedString.startsWith("u: "))
                    {
                        String fetchedName = fetchedString.substring("u: ".length());
                        this.getEventEmitter().fireEvent(new NetworkSocketNameReceivedEvent( fetchedName));
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
    }

    /**
     * TODO
     */
    private void shutdown()
    {
        try
        {
            this.getSocket().close();
            this.getServerSocket().close();
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

    public void sendGameState(/* Irgendein Objekt, dass den Spielstand beinhaltet */)
    {
        // Soll den Spielstand an verbundenen Client senden.
    }


    /* GETTERS & SETTERS */

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
    private ServerSocket getServerSocket()
    {
        return this._serverSocket;
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