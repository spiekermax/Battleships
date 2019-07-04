package de.uni_hannover.hci.battleships.network.socket;

// Internal dependencies
import de.uni_hannover.hci.battleships.network.NetworkSocket;

// Java
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client implements NetworkSocket
{
    /* ATTRIBUTES */

    private boolean _isRunning = false;

    private int _port;
    private Socket _socket;

    private BufferedReader _inputStreamReader;
    private BufferedWriter _outputStreamWriter;


    /* LIFECYCLE */

    /**
     * TODO
     * @param port
     */
    public Client(int port)
    {
        this._port = port;

        try
        {
            this._socket = new Socket("localhost", this.getPort());
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
     * @param message
     */
    public void sendMessage(String message)
    {
        try
        {
            this.getOutputStreamWriter().write(message + "\n");
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
                        // Fire event here
                        System.out.println("Message from Server: " + fetchedString);
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