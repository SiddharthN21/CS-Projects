import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

/**
 * LoginFrame Class
 * This class is used to allow the user to log in, create a new user, or exit from the messaging system. The class
 * has three buttons to allow the user to perform these operations. The login button has two text fields and two
 * labels to get the email and password from the user and send it to the server for processing. The create user
 * button has 4 text fields and 4 labels to get the username, email, password, and role from the user and send it to
 * the server for processing. Both the login and create user buttons have an ok and reset button. The ok button is
 * used to confirm the user's input while the reset button is used to reset the values entered in the text fields.
 * The exit button is used to exit from the messaging system and close the client frames. Once the processing has
 * been done by the server, the output is sent back to the client and displayed as JOptionPanes letting the user know
 * if the operation was a success or an error occurred
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 05
 *
 * @author Siddharth Nadgaundi, lab sec l04
 * @version December 08, 2022
 *
 */

public class LoginFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("OK");
    JButton resetButton = new JButton("RESET");
    JButton option1 = new JButton("LOGIN...");
    JButton option2 = new JButton("CREATE USER...");
    JButton option3 = new JButton("EXIT");
    JLabel userName = new JLabel("Enter user name:");
    JTextField eUserName = new JTextField("", 10);
    JLabel userMail = new JLabel("Enter user email:");
    JTextField eUserMail = new JTextField("", 20);
    JLabel userPass = new JLabel("Enter password:");
    JPasswordField eUserPass = new JPasswordField("", 20);
    JLabel userRole = new JLabel("Enter user role:");
    String[] roleOptions = new String[] {"", "Customer", "Seller"};
    JList<String> userRoleList = new JList<String>(roleOptions);
    JScrollPane eUserRole = new JScrollPane(userRoleList);
    JButton uOK = new JButton("OK");

    private int selOption = 0;
    BufferedReader reader;
    PrintWriter writer;

    private CustSellerFrame subMenuFrame;


    Socket socket = null;
    String loginStatus = "";
    String creationStatus = "";
    public static final String GOODBYE = "Thank you for using marketplace messaging system!";



    JCheckBox showPassword = new JCheckBox("Show Password");


    public LoginFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void connectToServer() throws IOException {
        socket = new Socket("localhost", 4242);
        if (socket != null) {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        }
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }



    public void setLocationAndSize() {
        option1.setBounds(50,30,100,30);
        option2.setBounds(250,30,100,30);
        option3.setBounds(450,30,100,30);

        userName.setBounds(200, 100, 150, 20);
        eUserName.setBounds(200, 130, 193, 28);
        userMail.setBounds(200, 160, 150, 20);
        eUserMail.setBounds(200, 180, 193, 28);
        userPass.setBounds(200, 210, 150, 20);
        eUserPass.setBounds(200, 230, 193, 28);
        showPassword.setBounds(400, 230, 150, 28);
        userRole.setBounds(200, 260, 150, 20);
        eUserRole.setBounds(200, 280, 193, 40);
        uOK.setBounds(200, 330, 100, 28);
        resetButton.setBounds(330, 330, 100, 30);

        userName.setVisible(false);
        eUserName.setVisible(false);
        userMail.setVisible(false);
        eUserMail.setVisible(false);
        userPass.setVisible(false);
        eUserPass.setVisible(false);
        showPassword.setVisible(false);
        userRole.setVisible(false);
        eUserRole.setVisible(false);
        uOK.setVisible(false);
        resetButton.setVisible(false);
    }

    public void addComponentsToContainer() {
        container.add(showPassword);
        container.add(resetButton);
        container.add(option1);
        container.add(option2);
        container.add(option3);
        container.add(userName);
        container.add(userRole);
        container.add(userPass);
        container.add(userMail);
        container.add(eUserName);
        container.add(eUserMail);
        container.add(eUserPass);
        container.add(eUserRole);
        container.add(uOK);
    }

    public void addActionEvent() {
        //loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        option1.addActionListener(this);
        option2.addActionListener(this);
        option3.addActionListener(this);
        uOK.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == option1) {
            userName.setVisible(false);
            userRole.setVisible(false);
            eUserRole.setVisible(false);
            eUserName.setVisible(false);
            userMail.setVisible(true);
            eUserMail.setVisible(true);
            userPass.setVisible(true);
            eUserPass.setVisible(true);
            showPassword.setVisible(true);
            uOK.setVisible(true);
            resetButton.setVisible(true);
            eUserMail.setText("");
            eUserPass.setText("");
            selOption = 1;
        }
        if (e.getSource() == option2) {
            userName.setVisible(true);
            userRole.setVisible(true);
            eUserRole.setVisible(true);
            eUserName.setVisible(true);
            userMail.setVisible(true);
            eUserMail.setVisible(true);
            userPass.setVisible(true);
            eUserPass.setVisible(true);
            showPassword.setVisible(true);
            uOK.setVisible(true);
            resetButton.setVisible(true);
            eUserName.setText("");
            eUserMail.setText("");
            eUserPass.setText("");
            userRoleList.setSelectedIndex(0);
            selOption = 2;
        }
        if (e.getSource() == option3) {
            selOption = 3;
            String mainMenuOpt = "3";
            writer.write(mainMenuOpt);
            writer.println();
            writer.flush();
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null, GOODBYE, "Marketplace", JOptionPane.PLAIN_MESSAGE);
            subMenuFrame.dispose();
            this.dispose();
        }
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");

            eUserName.setText("");
            eUserPass.setText("");
            eUserMail.setText("");
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                eUserPass.setEchoChar((char) 0);
            } else {
                eUserPass.setEchoChar('*');
            }
        }

        if (e.getSource() == uOK && selOption == 1) {
            String mainMenuOpt = "1";
            writer.write(mainMenuOpt);
            writer.println();
            writer.flush();
            String uEmail = eUserMail.getText();
            String uPassword = String.valueOf(eUserPass.getPassword());
            writer.write(uEmail);
            writer.println();
            writer.flush();
            writer.write(uPassword);
            writer.println();
            writer.flush();
            try {
                loginStatus = reader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, loginStatus,
                    "Login", JOptionPane.INFORMATION_MESSAGE);

            if (!loginStatus.contains("Invalid"))
            {
                String uRole = "";
                try {
                    uRole = reader.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if (uRole.equals("customer")) {
                    subMenuFrame.newStore.setVisible(false);
                } else {
                    subMenuFrame.newStore.setVisible(true);
                }
                subMenuFrame.setVisible(true);
                this.setVisible(false);
                subMenuFrame.setSocketReader(reader);
                subMenuFrame.setSocketWriter(writer);

            }
        }
        if (e.getSource() == uOK && selOption == 2) {
            String uRole = roleOptions[userRoleList.getSelectedIndex()].toLowerCase();
            if (uRole.equals("seller") || uRole.equals("customer")) {
                String mainMenuOpt = "2";
                writer.write(mainMenuOpt);
                writer.println();
                writer.flush();
                String uName = eUserName.getText();
                String uEmail = eUserMail.getText();
                String uPassword = String.valueOf(eUserPass.getPassword());
                writer.write(uName);
                writer.println();
                writer.flush();
                writer.write(uEmail);
                writer.println();
                writer.flush();
                writer.write(uPassword);
                writer.println();
                writer.flush();
                writer.write(uRole);
                writer.println();
                writer.flush();

                try {
                    creationStatus = reader.readLine();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(null, creationStatus,
                        "Login", JOptionPane.INFORMATION_MESSAGE);

                if (!creationStatus.contains("exists")) {
                    subMenuFrame.setVisible(true);
                    this.setVisible(false);
                    subMenuFrame.setSocketReader(reader);
                    subMenuFrame.setSocketWriter(writer);
                    if (uRole.equals("customer")) {
                        subMenuFrame.newStore.setVisible(false);
                    } else {
                        subMenuFrame.newStore.setVisible(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "User role should " +
                                "be seller/customer(lower case). Please correct.",
                        "Login", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    public void setSubMenuFrame(CustSellerFrame frame)
    {
        subMenuFrame = frame;
    }
}
