package at.battleship.components;

import at.battleship.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Playground {

    private Field[][] map;
    private ArrayList<Ship> ships = new ArrayList<>();
    private Player player;
    private boolean shipDestroyed = false;

    public Playground(ArrayList<Ship> ships, Player player) {
        this.map = createNewArray();
        this.ships = ships;
        this.player = player;
    }

    public Playground(Player player) {
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

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public void addShip(Ship ship) {
        this.ships.add(ship);
    }

//    public int[] getDamagedShip() {
//        int[] damagedShipFields = new int[0];
//        Ship damagedShip = this.ships.stream()
//                .filter(e -> e.getHitPoints() != e.getLength(e.getType()) && e.getHitPoints() > 0)
//                .findFirst()
//                .get();
//            int[] rangeX = damagedShip.getValueBetweenX(damagedShip);
//            int[] rangeY = damagedShip.getValueBetweenY(damagedShip);
//            if (!(rangeX.length == 0)) {
//                damagedShipFields = new int[rangeX.length + 2];
//                for (int i = 2; i <= damagedShipFields.length - 1; i++) {
//                    damagedShipFields[0] = 0;
//                    damagedShipFields[1] = damagedShip.getStartRangeY();
//                    damagedShipFields[i] = rangeX[i - 1];
//                }
//            } else if (!(rangeY.length == 0)) {
//                damagedShipFields = new int[rangeY.length + 2];
//                for (int i = 2; i <= damagedShipFields.length - 1; i++) {
//                    damagedShipFields[0] = 1;
//                    damagedShipFields[1] = damagedShip.getStartRangeX();
//                    damagedShipFields[i] = rangeY[i - 1];
//                }
//            }
//        return damagedShipFields;
//    }

    public void checkShipHitPoints(int x, int y, Player opponent) throws InterruptedException {
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

    public int checkMaxShipHitPointsCombined() {
        int currentHitPointsCombined = 0;
        for (Ship ship : this.ships) {
            currentHitPointsCombined += ship.getLength(ship.getType());
        }
        return currentHitPointsCombined;
    }

    public int checkPosition(int posX, int posY) {
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

    public boolean isShipDestroyed() {
        return shipDestroyed;
    }
}
