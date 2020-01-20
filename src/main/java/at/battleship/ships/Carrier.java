package at.battleship.ships;

import at.battleship.components.Player;

public class Carrier extends Ship {

    private int tiles = 5;
    private Character acronym = 'C';

    public Carrier(Player player) {
        super(player);
    }
}
