package at.battleship.components;

public class Player {

    private String name;
    private boolean playerTurn;
    private boolean isVisible;
    private int currentScore;
    private int movesRequiredToWin = 0;

    public Player(String name, boolean playerTurn, boolean isVisible, int currentScore) {
        this.name = name;
        this.playerTurn = playerTurn;
        this.isVisible = isVisible;
        this.currentScore = currentScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getMovesRequiredToWin() {
        return movesRequiredToWin;
    }

    public void addMovesRequiredToWin() {
        this.movesRequiredToWin++;
    }
}
