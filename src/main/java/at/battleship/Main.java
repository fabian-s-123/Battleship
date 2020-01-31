package at.battleship;

import at.battleship.game.BotVsBot;
import at.battleship.game.HumanVsBot;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        System.out.println("\nWelcome to Battleship! Do you want to fight vs a bot (1) or let it fight it out themselves (2):\n");

        while (!validInput) {
            String input = sc.nextLine();

            if (input.equals("1")) {
                HumanVsBot humanVsBot = new HumanVsBot();
                humanVsBot.play();
            } else if (input.equals("2")) {
                BotVsBot botVsBot = new BotVsBot();
                botVsBot.play();
            } else {
                System.out.println("Invalid input, '1' for fight against bot or '2' for bot fight.");
                validInput = true;
            }
        }
    }
}
