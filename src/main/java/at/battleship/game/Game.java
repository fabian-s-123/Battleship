package at.battleship.game;

import at.battleship.players.Player;

import java.util.ArrayList;
import java.util.BitSet;

public abstract  class Game {
    
    public abstract int transformStringInputToXValue(char charAt);

    public abstract int transformStringInputToYValue(String substring);

    public abstract char transformNumericInputOfXToCharValue(int xNeighbourRight);

    public abstract String transformNumericInputOfYToStringValue(int yField);

    public abstract ArrayList<String> getMovesPlayer2();

    public abstract ArrayList<String> getMovesPlayer1();

    public abstract Player getPlayer1();

    public abstract Player getPlayer2();
}
