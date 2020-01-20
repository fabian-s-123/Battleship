package at.battleship.ships;

import at.battleship.components.Player;

public class Destroyer extends Ship {

    private int tiles = 3;
    private Character acronym = 'D';

    public Destroyer(Player player) {
        super(player);
    }
}
