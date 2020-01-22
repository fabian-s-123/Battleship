package at.battleship.components;

public class Ship {

    private Type type;
    private int startRangeX;
    private int endRangeX;
    private int startRangeY;
    private int endRangeY;

    public Ship(Type type, int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
        this.type = type;
        this.startRangeX = startRangeX;
        this.endRangeX = endRangeX;
        this.startRangeY = startRangeY;
        this.endRangeY = endRangeY;
        //this.setPosition(startRangeX, endRangeX, startRangeY, endRangeY);
    }

    public Ship(Type type) {
        this.type = type;
    }

    public enum Type {
        CARRIER (5),
        BATTLESHIP (4),
        DESTROYER (3),
        SUBMARINE (2);

        private final int length;

        Type(int length) {
            this.length = length;
        }
    }


    /*    protected void setPosition(int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
        if (startRangeX == endRangeX) {
            for (int y = startRangeY; y <= endRangeY; y++) {
                [startRangeX][y] = y;
            }
        } else if (startRangeY == endRangeY) {
            for (int x = startRangeX; x <= endRangeX; x++) {
                [x][startRangeY] = x;
            }
        }
    }*/

    public int[] getValueBetweenX(Ship ship) {
        if ((ship.getEndRangeX() - ship.getStartRangeX()) == 0) {
            return new int[0];
        } else {
            int[] values = new int[ship.getEndRangeX() - ship.getStartRangeX() + 1];
            for (int i = ship.getStartRangeX(); i <= ship.getEndRangeX(); i++) {
                values[i - ship.getStartRangeX()] = i;
            }
            return values;
        }
    }

    public int[] getValueBetweenY(Ship ship) {
        if ((ship.getEndRangeY() - ship.getStartRangeY()) == 0) {
            return new int[0];
        } else {
            int[] values = new int[ship.getEndRangeY() - ship.getStartRangeY() + 1];
            for (int i = ship.getStartRangeY(); i <= ship.getEndRangeY(); i++) {
                values[i - ship.getStartRangeY()] = i;
            }
            return values;
        }
    }

    public Type getType () {
        return this.type;
    }

    public boolean isInRange(int x, int y) {
        return false;
    }

    public int getStartRangeX() {
        return startRangeX;
    }

    public void setStartRangeX(int startRangeX) {
        this.startRangeX = startRangeX;
    }

    public int getEndRangeX() {
        return endRangeX;
    }

    public void setEndRangeX(int endRangeX) {
        this.endRangeX = endRangeX;
    }

    public int getStartRangeY() {
        return startRangeY;
    }

    public void setStartRangeY(int startRangeY) {
        this.startRangeY = startRangeY;
    }

    public int getEndRangeY() {
        return endRangeY;
    }

    public void setEndRangeY(int endRangeY) {
        this.endRangeY = endRangeY;
    }
}
