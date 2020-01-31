package at.battleship.players;

import at.battleship.components.Field;
import at.battleship.components.Playground;
import at.battleship.game.Game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Bot extends Player {

    private Game game;
    private ArrayList<String> fieldsAvailableForBot;
    private ArrayList<String> successfulMoves = new ArrayList<>();

    public Bot(String name, boolean playerTurn, boolean isVisible, int currentScore, Game game) {
        super(name, playerTurn, isVisible, currentScore);
        this.game = game;
        this.fieldsAvailableForBot = this.getAllFields();
    }


    /**
     * Main Method
     */
    public int[] playRoundBot(boolean shipDestroyed) {
        int xNextMove = -1;
        int yNextMove = -1;

        ArrayList<String> potentialMoves; //all potential moves are stored here
        potentialMoves = this.choosePotentialMoves(this.successfulMoves); //fills the list with every field around a already successfully targeted field ( = Field.CurrentState.HIT)

        //if no ship has been destroyed with the previous hit, go for standard logic of trying to find a line of previous hits and move along that coordinate
        if (!shipDestroyed) {

            //checks if the two last successful moves were on the same X or Y coordinate -> if so: move along the same coordinate for the next guess
            char potentialX = 'z';
            String potentialY = "";
            if (this.successfulMoves.size() >= 2) {
                int indexTop = this.successfulMoves.size() - 1;
                int indexBelowTop = this.successfulMoves.size() - 2;
                if (this.successfulMoves.get(indexTop).charAt(0) == this.successfulMoves.get(indexBelowTop).charAt(0)) {
                    potentialX = this.successfulMoves.get(indexTop).charAt(0);
                } else if (this.successfulMoves.get(indexTop).substring(1).equals(this.successfulMoves.get(indexBelowTop).substring(1))) {
                    potentialY = this.successfulMoves.get(indexTop).substring(1);
                } else { //else just use a field neighbour of the last successful guess for the next move
                    ArrayList<String> lastSuccessfulMove = new ArrayList<>();
                    lastSuccessfulMove.add(this.successfulMoves.get(indexTop));
                    potentialMoves = this.choosePotentialMoves(lastSuccessfulMove);
                }
            }

            //leaves only potential moves in the list which align with previous hits on the same coordinate within a range of +4 to -4
            if (potentialX != 'z') {
                String lastSuccessfulMove = this.successfulMoves.get(this.successfulMoves.size() - 1);
                char finalPotentialX = potentialX;
                potentialMoves = (ArrayList<String>) potentialMoves.stream()
                        .filter(e -> e.charAt(0) == finalPotentialX)
                        .filter(e -> (int) e.charAt(0) != ((int) e.charAt(0) - 1) || (int) e.charAt(0) != ((int) e.charAt(0) + 1))
                        .filter(e -> (Integer.parseInt(e.substring(1)) - Integer.parseInt(lastSuccessfulMove.substring(1))) < 5 &&
                                (Integer.parseInt(e.substring(1)) - Integer.parseInt(lastSuccessfulMove.substring(1))) > -5)
                        .collect(Collectors.toList());

                //removes all potential moves which are separated by fieldRenderState.MISS
//                if (potentialMoves.size() >= 2) {
//                    potentialMoves = this.checkFieldRenderStateNextTo(potentialMoves, true);
//                }

            } else if (potentialY.length() > 0) {
                String lastSuccessfulMove = this.successfulMoves.get(this.successfulMoves.size() - 1);
                String finalPotentialY = potentialY;
                potentialMoves = (ArrayList<String>) potentialMoves.stream()
                        .filter(e -> e.substring(1).equals(finalPotentialY))
                        .filter(e -> Integer.parseInt(e.substring(1)) != (Integer.parseInt(e.substring(1)) - 1) || Integer.parseInt(e.substring(1)) != (Integer.parseInt(e.substring(1)) + 1))
                        .filter(e -> ((int) e.charAt(0) - (int) lastSuccessfulMove.charAt(0)) < 5 &&
                                ((int) e.charAt(0) - (int) lastSuccessfulMove.charAt(0)) > -5)
                        .collect(Collectors.toList());

                //removes all potential moves which are separated by fieldRenderState.MISS
//                if (potentialMoves.size() >= 2) {
//                    potentialMoves = this.checkFieldRenderStateNextTo(potentialMoves, false);
//                }
            }


            //if no logical move is available or the last four moves were unsuccessful and the bot has already moved at least 40 times -> go for the fields with the most potential field neighbours
            if (this.movesTally < 40) {
                if (potentialMoves.size() == 0 || this.successfulMoves.size() >= 5 && !this.checkLastFourMoves(this.successfulMoves)) {
                    int[] nextMoves = this.getRandomNumber(this.fieldsAvailableForBot);
                    xNextMove = nextMoves[0];
                    yNextMove = nextMoves[1];
                } else {
                    int[] nextMoves = this.getRandomNumber(potentialMoves);
                    xNextMove = nextMoves[0];
                    yNextMove = nextMoves[1];
                }

                //if no logical move is available or the last four moves were unsuccessful and the bot has not yet moved 40 times -> go for random move
            } else {
                if (potentialMoves.size() == 0 || this.successfulMoves.size() >= 5 && !this.checkLastFourMoves(this.successfulMoves)) {
                    int[] nextMoves = this.getRandomNumberOfFieldsWithTheMostPotentialNeighbours();
                    xNextMove = nextMoves[0];
                    yNextMove = nextMoves[1];
                } else {
                    int[] nextMoves = this.getRandomNumber(potentialMoves);
                    xNextMove = nextMoves[0];
                    yNextMove = nextMoves[1];
                }
            }

            //if a ship has been destroyed with the previous move: either go for complete random move (if movesTally < 40) or go for random move of the fields with the most potential field neighbours
        } else if (this.movesTally < 40) {
            int[] nextMoves = this.getRandomNumber(this.fieldsAvailableForBot);
            xNextMove = nextMoves[0];
            yNextMove = nextMoves[1];
        } else {
            int[] nextMoves = this.getRandomNumberOfFieldsWithTheMostPotentialNeighbours();
            xNextMove = nextMoves[0];
            yNextMove = nextMoves[1];
        }

        return new int[]{xNextMove, yNextMove};
    }


    /**
     * Helper Methods
     */
    private int[] getRandomNumber(ArrayList<String> fields) {
        double randomIndexMin = 0.5;
        int randomIndexMax = fields.size();
        int randomIndex = (int) ((Math.random() * (randomIndexMax - randomIndexMin)) + randomIndexMin);

        return new int[]{this.game.transformStringInputToXValue(fields.get(randomIndex).charAt(0)), this.game.transformStringInputToYValue(fields.get(randomIndex).substring(1))};
    }

    private int[] getRandomNumberOfFieldsWithTheMostPotentialNeighbours() {
        //all available fields are checked for the amount of viable field neighbours and only the ones with the most will be picked for the random draw
        ArrayList<String> fieldsAvailableForBotSorted = (ArrayList<String>) this.fieldsAvailableForBot.stream()
                .sorted(Comparator.comparingInt(this::checkViableFieldNeighbours))
                .collect(Collectors.toList());

        int viableNeighboursAtTop = this.checkViableFieldNeighbours(fieldsAvailableForBotSorted.get(fieldsAvailableForBotSorted.size() - 1));

        fieldsAvailableForBotSorted = (ArrayList<String>) fieldsAvailableForBotSorted.stream()
                .filter(e -> this.checkViableFieldNeighbours(e) == viableNeighboursAtTop)
                .collect(Collectors.toList());

        double randomIndexMin = 0.5;
        int randomIndexMax = fieldsAvailableForBotSorted.size();
        int randomIndex = (int) ((Math.random() * (randomIndexMax - randomIndexMin)) + randomIndexMin);

        return new int[]{this.game.transformStringInputToXValue(fieldsAvailableForBotSorted.get(randomIndex).charAt(0)), this.game.transformStringInputToYValue(fieldsAvailableForBotSorted.get(randomIndex).substring(1))};
    }

    private ArrayList<String> choosePotentialMoves(ArrayList<String> successfulMoves) {
        ArrayList<String> potentialNextMoves = new ArrayList<>();
        for (String move : successfulMoves) {
            potentialNextMoves.add(this.getFieldNeighbours(move).get(0));
            potentialNextMoves.add(this.getFieldNeighbours(move).get(1));
            potentialNextMoves.add(this.getFieldNeighbours(move).get(2));
            potentialNextMoves.add(this.getFieldNeighbours(move).get(3));
        }
        for (int i = 0; i <= potentialNextMoves.size() - 1; i++) {
            if (!this.fieldsAvailableForBot.contains(potentialNextMoves.get(i))) {
                potentialNextMoves.remove(potentialNextMoves.get(i));
                i--;
            }
        }
        return potentialNextMoves;
    }

    private ArrayList<String> getFieldNeighbours(String field) {
        ArrayList<String> fieldNeighbours = new ArrayList<>();
        int xField = this.game.transformStringInputToXValue(field.charAt(0));
        int yField = this.game.transformStringInputToYValue(field.substring(1));

        //neighbour right
        int xNeighbourRight = xField + 1;
        char xNeighbourRightAsChar = this.game.transformNumericInputOfXToCharValue(xNeighbourRight);
        String yNeighbourRightAsString = this.game.transformNumericInputOfYToStringValue(yField);
        fieldNeighbours.add(xNeighbourRightAsChar + yNeighbourRightAsString);

        //neighbour left
        int xNeighbourLeft = xField - 1;
        char xNeighbourLeftAsChar = this.game.transformNumericInputOfXToCharValue(xNeighbourLeft);
        String yNeighbourLeftAsString = this.game.transformNumericInputOfYToStringValue(yField);
        fieldNeighbours.add(xNeighbourLeftAsChar + yNeighbourLeftAsString);

        //neighbour top
        int yNeighbourTop = yField - 1;
        char xNeighbourTopAsChar = this.game.transformNumericInputOfXToCharValue(xField);
        String yNeighbourTopAsString = this.game.transformNumericInputOfYToStringValue(yNeighbourTop);
        fieldNeighbours.add(xNeighbourTopAsChar + yNeighbourTopAsString);

        //neighbour bottom
        int yNeighbourBottom = yField + 1;
        char xNeighbourBottomAsChar = this.game.transformNumericInputOfXToCharValue(xField);
        String yNeighbourBottomAsString = this.game.transformNumericInputOfYToStringValue(yNeighbourBottom);
        fieldNeighbours.add(xNeighbourBottomAsChar + yNeighbourBottomAsString);

        return fieldNeighbours;
    }

    private int checkViableFieldNeighbours(String field) {
        int neighboursAvailable = 0;
        ArrayList<String> neighbours = this.getFieldNeighbours(field);
        for (String neighbour : neighbours) {
            if (this.fieldsAvailableForBot.contains(neighbour)) {
                neighboursAvailable++;
            }
        }
        return neighboursAvailable;
    }


    private boolean checkLastFourMoves(ArrayList<String> successfulMoves) {
        boolean lastFourMovesSuccessful = false;
        if (this.equals(this.game.getPlayer1())) {
            for (int i = this.game.getMovesPlayer1().size() - 1; i > this.game.getMovesPlayer1().size() - 5; i--) {
                if (successfulMoves.contains(this.game.getMovesPlayer1().get(i))) {
                    lastFourMovesSuccessful = true;
                    break;
                }
            }
        } else if (this.equals(this.game.getPlayer2())) {
            for (int i = this.game.getMovesPlayer2().size() - 1; i > this.game.getMovesPlayer2().size() - 5; i--) {
                if (successfulMoves.contains(this.game.getMovesPlayer2().get(i))) {
                    lastFourMovesSuccessful = true;
                    break;
                }
            }
        }
        return lastFourMovesSuccessful;
    }

    private ArrayList<String> getAllFields() {
        ArrayList<String> allFields = new ArrayList<>();
        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                char reverseX = this.game.transformNumericInputOfXToCharValue(x);
                String reverseY = this.game.transformNumericInputOfYToStringValue(y);
                allFields.add(reverseX + reverseY);
            }
        }
        return allFields;
    }


    private ArrayList<String> checkFieldRenderStateNextTo(ArrayList<String> potentialMoves, boolean isX) {
        ArrayList<String> viableMoves = (ArrayList<String>) potentialMoves.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        char xViableMove;
        String yViableMove;

        char xNonViableMove;
        String yNonViableMove;

        if (this.equals(game.getPlayer1())) {
            if (isX) {
                for (int i = 0; i < potentialMoves.size() - 1; i++) {
                    int x = this.game.transformStringInputToXValue(potentialMoves.get(i).charAt(0));
                    int y = this.game.transformStringInputToYValue(potentialMoves.get(i).substring(1));

                    if (this.game.getPlaygroundPlayer2().getMap()[x][y + 1].getFieldRenderState() == Field.CurrentState.MISS) {
                        xNonViableMove = this.game.transformNumericInputOfXToCharValue(x);
                        yNonViableMove = this.game.transformNumericInputOfYToStringValue(y + 1);
                        viableMoves.remove(xNonViableMove + yNonViableMove);
                        i--;
                    } else {
                        xViableMove = this.game.transformNumericInputOfXToCharValue(x);
                        yViableMove = this.game.transformNumericInputOfYToStringValue(y + 1);
                        viableMoves.add(xViableMove + yViableMove);
                    }
                }

            } else {
                for (int i = 0; i < potentialMoves.size() - 1; i++) {
                    int x = this.game.transformStringInputToXValue(potentialMoves.get(i).charAt(0));
                    int y = this.game.transformStringInputToYValue(potentialMoves.get(i).substring(1));

                    if (this.game.getPlaygroundPlayer2().getMap()[x + 1][y].getFieldRenderState() == Field.CurrentState.MISS) {
                        xNonViableMove = this.game.transformNumericInputOfXToCharValue(x + 1);
                        yNonViableMove = this.game.transformNumericInputOfYToStringValue(y);
                        viableMoves.remove(xNonViableMove + yNonViableMove);
                        i--;
                    } else {
                        xViableMove = this.game.transformNumericInputOfXToCharValue(x + 1);
                        yViableMove = this.game.transformNumericInputOfYToStringValue(y);
                        viableMoves.add(xViableMove + yViableMove);
                    }
                }
            }

        } else {
            if (isX) {
                for (int i = 0; i < potentialMoves.size() - 1; i++) {
                    int x = this.game.transformStringInputToXValue(potentialMoves.get(i).charAt(0));
                    int y = this.game.transformStringInputToYValue(potentialMoves.get(i).substring(1));

                    if (this.game.getPlaygroundPlayer1().getMap()[x][y + 1].getFieldRenderState() == Field.CurrentState.MISS) {
                        xNonViableMove = this.game.transformNumericInputOfXToCharValue(x);
                        yNonViableMove = this.game.transformNumericInputOfYToStringValue(y + 1);
                        viableMoves.remove(xNonViableMove + yNonViableMove);
                        i--;
                    } else {
                        xViableMove = this.game.transformNumericInputOfXToCharValue(x);
                        yViableMove = this.game.transformNumericInputOfYToStringValue(y + 1);
                        viableMoves.add(xViableMove + yViableMove);
                    }
                }

            } else {
                for (int i = 0; i < potentialMoves.size() - 1; i++) {
                    int x = this.game.transformStringInputToXValue(potentialMoves.get(i).charAt(0));
                    int y = this.game.transformStringInputToYValue(potentialMoves.get(i).substring(1));

                    if (this.game.getPlaygroundPlayer1().getMap()[x + 1][y].getFieldRenderState() == Field.CurrentState.MISS) {
                        xNonViableMove = this.game.transformNumericInputOfXToCharValue(x + 1);
                        yNonViableMove = this.game.transformNumericInputOfYToStringValue(y);
                        viableMoves.remove(xNonViableMove + yNonViableMove);
                        i--;
                    } else {
                        xViableMove = this.game.transformNumericInputOfXToCharValue(x + 1);
                        yViableMove = this.game.transformNumericInputOfYToStringValue(y);
                        viableMoves.add(xViableMove + yViableMove);
                    }
                }
            }
        }
        return viableMoves;
    }


    public void removeFieldAvailableForBot(String lastMove) {
        this.fieldsAvailableForBot.remove(lastMove);
    }

    public void addSuccessfulMoves(String successfulMove) {
        this.successfulMoves.add(successfulMove);
    }
}
