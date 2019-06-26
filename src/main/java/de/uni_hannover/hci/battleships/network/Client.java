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

    public Client(int port) throws IOException
    {
        this.client = new Socket("localhost", port);
    }

    public void ipaddress()
    {
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("IP address : " + ip);
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
            String s = "izerjherjwk";
            String toSend = s + "\n";
            bw.write(toSend);
            bw.flush();

            while(toSend != "shutdown")
            {
                System.out.println(toSend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}