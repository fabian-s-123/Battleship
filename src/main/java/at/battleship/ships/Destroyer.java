package at.battleship.ships;

import at.battleship.components.Player;

public class Destroyer extends Ship {

    private int tiles = 3;
    private Character acronym = 'D';
    private int startRangeX;
    private int endRangeX;
    private int startRangeY;
    private int endRangeY;
    private Player player;
    private int[][] positionShip;

    public Destroyer(Player player, int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
        super(player, startRangeX, endRangeX, startRangeY, endRangeY);
    }

    public int getTiles() {
        return tiles;
    }

    public char getAcronym() {
        return acronym;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int[][] getPositionShip() {
        return positionShip;
    }

    public void setPositionShip(int[][] positionShip) {
        this.positionShip = positionShip;
    }
}
