package at.battleship.components;

public class Ship {

    private Type type;
    private int startRangeX;
    private int endRangeX;
    private int startRangeY;
    private int endRangeY;
    private int hitPoints;

    Ship(Type type, int startRangeX, int endRangeX, int startRangeY, int endRangeY) {
        this.type = type;
        this.startRangeX = startRangeX;
        this.endRangeX = endRangeX;
        this.startRangeY = startRangeY;
        this.endRangeY = endRangeY;
        this.hitPoints = type.length;
    }

    Ship(Type type) {
        this.type = type;
    }

    public enum Type {
        CARRIER(5),
        BATTLESHIP(4),
        DESTROYER(3),
        SUBMARINE(2);

        private final int length;

        Type(int length) {
            this.length = length;
        }
    }

    int getLength(Type type) {
        return type.length;
    }

    int[] getValueBetweenX(Ship ship) {
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

    int[] getValueBetweenY(Ship ship) {
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

    int getHitPoints() {
        return this.hitPoints;
    }

    void reduceHitPoints() {
        hitPoints--;
    }

    Type getType() {
        return this.type;
    }

    int getStartRangeX() {
        return startRangeX;
    }

    void setStartRangeX(int startRangeX) {
        this.startRangeX = startRangeX;
    }

    private int getEndRangeX() {
        return endRangeX;
    }

    void setEndRangeX(int endRangeX) {
        this.endRangeX = endRangeX;
    }

    int getStartRangeY() {
        return startRangeY;
    }

    void setStartRangeY(int startRangeY) {
        this.startRangeY = startRangeY;
    }

    private int getEndRangeY() {
        return endRangeY;
    }

    void setEndRangeY(int endRangeY) {
        this.endRangeY = endRangeY;
    }

    @Override
    public String toString() {
        char firstLetter = type.toString().charAt(0);
        String restOfTheLetters = type.toString().substring(1).toLowerCase();
        return (firstLetter + restOfTheLetters);
    }
}
