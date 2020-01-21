package at.battleship.components;

import at.battleship.services.FieldRenderState;
import at.battleship.ships.Ship;

public class Field {

    //private String fieldName;
    private FieldRenderState.CurrentState fieldRenderState;
    private Ship ship;

    public Field(Ship ship) {
        //this.fieldName = fieldName;
        this.fieldRenderState =  FieldRenderState.CurrentState.NEUTRAL;
        this.ship = ship;
    }

/*    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }*/

    public FieldRenderState.CurrentState getFieldRenderState() {
        return fieldRenderState;
    }

    public void setFieldRenderState(FieldRenderState.CurrentState fieldRenderState) {
        if (!(this.ship == null)) {
            this.fieldRenderState = fieldRenderState;
        }
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        switch (this.ship.getAcronym()) {
            case 'C':
                this.fieldRenderState = FieldRenderState.CurrentState.CARRIER;
                break;
            case 'B':
                this.fieldRenderState = FieldRenderState.CurrentState.BATTLESHIP;
                break;
            case 'D':
                this.fieldRenderState = FieldRenderState.CurrentState.DESTROYER;
                break;
            case 'S':
                this.fieldRenderState = FieldRenderState.CurrentState.SUBMARINE;
                break;
        }
    }

    public boolean isEmpty() {
        return ship == null;
    }
}
