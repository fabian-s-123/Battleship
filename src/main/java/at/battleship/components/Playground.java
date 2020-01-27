package at.battleship.components;

import java.util.ArrayList;
import java.util.Arrays;

public class Playground {

    private Field[][] map;
    private ArrayList<Ship> ships;
    private Player player;

    //TODO delete this constructor after transitioning to manual placement of ships
    Playground(ArrayList<Ship> ships, Player player) {
        this.map = createNewArray();
        this.ships = ships;
        this.player = player;
    }

    Playground(Player player) {
        this.map = createNewArray();
        this.player = player;
    }

    private Field[][] createNewArray() {
        Field[][] array = new Field[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                array[x][y] = new Field();
            }
        }
        return array;
    }

    void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    void addShip(Ship ship) {
        this.ships.add(ship);
    }

    void checkShipHitPoints(int x, int y) throws InterruptedException {
        for (Ship ship : this.ships) {
            if (ship.getValueBetweenX(ship).length != 0) {
                if (Arrays.stream(ship.getValueBetweenX(ship)).anyMatch(e -> e == x) && ship.getStartRangeY() == y) {
                    ship.reduceHitPoints();
                    if (ship.getHitPoints() == 0) {
                        System.out.println("Enemy " + ship.toString() + " has been destroyed.");
                        Thread.sleep(2000);
                        break;
                    }
                    break;
                }
            } else {
                if (Arrays.stream(ship.getValueBetweenY(ship)).anyMatch(e -> e == y) && ship.getStartRangeX() == x) {
                    ship.reduceHitPoints();
                    if (ship.getHitPoints() == 0) {
                        System.out.println("Enemy " + ship.toString() + " has been destroyed.");
                        Thread.sleep(2000);
                        break;
                    }
                    break;
                }
            }
        }
    }

    int checkMaxShipHitPointsCombined() {
        int currentHitPointsCombined = 0;
        for (Ship ship : this.ships) {
            currentHitPointsCombined += ship.getLength(ship.getType());
        }
        return currentHitPointsCombined;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    int checkPosition(int posX, int posY) {
        Field currentField = this.getMap()[posX][posY];
        Field.CurrentState currentState = currentField.getFieldRenderState();
        int result = -3;
        switch (currentState) {
            case NEUTRAL:
                result = 0;
                currentField.setCurrentState(Field.CurrentState.MISS);
                break;
            case CARRIER:
            case BATTLESHIP:
            case DESTROYER:
            case SUBMARINE:
                result = 1;
                currentField.setCurrentState(Field.CurrentState.HIT);
                break;
            case MISS:
                result = -1;
                break;
            case HIT:
                result = -2;
                break;
        }
        return result;
    }

    public Field[][] getMap() {
        return map;
    }
}
