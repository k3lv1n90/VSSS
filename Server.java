import java.util.ArrayList;
import java.time.LocalDateTime;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.SocketException;
/**
 * Write a description of class Server here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Server{
    private int udpPort;
    private int clientUDPPort;
    private DatagramSocket outSocket;
    private DatagramSocket inSocket;

    private byte[] buf;
    private ArrayList<Client> clients;
    private ArrayList<Video> videos;

    private String inMsg;
    private String outMsg;

    public Server(int udpPort) throws SocketException
    {
        clients = new ArrayList<Client>();
        videos = new ArrayList<Video>();
        this.udpPort = udpPort;
        outSocket = new DatagramSocket();
        inSocket = new DatagramSocket(this.udpPort);
        buf = new byte[1024];
    }

    public boolean goOnline() throws IOException
    {
        System.out.println("Waiting for clients...");
        while(true)
        {
            DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
            inSocket.receive(inPacket);
            inMsg = new String(inPacket.getData(), 0, inPacket.getLength());
            InetAddress clientAddress = inPacket.getAddress();
            clientUDPPort = inPacket.getPort();
            if(inMsg.startsWith("I'm online!"))
            {
                Thread connectionAccepter = new Thread(new ConnectionAccepter(inMsg, clientAddress, clientUDPPort, clients, udpPort));
                connectionAccepter.start();
            }
            if(inMsg.startsWith("myVideosList"))
            {
                Thread listUpdater = new Thread(new ListUpdater(inMsg, clientAddress, clientUDPPort, videos, clients, udpPort));
                listUpdater.start();
            }
            if(inMsg.startsWith("bye!"))
            {
                Thread connectionCloser = new Thread(new ConnectionCloser(inMsg, clientAddress, clientUDPPort, videos, clients));
                connectionCloser.start();
            }
        }
    }

    public void goOffline()
    {
        outSocket.close();
        inSocket.close();
        System.out.println("Server is offline...");
    }

} 