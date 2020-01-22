package at.battleship.components;

public class Player {

    private String name;
    private boolean playerTurn;
    private boolean isVisible;
    private int currentScore;
    private int movesTally = 0;

    Player(String name, boolean playerTurn, boolean isVisible, int currentScore) {
        this.name = name;
        this.playerTurn = playerTurn;
        this.isVisible = isVisible;
        this.currentScore = currentScore;
    }

    public String getName() {
        return name;
    }

    boolean getPlayerTurn() {
        return playerTurn;
    }

    void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    int getMovesTally() {
        return movesTally;
    }

    void addMovesTally() {
        this.movesTally++;
    }
}
