package at.battleship.ships;

import at.battleship.components.Player;

public abstract class Ship {

    protected char acronym;
    protected int tiles;

    protected Player player;

    public Ship(Player player) {
        this.player = player;
    }
}
