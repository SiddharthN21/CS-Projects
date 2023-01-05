import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;

/**
 * SellerMessaging class
 * This class manages all messaging by the seller. It includes the
 * select customer, send message, send message by file, edit message,
 * delete message, and export messages functions.
 *
 * @author Leon Yee
 * @version 11/13/22
 */
public class SellerMessaging extends JComponent {

    private FileManipulation fm = new FileManipulation();
    private Account account;
    private String store;

    JButton submitButton;
    JButton newButton;

    JButton sendButton;
    JButton sendFileButton;
    JButton editButton;
    JButton deleteButton;
    JButton exportButton;
    JButton exitButton;

    JTextField textField;
    JLabel titleLabel;
    JLabel listLabel;
    JLabel detailLabel;
    JComboBox<String> listView;
    JComboBox<String> oldMessages;

    JTextArea messageLabel;
    JTextArea messageLabel2;
    JTextArea messageLabel3;

    JFrame frame;
    JFrame editFrame;
    JFrame deleteFrame;
    JPanel panel2;
    JPanel panel3;
    JScrollPane main;
    JScrollPane scroll;

    boolean pass = true;
    boolean pass2 = true;
    String choice = "";
    String storeName = "";
    String search = "";
    String message = "";
    String result = "";

    public SellerMessaging(Account acc) {
        this.account = acc;
    }

    /*
     * Finds all previous conversations with customers, and allows seller to choose
     * customer to continue the conversation. if there are none or seller chooses a
     * to start a new conversation, it will prompt the seller to select or create a
     * store to message from. then, it will prompt seller to choose a customer from
     * a list or enter a customer username. returns customer username.
     */
    public String selectCustomer() {

        ArrayList<String> customers = fm.getAllCustomerUsernames(account);

        // Create JFrame for selectCustomer() and initialize other labels/buttons/fields
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("Messaging");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(750, 500);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout());
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        choice = "CLOSED";
                        pass = true;
                    }
                });

                Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

                JPanel panel = new JPanel();
                panel.setBorder(padding);
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                titleLabel = new JLabel("title label here");
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                titleLabel.setFont(titleLabel.getFont().deriveFont(25.0f));
                panel.add(titleLabel);

                JPanel scrollPanel = new JPanel();
                scrollPanel.setBorder(padding);

                listLabel = new JLabel("1. list label");
                listLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                listLabel.setFont(titleLabel.getFont().deriveFont(20.0f));
                scrollPanel.add(listLabel);

                JScrollPane jsp = new JScrollPane(scrollPanel);
                jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                panel.add(jsp);

                panel2 = new JPanel();
                panel2.setBorder(padding);
                panel2.setLayout(new FlowLayout());

                panel3 = new JPanel();
                panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

                detailLabel = new JLabel("detail label");
                detailLabel.setFont(titleLabel.getFont().deriveFont(15.0f));
                detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel3.add(detailLabel);

                listView = new JComboBox<String>();
                listView.addItem("combo box");
                listView.addItem("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                listView.setMaximumRowCount(10);
                panel2.add(listView);

                // textField = new JTextField("", 20);
                // panel2.add(textField);

                submitButton = new JButton("Submit");
                submitButton.addActionListener(e -> {
                    choice = (String) listView.getSelectedItem();
                    pass = true;
                });
                submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel2.add(submitButton);

                panel3.add(panel2);

                frame.add(panel);
                frame.add(panel3, BorderLayout.SOUTH);
                frame.setVisible(true);
            }
        });

        // get all previous messaged customers
        ArrayList<String> allConversations = new ArrayList<String>();
        for (String customer : customers) {
            ArrayList<String> conversations = fm.getConversations(customer, account.getName());
            if (conversations.size() > 0) {
                for (String converstaion : conversations) {
                    allConversations.add(converstaion);
                }
            }
        }

        if (allConversations.size() > 0) {
            if (pass) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        listView.removeAllItems();
                    }
                });

                String listLabelString = "<html>";

                for (int i = 0; i < allConversations.size(); i++) {

                    String[] convo = allConversations.get(i).split("\"");
                    String s = (i + 1) + ". To " + convo[1] + " on " + convo[5];
                    listLabelString += "" + s + "<br>";

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listView.addItem(s);
                        }
                    });

                }

                listLabelString += "</html>";
                final String listLabelList2 = listLabelString;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        pass = false;
                        titleLabel.setText("Select conversation");
                        listLabel.setText(listLabelList2);
                        detailLabel.setText("Select a previous conversation or create new conversation: ");

                        newButton = new JButton("New");
                        newButton.addActionListener(e -> {
                            choice = "new";
                            pass = true;
                        });
                        panel2.add(newButton);
                    }
                });

                pass = false;

            }

            while (true) {
                System.out.print("");
                if (pass) {
                    if (choice.equals("new")) {
                        break;
                    } else {
                        try {
                            String temp = choice.split(". ")[0];
                            int index = Integer.parseInt(temp);
                            if (index > 0 && index <= allConversations.size()) {
                                String[] ret = allConversations.get(index - 1).split("\"");
                                this.store = ret[5];
                                frame.setVisible(false);
                                frame.dispose();
                                return ret[1];
                            } else {
                                if (choice.equals("CLOSED")) {
                                    return null;
                                }
                            }
                        } catch (Exception e) {
                            if (choice.equals("CLOSED")) {
                                return null;
                            }
                        }
                        pass = false;
                    }
                }
            }
        }

        // select a store to message from, or create a new one
        if (pass) {
            pass = false;

            ArrayList<String> stores = fm.getStores(account.getName());
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    titleLabel.setText("Store selection");
                    frame.revalidate();
                }
            });

            if (stores.size() == 0) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        listLabel.setText("You have no stores to message from. Please create a store first.");
                        detailLabel.setText("Enter name of store:");
                        panel2.remove(listView);
                        panel2.remove(submitButton);
                        textField = new JTextField("", 20);
                        panel2.add(textField);
                        panel2.add(submitButton);

                        submitButton.removeActionListener(submitButton.getActionListeners()[0]);
                        submitButton.addActionListener(e -> {
                            storeName = textField.getText();
                            pass = true;
                        });
                    }
                });

                createStoreLoop: while (true) {
                    System.out.print("");

                    if (choice.equals("CLOSED")) {
                        return null;
                    }

                    if (pass) {
                        if (storeName.trim().equals("") || storeName == null) {
                            JOptionPane.showMessageDialog(null, "Store name cannot be empty.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            pass = false;
                            continue createStoreLoop;
                        }

                        for (String sellerStores : fm.getStores(account.getName())) {
                            if (sellerStores.equals(storeName)) {
                                JOptionPane.showMessageDialog(null, "Store already exists, please try again.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                pass = false;
                                continue createStoreLoop;
                            }
                        }

                        fm.addStore(account.getName(), storeName);
                        store = storeName;
                        pass = true;
                        break createStoreLoop;
                    }
                }

            } else {
                storeLoop: while (true) {
                    System.out.print("");
                    if (pass2) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                listView.removeAllItems();
                                String listLabelString = "<html>";
                                for (int i = 0; i < stores.size(); i++) {
                                    String s = (i + 1) + ". " + stores.get(i);
                                    listLabelString += "" + s + "<br>";
                                    listView.addItem(s);
                                }
                                listLabelString += "</html>";
                                listLabel.setText(listLabelString);
                                detailLabel.setText("Select a store to message from, or create a new one: ");

                                if (newButton == null) {
                                    newButton = new JButton("New");
                                    newButton.addActionListener(e -> {
                                        choice = "new";
                                        pass = true;
                                    });
                                    panel2.add(newButton);
                                }
                            }
                        });

                        pass = false;
                        while (true) {
                            System.out.print("");
                            if (pass) {
                                if (choice.toLowerCase().equals("new")) {
                                    pass2 = false;

                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            titleLabel.setText("Creating store");
                                            listLabel.setText("");
                                            detailLabel.setText("Enter name of store:");
                                            panel2.remove(newButton);
                                            panel2.remove(listView);
                                            panel2.remove(submitButton);
                                            textField = new JTextField("", 20);
                                            panel2.add(textField);
                                            panel2.add(submitButton);

                                            submitButton.removeActionListener(submitButton.getActionListeners()[0]);
                                            submitButton.addActionListener(e -> {
                                                storeName = textField.getText();
                                                pass2 = true;
                                            });
                                            frame.revalidate();
                                        }
                                    });

                                    createStoreLoop: while (true) {
                                        System.out.print("");

                                        if (choice.equals("CLOSED")) {
                                            return null;
                                        }

                                        if (pass2) {
                                            if (storeName.trim().equals("") || storeName == null) {
                                                JOptionPane.showMessageDialog(null, "Store name cannot be empty.",
                                                        "Error", JOptionPane.ERROR_MESSAGE);
                                                pass2 = false;
                                                continue createStoreLoop;
                                            }

                                            for (String sellerStores : fm.getStores(account.getName())) {
                                                if (sellerStores.equals(storeName)) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Store already exists, please try again.", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                                    pass2 = false;
                                                    continue createStoreLoop;
                                                }
                                            }

                                            fm.addStore(account.getName(), storeName);
                                            store = storeName;
                                            pass = true;
                                            break storeLoop;
                                        }
                                    }
                                } else {

                                    try {
                                        String temp = choice.split(". ")[0];
                                        store = stores.get(Integer.parseInt(temp) - 1);
                                        pass = true;
                                        break storeLoop;
                                    } catch (Exception e) {
                                        if (choice.equals("CLOSED")) {
                                            return null;
                                        }
                                    }

                                }
                            }
                        }
                    }
                } // end loop
            }
        }

        // select the customer to message, or search for a specific customer. returns
        // the customer's username

        pass = false;

        if (customers.size() == 0) {
            JOptionPane.showMessageDialog(null, "There are no customers to message. Please try again later.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            frame.setVisible(false);
            frame.dispose();
            return null;
        }

        while (true) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                    String listLabelString = "<html>";
                    listView.removeAllItems();

                    for (int i = 0; i < customers.size(); i++) {
                        String s = (i + 1) + ": " + customers.get(i);
                        listLabelString += "" + s + "<br>";
                        listView.addItem(s);
                    }

                    listLabelString += "</html>";

                    titleLabel.setText("Customer selection");
                    listLabel.setText(listLabelString);
                    detailLabel.setText("Select a customer to message, or search for a customer: ");

                    if (textField != null) {
                        panel2.remove(textField);
                        panel2.remove(submitButton);
                        panel2.add(listView);
                        panel2.add(submitButton);
                        submitButton.removeActionListener(submitButton.getActionListeners()[0]);
                        submitButton.addActionListener(e -> {
                            choice = listView.getSelectedItem().toString();
                            pass = true;
                        });
                        frame.revalidate();
                    }

                    if (listView == null) {
                        panel2.remove(submitButton);
                        panel2.add(listView);
                        panel2.add(submitButton);
                        frame.revalidate();
                    }

                    if (panel2.getComponentCount() == 2) {
                        newButton = new JButton("Search");
                        newButton.addActionListener(e -> {
                            choice = "search";
                            pass = true;
                        });
                        panel2.add(newButton);
                    } else {
                        panel2.remove(newButton);
                        newButton.setText("Search");
                        newButton.removeActionListener(newButton.getActionListeners()[0]);
                        newButton.addActionListener(e -> {
                            choice = "search";
                            pass = true;
                        });
                        panel2.add(newButton);
                    }

                    frame.revalidate();


                }
            });

            pass = false;

            while (true) {
                System.out.print("");

                if (pass) {
                    if (choice.toLowerCase().equals("search")) {

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {

                                detailLabel.setText("Enter search term: ");
                                listView.removeAllItems();
                                panel2.remove(listView);
                                panel2.remove(submitButton);
                                panel2.remove(newButton);

                                textField = new JTextField("", 20);
                                submitButton.removeActionListener(submitButton.getActionListeners()[0]);
                                submitButton.addActionListener(e -> {
                                    search = textField.getText();
                                    pass2 = true;
                                });

                                panel2.add(textField);
                                panel2.add(submitButton);

                                if (panel2.getComponentCount() == 3) {
                                    panel2.remove(textField);
                                }

                                frame.revalidate();
                            }
                        });

                        pass2 = false;

                        while (true) {
                            System.out.print("");

                            if (choice.equals("CLOSED")) {
                                return null;
                            }

                            if (pass2) {
                                ArrayList<String> searchResults = new ArrayList<String>();
                                String listLabelString = "<html>";

                                for (int i = 0; i < customers.size(); i++) {
                                    if (customers.get(i).toLowerCase().contains(search.toLowerCase())) {
                                        searchResults.add(customers.get(i));
                                    }
                                }

                                for (int i = 0; i < searchResults.size(); i++) {
                                    String s = (i + 1) + ": " + searchResults.get(i);
                                    listLabelString += "" + s + "<br>";

                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            listView.addItem(s);
                                        }
                                    });
                                }

                                listLabelString += "</html>";

                                if (searchResults.size() == 0) {
                                    JOptionPane.showMessageDialog(null, "No results found.", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    pass2 = false;
                                    continue;

                                } else {
                                    final String listLabelString2 = listLabelString;

                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            detailLabel.setText("Select a search result:");
                                            listLabel.setText(listLabelString2);
                                            panel2.remove(textField);
                                            panel2.remove(submitButton);

                                            submitButton.removeActionListener(submitButton.getActionListeners()[0]);
                                            submitButton.addActionListener(e -> {
                                                choice = (String) listView.getSelectedItem();
                                                pass2 = true;
                                            });

                                            panel2.add(listView);
                                            panel2.add(submitButton);
                                            frame.revalidate();
                                            frame.repaint();
                                        }
                                    });

                                    pass2 = false;

                                    while (true) {
                                        System.out.print("");

                                        if (choice.equals("CLOSED")) {
                                            return null;
                                        }

                                        if (pass2) {
                                            try {
                                                String temp = choice.split(": ")[0];
                                                String customer = searchResults.get(Integer.parseInt(temp) - 1);
                                                frame.setVisible(false);
                                                frame.dispose();
                                                return customer;
                                            } catch (Exception e) {
                                                if (choice.equals("CLOSED")) {
                                                    return null;
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }

                    } else {
                        try {
                            String temp = choice.split(": ")[0];
                            String customer = customers.get(Integer.parseInt(temp) - 1);
                            frame.setVisible(false);
                            frame.dispose();
                            return customer;
                        } catch (Exception e) {
                            if (choice.equals("CLOSED")) {
                                return null;
                            }
                        }
                    }

                }
            }

        } // end loop

    } // end selectCustomer

    // sends message to customer and saves it to file.
    public void sendMessage(String customer) {

        // checks for empty message
        if (message.trim().equals("") || message == null) {
            JOptionPane.showMessageDialog(null, "Message cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // get current seller object, gets message, update both files
        String seller = account.getName();
        Date time = new Date(System.currentTimeMillis());
        String timeString = new SimpleDateFormat("HH:mm:ss").format(time);
        fm.addMessage(customer, seller, store, account.getName() + ",;" +
                timeString + ",;" +
                message);
        textField.setText("");
        // outputs as "Seller,;time,;message"
    }

    // import file to send message to customer, and save to file.
    public void sendMessageFromFile(String customer) {

        // initialize joptionpane to prompt for filePath
        String filePath = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle("Select file to send");
        fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            filePath = fileToOpen.getAbsolutePath();
        } else {
            return;
        }

        // read the file and send entire file as message
        String seller = account.getName();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Date time = new Date(System.currentTimeMillis());
            String timeString = new SimpleDateFormat("HH:mm:ss").format(time);
            String formatting = account.getName() + ",;" + timeString + ",;";
            while ((line = br.readLine()) != null) {
                fm.addMessage(customer, seller, store, formatting + line);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    // display all messages with the customer. seller can choose out
    // of the options to send, send from file, edit, delete, export, or exit.
    public int displayMessages(String customer) {
        // initialize JFrame with relevant buttons
        if (!frame.isVisible()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    frame = new JFrame("Messaging");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(750, 500);
                    frame.setLocationRelativeTo(null);
                    frame.setLayout(new BorderLayout());

                    frame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            choice = "7";
                            pass = true;
                        }
                    });

                    Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

                    JPanel options = new JPanel();
                    options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
                    options.setBorder(padding);

                    JPanel options2 = new JPanel();
                    options2.setLayout(new FlowLayout());

                    JPanel options3 = new JPanel();
                    options3.setLayout(new FlowLayout());

                    textField = new JTextField(20);
                    sendButton = new JButton("Send");
                    sendButton.addActionListener(e -> {
                        message = textField.getText();
                        choice = "1";
                        pass = true;
                    });

                    options2.add(textField);
                    options2.add(sendButton);
                    options.add(options2);
                    options.add(options3);

                    sendFileButton = new JButton("Send from file");
                    sendFileButton.addActionListener(e -> {
                        choice = "2";
                        pass = true;
                    });

                    editButton = new JButton("Edit");
                    for (java.awt.event.ActionListener al : editButton.getActionListeners()) {
                        editButton.removeActionListener(al);
                    }
                    editButton.addActionListener(e -> {
                        choice = "3";
                        pass = true;
                    });

                    deleteButton = new JButton("Delete");
                    for (java.awt.event.ActionListener al : deleteButton.getActionListeners()) {
                        deleteButton.removeActionListener(al);
                    }
                    deleteButton.addActionListener(e -> {
                        choice = "4";
                        pass = true;
                    });

                    exportButton = new JButton("Export");
                    for (java.awt.event.ActionListener al : exportButton.getActionListeners()) {
                        exportButton.removeActionListener(al);
                    }
                    exportButton.addActionListener(e -> {
                        choice = "6";
                        pass = true;
                    });

                    exitButton = new JButton("Exit");
                    exitButton.addActionListener(e -> {
                        choice = "7";
                        frame.setVisible(false);
                        frame.dispose();
                        pass = true;
                    });

                    options3.add(sendFileButton);
                    options3.add(editButton);
                    options3.add(deleteButton);
                    options3.add(exportButton);
                    options3.add(exitButton);

                    frame.add(options, BorderLayout.SOUTH);

                    JPanel mainPanel = new JPanel();
                    mainPanel.setBorder(padding);
                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

                    JLabel title = new JLabel("Messaging " + customer);
                    title.setFont(title.getFont().deriveFont(25.0f));
                    title.setAlignmentX(Component.CENTER_ALIGNMENT);

                    messageLabel = new JTextArea("1. message message message");
                    messageLabel.setFont(messageLabel.getFont().deriveFont(15.0f));
                    messageLabel.setLayout(new BorderLayout(0, 0));
                    messageLabel.setBorder(padding);
                    messageLabel.setMaximumSize(new Dimension(700, 100));
                    messageLabel.setEditable(false);
                    messageLabel.setLineWrap(true);
                    messageLabel.setWrapStyleWord(true);
                    messageLabel.setFocusable(false);

                    mainPanel.add(title);

                    JPanel messagePanel = new JPanel();
                    messagePanel.setLayout(new BorderLayout());
                    messagePanel.add(messageLabel, BorderLayout.SOUTH);

                    main = new JScrollPane(messagePanel);
                    main.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    main.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    main.getVerticalScrollBar().setUnitIncrement(16);

                    mainPanel.add(main);

                    frame.add(mainPanel);
                    frame.setVisible(true);
                }
            });
        }

        // get current seller object, get messages, show messages
        String seller = account.getName();
        ArrayList<String> messages = fm.getMessagesSeller(customer, seller, store);

        if (messages == null) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    messageLabel.setText("");
                }
            });
            messages = new ArrayList<String>();
        }

        pass = false;

        while (true) {

            if (messages.size() == 0) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        messageLabel.setText("");

                        editButton.removeActionListener(editButton.getActionListeners()[0]);
                        deleteButton.removeActionListener(deleteButton.getActionListeners()[0]);
                        exportButton.removeActionListener(exportButton.getActionListeners()[0]);

                        editButton.addActionListener(e -> {
                            choice = "";
                            JOptionPane.showMessageDialog(null, "There are no messages to edit!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            pass = false;
                        });
                        deleteButton.addActionListener(e -> {
                            choice = "";
                            JOptionPane.showMessageDialog(null, "There are no messages to delete!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            pass = false;
                        });
                        exportButton.addActionListener(e -> {
                            choice = "";
                            JOptionPane.showMessageDialog(null, "There are no messages to export!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            pass = false;
                        });

                    }
                });

            } else {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        for (java.awt.event.ActionListener al : editButton.getActionListeners()) {
                            editButton.removeActionListener(al);
                        }
                        editButton.addActionListener(e -> {
                            choice = "3";
                            pass = true;
                        });

                        for (java.awt.event.ActionListener al : deleteButton.getActionListeners()) {
                            deleteButton.removeActionListener(al);
                        }
                        deleteButton.addActionListener(e -> {
                            choice = "4";
                            pass = true;
                        });

                        for (java.awt.event.ActionListener al : exportButton.getActionListeners()) {
                            exportButton.removeActionListener(al);
                        }
                        exportButton.addActionListener(e -> {
                            choice = "6";
                            pass = true;
                        });
                    }
                });

                String text = "";
                for (int i = 0; i < messages.size(); i++) {
                    String content = messages.get(i).split(",;")[1] + " - " +
                            messages.get(i).split(",;")[0] + ": " +
                            messages.get(i).split(",;")[2];
                    text += content + "\n";

                }

                final String finalText = text;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        messageLabel.setText(finalText);
                        frame.revalidate();
                    }
                });

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JScrollBar vertical = main.getVerticalScrollBar();
                        vertical.setValue(vertical.getMaximum());
                    }
                });

            }

            while (true) {
                System.out.print("");
                if (pass) {
                    return Integer.parseInt(choice);
                }
            }

        } // end loop

    } // end displayMessages

    // displays the messages sent by the user for the user to edit,
    // and prompts the user to enter the new message. saves to file.
    public void editMessage(String customer) {

        // initiate jframe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                editFrame = new JFrame("Edit Message");
                editFrame.setSize(750, 500);
                editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editFrame.setLocationRelativeTo(null);
                editFrame.setLayout(new BorderLayout());

                result = "";
                editFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        result = "--CANCEL--";
                        pass = true;
                    }
                });

                Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

                JPanel options = new JPanel();
                options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
                options.setBorder(padding);

                JLabel oldMessageLabel = new JLabel("Old Message: ");
                oldMessageLabel.setFont(oldMessageLabel.getFont().deriveFont(15.0f));

                oldMessages = new JComboBox<>();
                oldMessages.setMaximumRowCount(5);
                oldMessages.setPreferredSize(new Dimension(200, 25));

                JLabel newMessageLabel = new JLabel("New Message: ");
                newMessageLabel.setFont(newMessageLabel.getFont().deriveFont(15.0f));

                JTextField newMessage = new JTextField(20);

                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(e -> {
                    search = newMessage.getText();
                    result = oldMessages.getSelectedItem().toString().split(":")[0];
                    pass = true;
                });

                JPanel temp = new JPanel();
                temp.setLayout(new FlowLayout());
                temp.add(oldMessageLabel);
                temp.add(oldMessages);
                options.add(temp);

                JPanel temp2 = new JPanel();
                temp2.add(newMessageLabel);
                temp2.add(newMessage);
                options.add(temp2);

                JPanel temp3 = new JPanel();
                temp3.removeAll();
                temp3.add(saveButton);
                options.add(temp3);

                JPanel main = new JPanel();
                main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
                main.setBorder(padding);

                JLabel titleLabel = new JLabel("Edit Message");
                titleLabel.setFont(titleLabel.getFont().deriveFont(25.0f));
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel detailLabel = new JLabel("Showing most recent messages sent by seller first.");
                detailLabel.setFont(detailLabel.getFont().deriveFont(10.0f));
                detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel scrollPanel = new JPanel();
                scrollPanel.setLayout(new BorderLayout());
                scrollPanel.setBorder(padding);

                messageLabel2 = new JTextArea("abcdefg<br>hijklmn<br>opqrstu<br>vwxyz");
                messageLabel2.setFont(messageLabel2.getFont().deriveFont(15.0f));
                messageLabel2.setAlignmentY(Component.TOP_ALIGNMENT);
                messageLabel2.setEditable(false);
                messageLabel2.setLineWrap(true);
                messageLabel2.setWrapStyleWord(true);
                messageLabel2.setOpaque(false);
                messageLabel2.setFocusable(false);

                scrollPanel.add(messageLabel2, BorderLayout.NORTH);

                scroll = new JScrollPane(scrollPanel);
                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scroll.getVerticalScrollBar().setUnitIncrement(16);

                main.add(titleLabel);
                main.add(detailLabel);
                main.add(scroll);

                editFrame.add(main, BorderLayout.CENTER);
                editFrame.add(options, BorderLayout.SOUTH);

                editFrame.setVisible(true);

            }
        });

        pass = false;
        String seller = account.getName();
        ArrayList<String> messages = fm.getMessagesSeller(customer, seller, store);
        Collections.reverse(messages);

        // filter for only messages by seller
        for (int i = 0; i < messages.size(); i++) {
            if (!messages.get(i).split(",;")[0].equals(seller)) {
                messages.remove(i);
                i--;
            }
        }

        // add all messages to message label to display
        String labelText = "";
        for (int i = 0; i < messages.size(); i++) {
            String content = (i + 1) + ": " + messages.get(i).split(",;")[2];
            labelText += content + "\n";

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    oldMessages.addItem(content);
                }
            });

        }

        final String finalText = labelText;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messageLabel2.setText(finalText);
                editFrame.revalidate();
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messageLabel2.setCaretPosition(0);
                JScrollBar vertical = scroll.getVerticalScrollBar();
                vertical.setValue(vertical.getMinimum());
            }
        });

        // wait for user to select message to edit
        while (true) {
            System.out.print("");
            if (pass) {

                if (result.equals("--CANCEL--")) {
                    frame.dispose();
                    frame.setVisible(false);
                    return;
                }

                if (search.trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter a message to edit.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    pass = false;
                    continue;
                }

                try {
                    String oldMessage = messages.get(Integer.parseInt(result) - 1);
                    String newMessage = search;
                    Date time = new Date(System.currentTimeMillis());
                    String timeString = new SimpleDateFormat("HH:mm:ss").format(time);
                    fm.editMessage(customer, seller, store, oldMessage, account.getName() + ",;" +
                            timeString + ",;" +
                            newMessage);
                    editFrame.dispose();
                    editFrame.setVisible(false);
                    return;
                } catch (Exception e) {
                    editFrame.dispose();
                    editFrame.setVisible(false);
                    return;
                }
            }
        }

    } // end editMessage

    // displays the 5 most recent messages sent by the user for the user to delete,
    // and prompts the user to enter the new message. saves to file.
    public void deleteMessage(String customer) {

        // initiate jframe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                deleteFrame = new JFrame("Delete Message");
                deleteFrame.setSize(750, 500);
                deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                deleteFrame.setLocationRelativeTo(null);
                deleteFrame.setLayout(new BorderLayout());

                result = "";
                deleteFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        result = "--CANCEL--";
                        pass = true;
                    }
                });

                Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

                JPanel options = new JPanel();
                options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
                options.setBorder(padding);

                JLabel oldMessageLabel = new JLabel("Message for deletion: ");
                oldMessageLabel.setFont(oldMessageLabel.getFont().deriveFont(15.0f));

                oldMessages = new JComboBox<>();
                oldMessages.setMaximumRowCount(5);
                oldMessages.setPreferredSize(new Dimension(200, 25));

                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(e -> {
                    result = oldMessages.getSelectedItem().toString().split(":")[0];
                    pass = true;
                });

                JPanel temp = new JPanel();
                temp.setLayout(new FlowLayout());
                temp.add(oldMessageLabel);
                temp.add(oldMessages);
                options.add(temp);

                JPanel temp3 = new JPanel();
                temp3.removeAll();
                temp3.add(saveButton);
                options.add(temp3);

                JPanel main = new JPanel();
                main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
                main.setBorder(padding);

                JLabel titleLabel = new JLabel("Deletes Message");
                titleLabel.setFont(titleLabel.getFont().deriveFont(25.0f));
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel detailLabel = new JLabel("Showing most recent messages sent by seller first.");
                detailLabel.setFont(detailLabel.getFont().deriveFont(10.0f));
                detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel scrollPanel = new JPanel();
                scrollPanel.setLayout(new BorderLayout());
                scrollPanel.setBorder(padding);

                messageLabel3 = new JTextArea("<html>abcdefg<br>hijklmn<br>opqrstu<br>vwxyz</html>");
                messageLabel3.setFont(messageLabel3.getFont().deriveFont(15.0f));
                messageLabel3.setAlignmentY(Component.TOP_ALIGNMENT);
                messageLabel3.setEditable(false);
                messageLabel3.setLineWrap(true);
                messageLabel3.setWrapStyleWord(true);
                messageLabel3.setOpaque(false);
                messageLabel3.setFocusable(false);

                scrollPanel.add(messageLabel3, BorderLayout.NORTH);

                scroll = new JScrollPane(scrollPanel);
                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scroll.getVerticalScrollBar().setUnitIncrement(16);

                main.add(titleLabel);
                main.add(detailLabel);
                main.add(scroll);

                deleteFrame.add(main, BorderLayout.CENTER);
                deleteFrame.add(options, BorderLayout.SOUTH);

                deleteFrame.setVisible(true);

            }
        });

        pass = false;
        String seller = account.getName();
        ArrayList<String> messages = fm.getMessagesSeller(customer, seller, store);
        Collections.reverse(messages);

        // filter for only messages by seller
        for (int i = 0; i < messages.size(); i++) {
            if (!messages.get(i).split(",;")[0].equals(seller)) {
                messages.remove(i);
                i--;
            }
        }

        // add all messages to message label to display
        String labelText = "";
        for (int i = 0; i < messages.size(); i++) {
            String content = (i + 1) + ": " + messages.get(i).split(",;")[2];
            labelText += content + "\n";

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    oldMessages.addItem(content);
                }
            });

        }

        final String finalText = labelText;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messageLabel3.setText(finalText);
                deleteFrame.revalidate();
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messageLabel3.setCaretPosition(0);
                JScrollBar vertical = scroll.getVerticalScrollBar();
                vertical.setValue(vertical.getMinimum());
            }
        });

        while (true) {
            System.out.print("");
            if (pass) {
                break;
            }
        }

        if (result.equals("--CANCEL--")) {
            frame.dispose();
            frame.setVisible(false);
            return;
        }

        try {
            String message = messages.get(Integer.parseInt(result) - 1);
            fm.deleteMessageSeller(customer, seller, store, message);
            deleteFrame.dispose();
            deleteFrame.setVisible(false);
            return;
        } catch (Exception e) {
            deleteFrame.dispose();
            deleteFrame.setVisible(false);
        }

    } // end deleteMessage

    // export messages into csv using customer username
    // fileName is Exported_Conversation_From-Seller-Store_To-Customer.csv
    public void exportMessages(String customer) {
        String seller = account.getName();
        String fileName = String.format("Exported_Conversation_From-%s-%s_To-%s.csv", seller, store, customer);
        fm.exportConversationSeller(customer, seller, store, fileName);
        JOptionPane.showMessageDialog(null, "Successfully exported to " + fileName, "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
