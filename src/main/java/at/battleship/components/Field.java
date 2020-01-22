package at.battleship.components;

public class Field {

    private CurrentState fieldRenderState;

    Field() {
        this.fieldRenderState = CurrentState.NEUTRAL;
    }

    public enum CurrentState {
        NEUTRAL,
        MISS,
        HIT,
        CARRIER,
        BATTLESHIP,
        DESTROYER,
        SUBMARINE
    }

    public String displayCurrentState(boolean visible) {
        if (visible) {
            switch (this.fieldRenderState) {
                case NEUTRAL:
                    return " - ";
                case MISS:
                    return "   ";
                case HIT:
                    return " X ";
                case CARRIER:
                    return " C ";
                case BATTLESHIP:
                    return " B ";
                case DESTROYER:
                    return " D ";
                case SUBMARINE:
                    return " S ";
                default:
                    return " e ";
            }
        } else {
            switch (this.fieldRenderState) {
                case NEUTRAL:
                case CARRIER:
                case BATTLESHIP:
                case DESTROYER:
                case SUBMARINE:
                    return " - ";
                case MISS:
                    return "   ";
                case HIT:
                    return " X ";
                default:
                    return " e ";
            }
        }
    }


    void setShip(Ship.Type ship) {
        switch (ship) {
            case CARRIER:
                this.setCurrentState(CurrentState.CARRIER);
                break;
            case BATTLESHIP:
                this.setCurrentState(CurrentState.BATTLESHIP);
                break;
            case DESTROYER:
                this.setCurrentState(CurrentState.DESTROYER);
                break;
            case SUBMARINE:
                this.setCurrentState(CurrentState.SUBMARINE);
                break;
        }
    }

    CurrentState getFieldRenderState() {
        return fieldRenderState;
    }

    void setCurrentState(CurrentState currentState) {
        this.fieldRenderState = currentState;
    }
}
