package at.battleship.components;

import at.battleship.services.FieldRenderState;
import at.battleship.ships.Ship;

public class Field {

    private String fieldName;
    private FieldRenderState fieldRenderState;
    private Ship ship;

    public Field(String fieldName, FieldRenderState fieldRenderState, Ship ship) {
        this.fieldName = fieldName;
        this.fieldRenderState = fieldRenderState;
        this.ship = ship;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldRenderState getFieldRenderState() {
        return fieldRenderState;
    }

    public void setFieldRenderState(FieldRenderState fieldRenderState) {
        this.fieldRenderState = fieldRenderState;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isEmpty() {
        return ship == null;
    }
}
