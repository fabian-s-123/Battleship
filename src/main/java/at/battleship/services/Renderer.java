package at.battleship.services;

import at.battleship.components.Playground;

public class Renderer {

    private Playground playGroundPlayer1;
    private Playground playGroundPlayer2;

    public Renderer(Playground playgroundPlayer1, Playground playgroundPlayer2) {
        this.playGroundPlayer1 = playgroundPlayer1;
        this.playGroundPlayer2 = playgroundPlayer2;
    }

    public void renderPlaygrounds() {
        System.out.println("Player 1");
        for (int i = 0; i < this.playGroundPlayer1.getMap().length; i++) {
            System.out.println();
            for (int j = 0; j < this.playGroundPlayer1.getMap().length; j++) {
                System.out.print(playGroundPlayer1.getMap()[j][i].displayCurrentState());
            }
        }
        System.out.println("\n");
        System.out.println("Player 2");

        for (int i = 0; i < this.playGroundPlayer2.getMap().length; i++) {
            System.out.println();
            for (int j = 0; j < this.playGroundPlayer2.getMap().length; j++) {
                System.out.print(playGroundPlayer2.getMap()[j][i].displayCurrentState());
            }
        }
    }
}
