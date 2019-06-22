package de.uni_hannover.hci.battleships.data;

public class Ship {
    protected int length;
    protected boolean haveSunk;
    protected int[][] coordinates;

    public Ship(int length) {
        this.length = length;
        this.haveSunk = false;
        this.coordinates = new int[length][length];
    }
    public int getLength() {
        return length;
    }

    public boolean getHaveSunk() {
        return haveSunk;
    }

}
