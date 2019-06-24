package de.uni_hannover.hci.battleships.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server
{
    private int port;

    // Wir
    private ServerSocket server;

    // Der andere Typ
    private Socket client;

    // Input stream
    DataInputStream inputStream;

    public Server(int port) throws IOException
    {
        this.port = port;

        this.server = new ServerSocket(this.port);
        this.waitForConnection();

        this.shutdown();
    }

    private void waitForConnection() throws IOException
    {
        this.client = this.server.accept();
    }

    private void readIncomingMessages() throws IOException
    {
        this.inputStream = new DataInputStream(new BufferedInputStream(this.client.getInputStream()));

        boolean runServer = true;
        while(runServer)
        {
            try {
                String in = this.inputStream.readUTF();
                if(in == "shutdown") runServer = false;
                // if(in != null) this.fireEvent(...);
                System.out.println(in);
            } catch (IOException e) { runServer = false; }
        }
        this.shutdown();
    }

    private void shutdown() throws IOException
    {
        this.inputStream.close();
        this.client.close();
        this.server.close();
    }

    public void sendString(String message)
    {
        // Soll den String an verbundenen Client senden.
    }

    public void sendGameState(/* Irgendein Objekt, dass den Spielstand beinhaltet */)
    {
        // Soll den Spielstand an verbundenen Client senden.
    }

    // Soll ein eigenes Event firen, wenn ein neuer String vom verbundenen Client empfangen wird und
    // dem Event diesen String übergeben.

    // Soll ein eigenes Event firen, wenn ein neuer Spielstand vom verbundenen Client empfangen wird und
    // dem Event diesen String übergeben.
}