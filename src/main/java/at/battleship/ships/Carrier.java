package at.battleship.ships;

import at.battleship.components.Player;
import at.battleship.components.Playground;

public class Carrier extends Ship {

    private int tiles = 5;
    private Character acronym = 'C';
    private int startRangeX;
    private int endRangeX;
    private int startRangeY;
    private int endRangeY;
    private Player player;
    private int[][] positionShip;

    public Carrier(Player player, int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
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
