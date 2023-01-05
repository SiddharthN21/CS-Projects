import java.io.*;
import java.util.*;

/**
 * Customer Dashboard
 * This class is used to return and sort the dashboard of a buyer that has logged in.
 *
 * @author Aaron Shih, L04
 * @version November 10, 2022
 */
public class CustomerDashboard {
    private static FileManipulation fileManipulation = new FileManipulation();

    /**
     * Call this function to get the dashboard for a CUSTOMER.
     *
     * @param storeSellerHashtable
     * @param customer
     */
    public static String[] getDash(Hashtable<String, String> sellers, String customer) {
        //static method to get the dashboard
        ArrayList<String> dash = new ArrayList<String>(); 
		//hashtable in which the key is the seller name, the value is the store name
        try {
            for (Map.Entry<String, String> entry : sellers.entrySet()) {
                String store = entry.getKey();
                String sell = entry.getValue();
                int msgCount = 0;
                int msgSent = 0;
                ArrayList<String> messages = fileManipulation.getMessagesCustomer(customer, sell, store); 
				//gets the messages
                if (messages == null) {
                    continue;
                }
                for (int j = 0; j < messages.size(); j++) {
                    String name = messages.get(j).split(",;")[0];
                    String mLine = messages.get(j).substring(0, name.length());
                    if (name.equals(sell)) {
                        msgCount++;
                    } else {
                        msgSent++;
                    }
                }
                dash.add("Messages received from " + sell + ": " + msgCount +
                        "\nMessages sent to " + sell + ": " + msgSent); 
						//displays number of messages sent by the customer
            }
            return dash.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //

    /**
     * Call this function to sort the dashboard for a CUSTOMER. Sorts by stores with the most frequent messages.
     *
     * @param storeSellerHashtable with stores as the key, seller as the value
     * @param customer
     */
    public static String[] sortDash(Hashtable<String, String> sellers, String customer) {
        //sorts stores by most frequent messagers
        //
        try {
            String[] dash = getDash(sellers, customer);
            Hashtable<String, Integer> toSort = new Hashtable<String, Integer>();
            for (int i = 0; i < dash.length; i++) {
                String[] tempDash = dash[i].split(": |\n");
                toSort.put(dash[i], Integer.parseInt(tempDash[1])); //adds message count of the seller
            }
            for (int i = 0; i < dash.length - 1; i++) {
                for (int j = 0; j < dash.length - 1 - i; j++) {
                    if (toSort.get(dash[j + 1]) > toSort.get(dash[j])) {
                        String temp = dash[j + 1];
                        dash[j + 1] = dash[i];
                        dash[i] = temp;
                    }
                }
            }
            return (dash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
