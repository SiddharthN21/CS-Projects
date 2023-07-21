import java.util.Scanner;

/**
 * Lego Competition Program
 *
 * This program consists of two classes- CompetitionLog and LegoSetCompetition.
 *
 * The LegoSetCompetition class allows two players to compete against each other in building legos. The program
 * inputs the names of the sets and number of pieces present in each set. The user is asked to input the number of
 * pieces that each player builds on each day. The competition continues until a player wins. In case of a tie, a
 * tiebreaker competition is carried out. The tiebreaker competition will last until one of the players has won.
 * The results are returned according to the specified format.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 02
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version October 08, 2022
 */

public class LegoSetCompetition
{
    public static void main(String[] args)
    {
        String welcomeMessage = "Welcome to the Lego Set Competition!";
        String set1 = "Enter the name of Lego Set 1";
        String set2 = "Enter the name of Lego Set 2";
        String set3 = "Enter the name of Lego Set 3";
        String set1Pieces = "Enter the number of pieces in Lego Set 1";
        String set2Pieces = "Enter the number of pieces in Lego Set 2";
        String set3Pieces = "Enter the number of pieces in Lego Set 3";
        String p1PiecesByDay = "Enter the number of pieces player 1 used for building on day %d";
        String p2PiecesByDay = "Enter the number of pieces player 2 used for building on day %d";
        String tiebreaker = "The competition ended in a tie! There will be a tiebreaker round";
        String winner = "Congratulations to player %d for winning the Lego Set Competition!";
        String additionalInfo = "Additional information about the competition results is below";
        String totalDays = "The competition lasted %d days";
        int competitionDay = 0;
        int set1NumPieces = 0;
        int set2NumPieces = 0;
        int set3NumPieces = 0;
        String set1Name = "";
        String set2Name = "";
        String set3Name = "";
        int p1PiecesBuilt = 0;
        int p2PiecesBuilt = 0;
        int gameTotalPieces = 0;
        String temp = "";
        int temp1 = 0;

        Scanner scan = new Scanner(System.in);
        CompetitionLog playerOne = new CompetitionLog(1, "", "", p1PiecesBuilt);
        CompetitionLog playerTwo = new CompetitionLog(2, "", "", p2PiecesBuilt);

        System.out.println(welcomeMessage);

        while ((p1PiecesBuilt > p2PiecesBuilt || p2PiecesBuilt > p1PiecesBuilt) ||
                (p1PiecesBuilt == 0 && p2PiecesBuilt == 0) || (p1PiecesBuilt == p2PiecesBuilt))
        {
            System.out.println(set1);
            set1Name = scan.nextLine();
            System.out.println(set1Pieces);
            set1NumPieces = scan.nextInt();
            scan.nextLine();
            System.out.println(set2);
            set2Name = scan.nextLine();
            System.out.println(set2Pieces);
            set2NumPieces = scan.nextInt();
            scan.nextLine();
            System.out.println(set3);
            set3Name = scan.nextLine();
            System.out.println(set3Pieces);
            set3NumPieces = scan.nextInt();
            scan.nextLine();

            gameTotalPieces = gameTotalPieces + set1NumPieces + set2NumPieces + set3NumPieces;

            while ((p1PiecesBuilt < gameTotalPieces) && (p2PiecesBuilt < gameTotalPieces))
            {
                competitionDay++;
                System.out.printf(p1PiecesByDay, competitionDay);
                System.out.println();
                p1PiecesBuilt = p1PiecesBuilt + scan.nextInt();
                scan.nextLine();
                System.out.printf(p2PiecesByDay, competitionDay);
                System.out.println();
                p2PiecesBuilt = p2PiecesBuilt + scan.nextInt();
                scan.nextLine();

            }

            if (p1PiecesBuilt == p2PiecesBuilt)
            {
                System.out.println(tiebreaker);
                temp = playerOne.getCompleteSets();
                playerOne.setCompleteSets(temp + set1Name + ", " + set2Name + ", " + set3Name + ", ");
                playerOne.setIncompleteSets("");
                temp1 = playerOne.getPiecesBuilt();
                playerOne.setPiecesBuilt(temp1 + p1PiecesBuilt);
                temp = playerTwo.getCompleteSets();
                playerTwo.setCompleteSets(temp + set1Name + ", " + set2Name + ", " + set3Name + ", ");
                playerTwo.setIncompleteSets("");
                temp1 = playerTwo.getPiecesBuilt();
                playerTwo.setPiecesBuilt(temp1 + p2PiecesBuilt);
            } else
            {
                if (p1PiecesBuilt > p2PiecesBuilt)
                {
                    System.out.printf(winner, 1);
                    System.out.println();
                    System.out.println(additionalInfo);
                    temp = playerOne.getCompleteSets();
                    playerOne.setCompleteSets(temp + set1Name + ", " + set2Name + ", " + set3Name);
                    playerOne.setIncompleteSets("None");
                    temp1 = playerOne.getPiecesBuilt();
                    playerOne.setPiecesBuilt(p1PiecesBuilt);
                    temp1 = playerTwo.getPiecesBuilt();
                    playerTwo.setPiecesBuilt(p2PiecesBuilt);
                    if (p2PiecesBuilt >= set1NumPieces + set2NumPieces)
                    {
                        temp = playerTwo.getCompleteSets();
                        playerTwo.setCompleteSets(temp + set1Name + ", " + set2Name);
                        playerTwo.setIncompleteSets(set3Name);
                    } else if (p2PiecesBuilt >= set1NumPieces) {
                        temp = playerTwo.getCompleteSets();
                        playerTwo.setCompleteSets(temp + set1Name);
                        playerTwo.setIncompleteSets(set2Name + ", " + set3Name);
                    } else {
                        temp = playerTwo.getCompleteSets();
                        if (temp.equals(""))
                        {
                            temp = "None";
                        }
                        playerTwo.setCompleteSets(temp);
                        playerTwo.setIncompleteSets(set1Name + ", " + set2Name + ", " + set3Name);
                    }
                } else if (p2PiecesBuilt > p1PiecesBuilt) {
                    System.out.printf(winner, 2);
                    System.out.println();
                    System.out.println(additionalInfo);

                    temp = playerTwo.getCompleteSets();
                    playerTwo.setCompleteSets(temp + set1Name + ", " + set2Name + ", " + set3Name);
                    playerTwo.setIncompleteSets("None");
                    temp1 = playerTwo.getPiecesBuilt();
                    playerTwo.setPiecesBuilt(p2PiecesBuilt);
                    temp1 = playerOne.getPiecesBuilt();
                    playerOne.setPiecesBuilt(p1PiecesBuilt);
                    if (p1PiecesBuilt >= set1NumPieces + set2NumPieces)
                    {
                        temp = playerOne.getCompleteSets();
                        playerOne.setCompleteSets(temp + set1Name + ", " + set2Name);
                        playerOne.setIncompleteSets(set3Name);
                    } else if (p1PiecesBuilt >= set1NumPieces) {
                        temp = playerOne.getCompleteSets();
                        playerOne.setCompleteSets(temp + set1Name);
                        playerOne.setIncompleteSets(set2Name + ", " + set3Name);
                    } else {
                        temp = playerOne.getCompleteSets();
                        if (temp.equals(""))
                        {
                            temp = "None";
                        }
                        playerOne.setCompleteSets(temp);
                        playerOne.setIncompleteSets(set1Name + ", " + set2Name + ", " + set3Name);
                    }
                }
                break;
            }
        }

        System.out.print(playerOne.toString());
        System.out.print(playerTwo.toString());
        System.out.printf(totalDays, competitionDay);
    }
}
