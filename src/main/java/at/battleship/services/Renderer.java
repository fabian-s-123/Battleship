package at.battleship.services;

import at.battleship.components.Playground;

public class Renderer {

    private Playground yourPlayground;
    private Playground opponentPlayground;

    public Renderer(Playground yourPlayground, Playground opponentPlayground) {
        this.yourPlayground = yourPlayground;
        this.opponentPlayground = opponentPlayground;
    }

    public void renderYourPlayground() {}

    public void renderOpponentPlayground(){}

}
