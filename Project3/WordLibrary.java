import java.io.*;
import java.util.Random;
import java.nio.file.Files;

/**
 * Word Guessing Game
 *
 * WordLibrary Class
 * This class contains attributes related to the library of words used in the puzzle. Apart from getters and setters,
 * the class has a verifyWord(), processLibrary(), and chooseWord() method. The verifyWord() method is used to verify
 * if the word argument has exactly 5 characters and throw a new InvalidWordException in case it is not 5 characters.
 * The processLibrary() method is used to review the library field by calling verifyWord() on each word present and
 * remove the word from the array by printing a message to the terminal in case an exception is thrown. The
 * chooseWord() method is used to return a word randomly chosen from library using the random field with the bound
 * set to the length of the library.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 03
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version October 28, 2022
 */

public class WordLibrary {

    private String[] library;
    private int seed;
    private Random random;
    private String fileName;

    public WordLibrary(String fileName) throws FileNotFoundException
    {
        this.fileName = fileName;
        FileReader fr = new FileReader(fileName);
        BufferedReader bfr = new BufferedReader(fr);
        try
        {
            int i = 0;
            String line = bfr.readLine();
            seed = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
            random = new Random(seed);
            line = bfr.readLine();
            File f = new File(fileName);
            library = new String[(int) (Files.lines(f.toPath()).count() - 1)];
            while (line != null)
            {
                library[i] = line;
                line = bfr.readLine();
                i++;
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfr != null)
            {
                try
                {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try
        {
            processLibrary();
        } catch (InvalidWordException e) {
            e.printStackTrace();
        }
    }

    public void verifyWord(String word) throws InvalidWordException
    {
        if (word.length() != 5)
        {
            throw new InvalidWordException("Invalid word!");
        }
    }

    public void processLibrary() throws InvalidWordException
    {
        String temp = "";
        for (int i = 0; i < library.length; i++)
        {
            temp = library[i];
            try
            {
                verifyWord(temp);

            } catch (InvalidWordException e) {
                String[] anotherArray = new String[library.length - 1];
                System.arraycopy(library, 0, anotherArray, 0, i);
                System.arraycopy(library, i + 1, anotherArray, i, library.length - i - 1);
                library = anotherArray;
                System.out.println(e.getMessage());
            }

        }
    }

    public String chooseWord()
    {
        String s1 = "";
        s1 = library[random.nextInt(library.length)];
        return s1;
    }

    public String[] getLibrary() {
        return library;
    }

    public void setLibrary(String[] library) {
        this.library = library;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
