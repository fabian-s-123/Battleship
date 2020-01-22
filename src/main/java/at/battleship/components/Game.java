package at.battleship.components;

import at.battleship.services.Renderer;
import at.battleship.ships.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    private Player[] players = new Player[2];
    private int currentHitPointsPlayer1 = 30;
    private int currentHitPointsPlayer2 = 30;
    private Playground playgroundPlayer1;
    private Playground playgroundPlayer2;
    private ArrayList<Ship> shipsPlayer2;
    private LinkedList<String> movesPlayer1 = new LinkedList<>();
    private LinkedList<String> movesPlayer2 = new LinkedList<>();


    private static final Pattern SHIP_POSITION_PATTERN = Pattern.compile("^([A-J]+[1-9]|10)$");

    public void play() throws InterruptedException {
        /*player1 = new Player("Fabian", false, true, 0);
        player2 = new Player("Captain AngryMan", false, false, 0);*/

        this.playIntro();
        if (players[0] != null && players[1] != null) {
            this.setBattlefield(this.players[0], this.players[1]);
            this.play_TEST();
        }
    }


    public void setBattlefield(Player player1, Player player2) {
        ArrayList<Ship> shipsPlayer1 = this.setShipsPlayer1(player1);
        ArrayList<Ship> shipsPlayer2 = this.setShipsPlayer2(player2);

        this.playgroundPlayer1 = new Playground(shipsPlayer1, player1);
        this.playgroundPlayer2 = new Playground(shipsPlayer2, player2);

        this.addShipsToThePlayground(shipsPlayer1, playgroundPlayer1);
        this.addShipsToThePlayground(shipsPlayer2, playgroundPlayer2);

        Renderer renderer = new Renderer(playgroundPlayer1, playgroundPlayer2, player1, player2);
    }

    public ArrayList<Ship> setShipsPlayer1(Player player1) {
        ArrayList<Ship> ships = new ArrayList<>();
        Carrier carrier = new Carrier(player1, 8, 8, 3, 7);
        ships.add(carrier);

        Battleship battleship1 = new Battleship(player1, 3, 6, 0, 0);
        ships.add(battleship1);
        Battleship battleship2 = new Battleship(player1, 3, 3, 3, 6);
        ships.add(battleship2);

        Destroyer destroyer1 = new Destroyer(player1, 0, 2, 1, 1);
        ships.add(destroyer1);
        Destroyer destroyer2 = new Destroyer(player1, 0, 0, 4, 6);
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

        return ships;
    }


    public ArrayList<Ship> setShipsPlayer2(Player player2) {
        ArrayList<Ship> ships = new ArrayList<>();
        Carrier carrier = new Carrier(player2, 0, 4, 0, 0);
        ships.add(carrier);

        Battleship battleship1 = new Battleship(player2, 4, 7, 2, 2);
        ships.add(battleship1);
        Battleship battleship2 = new Battleship(player2, 0, 0, 3, 6);
        ships.add(battleship2);

        Destroyer destroyer1 = new Destroyer(player2, 2, 2, 2, 4);
        ships.add(destroyer1);
        Destroyer destroyer2 = new Destroyer(player2, 5, 7, 5, 5);
        ships.add(destroyer2);
        Destroyer destroyer3 = new Destroyer(player2, 5, 7, 8, 8);
        ships.add(destroyer3);

        Submarine submarine1 = new Submarine(player2, 9, 9, 0, 1);
        ships.add(submarine1);
        Submarine submarine2 = new Submarine(player2, 9, 9, 4, 5);
        ships.add(submarine2);
        Submarine submarine3 = new Submarine(player2, 2, 3, 6, 6);
        ships.add(submarine3);
        Submarine submarine4 = new Submarine(player2, 2, 3, 9, 9);
        ships.add(submarine4);

        return ships;
    }

    private void addShipsToThePlayground(ArrayList<Ship> ships, Playground playground) {
        for (int i = 0; i < ships.size(); i++) {
            int[] rangeX = ships.get(i).getValueBetweenX(ships.get(i));
            int[] rangeY = ships.get(i).getValueBetweenY(ships.get(i));
            if (!(rangeX.length == 0)) {
                for (int k = 0; k < rangeX.length; k++) {
                    playground.getMap()[rangeX[k]][ships.get(i).getStartRangeY()].setShip(ships.get(i));
                }
            } else if (!(rangeY.length == 0)) {
                for (int l = 0; l < rangeY.length; l++) {
                    playground.getMap()[ships.get(i).getStartRangeX()][rangeY[l]].setShip(ships.get(i));
                }
            }
        }
    }

    public void playIntro() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Battleships\nPlease enter your name:");
        boolean validName = false;
        String player1Name = sc.nextLine();
        while (!validName) {
            if (player1Name.length() < 20 && player1Name.length() > 0) {
                Player player1 = new Player(player1Name, false, true, 0);
                this.players[0] = player1;
                validName = true;
            } else {
                System.out.println("Your name must be between 1 and 20 characters.\nPlease enter your name:");
                player1Name = sc.nextLine();
            }
        }
        Player player2 = createBotOpponent();
        this.players[1] = player2;
        System.out.println("Hello " + players[0].getName() + ", your opponent is: " + players[1].getName());
    }


    public void play_TEST() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        boolean isPlaying = true;
        boolean validInput;
        while (isPlaying) {
            // if currentHitPointsPlayer1 or 2 hits 0 -> isPlaying = false;
            int coinFlip = (int) (Math.random() * 9) + 1;
            if (coinFlip <= 5) { //TODO replace with coinFlip
                players[0].setPlayerTurn(true);
            } else {
                players[1].setPlayerTurn(true);
            }

            while (players[0].getPlayerTurn()) {
                players[0].addMovesRequiredToWin();
                System.out.println("Your turn. Enter your guess:");
                String player1Input = sc.nextLine();

                //checks if the move has already been made
                boolean moveAlreadyMade = movesPlayer1.stream()
                        .equals(player1Input);

                if (checkInput(player1Input) && player1Input.length() > 1 && !moveAlreadyMade) {
                    this.movesPlayer1.add(player1Input);
                    int guessX = this.validateX(player1Input.charAt(0));
                    int guessY = this.validateY(player1Input.substring(1));
                    if (guessX < 0 || guessX > 9 && guessY < 0 || guessY > 9) {
                        break;
                    }
                    players[0].setPlayerTurn(this.attackOpponent(guessX, guessY, players[1], this.playgroundPlayer2)); //attacks
                    if (players[0].getCurrentScore() == 30) { //TODO make 30 the dynamic number of all tiles of all the ships in each Playground
                        System.out.println("Congratulations " + players[0].getName() + ", you won!");
                        System.out.println("It took you " +  players[0].getMovesRequiredToWin() + " rounds to defeat your opponent.");
                        players[0].setPlayerTurn(false);
                    }
                } else {
                    System.out.println("Invalid input. Try again:");
                }
                System.out.println("");
                players[1].setPlayerTurn(true);
            }

            //bot opponent
            while (players[1].getPlayerTurn()) {
                Thread.sleep(1000);
                players[1].addMovesRequiredToWin();
                System.out.println(players[1].getName() + " is on the move...");

                String player2Input =  "";
                boolean moveAlreadyMade;
                int guessX;
                int guessY;

                do {
                    Thread.sleep(1000);
                    guessX = (int) (Math.random() * 9);
                    guessY = (int) (Math.random() * 9);

                    char reverseX = this.reverseValidateX(guessX);
                    String reverseY = this.reverseValidateY(guessY);
                    player2Input = reverseX + reverseY;
                    System.out.println("Opponents guess: " + player2Input);
                    movesPlayer2.add(player2Input);
                    moveAlreadyMade = movesPlayer2.stream()
                            .equals(player2Input);
                } while (moveAlreadyMade);
                players[1].setPlayerTurn(this.attackOpponent(guessX, guessY, players[0], this.playgroundPlayer1)); //attacks
                if (players[1].getCurrentScore() == 30) { //TODO make 30 the dynamic number of all tiles of all the ships in each Playground
                    System.out.println("Congratulations " + players[0].getName() + ", you won!");
                    System.out.println("It took you " +  players[0].getMovesRequiredToWin() + " rounds to defeat your opponent.");
                    players[1].setPlayerTurn(false);
                }
                System.out.println("");
                players[0].setPlayerTurn(true);
            }

            //ends game
            if (!players[0].getPlayerTurn() && !players[1].getPlayerTurn()) {
                isPlaying = false;
            }

            //System.out.println("Set Your Ships: you have 1 Carrier (5x1), 2 Battleships (4x1), 3 Destroyer (3x1) & 4 Submarines (2x1)");
            //boolean settingShips = true;
/*            while (settingShips && validInput) {
                System.out.println("First, set the starting point of your Ships, starting in the same order as seen above.");
                System.out.println("You can set them in the range from 'A-J' and from '1-10', for example: C4");
                inputPlayer1 = sc.nextLine();
                if (checkInput(inputPlayer1)) {
                    playgroundPlayer1.setShipStartingPoint(inputPlayer1);
                } else {
                    System.out.println("Incorrect input.");
                }
                System.out.println("Next, set the direction of your Ship: user 'up', 'down', 'right' & 'left' for direction");*/

        }
    }

    private boolean attackOpponent(int x, int y, Player opponent, Playground opponentsPlayground) {
        boolean continueAttack = false;
        int attack = opponentsPlayground.checkPosition(x, y);
        if (attack == 1) {
            System.out.println("Hit!");
            opponent.setCurrentScore(opponent.getCurrentScore() + 1);
            continueAttack = true;
        } else if (attack == 0) {
            System.out.println("Miss...");
        } else {
            System.out.println("Incorrect state - something went wrong!");
        }
        return continueAttack;
    }


    private static boolean checkInput(String input) {
        Matcher matcher = SHIP_POSITION_PATTERN.matcher(input);
        return matcher.matches();
    }

    private int validateX(char xAsChar) {
        int x = (int) xAsChar - 65;
        if (x >= 0 && x <= 10) {
            return x;
        }
        return -1;
    }

    private int validateY(String yAsString) {
        int y = Integer.parseInt(yAsString) - 1;
        if (y >= 0 && y <= 10) {
            return y;
        }
        return -1;
    }


    private char reverseValidateX(int x) {
        return (char) (x + 65);
    }

    private String reverseValidateY(int y) {
        return String.valueOf(y + 1);
    }


    private void setShips() {
    }

    private Player createBotOpponent() {
        return new Player("Captain AngryMan", false, false, 0);
    }

    private String simulateOpponent() {
        return "";
    }
}
