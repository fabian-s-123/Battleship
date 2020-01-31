package at.battleship.players;

import at.battleship.game.HumanVsBot;

public class HumanPlayer extends Player {

    private HumanVsBot humanVsBot;


    public HumanPlayer(String name, boolean playerTurn, boolean isVisible, int currentScore) {
        super(name, playerTurn, isVisible, currentScore);
        this.humanVsBot = new HumanVsBot();
    }
}
