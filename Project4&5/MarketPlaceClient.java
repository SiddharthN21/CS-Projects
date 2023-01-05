import java.io.*;
import java.net.*;
import java.util.InputMismatchException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * MarketPlaceClient Class
 * This class is used to facilitate the client side of the MarketPlace app. All the frames are declared in this class
 * and connect to the server through the try-catch block. The bounds and default close operations of each frame are
 * also set withing this class. Once a particular operation is completed within each frame, they call the methods to
 * set the other frames visible.
 * ***Note - It is assumed that the user enters the values in the specified format mentioned in the README***
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 05
 *
 * @author Aaron Shih Sec: L04
 * @author Ahsan Zulfiquar Sec: L04
 * @author Leon Yee Sec: L04
 * @author Lucas Munteanu Sec: L04
 * @author Siddharth Nadgaundi Sec: L04
 * @version December 08, 2022
 */

public class MarketPlaceClient extends JComponent {
    public static final String WELCOME = "Welcome to marketplace messaging system!";

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException
    {
        LoginFrame frame = new LoginFrame();
        frame.setTitle(WELCOME);
        frame.setVisible(true);
        frame.setBounds(10, 10, 600, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        try {
            frame.connectToServer();
        }
        catch (Exception e)
        {
            frame.dispose();
        }

        CustSellerFrame frame1 = new CustSellerFrame();
        frame1.setTitle("Customer/Seller Menu");
        frame1.setBounds(610, 10, 1100, 450);
        frame1.setLocationRelativeTo(null);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setVisible(false);
        frame1.setResizable(false);

        DashboardFrame frame2 = new DashboardFrame();
        frame2.setTitle("Dashboard");
        frame2.setBounds(610, 10, 1100, 450);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setVisible(false);
        frame2.setResizable(false);

        frame.setSubMenuFrame(frame1);
        frame1.setMainMenuFrame(frame);
        frame1.setDashFrame(frame2);
        frame2.setFrameOnClose(frame1);
    }
}