package at.battleship.ships;

import at.battleship.components.Player;

public class Submarine extends Ship {

    private int tiles = 2;
    private Character acronym = 'S';
    private Player player;
    private int[][] positionShip;


    public Submarine(Player player, int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
        super(player, startRangeX, endRangeX, startRangeY, endRangeY);
    }

    public int getTiles() {
        return tiles;
    }

    public Character getAcronym() {
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
