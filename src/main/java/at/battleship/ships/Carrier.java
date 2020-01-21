package at.battleship.ships;

import at.battleship.components.Player;

public class Carrier extends Ship {

    private int tiles = 5;
    private Character acronym = 'C';
    private Player player;
    private int[][] positionShip;

    public Carrier(Player player, int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
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
