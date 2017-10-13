import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.SocketException;
/**
 * This class is responsible to start the application.
 * 
 * @author  Deniz Sumner
 * @version v1.0.1738
 */
public class Start
{
    private static Scanner reader = new Scanner(System.in);
    private static Server server;
    /**
     * The application starts execution from this method.
     * The console UserInterface is called from this method.
     * Company class object is initialized from this method.
     * 
     */
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Enter connection port : (i.e. 4501)");
            int udpPort = Integer.parseInt(reader.nextLine());
            server = new Server(udpPort);
        }
        catch(IOException e)
        {
            System.out.println("Server address is not valid!");
        }
        
        /**GO ONLINE*/
        try
        {
            server.goOnline();
        }
        catch(IOException e)
        {
            System.out.println("Unable to connect server!");
        }
    }

}
