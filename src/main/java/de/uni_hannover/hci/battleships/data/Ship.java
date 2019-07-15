package de.uni_hannover.hci.battleships.data;

import de.uni_hannover.hci.battleships.util.Vector2i;

/**
 * Diese Klasse beschreibt ein Schiff.
 */
public class Ship {
    protected int length;
    protected boolean haveSunk;
    protected Vector2i[] coordinates;
    protected Board myBoard;

    /**
     * Diese Methode ist ein Konstruktor.
     * @param cor Dies sind die Koordinaten des Schiffes
     * @param b Dies ist das Board, auf dem das Schiff erstellt wird
     */
    public Ship(Vector2i[] cor, Board b) {
        this.length = cor.length;
        this.haveSunk = false;
        this.coordinates = cor;
        this.myBoard = b;

        int x; int y;
        for(int i = 0; i < cor.length; i++) {
            x = cor[i].getX(); y = cor[i].getY();
            b.board[x][y] = FieldMode.SHIP;
        }
    }
    public int getLength() {
        return length;
    }

    public boolean getHaveSunk() {
        return haveSunk;
    }

    public Vector2i[] getCoordinates() { return coordinates; }

}