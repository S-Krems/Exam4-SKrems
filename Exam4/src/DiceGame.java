import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DiceGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of sides for the die being used: ");
        int numSides = scanner.nextInt();

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter the name for player " + (i + 1) + ": ");
            String playerName = scanner.next();
            players.add(new Player(playerName, new Die(numSides)));
        }

        for (Player player : players) {
            player.getDie().roll();
            System.out.println("Player " + player.getName() + " rolled a " + player.getDie().getValue() + " on a " + player.getDie().getNumSides() + " sided die");
        }

        int winnerIndex = decideWinner(players);
        if (winnerIndex == -1) {
            System.out.println("There is a tie!");
        } else {
            System.out.println(players.get(winnerIndex).getName() + " won the game");
        }

        // Extra Credit: Print to file
        printToFile(players, winnerIndex);
    }

    private static int decideWinner(List<Player> players) {
        int max = Integer.MIN_VALUE;
        int maxIndex = -1;
        boolean isTie = false;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.getDie().getValue() > max) {
                max = player.getDie().getValue();
                maxIndex = i;
                isTie = false;
            } else if (player.getDie().getValue() == max) {
                isTie = true;
            }
        }

        if (isTie) {
            return -1; // Tie
        } else {
            return maxIndex; // Winner index
        }
    }

    private static void printToFile(List<Player> players, int winnerIndex) {
        try {
            FileWriter writer = new FileWriter("DiceGameOutput.txt");
            for (Player player : players) {
                writer.write("Player " + player.getName() + " rolled a " + player.getDie().getValue() + " on a " + player.getDie().getNumSides() + " sided die\n");
            }

            if (winnerIndex == -1) {
                writer.write("There is a tie!");
            } else {
                writer.write(players.get(winnerIndex).getName() + " won the game");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}