import java.io.*;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Account Class
 * This class is used to create, edit, and delete accounts in the messaging system and to specify the user roles.
 * The Account class has one constructor to create a new account object. This constructor calls the createUser()
 * method to check if the inputs provided by the user do not correspond to any values already present in the file.
 * In case the createUser() method returns false, an exception is thrown (UserExistsException).
 * The Account class has a second constructor to log in to an already existing account object. This constructor calls
 * the validateUser() method to check if the inputs provided by the user correspond to an existing object in the file.
 * In case the validateUser() method returns false, an exception is thrown (InvalidUserInfoException).
 * There are mutator and accessor methods present for the fields in the class.
 * The getAllUsers() method is used to read the "account.txt" file and add the values in an arraylist. This method
 * has been synchronized as it is the common method to read data directly from the "account.txt" file.
 * The changeUserName() and changeUserPassword() method are used to change the name of the user and their password
 * respectively.
 * The deleteUser() method is used to delete the logged-in user. On the completion of this method, the user will be
 * taken back to the login menu present in the main method.
 * The blockUser() method is used by the logged-in user to block another user. The method checks the passed value
 * with the elements present in the array and removes an element when a match is found.
 * The getAllNonBlockingUsers() method is used to view a list of users who have not blocked the logged-in user.
 * The getAllUserThatCanBeBlocked() method is used to return a filtered list of users which the logged-in user can
 * block.
 * After the completion of any changes to the logged-in user attribute, the changes are written back to the file
 * "account.txt" using the saveAccounts() method. This ensures data consistency. This method has been synchronized as
 * it directly accesses the common resource (account.txt) to write the data back.
 * Purdue University -- CS18000 -- Fall 2022 -- Project 05
 *
 * @author Siddharth Nadgaundi, lab sec l04
 * @version December 08, 2022
 */

public class Account {
    private String email;
    private String name;
    private String uRole;
    private String password;
    private String blockedUsers = "";
    public static Object obj = new Object();


    public Account() {
        this.email = "";
        this.name = "";
        this.uRole = "";
        this.password = "";
        this.blockedUsers = "";
    }

    public Account(String email, String uName, String password, String role) throws UserExistsException {
        this.email = email;
        this.name = uName;
        this.password = password;
        this.uRole = role;
        this.blockedUsers = "";

        if (!createUser()) {
            throw new UserExistsException("The passed user already exists");
        }
    }

    public Account(String email, String password) throws InvalidUserInfoException {
        this.email = email;
        this.password = password;

        if (!validateUser(password)) {
            throw new InvalidUserInfoException("Invalid user ID(email) or Password");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getuRole() {
        return uRole;
    }

    public String getBlockedUsers() {
        return blockedUsers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean validateUser(String accPassword) {
        boolean rtn = false;
        ArrayList<Account> temp = getAllUsers();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getEmail().equals(this.getEmail()) && temp.get(i).password.equals(accPassword)) {
                this.password = accPassword;
                this.uRole = temp.get(i).getuRole();
                this.name = temp.get(i).getName();
                this.blockedUsers = temp.get(i).getBlockedUsers();
                rtn = true;
            }
        }
        return rtn;
    }

    private boolean createUser() {
        boolean rtn = false;
        ArrayList<Account> temp = getAllUsers();
        if (!(temp.isEmpty())) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getEmail().equals(this.getEmail())) {
                    return rtn;
                }
            }
        }
        temp.add(this);

        rtn = saveAccounts(temp);
        return rtn;
    }

    public synchronized ArrayList<Account> getAllUsers() {
        FileReader fr = null;
        String temp = "";
        String line1 = "";
        String line2 = "";
        ArrayList<Account> allUserList = new ArrayList<>();
        try {
            fr = new FileReader("account.txt");
            BufferedReader bfr = new BufferedReader(fr);
            line1 = bfr.readLine();
            line2 = bfr.readLine();

            while (line1 != null && line2 != null) {
                Account user = new Account();
                temp = line1.substring(0, line1.indexOf(","));
                user.name = temp;
                line1 = line1.substring(line1.indexOf(",") + 1);
                temp = line1.substring(0, line1.indexOf(","));
                user.email = temp;
                line1 = line1.substring(line1.indexOf(",") + 1);
                temp = line1.substring(0, line1.indexOf(","));
                user.password = temp;
                line1 = line1.substring(line1.indexOf(",") + 1);
                temp = line1.substring(0);
                user.uRole = temp;
                line1 = line1.substring(line1.indexOf(",") + 1);

                user.blockedUsers = line2.substring(line2.indexOf(":") + 1);
                allUserList.add(user);
                line1 = bfr.readLine();
                line2 = bfr.readLine();
            }

            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allUserList;
    }

    public boolean changeUserName(Account account, String newName) {
        boolean rtn = false;
        ArrayList<Account> temp = getAllUsers();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getEmail().equals(account.getEmail()) && temp.get(i).getName().equals(account.getName()) &&
                    temp.get(i).getuRole().equals(account.getuRole())) {
                account.name = newName;
                temp.get(i).name = newName;
                rtn = true;
            }
        }

        if (rtn == true) {
            rtn = saveAccounts(temp);
        }

        return rtn;
    }

    public boolean changeUserPassword(Account account, String oldPassword, String newPassword) {
        boolean rtn = false;
        ArrayList<Account> temp = getAllUsers();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getEmail().equals(account.getEmail()) && temp.get(i).getName().equals(account.getName()) &&
                    temp.get(i).getuRole().equals(account.getuRole()) && temp.get(i).password.equals(oldPassword)) {
                account.password = newPassword;
                temp.get(i).password = newPassword;
                rtn = true;
            }
        }
        if (rtn == true) {
            rtn = saveAccounts(temp);
        }

        return rtn;
    }

    public boolean deleteUser(Account account) {
        boolean rtn = false;
        ArrayList<Account> temp = getAllUsers();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getEmail().equals(account.getEmail()) && temp.get(i).getName().equals(account.getName()) &&
                    temp.get(i).getuRole().equals(account.getuRole())) {
                temp.remove(i);
                rtn = true;
            }
        }
        if (rtn == true) {
            rtn = saveAccounts(temp);
        }

        if (account.uRole.toLowerCase().equals("seller")) {
            FileManipulation fm = new FileManipulation();
            ArrayList<String> stores = fm.getStores(account.getName());

            for (int i = 0; i < stores.size(); i++) {
                fm.deleteStore(stores.get(i));
            }
        }

        return rtn;
    }

    public boolean blockUser(Account account, String blockedUserEmail) {
        boolean rtn = false;
        ArrayList<Account> temp = getAllUsers();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getEmail().equals(account.getEmail()) && temp.get(i).getName().equals(account.getName()) &&
                    temp.get(i).getuRole().equals(account.getuRole())) {
                account.blockedUsers = account.getBlockedUsers() + blockedUserEmail + ",";
                temp.get(i).blockedUsers = temp.get(i).getBlockedUsers() + blockedUserEmail + ",";
                rtn = true;
            }
        }
        if (rtn == true) {
            rtn = saveAccounts(temp);
        }
        return rtn;
    }

    public ArrayList<Account> getAllNonBlockingUsers(Account account) {
        ArrayList<Account> temp = getAllUsers();
        Predicate<Account> filterName = tempAccount -> tempAccount.getBlockedUsers().contains(account.getEmail());
        temp.removeIf(filterName);
        return temp;
    }

    public String getAllUserThatCanBeBlocked(Account account) {
        ArrayList<Account> temp = getAllUsers();
        String blockedUsersAccounts = "";
        String temp1 = "";
        Predicate<Account> filterName = tempAccount -> tempAccount.getuRole().equals(account.getuRole());
        temp.removeIf(filterName);
        blockedUsersAccounts = account.getBlockedUsers();
        long count = blockedUsersAccounts.chars().filter(ch -> ch == ',').count();

        while (count > 0) {
            temp1 = blockedUsersAccounts.substring(0, blockedUsersAccounts.indexOf(","));
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getEmail().equals(temp1)) {
                    temp.remove(i);
                }
            }
            if (count > 1) {
                blockedUsersAccounts = blockedUsersAccounts.substring(blockedUsersAccounts.indexOf(",") + 1);
            }
            count--;
        }
        temp1 = "";

        for (int i = 0; i < temp.size(); i++) {
            temp1 = temp1 + (i + 1) + "." + temp.get(i).getEmail() + "\n";
        }
        return temp1;
    }

    private synchronized boolean saveAccounts(ArrayList<Account> accounts) {
        boolean rtn = false;
        String line1 = "";
        String line2 = "";
        try {
            FileOutputStream fos1 = new FileOutputStream("account.txt", false);
            PrintWriter pw = new PrintWriter(fos1);
            for (int i = 0; i < accounts.size(); i++) {
                line1 = accounts.get(i).getName() + "," + accounts.get(i).getEmail() + "," + accounts.get(i).password +
                        "," + accounts.get(i).getuRole();
                line2 = "BlockedUser:" + accounts.get(i).blockedUsers;
                pw.println(line1);
                pw.println(line2);
            }
            pw.close();
            rtn = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtn;
    }
} 
