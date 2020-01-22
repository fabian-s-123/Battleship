package at.battleship.components;

import at.battleship.services.Renderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    private Player[] players = new Player[2];
    private Playground playgroundPlayer1;
    private Playground playgroundPlayer2;
    private LinkedList<String> movesPlayer1 = new LinkedList<>();
    private LinkedList<String> movesPlayer2 = new LinkedList<>();
    private Renderer renderer;
    private static final Pattern SHIP_POSITION_PATTERN = Pattern.compile("^([A-J])+(10|[1-9])$");


    public void play() throws InterruptedException {
        this.playIntro();
        if (players[0] != null && players[1] != null) {
            this.setBattlefield(this.players[0], this.players[1]);
            this.playRounds();
        }
    }


    private void setBattlefield(Player player1, Player player2) throws InterruptedException {
        ArrayList<Ship> shipsPlayer1 = this.setShipsPlayer1(player1);
        ArrayList<Ship> shipsPlayer2 = this.setShipsPlayer2(player2);

        this.playgroundPlayer1 = new Playground(shipsPlayer1, player1);
        this.playgroundPlayer2 = new Playground(shipsPlayer2, player2);

        this.addShipsToThePlayground(shipsPlayer1, playgroundPlayer1);
        this.addShipsToThePlayground(shipsPlayer2, playgroundPlayer2);

        this.renderer = new Renderer(playgroundPlayer1, playgroundPlayer2, player1, player2);
    }

    private void setShips() {
        System.out.println("Now, set Your Ships: you have 1 Carrier (5x1), 2 Battleships (4x1), 3 Destroyer (3x1) & 4 Submarines (2x1)");
        System.out.println("First, set the starting point of your Ships, starting in the same order as seen above.");
        System.out.println("You can set them in the range from 'A-J' and from '1-10', for example: C4");

        Scanner sc = new Scanner(System.in);
        ArrayList<Ship> ships = this.createShipList();

        for (Ship ship: ships) {
            switch (ship.getType()) {
                case CARRIER:
                    //TODO implement manual setting of ships
                    break;
                case BATTLESHIP:

                    break;
                case DESTROYER:

                    break;
                case SUBMARINE:

                    break;
            }
        }

        String input = "";

        int startRangeX = 0;
        int endRangeX = 0;
        int startRangeY = 0;
        int endRangeY = 0;

        input = sc.nextLine();

        boolean validInput = checkInput(input);
        boolean startingPointOk = false;

        //set the first ship
        do {
            if (checkInput(input)) {
                int validX = this.validateX(input.charAt(0));
                int validY = this.validateY(input.substring(1));
                System.out.println("Please enter the direction you want your ship to position in: 'r' for right; 'd' for down");
                String direction = sc.nextLine();
                if (direction.equalsIgnoreCase("r")) {

                } else if (direction.equalsIgnoreCase("d")) {

                } else {
                    System.out.println("Invalid input - try again:");
                }
            }
        } while (validInput);


/*        while (validInput) {
            int validX = this.validateX(input.charAt(0));
            int validY = this.validateY(input.substring(1));
            for (Ship ship: ships) {
                if (ship.getStartRangeX() != validX) {
                    if (ship.getStartRangeY() != validY) {
                        startingPointOk = true;
                    }
                    System.out.println("There is already a ship placed on this field. Please try a different one.");
                    break;
                }
                System.out.println("There is already a ship placed on this field. Please try a different one.");
                break;
            }
        }*/
        System.out.println("Invalid input - try again:");
    }


    private boolean checkForPositionCollision() {
        return false; //TODO check for overlay in array
    }


    private ArrayList<Ship> createShipList() {
        ArrayList<Ship> ships = new ArrayList<>();
            ships.add(new Ship(Ship.Type.CARRIER));
            ships.add(new Ship(Ship.Type.BATTLESHIP));
            ships.add(new Ship(Ship.Type.BATTLESHIP));
            ships.add(new Ship(Ship.Type.DESTROYER));
            ships.add(new Ship(Ship.Type.DESTROYER));
            ships.add(new Ship(Ship.Type.DESTROYER));
            ships.add(new Ship(Ship.Type.SUBMARINE));
            ships.add(new Ship(Ship.Type.SUBMARINE));
            ships.add(new Ship(Ship.Type.SUBMARINE));
            ships.add(new Ship(Ship.Type.SUBMARINE));
        return ships;
    }


    private void addShipsToThePlayground(ArrayList<Ship> ships, Playground playground) {
        for (int i = 0; i < ships.size(); i++) {
            Ship currentShip = ships.get(i);
            int[] rangeX = currentShip.getValueBetweenX(currentShip);
            int[] rangeY = currentShip.getValueBetweenY(currentShip);
            if (!(rangeX.length == 0)) {
                for (int k = 0; k < rangeX.length; k++) {
                    playground.getMap()[rangeX[k]][currentShip.getStartRangeY()].setShip(currentShip.getType());
                }
            } else if (!(rangeY.length == 0)) {
                for (int l = 0; l < rangeY.length; l++) {
                    playground.getMap()[currentShip.getStartRangeX()][rangeY[l]].setShip(currentShip.getType());
                }
            }
        }
    }


    private void playIntro() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Battleships\n\nPlease enter your name:");
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
        Player player2 = createBotOpponent("Captain AngryMan");
        this.players[1] = player2;
        System.out.println("Hello " + players[0].getName() + ", your opponent is:   " + players[1].getName());
    }

    //TODO outsource whatever is possible & restructure
    private void playRounds() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        boolean isPlaying = true;

        while (isPlaying) {
            // if currentHitPointsPlayer1 or 2 hits 0 -> isPlaying = false;
            int coinFlip = (int) (Math.random() * 9) + 1;
            if (coinFlip <= 5) {
                players[0].setPlayerTurn(true);
            } else {
                players[1].setPlayerTurn(true);
            }

            //play round
            while (players[0].getPlayerTurn()) {
                String player1Input;
                boolean moveAlreadyMade;
                int guessX = -1;
                int guessY = -1;

                do {
                    players[0].addMovesTally();
                    System.out.println("Your turn. Enter your guess:                    To exit enter 'e'");
                    player1Input = sc.nextLine();
                    //checks for exit
                    if (player1Input.equals("e")) {
                        System.out.println("Good bye.");
                        System.exit(0);
                    }
                    //checks if the move has already been made
                    moveAlreadyMade = movesPlayer1.stream()
                            .equals(player1Input);

                    if (checkInput(player1Input) && player1Input.length() > 1 && !moveAlreadyMade) {
                        this.movesPlayer1.add(player1Input);
                        guessX = this.validateX(player1Input.charAt(0));
                        guessY = this.validateY(player1Input.substring(1));
/*                        if (guessX < 0 || guessX > 9 && guessY < 0 || guessY > 9) {
                            break;
                        }*/
                    } else {
                        System.out.println("Invalid input - try again:");
                    }
                } while (moveAlreadyMade);
                Thread.sleep(1000);
                players[0].setPlayerTurn(this.attackOpponent(guessX, guessY, players[0], this.playgroundPlayer2)); //attacks
                this.renderer.render();
                if (players[0].getCurrentScore() == 30) { //TODO make 30 the dynamic number of all tiles of all the ships in each Playground
                    System.out.println("Congratulations " + players[0].getName() + ", you won!");
                    System.out.println("It took you " + players[0].getMovesTally() + " rounds to defeat your opponent.");
                    players[0].setPlayerTurn(false);
                }
            }
            System.out.println("");
            players[1].setPlayerTurn(true);


            //play round bot opponent
            while (players[1].getPlayerTurn()) {
                Thread.sleep(200);
                players[1].addMovesTally();
                System.out.println(players[1].getName() + " is on the move...");
                Thread.sleep(1000);

                String player2Input;
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
                    movesPlayer2.add(player2Input);
                    moveAlreadyMade = movesPlayer2.stream()
                            .equals(player2Input);
                } while (moveAlreadyMade);
                System.out.println("Opponent guesses: " + player2Input);
                Thread.sleep(1500);
                players[1].setPlayerTurn(this.attackOpponent(guessX, guessY, players[1], this.playgroundPlayer1)); //attacks
                this.renderer.render();

                if (players[1].getCurrentScore() == 30) { //TODO make 30 the dynamic number of all tiles of all the ships in each Playground
                    System.out.println("Congratulations " + players[0].getName() + ", you won!");
                    System.out.println("It took you " + players[0].getMovesTally() + " rounds to defeat your opponent.");
                    players[1].setPlayerTurn(false);
                }
                System.out.println("");
                players[0].setPlayerTurn(true);
            }

            //ends game
            if (!players[0].getPlayerTurn() && !players[1].getPlayerTurn()) {
                isPlaying = false;
            }
        }
    }

    private boolean attackOpponent(int x, int y, Player you, Playground opponentsPlayground) throws InterruptedException {
        boolean continueAttack = false;
        int attack = opponentsPlayground.checkPosition(x, y);
        if (attack == 1) {
            System.out.println("Hit!");
            you.setCurrentScore(you.getCurrentScore() + 1);
            continueAttack = true;
        } else if (attack == 0) {
            System.out.println("Miss...");
        } else {
            System.out.println("Hello hello - bug here. Please fix!");
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

    private Player createBotOpponent(String name) {
        return new Player(name, false, false, 0);
    }



    private ArrayList<Ship> setShipsPlayer1(Player player1) {
        ArrayList<Ship> ships = new ArrayList<>();
        Ship carrier = new Ship (Ship.Type.CARRIER, 8, 8, 3, 7);
        ships.add(carrier);

        Ship battleship1 = new Ship(Ship.Type.BATTLESHIP, 3, 6, 0, 0);
        ships.add(battleship1);
        Ship battleship2 = new Ship(Ship.Type.BATTLESHIP, 3, 3, 3, 6);
        ships.add(battleship2);

        Ship destroyer1 = new Ship(Ship.Type.DESTROYER, 0, 2, 1, 1);
        ships.add(destroyer1);
        Ship destroyer2 = new Ship(Ship.Type.DESTROYER, 0, 0, 4, 6);
        ships.add(destroyer2);
        Ship destroyer3 = new Ship(Ship.Type.DESTROYER, 1, 3, 9, 9);
        ships.add(destroyer3);

        Ship submarine1 = new Ship(Ship.Type.SUBMARINE, 6, 6, 2, 3);
        ships.add(submarine1);
        Ship submarine2 = new Ship(Ship.Type.SUBMARINE, 9, 9, 0, 1);
        ships.add(submarine2);
        Ship submarine3 = new Ship(Ship.Type.SUBMARINE, 6, 7, 8, 8);
        ships.add(submarine3);
        Ship submarine4 = new Ship(Ship.Type.SUBMARINE, 8, 9, 9, 9);
        ships.add(submarine4);

        return ships;
    }

    private ArrayList<Ship> setShipsPlayer2(Player player2) {
        ArrayList<Ship> ships = new ArrayList<>();
        Ship carrier = new Ship(Ship.Type.CARRIER, 0, 4, 0, 0);
        ships.add(carrier);

        Ship battleship1 = new Ship(Ship.Type.BATTLESHIP, 4, 7, 2, 2);
        ships.add(battleship1);
        Ship battleship2 = new Ship(Ship.Type.BATTLESHIP, 0, 0, 3, 6);
        ships.add(battleship2);

        Ship destroyer1 = new Ship(Ship.Type.DESTROYER, 2, 2, 2, 4);
        ships.add(destroyer1);
        Ship destroyer2 = new Ship(Ship.Type.DESTROYER, 5, 7, 5, 5);
        ships.add(destroyer2);
        Ship destroyer3 = new Ship(Ship.Type.DESTROYER, 5, 7, 8, 8);
        ships.add(destroyer3);

        Ship submarine1 = new Ship(Ship.Type.SUBMARINE, 9, 9, 0, 1);
        ships.add(submarine1);
        Ship submarine2 = new Ship(Ship.Type.SUBMARINE, 9, 9, 4, 5);
        ships.add(submarine2);
        Ship submarine3 = new Ship(Ship.Type.SUBMARINE, 2, 3, 6, 6);
        ships.add(submarine3);
        Ship submarine4 = new Ship(Ship.Type.SUBMARINE, 2, 3, 9, 9);
        ships.add(submarine4);

        return ships;
    }
}
