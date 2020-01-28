package at.battleship.components;

import java.util.ArrayList;
import java.util.Arrays;

public class Playground {

    private Field[][] map;
    private ArrayList<Ship> ships = new ArrayList<>();
    private Player player;
    private boolean shipDestroyed = false;

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

//    int[] getDamagedShips() {
//        int[] damagedFields = new int[]{};
//        Ship damagedShip = this.ships.stream().
//                filter(e -> e.getHitPoints() != e.getLength(e.getType()))
//                .findFirst()
//                .get();
//        if (damagedShip.getLength(damagedShip.getType()) - damagedShip.getHitPoints() >= 2) {
//            if (damagedShip.getValueBetweenX(damagedShip).length == 0) {
//                damagedFields = damagedShip.getValueBetweenY(damagedShip);
//            } else {
//                damagedFields = damagedShip.getValueBetweenX(damagedShip);
//            }
//        } else
//        return damagedFields;
//    }



    void checkShipHitPoints(int x, int y, Player opponent) throws InterruptedException {
        boolean shipDestroyed = false;
        for (Ship ship : this.ships) {
            if (ship.getValueBetweenX(ship).length != 0) {
                if (Arrays.stream(ship.getValueBetweenX(ship)).anyMatch(e -> e == x) && ship.getStartRangeY() == y) {
                    ship.reduceHitPoints();
                    if (ship.getHitPoints() == 0) {
                        if (this.player.equals(opponent)) {
                            System.out.println("Enemy " + ship.toString() + " has been destroyed.");
                            Thread.sleep(2000);
                            this.shipDestroyed = true;
                            break;
                        } else {
                            System.out.println("Your " + ship.toString() + " has been destroyed.");
                            Thread.sleep(2000);
                            this.shipDestroyed = true;
                            break;
                        }
                    }
                    this.shipDestroyed = false;
                    break;
                }
            } else {
                if (Arrays.stream(ship.getValueBetweenY(ship)).anyMatch(e -> e == y) && ship.getStartRangeX() == x) {
                    ship.reduceHitPoints();
                    if (ship.getHitPoints() == 0) {
                        if (this.player.equals(opponent)) {
                            System.out.println("Enemy " + ship.toString() + " has been destroyed.");
                            Thread.sleep(2000);
                            this.shipDestroyed = true;
                            break;
                        } else {
                            System.out.println("Your " + ship.toString() + " has been destroyed.");
                            Thread.sleep(2000);
                            this.shipDestroyed = true;
                            break;
                        }
                    } else {
                        this.shipDestroyed = false;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    boolean isShipDestroyed() {
        return shipDestroyed;
    }
}
