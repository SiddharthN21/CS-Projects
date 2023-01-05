**Test 1: User log in**

Steps:

1. User launches application.
2. User selects the "LOGIN..." button.
3. User selects user email textbox.
4. User enters user "a5@xyz.com" via the keyboard.
5. User selects the password textbox.
6. User enters password "pass5" via keyboard
7. User selects the "Log in" button. 
Expected result: Application verifies the user's username and password and loads their homepage automatically. 

Test Status: Passed. 

**Test 2: User creation**

Steps:

1. User launches application
2. User selects the "CREATE U..." button.
3. User enters username "test1".
4. User enters user email "test1@mail.com".
5. User enters password "test1".
6. User clicks "OK".
7. User clicks on user role Customer.
8. User clicks "OK".

Expected result: Application verifies that user's username and email have not been used. Since the user does not select a role, it will prompt the user to select a role until they do, at which point the account is created and the main menu is loaded.

Test Status: Passed.

**Test 3: Exiting the application**

Steps:

1. User launches the application.
2. User selects "EXIT"

Expected result: Application displays a JOptionPane window thanking the user and closes.

Test Status: Passed.

**Test 4: Attempting to open the Customer Dashboard**

1. Having created the account test1, login with the username "test1@mail.com" and password "test1".
2. Click on "Dashboard".

Expected result: Application finds that the user has had no conversations and sends a message regarding this, then closes the dashboard.

Test Status: Passed.


**Test 5: Opening the Seller Dashboard**

1. Use the premade account "a5@xyz.com" with password "pass5" to login.
2. Click on "Dashboard".

Expected result: Shows a dashboard with the statistics of a5's messages.

Test Status: Passed


**Test 6: Seller Dashboard Sorting**

1. With the premade account from test 5, open messaging and send a message to user test1.
2. Click on "Dashboard"
3. Click on "Sort Dashboard"

Expected result: User a5's dashboard is sorted.

Test Status: Passed

---

> Please clear all messaging files, exported CSV files, and clear the Stores.txt file. (messaging files look like eg. "a4_a5_a5's store.txt", exported CSV files look like eg. "Exported_Conversation_From-a5-a5's store_To-a4.csv")

**Test 7: Seller Messaging Sending Message**

1. Login using account "a5@xyz.com"
2. Open "Messaging"
3. No stores, so create a store "a5's store" by entering the name in the textbox and pressing "Submit".
4. Select customer "a4" to message, then press "Submit"
5. Send message "Hello World" by entering in the textbox and pressing "Send"
6. Send message "CS is cool!" and press "Send"

Expected result: Screen displays that there are no stores to message from, and then select a customer to message, then a message is sent.

Test Status: Passed


**Test 8: Seller Messaging Editing Message**

1. Login using account "a5@xyz.com"
2. Open "Messaging"
3. Select previous conversation "To a4 on a5's store"
4. Press "Edit"
5. Select previous message "Hello World"
6. Enter in new message "Hello there!", press "Save"

Expected result: Screen displays the edited message properly.

Test Status: Passed


**Test 9: Seller Messaging Deleting Message**

1. Login using account "a5@xyz.com"
2. Open "Messaging"
3. Select previous conversation "To a4 on a5's store"
4. Press "Delete"
5. Select the message "Hello there!", press save.

Expected result: Screen does not display the deleted message.

Test Status: Passed


**Test 10: Seller Messaging Sending Message through File**

1. Login using account "a5@xyz.com"
2. Open "Messaging"
3. Select previous conversation "To a4 on a5's store"
4. Press "Send from File"
5. Select the file "testMessage.txt", press "Open"

Expected result: Program sends the contents of the file properly, showing all the lines of the txt file.

Test Status: Passed


**Test 11: Seller Messaging Export Messages**

1. Login using account "a5@xyz.com"
2. Open "Messaging"
3. Select previous conversation "To a4 on a5's store"
4. Press "Export"

Expected result: JOptionPane displays a success message, and the Exported_Conversation file contains all the messaging contents.

Test Status: Passed


**Test 12: Seller Messaging Exit Program**

1. Login using account "a5@xyz.com"
2. Open "Messaging"
3. Select previous conversation "To a4 on a5's store"
4. Press "Exit"

Expected result: Program exits out of Messaging successfully.

Test Status: Passed


**Test 13: Seller Messaging Creating New Conversation**

1. Login using account "a5@xyz.com"
2. Open "Messaging"
3. Select "New"
4. Select "Submit" on the stores page
5. Select "Search"
6. Type in "a8"
7. Select "a8" for the customer to message
8. Send message "test"

Expected result: Program creates new conversation file, and message is successfully sent.

Test Status: Passed


**Test 14: Seller Messaging Creating New Store**

1. Login using account "a5@xyz.com"
2. Open "Messaging"
3. Select "New"
4. Select "New" on the stores page
5. Enter in "a5's second store"
6. Select "a4", press "Submit"
7. Send message "test"

Expected result: Program creates new conversation file, stores.txt is updated, and message is successfully sent.

Test Status: Passed


**Test 15: Account Creation, Deletion, & Exiting**

1. User launches application
2. User selects the "CREATE U..." button.
3. User enters username "testAccount".
4. User enters user email "testAccount@mail.com".
5. User enters password "testAccount".
6. User clicks on user role Seller.
7. User clicks "OK".
8. User clicks "Logout"
9. User selects the "Login" button.
10. User enters user email "testAccount@mail.com".
11. User enters password "testAccount".
12. User clicks "OK".
13. User selects the "Delete User" button.
14. User clicks "Yes".
15. User clicks "OK".
16. User clicks "OK" on login screen with pre-filled information.

Expected result: User creates a new account, logs out of the account, logs back into the accoun, deletes the account, attempts to login back into deleted account, warning pops up stating that "Invalid userID(email) or Password."

Test Status: Passed


**Test X: XYZ**

1. Text

Expected result:

Test Status: Passed
