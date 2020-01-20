package at.battleship.ships;

import at.battleship.components.Player;

public class Submarine extends Ship {

    private int tiles = 2;
    private Character acronym = 'S';

    public Submarine(Player player) {
        super(player);
    }
}
