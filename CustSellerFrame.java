import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * CustSellerFrame Class
 * This class contains the various operations that the user can select once they have logged into the marketplace
 * messaging system. The various operations available are change username, change password, delete user, block user,
 * messaging, dashboard, open new store, and logout.
 * Operations:
 * Change Username: When this button is clicked, a JOptionPane is opened which allows the user to enter their new
 * username.
 * Change Password: When this button is clicked, a JOptionPane is opened which allows the user to enter their new
 * password.
 * Delete User: When this button is clicked, a JOptionPane is opened asking the user if they would like to delete
 * their account. If the user clicks yes, their account is deleted, and they are sent back to the main-menu.
 * Block User: When this button is clicked, a JOptionPane is opened with a drop-down list of the users available for
 * blocking. The user selects one of the emails from the list which then blocks the selected user.
 * Messaging:
 * Dashboard:
 * Logout: When this button is clicked, the user is logged out of their account and sent back to the main-menu.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 05
 *
 * @author Aaron Shih Sec: L04
 * @author Siddharth Nadgaundi Sec: L04
 * @version December 08, 2022
 */

public class CustSellerFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    JButton chgUserName = new JButton("Change Username");
    JButton chgPassword = new JButton("Change Password");
    JButton delUser = new JButton("Delete User");
    JButton blkUser = new JButton("Block User");
    JButton messaging = new JButton("Messaging");
    JButton dashboard = new JButton("Dashboard");
    JButton newStore = new JButton("Open New Store");
    JButton logout = new JButton("Logout");

    BufferedReader reader;
    PrintWriter writer;

    private LoginFrame mainMenuFrame;
    private DashboardFrame dashFrame;
    private int sMenuOpt = 0;

    public CustSellerFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        chgUserName.setBounds(10,30,140,30);
        chgPassword.setBounds(150,30,140,30);
        delUser.setBounds(290,30,140,30);
        blkUser.setBounds(430,30,140,30);
        messaging.setBounds(570,30,140,30);
        dashboard.setBounds(710,30,140,30);
        newStore.setBounds(850,30,140,30);
        logout.setBounds(990,30,100,30);
    }
    public void addComponentsToContainer() {
        container.add(chgUserName);
        container.add(chgPassword);
        container.add(delUser);
        container.add(blkUser);
        container.add(messaging);
        container.add(dashboard);
        container.add(newStore);
        container.add(logout);
    }

    public void addActionEvent() {
        chgUserName.addActionListener(this);
        chgPassword.addActionListener(this);
        delUser.addActionListener(this);
        blkUser.addActionListener(this);
        messaging.addActionListener(this);
        dashboard.addActionListener(this);
        newStore.addActionListener(this);
        logout.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chgUserName) {
            sMenuOpt = 1;
            String smOption = "1";
            String newUname = "";
            newUname = (String) JOptionPane.showInputDialog(null, "Please enter a new user name",
                    "UserName", JOptionPane.PLAIN_MESSAGE);
            if (!(newUname == null)) {
                writer.write(smOption);
                writer.println();
                writer.flush();
                writer.write(newUname);
                writer.println();
                writer.flush();
                String uChangeStat = "";
                try {
                    uChangeStat = reader.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (uChangeStat.contains("Error")) {
                    JOptionPane.showMessageDialog(null, uChangeStat, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, uChangeStat, "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        if (e.getSource() == chgPassword) {
            sMenuOpt = 2;
            String smOption = "2";
            String newPass = "";
            newPass = (String) JOptionPane.showInputDialog(null, "Please enter a new password",
                    "Password", JOptionPane.PLAIN_MESSAGE);
            if (!(newPass == null)) {
                writer.write(smOption);
                writer.println();
                writer.flush();
                writer.write(newPass);
                writer.println();
                writer.flush();
                String pChangeStat = "";
                try {
                    pChangeStat = reader.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (pChangeStat.contains("Error")) {
                    JOptionPane.showMessageDialog(null, pChangeStat, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, pChangeStat, "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        if (e.getSource() == delUser) {
            sMenuOpt = 3;
            String smOption = "3";
            int delOption = 0;
            delOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the logged in user?", "Delete User",
                    JOptionPane.YES_NO_OPTION);
            if (delOption == JOptionPane.YES_OPTION) {
                writer.write(smOption);
                writer.println();
                writer.flush();
                String delStatus = "";
                try {
                    delStatus = reader.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (delStatus.contains("Error")) {
                    JOptionPane.showMessageDialog(null, delStatus, "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, delStatus, "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    mainMenuFrame.setVisible(true);
                    this.setVisible(false);
                }
            }
        }

        if (e.getSource() == blkUser)
        {
            sMenuOpt = 4;
            String smOption = "4";
            writer.write(smOption);
            writer.println();
            writer.flush();
            String blkMessage = "";
            try {
                blkMessage = reader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (blkMessage.contains("No")) {
                JOptionPane.showMessageDialog(null, blkMessage, "No Users",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                String numUsers = "";
                try {
                    numUsers = reader.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                int countUsers = Integer.parseInt(numUsers);
                String[] uOptions = new String[countUsers];
                for (int i = 0; i < countUsers; i++) {
                    try {
                        uOptions[i] = reader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                // reading extra blank line sent from server
                try {
                    String temp = reader.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                String blocked = (String) JOptionPane.showInputDialog(null, blkMessage,
                        "Block User", JOptionPane.INFORMATION_MESSAGE, null, uOptions, uOptions[0]);
                blocked = blocked.substring(blocked.indexOf(".") + 1);
                writer.write(blocked);
                writer.println();
                writer.flush();
                String blkStat = "";
                try {
                    blkStat = reader.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (blkStat.contains("Error") || blkStat.contains("Entered")) {
                    JOptionPane.showMessageDialog(null, blkStat, "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, blkStat, "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        if (e.getSource() == messaging) {
            sMenuOpt = 5;
            String smOption = "5";
            writer.write(smOption);
            writer.println();
            writer.flush();
        }
        if (e.getSource() == dashboard) {
            sMenuOpt = 6;
            String smOption = "6";
            writer.write(smOption);
            writer.println();
            writer.flush();
            this.setVisible(false);
            //make dashFrame visible
            dashFrame.setSocketReader(reader);
            dashFrame.setSocketWriter(writer);
            dashFrame.setVisible(true);
            try {
                dashFrame.activateDash();
            } catch (Exception exception) {

            }


        }

        if (e.getSource() == logout) {
            sMenuOpt = 7;
            String smOption = "7";
            writer.write(smOption);
            writer.println();
            writer.flush();
            //Make mainFrameVisble
            mainMenuFrame.setVisible(true);
            this.setVisible(false);
        }
        if (e.getSource() == newStore) {
            sMenuOpt = 8;
            String smOption = "8";
            writer.write(smOption);
            writer.println();
            writer.flush();
            String store = (String) JOptionPane.showInputDialog(null,
                    "Enter the name of the new store", "Opening Store", JOptionPane.QUESTION_MESSAGE);
            writer.write(store);
            writer.println();
            writer.flush();
            try {
                JOptionPane.showMessageDialog(null, reader.readLine(), "Opening Store", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception exception) {

            }
        }
        //**** Add different event listener code for each JButton. *****


    }
    public void setDashFrame(DashboardFrame frame) {
        dashFrame = frame;
    }
    public void setMainMenuFrame(LoginFrame frame) {
        mainMenuFrame = frame;
    }

    public void setSocketReader(BufferedReader reader1) {
        reader = reader1;
    }

    public void setSocketWriter(PrintWriter writer1) {
        writer = writer1;
    }
}
