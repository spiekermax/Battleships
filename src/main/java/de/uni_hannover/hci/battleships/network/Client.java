package de.uni_hannover.hci.battleships.network;

import java.io.*;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Client {

    private Socket client;
    private DataInputStream console;
    private OutputStream output;
    private InputStream input;
    private InetAddress ip;

    public Client(int port)
    {
        try{
            this.client = new Socket("localhost", port);
            System.out.print("Client gestartet!\n");
            this.sendMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void address()
    {
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("IP address : " + ip);
            System.out.println("Port address : " );

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages()
    {
        try {
            this.output = this.client.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(this.output);
            BufferedWriter bw = new BufferedWriter(osw);
            String s = "Hello world!";
            String toSend = s + "\n";
            bw.write(toSend);
            bw.flush();
            System.out.println(toSend);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}