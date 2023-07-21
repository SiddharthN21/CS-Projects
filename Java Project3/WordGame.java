import java.nio.file.Files;
import java.util.Scanner;
import java.io.*;

/**
 * Word Guessing Game
 *
 * WordGame Class
 * This class contains a main method and menu interfaces that allows the player to play the word guessing game. The
 * class also contains an updateGameLog() method which writes the results of the game into a gamelog file after
 * each completion of the game.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 03
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version October 28, 2022
 */

public class WordGame {

    public static String welcome = "Ready to play?";
    public static String yesNo = "1.Yes\n2.No";
    public static String noPlay = "Maybe next time!";
    public static String currentRoundLabel = "Current Round: ";
    public static String enterGuess = "Please enter a guess!";
    public static String winner = "You got the answer!";
    public static String outOfGuesses = "You ran out of guesses!";
    public static String solutionLabel = "Solution: ";
    public static String incorrect = "That's not it!";
    public static String keepPlaying = "Would you like to make another guess?";
    public static String fileNameInput = "Please enter a filename";

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        WordGame wordGame = null;
        WordLibrary wordLibrary = null;
        int play = 1;
        int play1 = 1;
        int currentRound = 1;
        String s1 = "";
        String[] roundGuesses = {"", "", "", "", ""};

        try
        {
            System.out.println(fileNameInput);
            String fileName = scanner.nextLine();
            wordLibrary = new WordLibrary(fileName);
            wordLibrary.verifyWord(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidWordException e) {
            e.getMessage();
        }

        s1 = wordLibrary.chooseWord();
        WordGuesser wGuesser = new WordGuesser(s1);
        System.out.println(welcome);
        System.out.println(yesNo);
        play = scanner.nextInt();
        scanner.nextLine();
        if (play == 2)
        {
            System.out.println(noPlay);
        }
        while (play == 1)
        {
            System.out.print(currentRoundLabel);
            System.out.println(wGuesser.getRound());
            wGuesser.printField();
            System.out.println(enterGuess);
            String guess = scanner.nextLine();
            roundGuesses[wGuesser.getRound() - 1] = guess;
            try
            {
                if ((wGuesser.checkGuess(guess)) && (guess.equals(wGuesser.getSolution())))
                {
                    updateGameLog(s1, roundGuesses, true);
                    roundGuesses = new String[]{"", "", "", "", ""};
                    System.out.println(winner);
                    wGuesser.printField();
                    System.out.println(welcome);
                    System.out.println(yesNo);
                    play = scanner.nextInt();
                    scanner.nextLine();
                    wGuesser.setRound(1);
                    s1 = wordLibrary.chooseWord();
                    wGuesser = new WordGuesser(s1);
                } else {
                    if (wGuesser.getRound() < 5)
                    {
                        System.out.println(incorrect);
                        System.out.println(keepPlaying);
                        System.out.println(yesNo);
                        play1 = scanner.nextInt();
                        scanner.nextLine();
                        if (play1 == 2)
                        {
                            wGuesser.printField();
                            System.out.println(welcome);
                            System.out.println(yesNo);
                            play = scanner.nextInt();
                            scanner.nextLine();
                            wGuesser.setRound(1);
                            s1 = wordLibrary.chooseWord();
                            wGuesser = new WordGuesser(s1);
                            roundGuesses = new String[]{"", "", "", "", ""};
                        } else {
                            wGuesser.setRound(wGuesser.getRound() + 1);
                        }
                    } else {
                        updateGameLog(s1, roundGuesses, false);
                        roundGuesses = new String[]{"", "", "", "", ""};
                        System.out.println(outOfGuesses);
                        System.out.print(solutionLabel);
                        System.out.println(wGuesser.getSolution());
                        wGuesser.printField();
                        System.out.println(welcome);
                        System.out.println(yesNo);
                        play = scanner.nextInt();
                        scanner.nextLine();
                        wGuesser.setRound(1);
                        s1 = wordLibrary.chooseWord();
                        wGuesser = new WordGuesser(s1);
                    }
                }
            } catch (InvalidGuessException e) {
                System.out.println(e.getMessage());
            }
            if (play == 2)
            {
                System.out.println(noPlay);
            }
        }
    }

    public static void updateGameLog(String solution, String[] guesses, boolean solved)
    {
        String fileName = "gamelog.txt";
        int gamesCompleted = 0;
        String[] list;
        int j = 0;
        String temp = "";
        int temp1;
        int ngNumber;
        int nonBlankIndex = 0;

        for (int i = 0; i < guesses.length; i++)
        {
            if (!guesses[i].equals(""))
            {
                nonBlankIndex = i;
            }
        }

        try
        {
            File f = new File(fileName);
            if (!f.exists() && !f.isDirectory())
            {
                f.createNewFile();
                FileOutputStream fos = new FileOutputStream(fileName, false);
                PrintWriter pw = new PrintWriter(fos);
                pw.println("Games Completed: 1");
                pw.println("Game 1");
                pw.println("- Solution: " + solution);
                for (int i = 0; i < guesses.length; i++)
                {
                    temp = temp + guesses[i];
                    if ((i < guesses.length - 1) && (i < nonBlankIndex))
                    {
                        temp = temp + ",";
                    }
                }
                pw.println("- Guesses: " + temp);
                if (solved)
                {
                    temp = "Yes";
                } else {
                    temp = "No";
                }
                pw.println("- Solved: " + temp);
                pw.close();
            } else {
                FileReader fr = new FileReader(fileName);
                BufferedReader bfr = new BufferedReader(fr);
                list = new String[(int) Files.lines(f.toPath()).count() + 4];
                String line = bfr.readLine();
                while (line != null)
                {
                    list[j] = line;
                    line = bfr.readLine();
                    j++;
                }
                bfr.close();
                temp = list[0];
                temp1 = Integer.parseInt(temp.substring(temp.indexOf(": ") + 2));
                temp1++;
                list[0] = "Games Completed: " + temp1;
                temp = list[list.length - 8];
                temp1 = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
                ngNumber = ++temp1;
                list[list.length - 4] = "Game " + ngNumber;
                list[list.length - 3] = "- Solution: " + solution;
                temp = "";
                for (int i = 0; i < guesses.length; i++)
                {
                    temp = temp + guesses[i];
                    if ((i < guesses.length - 1) && (i < nonBlankIndex ))
                    {
                        temp = temp + ",";
                    }
                }
                list[list.length - 2] = "- Guesses: " + temp;
                if (solved)
                {
                    temp = "Yes";
                } else {
                    temp = "No";
                }
                list[list.length - 1] = "- Solved: " + temp;
                FileOutputStream fos1 = new FileOutputStream(fileName, false);
                PrintWriter pw = new PrintWriter(fos1);
                for (int i = 0; i < list.length; i++)
                {
                    pw.println(list[i]);
                }
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
