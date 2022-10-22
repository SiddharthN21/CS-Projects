/**
 * Lego Competition Program
 *
 * This program consists of two classes- CompetitionLog and LegoSetCompetition.
 *
 * The CompetitionLog class is used to log information about the results of a competition. The class has 4 fields, a
 * constructor, and mutator & accessor methods.
 * The class also has a toString() method to return the information about the results of the competition in the
 * specified format.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 02
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version October 08, 2022
 */

public class CompetitionLog
{
    private int playerNumber;
    private String completeSets;
    private String incompleteSets;
    private int piecesBuilt;

    public CompetitionLog(int playerNumber, String completeSets, String incompleteSets, int piecesBuilt)
    {
        this.playerNumber = playerNumber;
        this.completeSets = completeSets;
        this.incompleteSets = incompleteSets;
        this.piecesBuilt = piecesBuilt;
    }

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    public String getCompleteSets()
    {
        return completeSets;
    }

    public String getIncompleteSets()
    {
        return incompleteSets;
    }

    public int getPiecesBuilt()
    {
        return piecesBuilt;
    }

    public void setPlayerNumber(int playerNumber)
    {
        this.playerNumber = playerNumber;
    }

    public void setCompleteSets(String completeSets)
    {
        this.completeSets = completeSets;
    }

    public void setIncompleteSets(String incompleteSets)
    {
        this.incompleteSets = incompleteSets;
    }

    public void setPiecesBuilt(int piecesBuilt)
    {
        this.piecesBuilt = piecesBuilt;
    }

    public String toString()
    {
        String format = "Player %d completed the following sets: %s\n";
        format = format + "Player %d did not complete the following sets: %s\n";
        format = format + "Player %d built a total of %d pieces\n";

        return String.format(format, this.playerNumber, this.completeSets, this.playerNumber, this.incompleteSets,
                this.playerNumber, this.piecesBuilt);
    }
}
