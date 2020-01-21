package at.battleship.services;

public class FieldRenderState {

    public enum CurrentState {
        NEUTRAL,
        MISS,
        HIT,
        CARRIER,
        BATTLESHIP,
        DESTROYER,
        SUBMARINE
    }

    private Character displayCurrentState(CurrentState currentState) {
        switch (currentState) {
            case NEUTRAL:
                return '-';
            case MISS:
                return ' ';
            case HIT:
                return 'X';
            case CARRIER:
                return 'C';
            case BATTLESHIP:
                return 'B';
            case DESTROYER:
                return 'D';
            case SUBMARINE:
                return 'S';
            default:
                return 'o';
        }
    }
}
