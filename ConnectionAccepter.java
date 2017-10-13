import java.util.ArrayList;
import java.time.LocalDateTime;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.SocketException;
/**
 * Write a description of class ConnectionAccepter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ConnectionAccepter implements Runnable
{
    private String inMsg;
    private InetAddress clientAddress;
    private int clientUDPPort;
    private ArrayList<Client> clients;
    private int udpPort;
    /**
     * Constructor for objects of class ConnectionAccepter
     */
    public ConnectionAccepter(String inMsg, InetAddress clientAddress, int clientUDPPort, ArrayList<Client> clients, int udpPort)
    {
        this.inMsg = inMsg;
        this.clientAddress = clientAddress;
        this.clientUDPPort = clientUDPPort;
        this.clients = clients;
        this.udpPort = udpPort;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void run()
    {
        try
        {
            String ack = "You are online!";
            DatagramPacket outPacket = new DatagramPacket(ack.getBytes(), ack.getBytes().length, clientAddress, udpPort);
            DatagramSocket outSocket = new DatagramSocket();
            outSocket.send(outPacket);
            clients.add(new Client(inMsg.split(",")[1], clientAddress));
            System.out.println(LocalDateTime.now() + 
                "\nClient " + inMsg.split(",")[1] + " connected!" +
                "\nClient address is : " + clientAddress + " (" + clientUDPPort + ")" +
                "\n============================================");
        }
        catch(SocketException e)
        {
            System.out.println("SocketException in ConnectionAccepter : " +
                "\n" + e + 
                "\n============================================");
        }
        catch(IOException e)
        {
            System.out.println("IOException in ConnectionAccepter : " +
                "\n" + e + 
                "\n============================================");
        }
    }
}
