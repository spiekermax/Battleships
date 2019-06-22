package de.uni_hannover.hci.battleships.data;

public class Player {
    //private String name;
    protected Board myBoard;
    protected Board enemyBoard;

    protected Ship[] myShips;

    public Player() {
        this.myBoard = new Board(10,10);
        this.enemyBoard = new Board(10,10);

        this.myShips = new Ship[10];
    }

    public boolean hasWon() {
        return false;
    }

}
