package de.uni_hannover.hci.battleships.network;

// Java
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client implements NetworkInterface
{
    /* ATTRIBUTES */

    private int _port;
    private boolean _isRunning = false;

    private Socket _client;

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
            this._client = new Socket("localhost", this.getPort());
            this._inputStreamReader = new BufferedReader(new InputStreamReader(this.getClient().getInputStream()));
            this._outputStreamWriter = new BufferedWriter(new OutputStreamWriter(this.getClient().getOutputStream()));
        }
        catch(IOException e)
        {
            // TODO: Besseres ERROR-Handling
            e.printStackTrace();
        }

        this.setIsRunning(true);
        this.runFetchLoop();
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
                    String line = this._inputStreamReader.readLine();

                    if(line != null)
                    {
                        // Fire event here
                        System.out.println("Message from Server: " + line);
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
            this.getClient().close();
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
    private Socket getClient()
    {
        return this._client;
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