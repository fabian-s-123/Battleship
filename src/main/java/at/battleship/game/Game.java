package at.battleship.game;

import at.battleship.components.Field;
import at.battleship.components.Playground;
import at.battleship.components.Ship;
import at.battleship.players.Bot;
import at.battleship.players.HumanPlayer;
import at.battleship.players.Player;
import at.battleship.services.Renderer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    private HumanPlayer humanPlayer;
    private Bot bot;
    private Playground playgroundPlayer1;
    private Playground playgroundPlayer2;
    private ArrayList<String> movesPlayer1 = new ArrayList<>();
    private ArrayList<String> movesPlayer2 = new ArrayList<>();
    private Renderer renderer;
    private static final Pattern SHIP_POSITION_PATTERN = Pattern.compile("^([a-jA-J])+(10|[1-9])$");


    public void play() throws InterruptedException {
        this.playIntro();
        Thread.sleep(2000);
        if (humanPlayer != null && bot != null) {
            this.setBattlefield(humanPlayer, bot);
            this.renderer.render();
            this.playRounds();
        }
    }

    private void playIntro() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Battleships\n\nPlease enter your name:");
        boolean validName = false;
        String humanPlayerName = sc.nextLine();
        while (!validName) {
            if (humanPlayerName.length() < 20 && humanPlayerName.length() > 0) {
                this.humanPlayer = new HumanPlayer(humanPlayerName, false, true, 0);
                validName = true;
            } else {
                System.out.println("Your name must be between 1 and 20 characters.\nPlease enter your name:");
                humanPlayerName = sc.nextLine();
            }
        }
        this.bot = createBotOpponent();
        System.out.println("\nHello " + humanPlayer.getName() + ", your opponent is: " + bot.getName() + "\n");
    }

    private void setBattlefield(Player player1, Player player2) throws InterruptedException {
        /**
         * set battlefield for player1 & bot
         */
//        ArrayList<Ship> shipsPlayer1 = this.setShipsPlayer1();
//        ArrayList<Ship> shipsPlayer2 = this.setShipsPlayer2();
//        this.playgroundPlayer1 = new Playground(shipsPlayer1, this.humanPlayer);
//        this.playgroundPlayer2 = new Playground(shipsPlayer2, this.bot);
//        this.renderer = new Renderer(this.playgroundPlayer1, this.playgroundPlayer2, player1, player2);
//
//        this.addShipsToThePlayground(shipsPlayer1, this.playgroundPlayer1);
//        this.addShipsToThePlayground(shipsPlayer2, this.playgroundPlayer2);


        /**
         * set battlefield for player1 & randomly generated for bot
         */
//        ArrayList<Ship> shipsPlayer1 = this.setShipsPlayer1();
//        this.playgroundPlayer1 = new Playground(shipsPlayer1, this.humanPlayer);
//        this.playgroundPlayer2 = new Playground(this.bot);
//        this.renderer = new Renderer(this.playgroundPlayer1, this.playgroundPlayer2, player1, player2);
//
//        //executes the setting of the ships for the bot in a separate thread
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        executorService.execute(this::setShipsForBot);
//        executorService.shutdown();
//
//        this.addShipsToThePlayground(shipsPlayer1, this.playgroundPlayer1);


        /**
         * manual setting for player1 & randomly generated for bot
         */
        this.playgroundPlayer1 = new Playground(this.humanPlayer);
        this.playgroundPlayer2 = new Playground(this.bot);
        this.renderer = new Renderer(this.playgroundPlayer1, this.playgroundPlayer2, player1, player2);

        //executes the setting of the ships for the bot in a separate thread
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(this::setShipsForBot);
        executorService.shutdown();

        this.setShipsForHumanPlayer(player1);
    }

    private void setShipsForHumanPlayer(Player humanPlayer) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("First, set Your Ships: you have 1 Carrier (5x1), 2 Battleships (4x1), 3 Destroyer (3x1) & 4 Submarines (2x1).");
        System.out.println("Set the starting point of your Ships.");
        System.out.println("You can set them in the range from 'A-J' and from '1-10' and give them a direction, for example: C4 & down.");
        System.out.println("\nPress enter to continue.");

        if (sc.nextLine().equals("")) {
            ArrayList<Ship> ships = this.createNonSetShipList();
            Thread.sleep(1000);
            this.renderer.renderOnePlayground(humanPlayer);

            for (Ship ship : ships) {
                switch (ship.getType()) {
                    case CARRIER:
                        System.out.println("\nThis is a Carrier:");
                        break;
                    case BATTLESHIP:
                        System.out.println("\nThis is a Battleship:");
                        break;
                    case DESTROYER:
                        System.out.println("\nThis is a Destroyer:");
                        break;
                    case SUBMARINE:
                        System.out.println("\nThis is a Submarine:");
                        break;
                }
                this.verifyRangeForHumanPlayer(ship, sc);
                this.addShipToPlayground(ship, this.playgroundPlayer1);
                this.renderer.renderOnePlayground(humanPlayer);
            }
            System.out.println("The battlefield is set. Let's get into the game.\n\n");
        }
    }

    private void setShipsForBot() {
        ArrayList<Ship> ships = this.createNonSetShipList();
        for (Ship ship : ships) {
            switch (ship.getType()) {
                case CARRIER:
                case BATTLESHIP:
                case DESTROYER:
                case SUBMARINE:
                    this.verifyRangeForBot(ship);
                    this.addShipToPlayground(ship, this.playgroundPlayer2);
                    break;
            }
        }
    }

    private void selectFirstMove() throws InterruptedException {
        int coinFlip = (int) (Math.random() * 9) + 1;
        if (coinFlip <= 5) {
            this.humanPlayer.setPlayerTurn(true);
            System.out.println("It's been randomly selected who gets the first move.");
            Thread.sleep(1500);
            System.out.println("It is " + this.humanPlayer.getName() + " this time around.");
            Thread.sleep(1500);
        } else {
            this.bot.setPlayerTurn(true);
            System.out.println("It's randomly selected who gets the first move.");
            Thread.sleep(1500);
            System.out.println("It is " + this.bot.getName() + " this time around.");
            Thread.sleep(2500);
        }
    }

    private void playRounds() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        boolean isPlaying = true;
        boolean shipDestroyedWithPreviousMove = false;

        System.out.println("Press enter to start the game.");
        String enter = sc.nextLine();
        this.selectFirstMove();

        //if currentScore of either Player hits number of all ship tiles combined -> isPlaying = false;
        while (isPlaying) {
            //play round
            while (this.humanPlayer.getPlayerTurn()) {
                String playerInput;
                boolean moveAlreadyMade = true;
                boolean proceedWithAttack = false;
                int guessX = -1;
                int guessY = -1;

                while (moveAlreadyMade) {
                    this.humanPlayer.addMovesTally();
                    System.out.println("Your turn. Enter your guess:                    To exit enter 'e'");
                    playerInput = sc.nextLine();
                    //checks for exit
                    if (playerInput.equalsIgnoreCase("e")) {
                        System.out.println("Good bye.");
                        System.exit(0);
                    } else {
                        //checks if the move has already been made
                        moveAlreadyMade = this.checkIfMoveAlreadyMade(playerInput, this.humanPlayer);

                        if (checkInput(playerInput) && playerInput.length() > 1 && !moveAlreadyMade) {
                            this.movesPlayer1.add(playerInput);
                            String playerInputToUpperCase = playerInput.toUpperCase();
                            guessX = this.transformStringInputToXValue(playerInputToUpperCase.charAt(0));
                            guessY = this.transformStringInputToYValue(playerInputToUpperCase.substring(1));
                            proceedWithAttack = true;
                        } else {
                            System.out.println("Invalid input - try again:");
                        }
                    }
                }
                if (proceedWithAttack) {
                    Thread.sleep(800);
                    this.humanPlayer.setPlayerTurn(this.attackOpponent(guessX, guessY, this.humanPlayer, this.playgroundPlayer2)); //attacks
                    if (this.humanPlayer.getPlayerTurn()) {
                        this.playgroundPlayer2.checkShipHitPoints(guessX, guessY, this.bot);
                    }
                    this.renderer.render();
                    if (this.humanPlayer.getCurrentScore() == this.playgroundPlayer1.checkMaxShipHitPointsCombined()) {
                        System.out.println("Congratulations " + this.humanPlayer.getName() + ", you won!");
                        System.out.println("It took you " + this.humanPlayer.getMovesTally() + " rounds to defeat your opponent.");
                        this.humanPlayer.setPlayerTurn(false);
                        isPlaying = false;
                    }
                }
            }
            if (isPlaying) {
                this.bot.setPlayerTurn(true);
            }


            //play round bot opponent
            while (this.bot.getPlayerTurn()) {
                Thread.sleep(200);
                this.bot.addMovesTally();
                System.out.println(this.bot.getName() + " is on the move...");
                Thread.sleep(1000);

                String botInput = "";
                boolean moveAlreadyMade = true;
                int guessX = -1;
                int guessY = -1;

                while (moveAlreadyMade) {

                    Thread.sleep(1000);
                    int[] guesses = this.bot.playRoundBot(shipDestroyedWithPreviousMove);
                    guessX = guesses[0];
                    guessY = guesses[1];

                    char reverseX = this.transformNumericInputOfXToCharValue(guessX);
                    String reverseY = this.transformNumericInputOfYToStringValue(guessY);
                    botInput = reverseX + reverseY;

                    //checks if the move has already been made
                    moveAlreadyMade = this.checkIfMoveAlreadyMade(botInput, this.bot);

                }
                this.movesPlayer2.add(botInput);
                this.bot.removeFieldAvailableForBot(botInput);
                System.out.println("Opponent guesses: " + botInput);
                Thread.sleep(1500);
                this.bot.setPlayerTurn(this.attackOpponent(guessX, guessY, this.bot, this.playgroundPlayer1)); //attacks
                if (this.bot.getPlayerTurn()) {
                    this.playgroundPlayer1.checkShipHitPoints(guessX, guessY, this.bot);
                    shipDestroyedWithPreviousMove = this.playgroundPlayer1.isShipDestroyed();
                    this.bot.addSuccessfulMoves(botInput);
                } else {
                    shipDestroyedWithPreviousMove = this.playgroundPlayer1.isShipDestroyed();
                }
                this.renderer.render();
                //this.bot.setPlayerTurn(true); //TODO delete if Bot check is finished
                if (this.bot.getCurrentScore() == this.playgroundPlayer2.checkMaxShipHitPointsCombined()) {
                    System.out.println("Congratulations " + this.bot.getName() + ", you won!");
                    System.out.println("It took you " + this.bot.getMovesTally() + " rounds to defeat your opponent.");
                    this.bot.setPlayerTurn(false);
                    isPlaying = false;
                }
            }
            if (isPlaying) {
                this.humanPlayer.setPlayerTurn(true);
            }
        }
    }


    private void verifyRangeForHumanPlayer(Ship ship, Scanner sc) throws InterruptedException {
        boolean positionsVerified = false;

        while (!positionsVerified) {
            System.out.println("Please enter your starting point:               To exit enter 'e'");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("e")) {
                System.out.println("Good bye.");
                System.exit(0);
            }
            if (checkInput(input)) {
                String toUpperCase = input.toUpperCase();
                int validX = this.transformStringInputToXValue(toUpperCase.charAt(0));
                int validY = this.transformStringInputToYValue(toUpperCase.substring(1));
                System.out.println("Please enter the direction you want your ship to position in: 'r' for right; 'd' for down");
                String direction = sc.nextLine();
                if (direction.equalsIgnoreCase("r")) {
                    if (this.rangeIsWithinBounds(validX, ship)) {
                        LinkedList<Integer> xValues = new LinkedList<>();
                        for (int i = validX; i < validX + ship.getLength(ship.getType()); i++) {
                            xValues.add(i);
                        }
                        if (this.checkForPositionCollision(this.playgroundPlayer1, xValues, validY, true)) {
                            ship.setStartRangeX(validX);
                            ship.setEndRangeX(validX + ship.getLength(ship.getType()) - 1);
                            ship.setStartRangeY(validY);
                            ship.setEndRangeY(validY);
                            positionsVerified = true;
                        } else {
                            xValues.removeLast();
                            System.out.println("A ship has already been placed there - try again:");
                        }
                    } else {
                        System.out.println("Out of bounds - try again:");
                    }
                } else if (direction.equalsIgnoreCase("d")) {
                    if (this.rangeIsWithinBounds(validY, ship)) {
                        LinkedList<Integer> yValues = new LinkedList<>();
                        for (int i = validY; i < validY + ship.getLength(ship.getType()); i++) {
                            yValues.add(i);
                        }
                        if (this.checkForPositionCollision(this.playgroundPlayer1, yValues, validX, false)) {
                            ship.setStartRangeX(validX);
                            ship.setEndRangeX(validX);
                            ship.setStartRangeY(validY);
                            ship.setEndRangeY(validY + ship.getLength(ship.getType()) - 1);
                            positionsVerified = true;
                        } else {
                            yValues.removeLast();
                            System.out.println("A ship has already been placed there - try again:");
                        }
                    } else {
                        System.out.println("Out of bounds - try again:");
                    }
                } else {
                    System.out.println("Invalid input - try again:");
                }
            } else {
                System.out.println("Invalid input - try again:");
            }
        }
        System.out.println("Ship has been placed.");
        Thread.sleep(1000);
    }

    private void verifyRangeForBot(Ship ship) {
        LinkedList<Integer> xValues = new LinkedList<>();
        LinkedList<Integer> yValues = new LinkedList<>();
        boolean positionsVerified = false;

        do {
            //random number decides whether the ship will be positioned to the right or downwards
            double right1Down0 = Math.random();

            int x = (int) (Math.random() * 10);
            xValues.add(x);
            int y = (int) (Math.random() * 10);
            yValues.add(y);

            if (right1Down0 >= 0.5) {
                if (this.rangeIsWithinBounds(x, ship)) {
                    //fills xValues with the whole range of relevant xValues
                    for (int i = x; i <= x + ship.getLength(ship.getType()) - 1; i++) {
                        xValues.add(i);
                    }
                    if (this.checkForPositionCollision(this.playgroundPlayer2, xValues, y, true)) {
                        ship.setStartRangeX(x);
                        ship.setEndRangeX(x + ship.getLength(ship.getType()) - 1);
                        ship.setStartRangeY(y);
                        ship.setEndRangeY(y);
                        positionsVerified = true;
                    }
                }
            } else {
                if (this.rangeIsWithinBounds(y, ship)) {
                    //fills yValues with the whole range of relevant yValues
                    for (int i = y; i <= y + ship.getLength(ship.getType()) - 1; i++) {
                        yValues.add(i);
                    }
                    if (this.checkForPositionCollision(this.playgroundPlayer2, yValues, x, false)) {
                        ship.setStartRangeX(x);
                        ship.setEndRangeX(x);
                        ship.setStartRangeY(y);
                        ship.setEndRangeY(y + ship.getLength(ship.getType()) - 1);
                        positionsVerified = true;
                    }
                }
            }
            xValues.removeLast();
            yValues.removeLast();
        } while (!positionsVerified);
    }

    private boolean rangeIsWithinBounds(int startingPoint, Ship ship) {
        return startingPoint - 1 + ship.getLength(ship.getType()) <= 9;
    }

    private boolean checkForPositionCollision(Playground playground, LinkedList<Integer> values, int fixedPoint, boolean isX) {
        for (Integer value : values) {
            if (isX) {
                if (playground.getMap()[value][fixedPoint].getFieldRenderState() != Field.CurrentState.NEUTRAL) {
                    return false;
                }
            } else {
                if (playground.getMap()[fixedPoint][value].getFieldRenderState() != Field.CurrentState.NEUTRAL) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkForFieldRenderState(Playground playground, LinkedList<Integer> values, int fixedPoint, boolean isX, Field.CurrentState currentState) {
        for (Integer value : values) {
            if (isX) {
                if (playground.getMap()[value][fixedPoint].getFieldRenderState() != currentState) {
                    return false;
                }
            } else {
                if (playground.getMap()[fixedPoint][value].getFieldRenderState() != currentState) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean checkIfMoveAlreadyMade(String input, Player player) {
        boolean moveAlreadyMade;
        if (player == this.humanPlayer) {
            moveAlreadyMade = this.movesPlayer1.stream().anyMatch(e -> e.toUpperCase().equals(input.toUpperCase()));
        } else {
            moveAlreadyMade = this.movesPlayer2.stream().anyMatch(e -> e.toUpperCase().equals(input.toUpperCase()));
        }
        return moveAlreadyMade;
    }


    private ArrayList<Ship> createNonSetShipList() {
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

    private void addShipToPlayground(Ship ship, Playground playground) {
        int[] rangeX = ship.getValueBetweenX(ship);
        int[] rangeY = ship.getValueBetweenY(ship);
        if (!(rangeX.length == 0)) {
            for (int x : rangeX) {
                playground.getMap()[x][ship.getStartRangeY()].setShip(ship.getType());
            }
        } else if (!(rangeY.length == 0)) {
            for (int y : rangeY) {
                playground.getMap()[ship.getStartRangeX()][y].setShip(ship.getType());
            }
        }
        playground.addShip(ship);
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
        playground.setShips(ships);
    }


    private boolean attackOpponent(int x, int y, Player player, Playground opponentsPlayground) {
        boolean continueAttack = false;
        int attack = opponentsPlayground.checkPosition(x, y);
        if (attack == 1) {
            System.out.println("Hit!");
            player.setCurrentScore(player.getCurrentScore() + 1);
            continueAttack = true;
        } else if (attack == 0) {
            System.out.println("Miss...");
        } else if (attack == -1) {
            System.out.println("error code " + attack + ": MISS");
            System.exit(-1);
        } else if (attack == -2) {
            System.out.println("error code " + attack + ": HIT");
            System.exit(-1);
        } else if (attack == -3) {
            System.out.println("error code " + attack + ": jumped over switch case");
            System.exit(-1);
        }
        return continueAttack;
    }


    public static boolean checkInput(String input) {
        Matcher matcher = SHIP_POSITION_PATTERN.matcher(input);
        return matcher.matches();
    }

    public int transformStringInputToXValue(char xAsChar) {
        int x = (int) xAsChar - 65;
        if (x >= 0 && x <= 9) {
            return x;
        }
        return -1;
    }

    public int transformStringInputToYValue(String yAsString) {
        int y = Integer.parseInt(yAsString) - 1;
        if (y >= 0 && y <= 9) {
            return y;
        }
        return -1;
    }

    public char transformNumericInputOfXToCharValue(int x) {
        return (char) (x + 65);
    }

    public String transformNumericInputOfYToStringValue(int y) {
        return String.valueOf(y + 1);
    }

    private Bot createBotOpponent() {
        return new Bot ("Captain AngryMan", false, false, 0, this);
    }


    private ArrayList<Ship> setShipsPlayer1() {
        ArrayList<Ship> ships = new ArrayList<>();
        Ship carrier = new Ship(Ship.Type.CARRIER, 8, 8, 3, 7);
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

    private ArrayList<Ship> setShipsPlayer2() {
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


    /**
     * Getter & Setter
     */
    public Playground getPlaygroundPlayer1() {
        return playgroundPlayer1;
    }

    public void setPlaygroundPlayer1(Playground playgroundPlayer1) {
        this.playgroundPlayer1 = playgroundPlayer1;
    }

    public Playground getPlaygroundPlayer2() {
        return playgroundPlayer2;
    }

    public void setPlaygroundPlayer2(Playground playgroundPlayer2) {
        this.playgroundPlayer2 = playgroundPlayer2;
    }

    public ArrayList<String> getMovesPlayer1() {
        return movesPlayer1;
    }

    public void setMovesPlayer1(ArrayList<String> movesPlayer1) {
        this.movesPlayer1 = movesPlayer1;
    }

    public ArrayList<String> getMovesPlayer2() {
        return movesPlayer2;
    }

    public void setMovesPlayer2(ArrayList<String> movesPlayer2) {
        this.movesPlayer2 = movesPlayer2;
    }
}
