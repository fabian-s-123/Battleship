package at.battleship.services;

import at.battleship.components.Player;
import at.battleship.components.Playground;

public class Renderer {

    public Renderer(Playground playgroundPlayer1, Playground playgroundPlayer2, Player player1, Player player2) {
        this.draw(playgroundPlayer1, player1);
        this.draw(playgroundPlayer2, player2);
    }

/*    public void renderPlaygrounds() {
        System.out.println("Player 1");
        for (int y = 0; y < this.playGroundPlayer1.getMap().length; y++) {
            System.out.println();
            for (int x = 0; x < this.playGroundPlayer1.getMap().length; x++) {
                System.out.print(playGroundPlayer1.getMap()[x][y].displayCurrentState());
            }
        }
        System.out.println("\n");
        System.out.println("Player 2");

        for (int y = 0; y < this.playGroundPlayer2.getMap().length; y++) {
            System.out.println();
            for (int x = 0; x < this.playGroundPlayer2.getMap().length; x++) {
                System.out.print(playGroundPlayer2.getMap()[x][y].displayCurrentState());
            }
        }
    }*/

    public void draw(Playground playground, Player player) {
        System.out.println(renderPlayground(playground, player));
    }

    private String renderPlayground(Playground playground, Player player) {
        System.out.println(player.getName()+"\n");
        StringBuilder sb = new StringBuilder();
        sb.append(renderTop(playground));
        for (int y = 0; y < playground.getMap().length; y++) {
            for (int x = 0; x < playground.getMap().length; x++) {
                sb.append("\u2502").append(playground.getMap()[x][y].displayCurrentState());
            }
            sb.append("\u2502\n");
            if (playground.getMap().length - 1 != y) {
                sb.append(renderLine(playground));
            }
        }
        sb.append(renderBottom(playground));
        return sb.toString();
    }

    private String renderTop(Playground playground) {
        StringBuilder sb = new StringBuilder();
        sb.append("\u250C");
        for (int i = 0; i < playground.getMap().length - 1; i++) {
            sb.append("\u2500\u2500");
        }
        sb.append("\u2500\u2510\n");
        return sb.toString();
    }

    private String renderBottom(Playground playground) {
        StringBuilder sb = new StringBuilder();
        sb.append("\u2514");
        for (int i = 0; i < playground.getMap().length - 1; i++) {
            sb.append("\u2500\u2500");
        }
        sb.append("\u2500\u2518\n");
        return sb.toString();
    }

    private String renderLine(Playground playground) {
        StringBuilder sb = new StringBuilder();
        sb.append("\u2502");
        for (int i = 0; i < playground.getMap().length - 1; i++) {
            sb.append("\u2500\u2500");
        }
        sb.append("\u2500\u2502\n");
        return sb.toString();
    }
}
