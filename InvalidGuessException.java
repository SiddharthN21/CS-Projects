/**
 * Word Guessing Game
 *
 * InvalidGuessException Class
 * This class is used to throw an InvalidGuessException by calling the Exception superclass constructor with the
 * message passed in as a parameter.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 03
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version October 28, 2022
 */

public class InvalidGuessException extends Exception
{
    public InvalidGuessException(String message)
    {
        super(message);
    }
}
