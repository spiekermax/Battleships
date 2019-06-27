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
    InputStream inputStream;

    public Server(int port)
    {
        try {
            this.port = port;

            this.server = new ServerSocket(this.port);
            System.out.print("Server gestartet!\n");
            this.waitForConnection();
            this.readIncomingMessages();
            this.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException
    {
        this.client = this.server.accept();
    }

    private void readIncomingMessages() throws IOException
    {
        boolean runServer = true;

        while(runServer)
        {
            try {
                //this.client = this.server.accept();
                this.inputStream = client.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);
                String s = br.readLine();
                System.out.println("Message from Client " + s);
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