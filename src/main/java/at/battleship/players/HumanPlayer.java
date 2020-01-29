package at.battleship.players;

import at.battleship.game.Game;

public class HumanPlayer extends Player {

    private Game game;


    public HumanPlayer(String name, boolean playerTurn, boolean isVisible, int currentScore) {
        super(name, playerTurn, isVisible, currentScore);
        this.game = new Game();
    }
}
