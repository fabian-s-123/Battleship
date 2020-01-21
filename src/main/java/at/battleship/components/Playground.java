package at.battleship.components;

import at.battleship.ships.*;

import java.util.ArrayList;

public class Playground {

    private Field[][] map;
    private ArrayList<Ship> ships;
    private Player player;


    public Playground(ArrayList<Ship> ships, Player player) {
        this.map = createNewArray(10, 10);
        this.ships = ships;
        this.player = player;
    }

    private Field[][] createNewArray(int x1, int y1) {
        Field [][] array = new Field[x1][y1];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                array[x][y] = new Field(null);
            }
        }
        return array;
    }

/*    public void setShipsPlayer1() {
        int[] position = new int[2];
        char posX = playerInput.toLowerCase().charAt(0);
        int posY = playerInput.indexOf(1);

        switch (posX) {
            case 'a':
                position[0] = 0;
                break;
            case 'b':
                position[0] = 1;
                break;
            case 'c':
                position[0] = 2;
                break;
            case 'd':
                position[0] = 3;
                break;
            case 'e':
                position[0] = 4;
                break;
            case 'f':
                position[0] = 5;
                break;
            case 'g':
                position[0] = 6;
                break;
            case 'h':
                position[0] = 7;
                break;
            case 'i':
                position[0] = 8;
                break;
            case 'j':
                position[0] = 9;
                break;
        }
        position[1] = posY + 1;
        return position;
    }*/

    public void setShipDirection(String playerInput) {
        switch (playerInput.toLowerCase()) {
            case "up":

                break;
            case "down":

                break;
            case "right":

                break;
            case "left":

                break;
        }
    }



    private String checkPosition(int posX, int posY) {
        return " ";
    }

    public void setMap(Field[][] map) {
        this.map = map;
    }

    public Field[][] getMap() {
        return map;
    }
}
