package at.battleship.components;

public class Player {

    private String name;
    private boolean playerTurn;
    private int score;

    public Player(String name, boolean playerTurn, int score) {
        this.name = name;
        this.playerTurn = playerTurn;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
