package at.battleship.game;

import at.battleship.components.Field;
import at.battleship.components.Playground;
import at.battleship.components.Ship;
import at.battleship.players.Bot;
import at.battleship.players.Player;
import at.battleship.services.Renderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class BotVsBot extends Game {

    private Bot bot1;
    private Bot bot2;
    private Playground playgroundPlayer1;
    private Playground playgroundPlayer2;
    private ArrayList<String> movesPlayer1 = new ArrayList<>();
    private ArrayList<String> movesPlayer2 = new ArrayList<>();
    private Renderer renderer;

    public void play() throws InterruptedException {
        this.playIntro();
        Thread.sleep(2000);
        if (bot1 != null && bot2 != null) {
            this.setBattlefield(bot1, bot2);
            this.renderer.render();
            this.playRounds();
        }
    }

    private void playIntro() {
        this.bot1 = createBotOpponent("Captain AngryMan");
        this.bot2 = createBotOpponent("Davy Jones");
        System.out.println("\nHello people, the contestants today are: " + bot1.getName() + " and " + bot2.getName() + "\n");
    }

    private void setBattlefield(Player player1, Player player2) {

        //randomly setting of ships generated for bots
        this.playgroundPlayer2 = new Playground(this.bot1);
        this.playgroundPlayer1 = new Playground(this.bot2);
        this.renderer = new Renderer(this.playgroundPlayer1, this.playgroundPlayer2, player1, player2);

        this.setShipsForBot(this.playgroundPlayer1);
        this.setShipsForBot(this.playgroundPlayer2);
    }


    private void setShipsForBot(Playground playground) {
        ArrayList<Ship> ships = this.createNonSetShipList();
        for (Ship ship : ships) {
            switch (ship.getType()) {
                case CARRIER:
                case BATTLESHIP:
                case DESTROYER:
                case SUBMARINE:
                    this.verifyRangeForBot(ship, playground);
                    this.addShipToPlayground(ship, playground);
                    break;
            }
        }
    }

    private void selectFirstMove() throws InterruptedException {
        int coinFlip = (int) (Math.random() * 9) + 1;
        if (coinFlip <= 5) {
            this.bot1.setPlayerTurn(true);
            System.out.println("It's been randomly selected who gets the first move.");
            Thread.sleep(1500);
            System.out.println("It is " + this.bot1.getName() + " this time around.");
            Thread.sleep(1500);
        } else {
            this.bot2.setPlayerTurn(true);
            System.out.println("It's randomly selected who gets the first move.");
            Thread.sleep(1500);
            System.out.println("It is " + this.bot2.getName() + " this time around.");
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

            //play round bot1
            while (this.bot1.getPlayerTurn()) {
                Thread.sleep(200);
                this.bot1.addMovesTally();
                System.out.println(this.bot1.getName() + " is on the move...");
                Thread.sleep(1000);

                String botInput = "";
                boolean moveAlreadyMade = true;
                int guessX = -1;
                int guessY = -1;

                while (moveAlreadyMade) {

                    Thread.sleep(1000);
                    int[] guesses = this.bot1.playRoundBot(shipDestroyedWithPreviousMove);
                    guessX = guesses[0];
                    guessY = guesses[1];

                    char reverseX = this.transformNumericInputOfXToCharValue(guessX);
                    String reverseY = this.transformNumericInputOfYToStringValue(guessY);
                    botInput = reverseX + reverseY;

                    //checks if the move has already been made
                    moveAlreadyMade = this.checkIfMoveAlreadyMade(botInput, this.bot1);

                }
                this.movesPlayer1.add(botInput);
                this.bot1.removeFieldAvailableForBot(botInput);
                System.out.println("Opponent guesses: " + botInput);
                Thread.sleep(1500);
                this.bot1.setPlayerTurn(this.attackOpponent(guessX, guessY, this.bot1, this.playgroundPlayer2)); //attacks
                if (this.bot1.getPlayerTurn()) {
                    this.playgroundPlayer2.checkShipHitPoints(guessX, guessY, this.bot1);
                    shipDestroyedWithPreviousMove = this.playgroundPlayer2.isShipDestroyed();
                    this.bot1.addSuccessfulMoves(botInput);
                } else {
                    shipDestroyedWithPreviousMove = this.playgroundPlayer1.isShipDestroyed();
                }
                this.renderer.render();
                if (this.bot1.getCurrentScore() == this.playgroundPlayer1.checkMaxShipHitPointsCombined()) {
                    System.out.println("Congratulations " + this.bot1.getName() + ", you won!");
                    System.out.println("It took you " + this.bot1.getMovesTally() + " rounds to defeat your opponent.");
                    this.bot1.setPlayerTurn(false);
                    isPlaying = false;
                }
            }
            if (isPlaying) {
                this.bot2.setPlayerTurn(true);
            }


            //play round bot2
            while (this.bot2.getPlayerTurn()) {
                Thread.sleep(200);
                this.bot2.addMovesTally();
                System.out.println(this.bot2.getName() + " is on the move...");
                Thread.sleep(1000);

                String botInput = "";
                boolean moveAlreadyMade = true;
                int guessX = -1;
                int guessY = -1;

                while (moveAlreadyMade) {

                    Thread.sleep(1000);
                    int[] guesses = this.bot2.playRoundBot(shipDestroyedWithPreviousMove);
                    guessX = guesses[0];
                    guessY = guesses[1];

                    char reverseX = this.transformNumericInputOfXToCharValue(guessX);
                    String reverseY = this.transformNumericInputOfYToStringValue(guessY);
                    botInput = reverseX + reverseY;

                    //checks if the move has already been made
                    moveAlreadyMade = this.checkIfMoveAlreadyMade(botInput, this.bot2);

                }
                this.movesPlayer2.add(botInput);
                this.bot2.removeFieldAvailableForBot(botInput);
                System.out.println("Opponent guesses: " + botInput);
                Thread.sleep(1500);
                this.bot2.setPlayerTurn(this.attackOpponent(guessX, guessY, this.bot2, this.playgroundPlayer1)); //attacks
                if (this.bot2.getPlayerTurn()) {
                    this.playgroundPlayer1.checkShipHitPoints(guessX, guessY, this.bot2);
                    shipDestroyedWithPreviousMove = this.playgroundPlayer1.isShipDestroyed();
                    this.bot2.addSuccessfulMoves(botInput);
                } else {
                    shipDestroyedWithPreviousMove = this.playgroundPlayer2.isShipDestroyed();
                }
                this.renderer.render();
                if (this.bot2.getCurrentScore() == this.playgroundPlayer2.checkMaxShipHitPointsCombined()) {
                    System.out.println("Congratulations " + this.bot2.getName() + ", you won!");
                    System.out.println("It took you " + this.bot2.getMovesTally() + " rounds to defeat your opponent.");
                    this.bot2.setPlayerTurn(false);
                    isPlaying = false;
                }
            }
            if (isPlaying) {
                this.bot1.setPlayerTurn(true);
            }
        }
    }


    private void verifyRangeForBot(Ship ship, Playground playground) {
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
                    if (this.checkForPositionCollision(playground, xValues, y, true)) {
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
                    if (this.checkForPositionCollision(playground, yValues, x, false)) {
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

    private boolean checkIfMoveAlreadyMade(String input, Player player) {
        boolean moveAlreadyMade;
        if (player == this.bot1) {
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

    private Bot createBotOpponent(String name) {
        return new Bot(name, false, true, 0, this);
    }


    /**
     * Getter & Setter
     */
    public ArrayList<String> getMovesPlayer1() {
        return movesPlayer1;
    }

    public ArrayList<String> getMovesPlayer2() {
        return movesPlayer2;
    }

    @Override
    public Player getPlayer1() {
        return bot1;
    }

    @Override
    public Player getPlayer2() {
        return bot2;
    }
}
