package de.uni_hannover.hci.battleships.util;

// Java
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;


public class NetworkSocketConnectionValidator
{
    /* FUNCTIONS */

    /**
     * TODO
     * @param port
     * @param ipAdress
     * @return
     */
    public static boolean validateActiveServer(Integer port, String ipAdress)
    {
        if(port == null) return false;
        if(port.toString().length() != 4) return false;

        try
        {
            new ServerSocket(port, 1, InetAddress.getByName(ipAdress)).close();
            return false;
        }
        catch(IOException e1)
        {
            try
            {
                return InetAddress.getLocalHost().getHostAddress().equals(ipAdress) || NetworkSocketConnectionValidator.validatePort(port);
            }
            catch(UnknownHostException e2)
            {
                return false;
            }
        }
    }

    /**
     * TODO
     * @param port
     * @return
     */
    public static boolean validatePort(Integer port)
    {
        if(port == null) return false;
        if(port.toString().length() != 4) return false;

        try
        {
            new ServerSocket(port).close();
            return true;
        }
        catch(IOException e)
        {
            return false;
        }
    }
}