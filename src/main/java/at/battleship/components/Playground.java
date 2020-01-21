package at.battleship.components;

import at.battleship.ships.*;

import java.util.ArrayList;
import java.util.List;

public class Playground {

    private Field[][] map;
    private boolean ship;
    private Player player1;
    private Player player2;

    public Playground() {
        this.map = new Field[10][10];
    }


    public void setShipsPlayer1() {
        Carrier carrier = new Carrier(player1, 3, 7, 8, 8);
//        ships.add(carrier);

        Battleship battleship1 = new Battleship(player1, 0, 0, 3, 6);
//        ships.add(battleship1);
        Battleship battleship2 = new Battleship(player1, 3, 6, 3, 3);
//        ships.add(battleship2);

        Destroyer destroyer1 = new Destroyer(player1, 1, 1, 0, 2);
//        ships.add(destroyer1);
        Destroyer destroyer2 = new Destroyer(player1, 4, 6, 0, 0);
//        ships.add(destroyer2);
        Destroyer destroyer3 = new Destroyer(player1, 1, 3, 9, 9);
//        ships.add(destroyer3);

        Submarine submarine1 = new Submarine(player1, 6, 6, 2, 3);
//        ships.add(submarine1);
        Submarine submarine2 = new Submarine(player1, 9, 9, 0, 1);
//        ships.add(submarine2);
        Submarine submarine3 = new Submarine(player1, 6, 7, 8, 8);
//        ships.add(submarine3);
        Submarine submarine4 = new Submarine(player1, 8, 9, 9, 9);
//        ships.add(submarine4);

/*        int[] position = new int[2];
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
        return position;*/
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


    public void setShipsPlayer2() {
        Carrier carrier = new Carrier(player2, 0, 4, 0, 0);
//        ships.add(carrier);

        Battleship battleship1 = new Battleship(player2, 4, 7, 2, 2);
//        ships.add(battleship1);
        Battleship battleship2 = new Battleship(player2, 0, 0, 3, 6);
//        ships.add(battleship2);

        Destroyer destroyer1 = new Destroyer(player2, 2, 2, 2, 4);
//        ships.add(destroyer1);
        Destroyer destroyer2 = new Destroyer(player2, 6, 8, 5, 5);
//        ships.add(destroyer2);
        Destroyer destroyer3 = new Destroyer(player2, 6, 8, 8, 8);
//        ships.add(destroyer3);

        Submarine submarine1 = new Submarine(player2, 9, 9, 0, 1);
//        ships.add(submarine1);
        Submarine submarine2 = new Submarine(player2, 9, 9, 4, 5);
//        ships.add(submarine2);
        Submarine submarine3 = new Submarine(player2, 2, 3, 6, 6);
//        ships.add(submarine3);
        Submarine submarine4 = new Submarine(player2, 2, 3, 9, 9);
//        ships.add(submarine4);
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
