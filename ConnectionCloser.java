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
public class ConnectionCloser implements Runnable
{
    private String inMsg;
    private InetAddress clientAddress;
    private int clientUDPPort;
    private ArrayList<Client> clients;
    private ArrayList<Video> videos;
    /**
     * Constructor for objects of class ConnectionAccepter
     */
    public ConnectionCloser(String inMsg, InetAddress clientAddress, int clientUDPPort, ArrayList<Video> videos, ArrayList<Client> clients)
    {
        this.inMsg = inMsg;
        this.clientAddress = clientAddress;
        this.clientUDPPort = clientUDPPort;
        this.clients = clients;
        this.videos = videos;
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
            String ack = "You are offline!";
            DatagramPacket outPacket = new DatagramPacket(ack.getBytes(), ack.getBytes().length, clientAddress, 4599);
            DatagramSocket outSocket = new DatagramSocket();
            outSocket.send(outPacket);
            for(int i = 0; i < videos.size(); i++)
            {
                if(videos.get(i).getOwner().equals(clientAddress))
                {
                    videos.remove(i);
                }
            }
            for(int i = 0; i < videos.size(); i++)
            {
                if(clients.get(i).getAddress().equals(clientAddress))
                {
                    clients.remove(i);
                    break;
                }
            }
            System.out.println(LocalDateTime.now());
            System.out.println("Client " + inMsg.split(",")[1] + " disconnected! (" + clientAddress + ")");
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
