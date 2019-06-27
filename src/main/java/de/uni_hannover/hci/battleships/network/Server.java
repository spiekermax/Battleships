package de.uni_hannover.hci.battleships.network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server
{
    // port
    private int port;

    // IP Adresse
    private InetAddress ip;

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
            this.address();
            this.readIncomingMessages();
            //this.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void address()
    {
        try {
            this.ip = InetAddress.getLocalHost();
            System.out.println("IP address : " + ip);
            System.out.println("Port address : " + this.port );

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException
    {
        this.client = this.server.accept();
    }

    private void readIncomingMessages() throws IOException
    {
        try {
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String s = in.readLine();
                if (s == null) break;
                System.out.println("Message from Client: " + s);
            }
            this.client.close();
            this.server.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
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