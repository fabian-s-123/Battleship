package at.battleship.players;

public abstract class Player {

    private String name;
    private boolean playerTurn;
    private boolean isVisible;
    private int currentScore;
    int movesTally = 0;

    Player(String name, boolean playerTurn, boolean isVisible, int currentScore) {
        this.name = name;
        this.playerTurn = playerTurn;
        this.isVisible = isVisible;
        this.currentScore = currentScore;
    }

    public String getName() {
        return name;
    }

    public boolean getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getMovesTally() {
        return movesTally;
    }

    public void addMovesTally() {
        this.movesTally++;
    }
}
