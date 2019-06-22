package de.uni_hannover.hci.battleships.data;

public class Board {
    protected FieldMode[][] board;

    public Board(int height, int width) {
        this.board = new FieldMode[width][height];

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                board[i][j] = FieldMode.OCEAN;
            }
        }
    }

    public FieldMode[][] getBoard() {
        return this.board;
    }

    public int getWidth() {
        return this.board.length;
    }

    public int getHeight() {
        return this.board[0].length;
    }

    public FieldMode getField(int x, int y) {
        return board[x][y];
    }

    public void setShip(int x, int y) {
        board[x][y] = FieldMode.SHIP;
    }

    public void setSankedShip(int x, int y) {
        board[x][y] = FieldMode.SANKED_SHIP;
    }

    public boolean outOfBounds(int x, int y) {
        return !(x >= 0 && x < board.length && y >= 0 && y < board[0].length);
    }

    /*public boolean validDirection(int x, int y, int dx, int dy) {
        for(int i = 0; i < 10 && !outOfBounds(x+i,y); i++) {
            for(int j = 0; j < 10 && !outOfBounds(x+i, y+j); j++) {
                if(i == j) {continue;}  //Falls diagonal oder keine Richtung
                if(getField(x+i, y+j) == FieldMode.SHIP) {

                }
            }
        }
    }

    public boolean validShip(int x, int y) {
        for(int i = 0; i < 10 && !outOfBounds(x+i*dx,y); i++) {
            for(int j = 0; j < 10 && !outOfBounds(x+i*dx, y+i*dy); j++) {
                if(}  //Falls diagonal oder keine Richtung
                if(getField(x+i*x, y+j*y) == FieldMode.SHIP) {

                }
            }
        }
    }*/

}
