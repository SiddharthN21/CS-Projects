import java.util.Scanner;

/**
 * A Hockey Score Calculator
 *
 * This program is used to gather and display additional inputs about a hockey series by taking inputs about the
 * results of a seven game hockey series.
 * The program takes inputs of the team names, hockey scores for seven games, and the number of power play goals of
 * both the teams.
 * Using these inputs it calculates who won the series, maximum goals scored, power play goals, shutouts etc.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 01
 *
 * @author Siddharth Nadgaundi, lab sec l04
 *
 * @version September 25, 2022
 */

public class HockeyScores
{
    String winningTeam;
    String losingTeam;
    String seriesScore;
    String teamMaximumGoals;
    int team1GamesWon = 0;
    int team2GamesWon = 0;
    int team1TotalGoals;
    int team2TotalGoals;
    int team1PowerPlayGoals;
    int team2PowerPlayGoals;
    int team1StandardGoals;
    int team2StandardGoals;
    int team1Shutouts = 0;
    int team2Shutouts = 0;
    int maximumGoals;
    int maxGoalsGame;

    public static void main(String[] args)
    {
        // Accepting various inputs from the user.

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome!");
        System.out.println("Enter the name of team 1.");
        String teamOne = scanner.nextLine();
        System.out.println("Enter the name of team 2.");
        String teamTwo = scanner.nextLine();
        System.out.println("Enter hockey scores for seven games.");
        String scores = scanner.nextLine();
        System.out.println("Enter the number of power play goals for both teams in each game.");
        String powerPlay = scanner.nextLine();
        scanner.close();

        int currentScoreIndex = 0;
        int scoreOneTeamOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreOneTeamTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreTwoTeamOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreTwoTeamTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreThreeTeamOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreThreeTeamTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreFourTeamOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreFourTeamTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreFiveTeamOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreFiveTeamTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreSixTeamOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreSixTeamTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreSevenTeamOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int scoreSevenTeamTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));

        int currentPowerPlayIndex = 0;
        int powerPlayOneTeamOne = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayOneTeamTwo = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayTwoTeamOne = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayTwoTeamTwo = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayThreeTeamOne = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayThreeTeamTwo = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayFourTeamOne = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayFourTeamTwo = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayFiveTeamOne = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlayFiveTeamTwo = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlaySixTeamOne = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlaySixTeamTwo = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlaySevenTeamOne = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));
        currentPowerPlayIndex += 3;
        int powerPlaySevenTeamTwo = Integer.parseInt(powerPlay.substring(currentPowerPlayIndex,
                currentPowerPlayIndex + 2));

        // Calling various calculation methods.

        HockeyScores hs = new HockeyScores();

        hs.scoreCalculations(scoreOneTeamOne, scoreOneTeamTwo, scoreTwoTeamOne, scoreTwoTeamTwo, scoreThreeTeamOne,
                scoreThreeTeamTwo, scoreFourTeamOne, scoreFourTeamTwo, scoreFiveTeamOne, scoreFiveTeamTwo,
                scoreSixTeamOne, scoreSixTeamTwo, scoreSevenTeamOne, scoreSevenTeamTwo, teamOne, teamTwo);

        hs.powerScoreCalculations(powerPlayOneTeamOne, powerPlayOneTeamTwo, powerPlayTwoTeamOne, powerPlayTwoTeamTwo,
                powerPlayThreeTeamOne, powerPlayThreeTeamTwo, powerPlayFourTeamOne, powerPlayFourTeamTwo,
                powerPlayFiveTeamOne, powerPlayFiveTeamTwo, powerPlaySixTeamOne, powerPlaySixTeamTwo,
                powerPlaySevenTeamOne, powerPlaySevenTeamTwo);

        // Printing out the output of the game as per the specifications.

        System.out.printf("The %s won the series by a score of %s \n", hs.winningTeam, hs.seriesScore);
        if (hs.team1TotalGoals == 1)
        {
            System.out.printf("The %s scored %d total goal \n", teamOne, hs.team1TotalGoals);
        } else
        {
            System.out.printf("The %s scored %d total goals \n", teamOne, hs.team1TotalGoals);
        }
        if (hs.team2TotalGoals == 1)
        {
            System.out.printf("The %s scored %d total goal \n", teamTwo, hs.team2TotalGoals);
        } else
        {
            System.out.printf("The %s scored %d total goals \n", teamTwo, hs.team2TotalGoals);
        }
        if (hs.team1PowerPlayGoals == 1)
        {
            System.out.printf("The %s scored %d power play goal \n", teamOne, hs.team1PowerPlayGoals);
        } else
        {
            System.out.printf("The %s scored %d power play goals \n", teamOne, hs.team1PowerPlayGoals);
        }
        if (hs.team2PowerPlayGoals == 1)
        {
            System.out.printf("The %s scored %d power play goal \n", teamTwo, hs.team2PowerPlayGoals);
        } else
        {
            System.out.printf("The %s scored %d power play goals \n", teamTwo, hs.team2PowerPlayGoals);
        }
        if (hs.team1StandardGoals == 1)
        {
            System.out.printf("The %s scored %d standard goal \n", teamOne, hs.team1StandardGoals);
        } else
        {
            System.out.printf("The %s scored %d standard goals \n", teamOne, hs.team1StandardGoals);
        }
        if (hs.team2StandardGoals == 1)
        {
            System.out.printf("The %s scored %d standard goal \n", teamTwo, hs.team2StandardGoals);
        } else
        {
            System.out.printf("The %s scored %d standard goals \n", teamTwo, hs.team2StandardGoals);
        }
        if (hs.team1Shutouts == 1)
        {
            System.out.printf("The %s recorded %d shutout \n", teamOne, hs.team1Shutouts);
        } else
        {
            System.out.printf("The %s recorded %d shutouts \n", teamOne, hs.team1Shutouts);
        }
        if (hs.team2Shutouts == 1)
        {
            System.out.printf("The %s recorded %d shutout \n", teamTwo, hs.team2Shutouts);
        } else
        {
            System.out.printf("The %s recorded %d shutouts \n", teamTwo, hs.team2Shutouts);
        }
        System.out.printf("The maximum number of goals scored was %d by the %s in game %d \n", hs.maximumGoals,
                hs.teamMaximumGoals, hs.maxGoalsGame);
    }

    // scoreCalculations method calculates series outcome, total goals made by each team, shutouts for each team, and
    // maximum number of goals scored by a team across the series.

    public void scoreCalculations(int tScoreOneTeamOne, int tScoreOneTeamTwo, int tScoreTwoTeamOne,
                                  int tScoreTwoTeamTwo, int tScoreThreeTeamOne, int tScoreThreeTeamTwo,
                                  int tScoreFourTeamOne, int tScoreFourTeamTwo, int tScoreFiveTeamOne,
                                  int tScoreFiveTeamTwo, int tScoreSixTeamOne, int tScoreSixTeamTwo,
                                  int tScoreSevenTeamOne, int tScoreSevenTeamTwo, String team1, String team2)
    {
        team1TotalGoals = tScoreOneTeamOne + tScoreTwoTeamOne + tScoreThreeTeamOne + tScoreFourTeamOne +
                tScoreFiveTeamOne + tScoreSixTeamOne + tScoreSevenTeamOne;
        team2TotalGoals = tScoreOneTeamTwo + tScoreTwoTeamTwo + tScoreThreeTeamTwo + tScoreFourTeamTwo +
                tScoreFiveTeamTwo + tScoreSixTeamTwo + tScoreSevenTeamTwo;

        if (tScoreOneTeamOne > tScoreOneTeamTwo)
        {
            team1GamesWon++;
            if (tScoreOneTeamTwo == 0)
            {
                team1Shutouts++;
            }
            maximumGoals = tScoreOneTeamOne;
            maxGoalsGame = 1;
            teamMaximumGoals = team1;
        } else
        {
            team2GamesWon++;
            if (tScoreOneTeamOne == 0)
            {
                team2Shutouts++;
            }
            maximumGoals = tScoreOneTeamTwo;
            maxGoalsGame = 1;
            teamMaximumGoals = team2;
        }
        if (tScoreTwoTeamOne > tScoreTwoTeamTwo)
        {
            team1GamesWon++;
            if (tScoreTwoTeamTwo == 0)
            {
                team1Shutouts++;
            }
            if (maximumGoals < tScoreTwoTeamOne)
            {
                maximumGoals = tScoreTwoTeamOne;
                maxGoalsGame = 2;
                teamMaximumGoals = team1;
            }
        } else
        {
            team2GamesWon++;
            if (tScoreTwoTeamOne == 0)
            {
                team2Shutouts++;
            }
            if (maximumGoals < tScoreTwoTeamTwo)
            {
                maximumGoals = tScoreTwoTeamTwo;
                maxGoalsGame = 2;
                teamMaximumGoals = team2;
            }
        }
        if (tScoreThreeTeamOne > tScoreThreeTeamTwo)
        {
            team1GamesWon++;
            if (tScoreThreeTeamTwo == 0)
            {
                team1Shutouts++;
            }
            if (maximumGoals < tScoreThreeTeamOne)
            {
                maximumGoals = tScoreThreeTeamOne;
                maxGoalsGame = 3;
                teamMaximumGoals = team1;
            }
        } else
        {
            team2GamesWon++;
            if (tScoreThreeTeamOne == 0)
            {
                team2Shutouts++;
            }
            if (maximumGoals < tScoreThreeTeamTwo)
            {
                maximumGoals = tScoreThreeTeamTwo;
                maxGoalsGame = 3;
                teamMaximumGoals = team2;
            }
        }
        if (tScoreFourTeamOne > tScoreFourTeamTwo)
        {
            team1GamesWon++;
            if (tScoreFourTeamTwo == 0)
            {
                team1Shutouts++;
            }
            if (maximumGoals < tScoreFourTeamOne)
            {
                maximumGoals = tScoreFourTeamOne;
                maxGoalsGame = 4;
                teamMaximumGoals = team1;
            }
        } else
        {
            team2GamesWon++;
            if (tScoreFourTeamOne == 0)
            {
                team2Shutouts++;
            }
            if (maximumGoals < tScoreFourTeamTwo)
            {
                maximumGoals = tScoreFourTeamTwo;
                maxGoalsGame = 4;
                teamMaximumGoals = team2;
            }
        }
        if (tScoreFiveTeamOne > tScoreFiveTeamTwo)
        {
            team1GamesWon++;
            if (tScoreFiveTeamTwo == 0)
            {
                team1Shutouts++;
            }
            if (maximumGoals < tScoreFiveTeamOne)
            {
                maximumGoals = tScoreFiveTeamOne;
                maxGoalsGame = 5;
                teamMaximumGoals = team1;
            }
        } else
        {
            team2GamesWon++;
            if (tScoreFiveTeamOne == 0)
            {
                team2Shutouts++;
            }
            if (maximumGoals < tScoreFiveTeamTwo)
            {
                maximumGoals = tScoreFiveTeamTwo;
                maxGoalsGame = 5;
                teamMaximumGoals = team2;
            }
        }
        if (tScoreSixTeamOne > tScoreSixTeamTwo)
        {
            team1GamesWon++;
            if (tScoreSixTeamTwo == 0)
            {
                team1Shutouts++;
            }
            if (maximumGoals < tScoreSixTeamOne)
            {
                maximumGoals = tScoreSixTeamOne;
                maxGoalsGame = 6;
                teamMaximumGoals = team1;
            }
        } else
        {
            team2GamesWon++;
            if (tScoreSixTeamOne == 0)
            {
                team2Shutouts++;
            }
            if (maximumGoals < tScoreSixTeamTwo)
            {
                maximumGoals = tScoreSixTeamTwo;
                maxGoalsGame = 6;
                teamMaximumGoals = team2;
            }
        }
        if (tScoreSevenTeamOne > tScoreSevenTeamTwo)
        {
            team1GamesWon++;
            if (tScoreSevenTeamTwo == 0)
            {
                team1Shutouts++;
            }
            if (maximumGoals < tScoreSevenTeamOne)
            {
                maximumGoals = tScoreSevenTeamOne;
                maxGoalsGame = 7;
                teamMaximumGoals = team1;
            }
        } else
        {
            team2GamesWon++;
            if (tScoreSevenTeamOne == 0)
            {
                team2Shutouts++;
            }
            if (maximumGoals < tScoreSevenTeamTwo)
            {
                maximumGoals = tScoreSevenTeamTwo;
                maxGoalsGame = 7;
                teamMaximumGoals = team2;
            }
        }

        if (team1GamesWon > team2GamesWon)
        {
            winningTeam = team1;
            losingTeam = team2;
            seriesScore = String.valueOf(team1GamesWon) + "-" + String.valueOf(team2GamesWon);
        } else
        {
            winningTeam = team2;
            losingTeam = team1;
            seriesScore = String.valueOf(team2GamesWon) + "-" + String.valueOf(team1GamesWon);
        }

    }

    // powerScoreCalculations method calculates total power play goals made by each team and total standard goals
    // made by each team.

    public void powerScoreCalculations(int tPowerPlayOneTeamOne, int tPowerPlayOneTeamTwo, int tPowerPlayTwoTeamOne,
                                  int tPowerPlayTwoTeamTwo, int tPowerPlayThreeTeamOne, int tPowerPlayThreeTeamTwo,
                                  int tPowerPlayFourTeamOne, int tPowerPlayFourTeamTwo, int tPowerPlayFiveTeamOne,
                                  int tPowerPlayFiveTeamTwo, int tPowerPlaySixTeamOne, int tPowerPlaySixTeamTwo,
                                  int tPowerPlaySevenTeamOne, int tPowerPlaySevenTeamTwo)
    {
        team1PowerPlayGoals = tPowerPlayOneTeamOne + tPowerPlayTwoTeamOne + tPowerPlayThreeTeamOne +
                tPowerPlayFourTeamOne + tPowerPlayFiveTeamOne + tPowerPlaySixTeamOne + tPowerPlaySevenTeamOne;
        team2PowerPlayGoals = tPowerPlayOneTeamTwo + tPowerPlayTwoTeamTwo + tPowerPlayThreeTeamTwo +
                tPowerPlayFourTeamTwo + tPowerPlayFiveTeamTwo + tPowerPlaySixTeamTwo + tPowerPlaySevenTeamTwo;

        team1StandardGoals = team1TotalGoals - team1PowerPlayGoals;
        team2StandardGoals = team2TotalGoals - team2PowerPlayGoals;
    } 
}
 
