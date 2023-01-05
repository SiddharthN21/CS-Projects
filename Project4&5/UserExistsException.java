/**
 * UserExistsException Class
 *
 * This class is used to throw an exception when the user creates an account with login details that represent the
 * login details of an already existing user (account object). The class constructor calls the constructor of the
 * exception superclass with the message passed in as a parameter.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 04
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version November 11, 2022
 */

public class UserExistsException extends Exception
{
    public UserExistsException(String message)
    {
        super(message);
    }
}
