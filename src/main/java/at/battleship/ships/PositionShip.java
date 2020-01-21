package at.battleship.ships;

public class PositionShip {

    private int startRangeX;
    private int endRangeX;
    private int startRangeY;
    private int endRangeY;
    private int[][] position;

    public PositionShip(int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
        this.startRangeX = startRangeX;
        this.endRangeX = endRangeX;
        this.startRangeY = startRangeY;
        this.endRangeY = endRangeY;
        this.setPosition();
    }

    public void setPosition() {
        if (startRangeX == endRangeX) {
            for (int y = startRangeY; y <= endRangeY; y++) {
                position[startRangeX][y] = y;
            }
        } else if (startRangeY == endRangeY) {
            for (int x = startRangeX; x <= endRangeX; x++) {
                position[x][startRangeY] = x;
            }
        }
    }

    public int[][] getPosition() {
        return position;
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
