/**
 * Word Guessing Game
 *
 * InvalidWordException Class
 * This class is used to throw an InvalidWordException by calling the Exception superclass constructor with the
 * message passed in as a parameter.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 03
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version October 28, 2022
 */

public class InvalidWordException extends Exception
{
    public InvalidWordException(String message)
    {
        super(message);
    }
}
