package de.uni_hannover.hci.battleships.data;

/**
 * Diese Klasse beschreibt ein Schiff.
 */
public class Ship {
    protected int length;
    protected boolean haveSunk;
    protected int[][] coordinates;

    /**
     * Diese Methode ist ein Konstruktor.
     * @param length Dies ist die angegebene Länge des Schiffes
     */
    public Ship(int length) {
        this.length = length;
        this.haveSunk = false;
        this.coordinates = new int[length][2];

        for(int i = 0; i < length; i++) {
            for(int j = 0; j < 2; j++) {
                coordinates[i][j] = -1;
            }
        }
    }
    public int getLength() {
        return length;
    }

    public boolean getHaveSunk() {
        return haveSunk;
    }

    public int[][] getCoordinates() { return coordinates; }

}