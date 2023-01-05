
import javax.swing.JOptionPane;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * MarketPlace Class
 * This class contains the server of the marketplace messaging system.
 * mainMenu:
 * Case1: Login - Used by the user to log in to the marketplace messaging system.
 * Case2: Create User - Used by the user to create a new account.
 * Case3: Exit - Used for exiting the marketplace messaging system.
 * After successfully logging in, the user is navigated to the Customer/Seller Menu.
 * Customer/Seller Menu:
 * Case1: Change username - Used for changing the existing username to a new one.
 * Case2: Change password - Used for changing the existing password to a new one.
 * Case3: Delete user - Used for deleting the logged-in user. On performing this operation, the user is taken back to
 * the mainMenu.
 * Case4: Block user - Displays a list of users that the logged-in user can block one at a time.
 * Case5: Message sending for customers and sellers. Displays users to message and prompts user to
 * send message, send file, edit, or delete message.
 * Case6: Dashboard - Displays dashboard, allows sorting.
 * Case7: Logout - Used to log the user out and send them back to the mainMenu.
 * ***Note - It is assumed that the user enters the values in the specified format***
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
public class MarketPlaceServer extends Thread {
    public static final String WELCOME = "Welcome to marketplace messaging system!";
    public static final String MAINMENU = "Please select from following options.\n1.Login\n2.Create User\n3.Exit";
    public static final String USEREMAIL = "Enter user Email:";
    public static final String PWORD = "Enter Password:";
    public static final String USERNAME = "Enter UserName:";
    public static final String USERROLE = "Enter User Role(customer/seller):";
    public static final String LOGINSUCCESS = "Successfully Logged into messaging system";
    public static final String USERCREATIONSUCCESS = "Successfully created user";
    public static final String CUSTSELLERMENU = "Please select an operation:\n" +
            "1.Change user name\n" +
            "2.Change password\n" +
            "3.Delete user(Logged in user will be deleted)\n" +
            "4.Block user(select appropriate email)\n" +
            "5.Messaging\n" +
            "6.Dashboard\n" +
            "7.Logout";
    public static final String GOODBYE = "Thank you for using marketplace messaging system!";

    private Socket socket;

    public MarketPlaceServer(Socket socket) throws IOException
    {
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            int menuSelection = 0;
            String uEmail = "";
            String uPassword = "";
            String uName = "";
            String uRole = "";
            String selectedBlockUser;
            String avlUsersBlk;
            String unameChange = "";
            String pWordChange = "";
            String deleteStatus = "";
            Account myAccount = null;
            FileManipulation fileManipulation = new FileManipulation();

            while (true) {
                boolean exitYN = false;
                boolean continueSubMenu = true;
                String s1 = reader.readLine();
                menuSelection = Integer.parseInt(s1);

                uEmail = "";
                uPassword = "";
                uName = "";
                uRole = "";
                switch (menuSelection) {

                    case 1:
                        uEmail = reader.readLine();
                        uPassword = reader.readLine();
                        try {
                            myAccount = new Account(uEmail, uPassword);
                            writer.write(LOGINSUCCESS);
                            writer.println();
                            writer.flush();
                            uRole = myAccount.getuRole();
                            writer.write(uRole);
                            writer.println();
                            writer.flush();
                        } catch (InvalidUserInfoException e) {
                            writer.write(e.getMessage());
                            writer.println();
                            writer.flush();
                            continueSubMenu = false;
                        }
                        break;
                    case 2:
                        uName = reader.readLine();
                        uEmail = reader.readLine();
                        uPassword = reader.readLine();
                        uRole = reader.readLine();
                        try {
                            myAccount = new Account(uEmail, uName, uPassword, uRole);
                            writer.write(USERCREATIONSUCCESS);
                            writer.println();
                            writer.flush();
                        } catch (UserExistsException e) {
                            writer.write(e.getMessage());
                            writer.println();
                            writer.flush();
                            continueSubMenu = false;
                        }
                        break;
                    case 3:
                        exitYN = true;
                        break;
                    default:
                        continueSubMenu = false;
                        break;
                }
                if (exitYN) {
                    socket.close();
                    break;
                }

                while (continueSubMenu) {
                    uRole = myAccount.getuRole();

                    try {
                        String s2 = reader.readLine();
                        menuSelection = Integer.parseInt(s2);
                    } catch (NumberFormatException e) {
                        String invalid = "Invalid input! Please respond with an integer.";
                        writer.write(invalid);
                        writer.println();
                        writer.flush();
                        continue;
                    }

                    switch (menuSelection) {
                        case 1:
                            uName = reader.readLine();
                            if (myAccount.changeUserName(myAccount, uName)) {
                                myAccount.setName(uName);
                                unameChange = "User name changed successfully.";
                                writer.write(unameChange);
                                writer.println();
                                writer.flush();
                            } else {
                                unameChange = "Error in changing user name. Try again.";
                                writer.write(unameChange);
                                writer.println();
                                writer.flush();
                            }
                            break;
                        case 2:
                            uPassword = reader.readLine();
                            if (myAccount.changeUserPassword(myAccount, myAccount.getPassword(), uPassword)) {
                                myAccount.setPassword(uPassword);
                                pWordChange = "User Password changed successfully.";
                                writer.write(pWordChange);
                                writer.println();
                                writer.flush();
                            } else {
                                pWordChange = "Error in changing user password. Try again.";
                                writer.write(pWordChange);
                                writer.println();
                                writer.flush();
                            }
                            break;
                        case 3:
                            if (myAccount.deleteUser(myAccount)) {
                                deleteStatus = "Successfully deleted logged in user. Returning to main menu.";
                                writer.write(deleteStatus);
                                writer.println();
                                writer.flush();
                                myAccount = null;
                                exitYN = true;
                            } else {
                                deleteStatus = "Error deleting user. Try again.";
                                writer.write(deleteStatus);
                                writer.println();
                                writer.flush();
                            }
                            break;
                        case 4:
                            avlUsersBlk = myAccount.getAllUserThatCanBeBlocked(myAccount);

                            if (!avlUsersBlk.isEmpty()) {
                                String blockedList = "Please enter email to block user from below list";
                                writer.write(blockedList);
                                writer.println();
                                writer.flush();
                                int count = 0;
                                //System.out.print(avlUsersBlk);
                                for (int i = 0; i < avlUsersBlk.length(); i++)
                                {
                                    if (avlUsersBlk.charAt(i) == '@')
                                    {
                                        count++;
                                    }
                                }
                                String s = String.valueOf(count);
                                writer.write(s);
                                writer.println();
                                writer.flush();
                                writer.write(avlUsersBlk);
                                writer.println();
                                writer.flush();
                                selectedBlockUser = reader.readLine();
                                String temp1 = "";
                                String temp2 = avlUsersBlk;
                                boolean foundUserBlk = false;
                                for (int i = 0; i < count; i++)
                                {
                                    int nLineIdx = temp2.indexOf("\n");
                                    temp1 = temp2.substring(temp2.indexOf(".") + 1, nLineIdx);
                                    if (temp1.equals(selectedBlockUser))
                                    {
                                        foundUserBlk = true;
                                    } else
                                    {
                                        temp2 = temp2.substring(nLineIdx + 1);
                                    }
                                }
                                if (foundUserBlk)
                                {
                                    if (myAccount.blockUser(myAccount, selectedBlockUser))
                                    {
                                        String blockSuccess = "Successfully blocked selected user.";
                                        writer.write(blockSuccess);
                                        writer.println();
                                        writer.flush();
                                    }
                                    else {
                                        String blockFail = "Error in blocking selected user. Try again.";
                                        writer.write(blockFail);
                                        writer.println();
                                        writer.flush();
                                    }
                                }
                                else {
                                    String noMatch = "Entered user email does not match to the user in the list";
                                    writer.write(noMatch);
                                    writer.println();
                                    writer.flush();
                                }

                            } else {
                                String noUserAvailable = "No user available for blocking";
                                writer.write(noUserAvailable);
                                writer.println();
                                writer.flush();
                            }
                            break;
                        case 5:
                            //*****  Add Messaging Menu code - Leon & Ahsan  ******
                            //**** This case is for both customer and seller.
                            if (uRole.equals("seller")) { // LEON
                                SellerMessaging sellerMessaging = new SellerMessaging(myAccount);

                                String customer = sellerMessaging.selectCustomer();

                                if (customer == null) {
                                    break;
                                }

                                outer:
                                while (true) {
                                    int choice = sellerMessaging.displayMessages(customer);
                                    switch (choice) {
                                        case 1:
                                            sellerMessaging.sendMessage(customer);
                                            break;
                                        case 2:
                                            sellerMessaging.sendMessageFromFile(customer);
                                            break;
                                        case 3:
                                            sellerMessaging.editMessage(customer);
                                            break;
                                        case 4:
                                            sellerMessaging.deleteMessage(customer);
                                            break;
                                        case 6:
                                            sellerMessaging.exportMessages(customer);
                                            break;
                                        case 7:
                                            break outer;
                                        default:
                                            continue outer;
                                    }
                                }
                            } else if (uRole.equals("customer")) {
                                CustomerMessaging customerMessaging = new CustomerMessaging(myAccount);

                                String store = customerMessaging.selectSeller();
                                outer:
                                while (true && store != null) {
                                    int choice = customerMessaging.viewAllMessages(store);
                                    switch (choice) {
                                        case 1:
                                            customerMessaging.sendMessage(store);
                                            break;
                                        case 2:
                                            
                                            customerMessaging.sendMessageFromFile(store);
                                            break;
                                        case 3:
                                            customerMessaging.editMessages(store);
                                            break;
                                        case 4:
                                            customerMessaging.deleteMessage(store);
                                            break;
                                        case 5:
                                            customerMessaging.exportMessages(store);
                                            break;
                                        case 6:
                                            break outer;
                                        default:
                                            continue outer;
                                    }
                                }
                            }

                            break;

                        case 6:
                            //***** Add Dashboard code - Aaron ********
                            if (uRole.toLowerCase().equals("seller")) {
                                ArrayList<String> listOfStores = fileManipulation.getStores(myAccount.getName());
                                Hashtable<String, String[]> c = new Hashtable<String, String[]>();
                                ArrayList<String> cu = fileManipulation.getAllCustomerUsernames(myAccount);
                                for (String sellerStore : listOfStores) {
                                    ArrayList<String> validCustomers = new ArrayList<String>();
                                    for (int i = 0; i < cu.size(); i++) {
                                        String customer = cu.get(i);
                                        if (new File(customer + "_" + myAccount.getName() + "_" + sellerStore +
                                                ".txt").isFile()) {
                                            validCustomers.add(customer);
                                        }
                                    }
                                    String[] res = validCustomers.toArray(new String[0]);
                                    c.put(sellerStore, res);
                                }
                                String[] dash = SellerDashboard.getDash(c);
                                while (true) {
                                    writer.write(Integer.toString(dash.length));
                                    writer.println();
                                    writer.flush();
                                    for (int i = 0; i < dash.length; i++) {
                                        writer.write(dash[i]);
                                        writer.println();
                                        writer.flush();
                                    }
                                    String cont = reader.readLine();
                                    if (!cont.equals("go")) {
                                        break;
                                    }
                                    int next = 0;
                                    while (next != 1 && next != 2) {
                                        try {
                                            next = Integer.parseInt(reader.readLine());
                                        } catch (Exception e) {
                                            writer.write("Invalid input!");
                                            writer.println();
                                            writer.flush();
                                            break;
                                        }
                                    }
                                    if (next == 1) {
                                        dash = SellerDashboard.sortDash(c);
                                    } else {
                                        writer.write("end");
                                        writer.println();
                                        writer.flush();
                                        break;
                                    }
                                }
                            } else {
                                ArrayList<String> s = fileManipulation.getAllSellerUsernames(myAccount);
                                Hashtable<String, String> sellers = new Hashtable<String, String>();
                                for (int i = 0; i < s.size(); i++) {
                                    for (int j = 0; j < fileManipulation.getStores(s.get(i)).size(); j++) {
                                        sellers.put(fileManipulation.getStores(s.get(i)).get(j), s.get(i));
                                    }
                                }
                                String[] dash = CustomerDashboard.getDash(sellers, myAccount.getName());
                                while (true) {
                                    writer.write(Integer.toString(dash.length));
                                    writer.println();
                                    writer.flush();
                                    for (int i = 0; i < dash.length; i++) {
                                        writer.write(dash[i]);
                                        writer.println();
                                        writer.flush();
                                    }
                                    String cont = reader.readLine();
                                    if (!cont.equals("go")) {
                                        break;
                                    }
                                    int next = 0;
                                    while (next != 1 && next != 2) {
                                        try {
                                            next = Integer.parseInt(reader.readLine());
                                        } catch (Exception e) {
                                            writer.write("Invalid input!");
                                            writer.println();
                                            writer.flush();
                                            break;
                                        }
                                    }
                                    if (next == 1) {
                                        dash = CustomerDashboard.sortDash(sellers, myAccount.getName());
                                    }
                                } /*else {
                                    writer.write("end");
                                    writer.println();
                                    writer.flush();
                                    break;
                                }*/
                            }
                            break;
                        case 7:
                            myAccount = null;
                            exitYN = true;
                            break;
                        case 8:
                            if (uRole.toLowerCase().equals("seller")) {
                                String storeName = reader.readLine();
                                fileManipulation.addStore(myAccount.getName(), storeName);
                                writer.write("Successfully added " + storeName + " to your stores.");
                                writer.println();
                                writer.flush();
                            } else {
                                writer.write("Inaccurate option selected for Customer/Seller Menu");
                                writer.println();
                                writer.flush();
                            }
                            break;
                        default:
                            //System.out.println("Inaccurate option selected for Customer/Seller Menu");
                            String inaccurateOption = "Inaccurate option selected for Customer/Seller Menu";
                            writer.write(inaccurateOption);
                            writer.println();
                            writer.flush();

                    }
                    if (exitYN) {
                        break;
                    }
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
}
