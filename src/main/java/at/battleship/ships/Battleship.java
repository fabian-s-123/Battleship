package at.battleship.ships;

import at.battleship.components.Player;

public class Battleship extends Ship {

    private int tiles = 4;
    private Character acronym = 'B';

    public Battleship(Player player) {
        super(player);
    }
}
