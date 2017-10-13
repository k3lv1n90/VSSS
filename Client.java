import java.net.InetAddress;
/**
 * Write a description of class Client here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Client
{
    private String userName;
    private InetAddress address;
   

    /**
     * Constructor for objects of class Client
     */
    public Client(String userName, InetAddress address)
    {
        this.userName = userName;
        this.address = address;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public InetAddress getAddress()
    {
        return address;
    }
    
        /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public String getUserName()
    {
        return userName;
    }
}
