package de.uni_hannover.hci.battleships.datav2;

// Java
import java.util.ArrayList;
import java.util.Arrays;


public class Player
{
    /* ATTRIBUTES */

    private final String _name;

    private boolean _isReady = false;
    private boolean _hasTurn;

    private final Board _board = new Board();
    private ShipOrientation _shipOrientation = ShipOrientation.VERTICAL;

    // Eine Liste, die speichert wie viele und welche Länge die Schiffe des Speilers haben müssen
    private final ArrayList<Integer> _availableShips = new ArrayList<>( Arrays.asList(5, 4, 4, 3, 3, 3, 2, 2, 2, 2) );


    /* LIFECYCLE */

    public Player(String name, boolean hasTurn) {
        this._name = name;
        this.setHasTurn(hasTurn);
    }


    /* METHODS */

    public Integer removeFirstAvailableShip()
    {
        return this.getAvailableShips().remove(0);
    }

    /**
     * Diese Funktion ändert die Ausrichtung des zunächst zu setzenden Schiffes (vertical und horizontal)
     */
    public void toggleShipOrientation() {
        switch(this.getShipOrientation()) {
            case VERTICAL:
                this.setShipOrientation(ShipOrientation.HORIZONTAL);
                break;
            case HORIZONTAL:
                this.setShipOrientation(ShipOrientation.VERTICAL);
                break;
        }
    }


    /* GETTERS & SETTERS */

    public String getName()
    {
        return this._name;
    }

    public boolean isReady()
    {
        return this._isReady;
    }

    public void setIsReady(boolean newIsReady)
    {
        this._isReady = newIsReady;
    }

    public boolean hasTurn()
    {
        return this._hasTurn;
    }

    public void setHasTurn(boolean newHasTurn)
    {
        this._hasTurn = newHasTurn;
    }

    public Board getBoard()
    {
        return this._board;
    }

    public boolean hasAvailableShips()
    {
        return this._availableShips.size() != 0;
    }

    public ArrayList<Integer> getAvailableShips()
    {
        return this._availableShips;
    }

    public Integer getFirstAvailableShip()
    {
        return this.getAvailableShips().get(0);
    }

    public ShipOrientation getShipOrientation()
    {
        return this._shipOrientation;
    }

    public void setShipOrientation(ShipOrientation newShipOrientation)
    {
        this._shipOrientation = newShipOrientation;
    }
}