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
    /**
     * Constructor for objects of class ConnectionAccepter
     */
    public ConnectionAccepter(String inMsg, InetAddress clientAddress, int clientUDPPort, ArrayList<Client> clients)
    {
        this.inMsg = inMsg;
        this.clientAddress = clientAddress;
        this.clientUDPPort = clientUDPPort;
        this.clients = clients;

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
            DatagramPacket outPacket = new DatagramPacket(ack.getBytes(), ack.getBytes().length, clientAddress, 4599);
            DatagramSocket outSocket = new DatagramSocket();
            outSocket.send(outPacket);
            clients.add(new Client(inMsg.split(",")[1], clientAddress));
            System.out.println(LocalDateTime.now());
            System.out.println("Client " + inMsg.split(",")[1] + " connected!");
            System.out.println("Client address is : " + clientAddress + " (" + clientUDPPort + ")");
            System.out.println("============================================");
        }
        catch(SocketException e)
        {
            System.out.println("Error : " + e);
        }
        catch(IOException e)
        {
            System.out.println("Error : " + e);
        }
    }
}
