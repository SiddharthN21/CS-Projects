import java.io.*;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit;

/**
 * A class which handles file manipulation
 * in order to prepare and store information
 * for the messaging classes. Information is
 * also processed for exporting files.
 *
 * @author Lucas Munteanu Sec: L04
 * @version 11/12/2022
 */
public class FileManipulation {
    private ArrayList<String> deletedMessagesBooleans = new ArrayList<>();

    // Constructor which creates the file for storing the information of the sellers by their stores
    public FileManipulation() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(("Stores.txt"), true));
            pw.print("");
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Associates a store to a seller and saves the information.
     *
     * @param seller    name of seller
     * @param storeName name of seller's store
     */
    public synchronized void addStore(String seller, String storeName) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(("Stores.txt"), true));
            pw.println(seller + ";" + storeName);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the name of all the stores associated with a seller.
     *
     * @param seller name of seller
     * @return An ArrayList of Strings containing the stores owned by the inputted seller
     */
    public synchronized ArrayList<String> getStores(String seller) {
        ArrayList<String> listOfStores = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Stores.txt"));

            String line = bfr.readLine();
            while (line != null) {

                if (line.substring(0, line.indexOf(";")).equals(seller)) {
                    listOfStores.add(line.substring(line.indexOf(";") + 1));
                }

                line = bfr.readLine();
            }
            bfr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfStores;
    }

    /**
     * Gets the name of all the stores in the marketplace.
     *
     * @return An ArrayList of Strings containing the all the stores in the marketplace
     */
    public synchronized ArrayList<String> getAllStores() {
        ArrayList<String> listOfStores = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Stores.txt"));

            String line = bfr.readLine();
            while (line != null) {

                listOfStores.add(line.substring(line.indexOf(";") + 1));

                line = bfr.readLine();
            }
            bfr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfStores;
    }

    /**
     * Deletes the specified store within "Stores.txt".
     *
     * @param storeForDeletion the store to be deleted
     */
    public synchronized void deleteStore(String storeForDeletion) {
        ArrayList<String> listOfStores = getAllStores();
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(("Stores.txt"), false));

            for (String store : listOfStores) {
                if (!(store.equals(storeForDeletion))) {
                    pw.println(store);
                }
            }
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all the existing conversations between the customer and seller.
     * This to account for the fact that conversations between the same customer
     * and seller could occur through different stores.
     *
     * @param customer name of customer
     * @param seller   name of seller
     * @return An ArrayList of Strings of the stores associated with the sellers
     */
    public synchronized ArrayList<String> getConversations(String customer, String seller) {
        ArrayList<String> listOfStores = getStores(seller);
        ArrayList<String> listOfConversations = new ArrayList<>();

        for (String sellerStore : listOfStores) {
            if (new File(customer + "_" + seller + "_" + sellerStore + ".txt").isFile()) {
                listOfConversations.add("Customer\"" + customer + "\"_Seller\"" + seller + "\"_SellerStore\"" +
                        sellerStore + "\"");
            }
        }

        return listOfConversations;
    }

    /**
     * Gets the name of the seller that owns the store.
     *
     * @param storeName name of store
     * @return An ArrayList of Strings containing the name of the seller that owns the store inputted
     */
    public synchronized String getSeller(String storeName) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("Stores.txt"));

            String line = bfr.readLine();
            while (line != null) {

                if (line.substring(line.indexOf(";") + 1).equals(storeName)) {
                    return line.substring(0, line.indexOf(";"));
                }

                line = bfr.readLine();
            }
            bfr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Adds a message to a new or existing conversation between a customer and seller.
     *
     * @param customer    name of customer in conversation
     * @param seller      name of seller in conversation
     * @param sellerStore name of seller's store in conversation
     * @param message     String of text sent in a conversation
     */
    public synchronized void addMessage(String customer, String seller, String sellerStore, String message) {
        try {

            PrintWriter pw = new PrintWriter(new FileOutputStream((customer + "_" + seller + "_" +
                    sellerStore + ".txt"), true));
            pw.println("true;true");
            pw.println(message);
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits a message in an existing conversation between customer and seller.
     *
     * @param customer    name of customer in conversation
     * @param seller      name of seller in conversation
     * @param sellerStore name of seller's store in conversation
     * @param oldMessage  Old String of text sent in a conversation
     * @param newMessage  New String of text to replace old message in a conversation
     */
    public synchronized void editMessage(String customer, String seller, String sellerStore, String oldMessage, String newMessage) {
        try {
            ArrayList<String> messages = getMessages(customer, seller, sellerStore);
            PrintWriter pw = new PrintWriter(new FileOutputStream((customer + "_" + seller + "_" + sellerStore +
                    ".txt"), false));

            for (int i = 0; i < messages.size(); ++i) {
                pw.println(deletedMessagesBooleans.get(i));
                if (oldMessage.equals(messages.get(i))) {
                    pw.println(newMessage);
                } else {
                    pw.println(messages.get(i));
                }
            }
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a message in a conversation from the perspective from the customer.
     *
     * @param customer           name of customer in conversation
     * @param seller             name of seller in conversation
     * @param sellerStore        name of seller's store in conversation
     * @param messageForDeletion String in conversation to be deleted (from customer's perspective)
     */
    public synchronized void deleteMessageCustomer(String customer, String seller, String sellerStore, String messageForDeletion) {
        try {
            ArrayList<String> messages = getMessages(customer, seller, sellerStore);
            PrintWriter pw = new PrintWriter(new FileOutputStream((customer + "_" + seller + "_" + sellerStore +
                    ".txt"), false));
            String tempMessagesBooleans = "";

            for (int i = 0; i < messages.size(); ++i) {
                if (messageForDeletion.equals(messages.get(i))) {
                    tempMessagesBooleans = "false" + deletedMessagesBooleans.get(i).substring(
                            deletedMessagesBooleans.get(i).indexOf(";"));
                    pw.println(tempMessagesBooleans);
                    deletedMessagesBooleans.set(i, tempMessagesBooleans);
                } else {
                    pw.println(deletedMessagesBooleans.get(i));
                }
                pw.println(messages.get(i));
            }
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a message in a conversation from the perspective from the seller.
     *
     * @param customer           name of customer in conversation
     * @param seller             name of seller in conversation
     * @param sellerStore        name of seller's store in conversation
     * @param messageForDeletion String in conversation to be deleted (from seller's perspective)
     */
    public synchronized void deleteMessageSeller(String customer, String seller, String sellerStore, String messageForDeletion) {
        try {
            ArrayList<String> messages = getMessages(customer, seller, sellerStore);
            PrintWriter pw = new PrintWriter(new FileOutputStream((customer + "_" + seller + "_" + sellerStore +
                    ".txt"), false));
            String tempMessagesBooleans = "";

            for (int i = 0; i < messages.size(); ++i) {
                if (messageForDeletion.equals(messages.get(i))) {
                    tempMessagesBooleans = deletedMessagesBooleans.get(i).substring(
                            0, deletedMessagesBooleans.get(i).indexOf(";")) + ";false";
                    pw.println(tempMessagesBooleans);
                    deletedMessagesBooleans.set(i, tempMessagesBooleans);
                } else {
                    pw.println(deletedMessagesBooleans.get(i));
                }
                pw.println(messages.get(i));
            }
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves messaging information based on the customer and seller in the conversation.
     *
     * @param customer    name of customer in conversation
     * @param seller      name of seller in conversation
     * @param sellerStore name of seller's store in conversation
     * @return An ArrayList of Strings containing the conversation history between the seller and the customer
     */
    public synchronized ArrayList<String> getMessages(String customer, String seller, String sellerStore) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(customer + "_" + seller + "_" + sellerStore +
                    ".txt"));
            deletedMessagesBooleans = new ArrayList<>();

            String viewingBooleans = bfr.readLine();
            String line = bfr.readLine();
            while (line != null) {

                deletedMessagesBooleans.add(viewingBooleans);
                messages.add(line);

                viewingBooleans = bfr.readLine();
                line = bfr.readLine();
            }
            bfr.close();

            return messages;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves messaging information based on the customer and seller in the conversation.
     * Displays messages from the seller's perspective.
     *
     * @param customer    name of customer in conversation
     * @param seller      name of seller in conversation
     * @param sellerStore name of seller's store in conversation
     * @return An ArrayList of Strings containing the conversation history between the seller and the customer
     */
    public synchronized ArrayList<String> getMessagesSeller(String customer, String seller, String sellerStore) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(customer + "_" + seller + "_" + sellerStore +
                    ".txt"));
            deletedMessagesBooleans = new ArrayList<>();

            String viewingBooleans = bfr.readLine();
            String line = bfr.readLine();
            while (line != null) {
                deletedMessagesBooleans.add(viewingBooleans);
                if (Boolean.parseBoolean(viewingBooleans.substring(viewingBooleans.indexOf(";") + 1))) {
                    messages.add(line);
                }

                viewingBooleans = bfr.readLine();
                line = bfr.readLine();
            }
            bfr.close();

            return messages;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves messaging information based on the customer and seller in the conversation.
     * Displays messages from the customer's perspective.
     *
     * @param customer    name of customer in conversation
     * @param seller      name of seller in conversation
     * @param sellerStore name of seller's store in conversation
     * @return An ArrayList of Strings containing the conversation history between the seller and the customer
     */
    public synchronized ArrayList<String> getMessagesCustomer(String customer, String seller, String sellerStore) {
        ArrayList<String> messages = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(customer + "_" + seller + "_" + sellerStore +
                    ".txt"));
            deletedMessagesBooleans = new ArrayList<>();

            String viewingBooleans = bfr.readLine();
            String line = bfr.readLine();
            while (line != null) {
                deletedMessagesBooleans.add(viewingBooleans);
                if (Boolean.parseBoolean(viewingBooleans.substring(0, viewingBooleans.indexOf(";")))) {
                    messages.add(line);
                }

                viewingBooleans = bfr.readLine();
                line = bfr.readLine();
            }
            bfr.close();

            return messages;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves all the customers available to freely message from the seller's perspective.
     *
     * @param seller The seller account of the current user
     * @return An ArrayList of Strings containing all the customers visible to the seller
     */
    public ArrayList<String> getAllCustomerUsernames(Account seller) {
        ArrayList<Account> allUsers = new ArrayList<>();
        ArrayList<String> customerUsername = new ArrayList<>();
        Account allAccounts = new Account();

        try {
            allUsers = allAccounts.getAllNonBlockingUsers(seller);

            for (Account account : allUsers) {
                if (account.getuRole().equals("customer")) {
                    customerUsername.add(account.getName());
                }
            }

            return customerUsername;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves all the sellers available to freely message from the customer's perspective.
     *
     * @param customer The customer account of the current user
     * @return An ArrayList of Strings containing all the sellers visible to the customer
     */
    public ArrayList<String> getAllSellerUsernames(Account customer) {
        ArrayList<Account> allUsers = new ArrayList<>();
        ArrayList<String> sellerUsername = new ArrayList<>();
        Account allAccounts = new Account();

        try {
            allUsers = allAccounts.getAllNonBlockingUsers(customer);

            for (Account account : allUsers) {
                if (account.getuRole().equals("seller")) {
                    sellerUsername.add(account.getName());
                }
            }

            return sellerUsername;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Exports the information of an existing conversation to a .csv file stated by the seller.
     *
     * @param customer    name of customer in conversation
     * @param seller      name of seller in conversation
     * @param sellerStore name of seller's store in conversation
     * @param filePath    the file path of the .csv to be exported
     */
    public synchronized void exportConversationCustomer(String customer, String seller, String sellerStore, String filePath) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(customer + "_" + seller + "_" + sellerStore +
                    ".txt"));
            PrintWriter pw = new PrintWriter(new FileOutputStream(filePath, false));
            deletedMessagesBooleans = new ArrayList<>();

            pw.println("Participants" + "|" + "Message Sender" + "|" + "Timestamp" + "|" + "Contents");

            String sender;
            String timestamp;

            String viewingBooleans = bfr.readLine();
            String line = bfr.readLine();
            while (line != null) {
                deletedMessagesBooleans.add(viewingBooleans);

                if (Boolean.parseBoolean(viewingBooleans.substring(0, viewingBooleans.indexOf(";")))) {
                    sender = line.substring(0, line.indexOf(",;"));
                    line = line.substring(line.indexOf(",;") + 2);
                    timestamp = line.substring(0, line.indexOf(",;"));
                    line = line.substring(line.indexOf(",;") + 2);

                    pw.println("Customer\"" + customer + "\"_Seller\"" + seller + "\"_SellerStore\"" +
                            sellerStore + "\"|" + sender + "|" + timestamp + "|" + line);
                }

                viewingBooleans = bfr.readLine();
                line = bfr.readLine();
            }
            bfr.close();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exports the information of an existing conversation to a .csv file stated by the customer.
     *
     * @param customer    name of customer in conversation
     * @param seller      name of seller in conversation
     * @param sellerStore name of seller's store in conversation
     * @param filePath    the file path of the .csv to be exported
     */
    public synchronized void exportConversationSeller(String customer, String seller, String sellerStore, String filePath) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(customer + "_" + seller + "_" + sellerStore +
                    ".txt"));
            PrintWriter pw = new PrintWriter(new FileOutputStream(filePath, false));
            deletedMessagesBooleans = new ArrayList<>();

            pw.println("Participants" + "|" + "Message Sender" + "|" + "Timestamp" + "|" + "Contents");

            String sender;
            String timestamp;

            String viewingBooleans = bfr.readLine();
            String line = bfr.readLine();
            while (line != null) {
                deletedMessagesBooleans.add(viewingBooleans);

                if (Boolean.parseBoolean(viewingBooleans.substring(viewingBooleans.indexOf(";") + 1))) {
                    sender = line.substring(0, line.indexOf(",;"));
                    line = line.substring(line.indexOf(",;") + 2);
                    timestamp = line.substring(0, line.indexOf(",;"));
                    line = line.substring(line.indexOf(",;") + 2);

                    pw.println("Customer\"" + customer + "\"_Seller\"" + seller + "\"_SellerStore\"" +
                            sellerStore + "\"|" + sender + "|" + timestamp + "|" + line);
                }

                viewingBooleans = bfr.readLine();
                line = bfr.readLine();
            }
            bfr.close();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
