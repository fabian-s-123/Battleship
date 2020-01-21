package at.battleship.components;

public class Player {

    private String name;
    private boolean playerTurn;
    private boolean isVisible;
    private int score;

    public Player(String name, boolean playerTurn, boolean isVisible, int score) {
        this.name = name;
        this.playerTurn = playerTurn;
        this.isVisible = isVisible;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
