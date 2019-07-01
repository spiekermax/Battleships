package de.uni_hannover.hci.battleships.network;

// Java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server
{
    /* ATTRIBUTES */

    private int _port;
    private boolean _isRunning = false;

    private ServerSocket _serverSocket;
    private Socket _connectedClient;

    private BufferedReader _inputStreamReader;


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
            this._serverSocket = new ServerSocket(this.getPort());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        this.setIsRunning(true);
        this.acceptConnections();
    }


    /* METHODS */

    /**
     * TODO
     */
    private void acceptConnections()
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                this._connectedClient = this.getServerSocket().accept();
                this._inputStreamReader = new BufferedReader(new InputStreamReader(this.getConnectedClient().getInputStream()));

                this.runFetchLoop();
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
    private void runFetchLoop()
    {
        try
        {
            while(this.isRunning())
            {
                String line = this.getInputStreamReader().readLine();

                if(line != null)
                {
                    // Fire event here
                    System.out.println("Message from Client: " + line);
                }
            }
        }
        catch(IOException e)
        {
            // TODO: Besseres ERROR-Handling
            e.printStackTrace();
        }
    }

    /**
     * TODO
     */
    private void shutdown()
    {
        try
        {
            this.getConnectedClient().close();
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

    public void sendString(String message)
    {
        // Soll den String an verbundenen Client senden.
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
    private ServerSocket getServerSocket()
    {
        return this._serverSocket;
    }

    /**
     * TODO
     * @return
     */
    private Socket getConnectedClient()
    {
        return this._connectedClient;
    }

    /**
     * TODO
     * @return
     */
    private BufferedReader getInputStreamReader()
    {
        return this._inputStreamReader;
    }
}