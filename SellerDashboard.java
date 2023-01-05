import java.io.*;
import java.util.*;

/**
 * Seller Dashboard
 * This class is used to return and sort the dashboard of a seller that has logged in.
 *
 * @author Aaron Shih, L04
 * @version November 9, 2022
 */
public class SellerDashboard {
    private static FileManipulation fileManipulation = new FileManipulation();
    //Call this function to get the dashboard for a SELLER.

    /**
     * @param customers
     */
    public static String[] getDash(Hashtable<String, String[]> customers) {
        ArrayList<String> dash = new ArrayList<String>();

        try {
            //the frequency of words used in messages is recorded across all messages 
            // (requirements said overall messages)
            ArrayList<String> freqWords = new ArrayList<String>();
            ArrayList<Integer> frequency = new ArrayList<Integer>();
            Hashtable<String, Integer> messageDict = new Hashtable<String, Integer>(); 
                                                            //dictionary that records message frequency
            for (Map.Entry<String, String[]> entry : customers.entrySet()) {
                String store = entry.getKey();
                String[] c = entry.getValue();
                if (c.length > 0) {
                    dash.add("Store - " + store);
                }
                for (int i = 0; i < c.length; i++) {
                    String customer = c[i];
                    int msgCount = 0;
                    ArrayList<String> messages = fileManipulation.getMessagesSeller(customer, 
                                            fileManipulation.getSeller(store), store); //gets the messages
                    //if readmessages/equivalent doesn't strip names, that can be implemented later
                    for (int j = 0; j < messages.size(); j++) {
                        String name = messages.get(j).split(",;")[0];
                        String mLine = messages.get(j).split(",;")[2];
                        String[] splitMessages = mLine.split(" ");
                        if (name.equals(c[i])) {
                            msgCount++;
                        }
                        for (int k = 0; k < splitMessages.length; k++) {
                            if (name.equals(c[i])) {
                                if (messageDict.get(splitMessages[k]) == null) {
                                    messageDict.put(splitMessages[k], 1);
                                } else {
                                    messageDict.put(splitMessages[k], messageDict.get(splitMessages[k]) + 1);
                                }
                            }
                        }
                    }
                    for (Map.Entry<String, Integer> e : messageDict.entrySet()) {
                        String key = e.getKey();
                        Integer freq = e.getValue();
                        if (frequency.size() == 0) {
                            frequency.add(freq);
                            freqWords.add(key);
                        }
                        for (int j = 0; j < frequency.size(); j++) {
                            if (frequency.get(j) <= freq) {
                                freqWords.add(j, key);
                                frequency.add(j, freq);
                                break;
                            }
                            if (j == frequency.size() - 1) {
                                freqWords.add(key);
                                frequency.add(freq);
                            }
                        }
                    }
                    for (int j = frequency.size() - 1; j > 0; j--) {
                        if (freqWords.get(j).equals(freqWords.get(j - 1))) { //removes duplicates
                            frequency.remove(j);
                            freqWords.remove(j);
                        }
                    }
                    dash.add(customer + " message count: " + msgCount); 
                    //displays number of messages sent by the customer
                }
            }
            while (frequency.size() < 3) {
                freqWords.add("N/A");
                frequency.add(0);
            }
            int[] top3Count = new int[]{frequency.get(0), frequency.get(1), frequency.get(2)};
            String[] top3Words = new String[]{freqWords.get(0), freqWords.get(1), freqWords.get(2)};
            dash.add(String.format("Top three words across overall messages: %s (used %d times), %s (used %d times), " +
                                    "%s (used %d times).",
                    top3Words[0], top3Count[0], top3Words[1], top3Count[1], top3Words[2], top3Count[2]));
            return dash.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Call this function to sort the dashboard for a SELLER. Sorts each store by customers with the most
     * frequent messages.
     *
     * @param customerHashtable Keys: store Value: customers
     * @param customer
     */
    public static String[] sortDash(Hashtable<String, String[]> customers) {
        //sorts stores by most frequent messagers
        try {
            String[] dash = getDash(customers);
            Hashtable<String, Integer> toSort = new Hashtable<String, Integer>();
            int start = 0;
            for (int i = 0; i < dash.length; i++) {
                String[] tempDash = dash[i].split(": ");
                if (tempDash.length == 2) {
                    try {
                        toSort.put(dash[i], Integer.parseInt(tempDash[1])); //adds message count
                    } catch (Exception e) {
                        for (int k = start + 1; k < i && k < dash.length - 1; k++) {
                            for (int j = start; j < i - 2 && j < dash.length - 1; j++) {
                                if (toSort.get(dash[j + 1]) > toSort.get(dash[j])) {
                                    String temp = dash[j + 1];
                                    dash[j + 1] = dash[i];
                                    dash[i] = temp;
                                }
                            }
                        }
                        start = i + 1;
                    }
                } else if (tempDash.length == 1) {
                    for (int k = start + 1; k < i; k++) {
                        for (int j = start; j < i; j++) {
                            if (toSort.get(dash[j + 1]) > toSort.get(dash[j])) {
                                String temp = dash[j + 1];
                                dash[j + 1] = dash[i];
                                dash[i] = temp;
                            }
                        }
                    }
                    start = i + 1;
                }
            }
            return (dash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
