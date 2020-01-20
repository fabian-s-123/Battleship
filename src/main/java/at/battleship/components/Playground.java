package at.battleship.components;

import at.battleship.ships.*;

import java.util.ArrayList;
import java.util.List;

public class Playground {

    private Field[][] map;
    private boolean ship;
    private Player player1;
    private Player player2;


    public Playground(Field[][] map) {
        this.map = new Field[10][10];
    }

    private void setShip() {
        for (int i = 0; i < map.length; i++) {
            Field[] fields = map[i];
            
        }
    }

    private ArrayList<Ship> createShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        ships.add(new Carrier(player1)); //[3][8] - [7][8]
        ships.add(new Battleship(player1)); //[0][3] - [][8]
        ships.add(new Battleship(player1));
        ships.add(new Destroyer(player1));
        ships.add(new Destroyer(player1));
        ships.add(new Destroyer(player1));
        ships.add(new Submarine(player1));
        ships.add(new Submarine(player1));
        ships.add(new Submarine(player1));
        ships.add(new Submarine(player1));
        return ships;
    }



    public int[] setShipStartingPoint(String playerInput) {


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
    }

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

}
