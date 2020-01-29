package at.battleship.players;

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
        int xNextMove;
        int yNextMove;

        //if no ship has been destroyed with previous hit, go for standard logic of trying to find a line of previous hits and move along that coordinate
        if (!shipDestroyed) {

            ArrayList<String> potentialMoves; //all potential moves are stored here
            potentialMoves = this.choosePotentialMoves(this.successfulMoves); //fills the list with every field around a already successfully targeted field ( = Field.CurrentState.HIT)

            //checks if the two last successful moves were on the same X or Y coordinate -> if so: move along the same coordinate for the next guess
            char potentialX = 'z';
            String potentialY = "";
            if (this.successfulMoves.size() >= 2) {
                int indexTop = this.successfulMoves.size() - 1;
                int indexBelowTop = this.successfulMoves.size() - 2;
                if (this.successfulMoves.get(indexTop).charAt(0) == this.successfulMoves.get(indexBelowTop).charAt(0) &&
                        (Integer.parseInt(this.successfulMoves.get(indexTop).substring(1)) - Integer.parseInt(this.successfulMoves.get(indexBelowTop).substring(1))) < 2 &&
                        (Integer.parseInt(this.successfulMoves.get(indexTop).substring(1)) - Integer.parseInt(this.successfulMoves.get(indexBelowTop).substring(1))) > -2) {
                    potentialX = this.successfulMoves.get(indexTop).charAt(0);
                } else if (this.successfulMoves.get(indexTop).substring(1).equals(this.successfulMoves.get(indexBelowTop).substring(1)) &&
                        ((int)this.successfulMoves.get(indexTop).charAt(0) - (int)this.successfulMoves.get(indexBelowTop).charAt(0)) < 2 &&
                        ((int)this.successfulMoves.get(indexTop).charAt(0) - (int)this.successfulMoves.get(indexBelowTop).charAt(0)) > -2) {
                    potentialY = this.successfulMoves.get(indexTop).substring(1);
                } else { //else just use a field neighbour of the last successful guess for the next move
                    ArrayList<String> lastSuccessfulMove = new ArrayList<>();
                    lastSuccessfulMove.add(this.successfulMoves.get(indexTop));
                    potentialMoves = this.choosePotentialMoves(lastSuccessfulMove);
                }
            }

            //leaves only potential moves in the list which align with previous hits on the same coordinate
            if (potentialX != 'z') {
                String lastSuccessfulMove = this.successfulMoves.get(this.successfulMoves.size() - 1);
                char finalPotentialX = potentialX;
                potentialMoves = (ArrayList<String>) potentialMoves.stream()
                        .filter(e -> e.charAt(0) == finalPotentialX)
                        .filter(e -> (int) e.charAt(0) != ((int) e.charAt(0) + 1))
                        .filter(e -> (int) e.charAt(0) != ((int) e.charAt(0) + 1))
                        .filter(e -> (Integer.parseInt(e.substring(1)) - Integer.parseInt(lastSuccessfulMove.substring(1))) < 5 &&
                                (Integer.parseInt(e.substring(1)) - Integer.parseInt(lastSuccessfulMove.substring(1))) > -5)
                        .collect(Collectors.toList());
            } else if (potentialY.length() > 0) {
                String lastSuccessfulMove = this.successfulMoves.get(this.successfulMoves.size() - 1);
                String finalPotentialY = potentialY;
                potentialMoves = (ArrayList<String>) potentialMoves.stream()
                        .filter(e -> e.substring(1).equals(finalPotentialY))
                        .filter(e -> Integer.parseInt(e.substring(1)) != (Integer.parseInt(e.substring(1)) - 1))
                        .filter(e -> Integer.parseInt(e.substring(1)) != (Integer.parseInt(e.substring(1)) + 1))
                        .filter(e -> ((int) e.charAt(0) - (int) lastSuccessfulMove.charAt(0)) < 5 &&
                                ((int) e.charAt(0) - (int) lastSuccessfulMove.charAt(0)) > -5)
                        .collect(Collectors.toList());
            }

            if (potentialMoves.size() == 0 || this.successfulMoves.size() >= 5 && !this.checkLastFourMoves(this.successfulMoves)) {
                //all available fields are checked for the amount of viable field neighbours and only the ones with the most will be picked for the random draw
                ArrayList<String> fieldsAvailableForBotSorted = (ArrayList<String>) this.fieldsAvailableForBot.stream()
                        .sorted(Comparator.comparingInt(this::checkViableFieldNeighbours))
                        .collect(Collectors.toList());

                int viableNeighboursAtTop = this.checkViableFieldNeighbours(fieldsAvailableForBotSorted.get(fieldsAvailableForBotSorted.size() - 1));

                fieldsAvailableForBotSorted = (ArrayList<String>) fieldsAvailableForBot.stream()
                        .filter(e -> this.checkViableFieldNeighbours(e) == viableNeighboursAtTop)
                        .collect(Collectors.toList());

                double randomIndexMin = 0.5;
                int randomIndexMax = fieldsAvailableForBotSorted.size();
                int randomIndex = (int) ((Math.random() * (randomIndexMax - randomIndexMin)) + randomIndexMin);
                xNextMove = this.game.transformStringInputToXValue(fieldsAvailableForBot.get(randomIndex).charAt(0));
                yNextMove = this.game.transformStringInputToYValue(fieldsAvailableForBot.get(randomIndex).substring(1));

            } else {
                double min = 0.5;
                int max = potentialMoves.size() - 1;
                int index = (int) ((Math.random() * max) + min);
                xNextMove = this.game.transformStringInputToXValue(potentialMoves.get(index).charAt(0));
                yNextMove = this.game.transformStringInputToYValue(potentialMoves.get(index).substring(1));
            }

        } else { //if a ship has been destroyed with the last move or no logical potential next moves are available -> go for random number
            //all available fields are checked for the amount of viable field neighbours and only the ones with the most will be picked for the random draw
            ArrayList<String> fieldsAvailableForBotSorted = (ArrayList<String>) this.fieldsAvailableForBot.stream()
                    .sorted(Comparator.comparingInt(this::checkViableFieldNeighbours))
                    .collect(Collectors.toList());

            int viableNeighboursAtTop = this.checkViableFieldNeighbours(fieldsAvailableForBotSorted.get(fieldsAvailableForBotSorted.size() - 1));

            fieldsAvailableForBotSorted = (ArrayList<String>) fieldsAvailableForBot.stream()
                    .filter(e -> this.checkViableFieldNeighbours(e) == viableNeighboursAtTop)
                    .collect(Collectors.toList());

            double randomIndexMin = 0.5;
            int randomIndexMax = fieldsAvailableForBotSorted.size();
            int randomIndex = (int) ((Math.random() * (randomIndexMax - randomIndexMin)) + randomIndexMin);

            xNextMove = this.game.transformStringInputToXValue(fieldsAvailableForBot.get(randomIndex).charAt(0));
            yNextMove = this.game.transformStringInputToYValue(fieldsAvailableForBot.get(randomIndex).substring(1));
        }

        return new int[]{xNextMove, yNextMove};
    }



    /**
     * Helper Methods
     */
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
        char xNeighbourRightAsChar = this.game.transformNumericInputOfXToStringValue(xNeighbourRight);
        String yNeighbourRightAsString = this.game.transformNumericInputOfYToStringValue(yField);
        fieldNeighbours.add(xNeighbourRightAsChar + yNeighbourRightAsString);

        //neighbour left
        int xNeighbourLeft = xField - 1;
        char xNeighbourLeftAsChar = this.game.transformNumericInputOfXToStringValue(xNeighbourLeft);
        String yNeighbourLeftAsString = this.game.transformNumericInputOfYToStringValue(yField);
        fieldNeighbours.add(xNeighbourLeftAsChar + yNeighbourLeftAsString);

        //neighbour top
        int yNeighbourTop = yField - 1;
        char xNeighbourTopAsChar = this.game.transformNumericInputOfXToStringValue(xField);
        String yNeighbourTopAsString = this.game.transformNumericInputOfYToStringValue(yNeighbourTop);
        fieldNeighbours.add(xNeighbourTopAsChar + yNeighbourTopAsString);

        //neighbour bottom
        int yNeighbourBottom = yField + 1;
        char xNeighbourBottomAsChar = this.game.transformNumericInputOfXToStringValue(xField);
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
        for (int i = this.game.getMovesPlayer2().size() - 1; i > this.game.getMovesPlayer2().size() - 5; i--) {
            if (successfulMoves.contains(this.game.getMovesPlayer2().get(i))) {
                lastFourMovesSuccessful = true;
                break;
            }
        }
        return lastFourMovesSuccessful;
    }

    private ArrayList<String> getAllFields() {
        ArrayList<String> allFields = new ArrayList<>();
        for (int x = 0; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                char reverseX = this.game.transformNumericInputOfXToStringValue(x);
                String reverseY = this.game.transformNumericInputOfYToStringValue(y);
                allFields.add(reverseX + reverseY);
            }
        }
        return allFields;
    }



    public void removeFieldAvailableForBot(String lastMove) {
        this.fieldsAvailableForBot.remove(lastMove);
    }

    public void addSuccessfulMoves(String successfulMove) {
        this.successfulMoves.add(successfulMove);
    }
}
