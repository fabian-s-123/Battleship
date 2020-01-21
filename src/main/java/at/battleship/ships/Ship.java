package at.battleship.ships;

import at.battleship.components.Player;

public abstract class Ship {

    protected char acronym;
    protected int tiles;
    protected Player player;
    protected int[][] positionShip;

    public Ship(Player player, int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
        this.player = player;
        this.setPosition(startRangeX, endRangeX, startRangeY, endRangeY);
    }

    public void setPosition(int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
        if (startRangeX == endRangeX) {
            for (int y = startRangeY; y <= endRangeY; y++) {
                this.positionShip[startRangeX][y] = y;
            }
        } else if (startRangeY == endRangeY) {
            for (int x = startRangeX; x <= endRangeX; x++) {
                this.positionShip[x][startRangeY] = x;
            }
        }
    }

    public boolean isInRange(int x, int y) {
        return false;
    }

    public int[][] getPositionShip() {
        return positionShip;
    }
}
