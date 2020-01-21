package at.battleship.components;

import at.battleship.services.Renderer;
import at.battleship.ships.*;

import java.util.ArrayList;
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
    private boolean validInput;
    private int currentHitPointsPlayer1 = 30;
    private int currentHitPointsPlayer2 = 30;
    private Renderer renderer = new Renderer();
    private static final Pattern SHIP_POSITION_PATTERN = Pattern.compile("^([a-jA-J]+[1-9]|10)$");

    public void setBattlefield(Player player1, Player player2) {
        Playground playgroundPlayer1 = new Playground();
        Playground playgroundPlayer2 = new Playground();
        this.setShipsPlayer1(playgroundPlayer1);
        this.setShipsPlayer2(playgroundPlayer2);
    }


    public void setShipsPlayer1(Playground playground) {
        ArrayList<Ship> ships = new ArrayList<>();
        Carrier carrier = new Carrier(player1, 3, 7, 8, 8);
        ships.add(carrier);

        Battleship battleship1 = new Battleship(player1, 0, 0, 3, 6);
        ships.add(battleship1);
        Battleship battleship2 = new Battleship(player1, 3, 6, 3, 3);
        ships.add(battleship2);

        Destroyer destroyer1 = new Destroyer(player1, 1, 1, 0, 2);
        ships.add(destroyer1);
        Destroyer destroyer2 = new Destroyer(player1, 4, 6, 0, 0);
        ships.add(destroyer2);
        Destroyer destroyer3 = new Destroyer(player1, 1, 3, 9, 9);
        ships.add(destroyer3);

        Submarine submarine1 = new Submarine(player1, 6, 6, 2, 3);
        ships.add(submarine1);
        Submarine submarine2 = new Submarine(player1, 9, 9, 0, 1);
        ships.add(submarine2);
        Submarine submarine3 = new Submarine(player1, 6, 7, 8, 8);
        ships.add(submarine3);
        Submarine submarine4 = new Submarine(player1, 8, 9, 9, 9);
        ships.add(submarine4);
    }


    public void setShipsPlayer2(Playground playground) {
        ArrayList<Ship> ships = new ArrayList<>();
        Carrier carrier = new Carrier(player2, 0, 4, 0, 0);
        ships.add(carrier);

        Battleship battleship1 = new Battleship(player2, 4, 7, 2, 2);
        ships.add(battleship1);
        Battleship battleship2 = new Battleship(player2, 0, 0, 3, 6);
        ships.add(battleship2);

        Destroyer destroyer1 = new Destroyer(player2, 2, 2, 2, 4);
        ships.add(destroyer1);
        Destroyer destroyer2 = new Destroyer(player2, 6, 8, 5, 5);
        ships.add(destroyer2);
        Destroyer destroyer3 = new Destroyer(player2, 6, 8, 8, 8);
        ships.add(destroyer3);

        Submarine submarine1 = new Submarine(player2, 9, 9, 0, 1);
        ships.add(submarine1);
        Submarine submarine2 = new Submarine(player2, 9, 9, 4, 5);
        ships.add(submarine2);
        Submarine submarine3 = new Submarine(player2, 2, 3, 6, 6);
        ships.add(submarine3);
        Submarine submarine4 = new Submarine(player2, 2, 3, 9, 9);
        ships.add(submarine4);
    }

    private void addShipsToThePlayground(ArrayList<Ship> ships, Playground playground) {
        for (int i = 0; i < playground.getMap().length; i++) {
            playground.setMap(ships.get(i).getPositionShip());
        }
    }



    public void play() {
        this.createBotOpponent();
        System.out.println("Welcome to Battleships\nPlease enter your name:");
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
