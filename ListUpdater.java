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
public class ListUpdater implements Runnable
{
    private String inMsg;
    private InetAddress clientAddress;
    private int clientUDPPort;
    private ArrayList<Client> clients;
    private ArrayList<Video> videos;
    private int udpPort;
    /**
     * Constructor for objects of class ConnectionAccepter
     */
    public ListUpdater(String inMsg, InetAddress clientAddress, int clientUDPPort, ArrayList<Video> videos, ArrayList<Client> clients, int udpPort)
    {
        this.inMsg = inMsg;
        this.clientAddress = clientAddress;
        this.clientUDPPort = clientUDPPort;
        this.clients = clients;
        this.videos = videos;
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
            String[] vS = inMsg.split(",");
            for(int i = 1; i < vS.length; i = i + 2)
            {
                videos.add(new Video(vS[i], InetAddress.getByName(vS[i+1])));
            }
            int clientIndex;
            for(clientIndex = 0; clientIndex < clients.size(); clientIndex++)
            {
                if(clients.get(clientIndex).getAddress().equals(clientAddress))
                {
                    break;
                }
            } 
            System.out.println(LocalDateTime.now() +
                "\nList of videos received from " + clients.get(clientIndex).getUserName() +
                "\n" + inMsg +
                "\n============================================");
            System.out.println(LocalDateTime.now() + "\nLatest video list : ");
                
            String videosString = "";
            for(Video v: videos)
            {
                videosString += v.toString() + "," ;
                System.out.println("\n - " + v.toString());
            }
            System.out.println("\n============================================");
            videosString = videosString.substring(0, videosString.length() - 1);
            for(int i = 0; i < clients.size(); i++)
            {
                DatagramPacket outPacket = new DatagramPacket(videosString.getBytes(), videosString.getBytes().length, clients.get(i).getAddress(), udpPort);
                DatagramSocket outSocket = new DatagramSocket();
                outSocket.send(outPacket);
            }
            System.out.println(LocalDateTime.now() +
                "\nVideos list updated and sent to all clients" + 
                "\n============================================");
        }
        catch(UnknownHostException e)
        {
            System.out.println("UnknownHostException in ListUpdater : " + e + 
                "\n============================================");
        }
        catch(SocketException e)
        {
            System.out.println("SocketException in ListUpdater : " + e + 
                "\n============================================");
        }
        catch(IOException e)
        {
            System.out.println("IOException in ListUpdater : " + e + 
                "\n============================================");
        }
    }
}
