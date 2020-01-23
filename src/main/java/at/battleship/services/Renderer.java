package at.battleship.services;

import at.battleship.components.Player;
import at.battleship.components.Playground;

import java.io.IOException;

public class Renderer {

    private Playground playgroundPlayer1;
    private Playground playgroundPlayer2;
    private Player player1;
    private Player player2;

    public Renderer(Playground playgroundPlayer1, Playground playgroundPlayer2, Player player1, Player player2) throws InterruptedException {
        this.playgroundPlayer1 = playgroundPlayer1;
        this.playgroundPlayer2 = playgroundPlayer2;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void render() throws InterruptedException {
        Thread.sleep(2500);
        this.clearConsole();
        this.draw(this.playgroundPlayer1, this.player1);
        this.draw(this.playgroundPlayer2, this.player2);
    }

    public void renderOnePlayground(Player player) {
        if (player == this.player1) {
            this.clearConsole();
            this.draw(this.playgroundPlayer1, this.player1);
        } else {
            this.clearConsole();
            this.draw(this.playgroundPlayer2, this.player2);
        }
    }

    private void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void draw(Playground playground, Player player) {
        renderPlayerInformation(player);
        System.out.println(renderPlayground(playground, player));
    }

    private void renderPlayerInformation(Player player) {
        System.out.printf("\n   %-22s Current Score: %2d\n\n",  player.getName(), player.getCurrentScore());
    }

    private String renderPlayground(Playground playground, Player player) {
        int column = 65;
        for (int a = 0; a < playground.getMap().length; a++) {
            char columnLabel = (char) column;
            if (a == 0) {
                System.out.printf("     %1c ", columnLabel);
                column++;
            } else {
                System.out.printf("  %1c ", columnLabel);
                column++;
            }
        }
        System.out.println("");

        StringBuilder sb = new StringBuilder();
        sb.append(renderTop(playground));
        int row = 1;
        for (int y = 0; y < playground.getMap().length; y++) {
            for (int x = 0; x < playground.getMap().length; x++) {
                if (x == 0) {
                    if (row == playground.getMap().length) {
                        sb.append(row).append(" ");
                    } else if (row < playground.getMap().length) {
                        sb.append(row).append("  ");
                        row++;
                    }
                }
                sb.append("\u2502").append(playground.getMap()[x][y].displayCurrentState(player.isVisible()));
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
        sb.append("   \u250C\u2500");
        for (int i = 0; i < playground.getMap().length - 1; i++) {
            sb.append("\u2500\u2500\u2500\u2500");
        }
        sb.append("\u2500\u2500\u2510\n");
        return sb.toString();
    }

    private String renderBottom(Playground playground) {
        StringBuilder sb = new StringBuilder();
        sb.append("   \u2514\u2500");
        for (int i = 0; i < playground.getMap().length - 1; i++) {
            sb.append("\u2500\u2500\u2500\u2500");
        }
        sb.append("\u2500\u2500\u2518\n");
        return sb.toString();
    }

    private String renderLine(Playground playground) {
        StringBuilder sb = new StringBuilder();
        sb.append("   \u2502\u2500");
        for (int i = 0; i < playground.getMap().length - 1; i++) {
            sb.append("\u2500\u2500\u2500\u2500");
        }
        sb.append("\u2500\u2500\u2502\n");
        return sb.toString();
    }
}
