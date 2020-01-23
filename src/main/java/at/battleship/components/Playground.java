package at.battleship.components;

import java.util.ArrayList;

public class Playground {

    private Field[][] map;
    private ArrayList<Ship> ships;
    private Player player;

    //TODO delete this constructor after transitioning to manual placement of ships
    Playground(ArrayList<Ship> ships, Player player) {
        this.map = createNewArray(10, 10);
        this.ships = ships;
        this.player = player;
    }

    Playground(Player player) {
        this.map = createNewArray(10, 10);
        this.player = player;
    }

    private Field[][] createNewArray(int x1, int y1) {
        Field [][] array = new Field[x1][y1];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                array[x][y] = new Field();
            }
        }
        return array;
    }

    private void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
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
