package at.battleship.services;

import at.battleship.components.Playground;

public class Renderer {

    private Playground playGroundPlayer1;
    private Playground playGroundPlayer2;

    public Renderer(Playground yourPlayground, Playground opponentPlayground) {
        this.playGroundPlayer1 = yourPlayground;
        this.playGroundPlayer2 = opponentPlayground;
    }

    public void renderPlaygroundPlayer1() {
        System.out.println(playGroundPlayer1.getMap()[0][0].getFieldRenderState());
    }

    public void renderPlayGroundPlayer2(){}

}
