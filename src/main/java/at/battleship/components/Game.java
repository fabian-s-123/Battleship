package at.battleship.components;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    private boolean isPlaying = true;
    private Player player1;
    private Player player2;
    private boolean turnPlayer1;
    private boolean turnPlayer2;
    private Scanner sc = new Scanner(System.in);
    private String inputPlayer1 = "";
    private String inputPlayer2 = "";
    private Playground playgroundPlayer1;
    private Playground playgroundPlayer2;
    private boolean validInput;
    private static final Pattern SHIP_POSITION_PATTERN = Pattern.compile("^([a-jA-J]+[1-9]|10)$");

    public void play() {
        this.createBotOpponent();
        System.out.println("Welcome to Battleship\nPlease enter your name:");
        while (isPlaying) {
            this.player1.setName(sc.nextLine());
            System.out.println("Set Your Ships: you have 1 Carrier (5x1), 2 Battleships (4x1), 3 Destroyer (3x1) & 4 Submarines (2x1)");
            boolean settingShips = true;
            boolean validInput = true;
            while (settingShips && validInput) {
                System.out.println("First, set the starting point of your Ships, starting in the same order as seen above.");
                System.out.println("You can set them in the range from 'A-J' and from '1-10', for example: C4");
                inputPlayer1 = sc.nextLine();
                if (checkInput(inputPlayer1)) {
                    playgroundPlayer1.setShipStartingPoint(inputPlayer1);

                } else {
                    System.out.println("Incorrect input.");
                }
                System.out.println("Next, set the direction of your Ship: user 'up', 'down', 'right' & 'left' for direction");


            }


        }
        this.setShips();

    }

    private static boolean checkInput(String input) {
        Matcher matcher = SHIP_POSITION_PATTERN.matcher(input);
        return matcher.matches();
    }

    private void setShips() {}

    private void createBotOpponent() {
        this.player2.setName("Captain AngryMan");
        this.player2.setPlayerTurn(false);
    }

    private String simulateOpponent(){
        return "";
    }

}
