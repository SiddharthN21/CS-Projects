import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ThreadServer Class
 * This class is used to spawn multiple threads and allow several clients to connect to the server. This allows the
 * users to access the common resources within the messaging system and update the files through inputs sent by
 * multiple clients. The while loop allows the server to accept any new connections on the specified port number. The
 * code is enclosed in a try-catch block to identify any errors that may occur while connecting.
 * Port Number -4242
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 05
 *
 * @author Siddharth Nadgaundi Sec: L04
 * @version December 08, 2022
 */

public class ThreadServer {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        int clientNo = 1;
        try (ServerSocket serverSocket = new ServerSocket(4242)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new MarketPlaceServer(socket).start();
                clientNo++;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
