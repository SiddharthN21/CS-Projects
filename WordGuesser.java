/**
 * Word Guessing Game
 *
 * WordGuesser Class
 * This class contains fields and methods that will support playing the game. Apart from getters and setters, this
 * class has a checkGuess() and printField() method. The checkGuess() method determines is the users guess matches
 * the solution and updates the playing field. If the guess is same as the solution, it returns true. In case the
 * user's guess is not exactly 5 characters, it throws an InvalidGuessException. The printField() method is used to
 * print the playingField in a grid with each character separated by a "|".
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 03
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version October 28, 2022
 */

public class WordGuesser
{
    private String[][] playingField;
    private int round;
    private String solution;

    public WordGuesser(String solution)
    {
        this.solution = solution;
        this.round = 1;
        this.playingField = new String[5][5];

        for (int i = 0; i < playingField.length; i++)
        {
            for (int j = 0; j < playingField[i].length; j++)
            {
                this.playingField[i][j] = " ";
            }
        }
    }

    public String[][] getPlayingField()
    {
        return playingField;
    }

    public int getRound()
    {
        return round;
    }

    public String getSolution()
    {
        return solution;
    }

    public void setPlayingField(String[][] playingField)
    {
        this.playingField = playingField;
    }

    public void setRound(int round)
    {
        this.round = round;
    }

    public void setSolution(String solution)
    {
        this.solution = solution;
    }

    public boolean checkGuess(String guess) throws InvalidGuessException
    {
        if (guess.length() != 5)
        {
            throw new InvalidGuessException("Invalid Guess!");
        }
        boolean gAnswer = true;
        String temp = "";
        for (int i = 0; i < solution.length(); i++)
        {
            if (guess.substring(i, i + 1).equals(solution.substring(i, i + 1)))
            {
                temp = temp + "'" + guess.substring(i, i + 1) + "'";
            } else if (solution.contains(guess.substring(i, i + 1))) {
                temp = temp + "*" + guess.substring(i, i + 1) + "*";
                gAnswer = false;
            } else {
                temp = temp + "{" + guess.substring(i, i + 1) + "}";
                gAnswer = false;
            }
            playingField[round - 1][i] = temp;
            temp = "";
        }
        return gAnswer;
    }

    public void printField()
    {
        String temp = "";
        for (int i = 0; i < playingField.length; i++)
        {
            for (int j = 0; j < playingField[i].length; j++)
            {
                temp = temp + playingField[i][j];
                if (j < 4)
                {
                    temp = temp + " | ";
                }
            }
            System.out.println(temp);
            temp = "";
        }
    }
}
