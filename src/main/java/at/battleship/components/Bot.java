package at.battleship.components;

import java.util.ArrayList;

public class Bot {

    Playground opponentsPlayground;
    ArrayList<String> movesBot = new ArrayList<>();

    public Bot (Playground opponentsPlayground){
        this.opponentsPlayground = opponentsPlayground;
    }

    public void addMovesBot (String newMove) {
        this.movesBot.add(newMove);
    }

    public int[] firstMove() {
        int x = 0;
        int y = 0;
        String lastMove = this.movesBot.get(this.movesBot.size() - 1);

    }

}
