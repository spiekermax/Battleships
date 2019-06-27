package de.uni_hannover.hci.battleships.network;

import java.io.*;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {

    private Socket client;
    private DataInputStream console;
    private OutputStream output;
    private InputStream input;

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


    public void sendMessages()
    {
        try {
            while(true) {
                Scanner scanner = new Scanner(System.in);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
                String s = scanner.nextLine();
                if(s.equals("shutdown")) {
                    scanner.close();
                    break;
                }
                String toSend = s + "\n";
                bw.write(toSend);
                bw.flush();
            }
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}