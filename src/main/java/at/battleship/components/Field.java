package at.battleship.components;

import at.battleship.ships.Ship;

public class Field {

    //private String fieldName;
    private CurrentState fieldRenderState;
    private Ship ship;

    public Field(Ship ship) {
        //this.fieldName = fieldName;
        this.fieldRenderState = CurrentState.NEUTRAL;
        this.ship = ship;
    }

/*    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }*/

    public CurrentState getFieldRenderState() {
        return fieldRenderState;
    }

/*    public void setFieldRenderState(FieldRenderState fieldRenderState) {
        if (!(this.ship == null)) {
            this.fieldRenderState = fieldRenderState;
        }
    }*/

    public Ship getShip() {
        return ship;
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

    public Character displayCurrentState() {
        switch (this.fieldRenderState) {
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

    public void setCurrentState(CurrentState currentState) {
        this.fieldRenderState = currentState;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        switch (this.ship.getAcronym()) {
            case 'C':
                this.setCurrentState(CurrentState.CARRIER);
                break;
            case 'B':
                this.setCurrentState(CurrentState.BATTLESHIP);
                break;
            case 'D':
                this.setCurrentState(CurrentState.DESTROYER);
                break;
            case 'S':
                this.setCurrentState(CurrentState.SUBMARINE);
                break;
        }
    }

    public boolean isEmpty() {
        return ship == null;
    }
}
