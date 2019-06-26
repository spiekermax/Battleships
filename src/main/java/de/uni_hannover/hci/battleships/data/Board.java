package de.uni_hannover.hci.battleships.data;

/**
 * Diese Klasse beschreibt ein Spielfeld
 */
public class Board {
    protected FieldMode[][] board;

    /**
     * Es wird ein neues Spielfeld erstellt, welches die Anfangskonfiguration hat, dass alle Felder Ozean sind.
     * @param width Die Breite des Spielfelds
     * @param height Die Höhe des Spielfelds
     */
    public Board(int width, int height) {
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

    public int getWidth() { return this.board.length; }

    public int getHeight() { return this.board[0].length; }

    public FieldMode getField(int x, int y) {
        if(!outOfBounds(x,y)) {
            return board[x][y];
        } else {
            return null;
        }
    }

    public void setShip(int x, int y) {
        if(!outOfBounds(x,y)) {
            board[x][y] = FieldMode.SHIP;
        }
    }

    public void setSankedShip(int x, int y) {
        if (!outOfBounds(x, y)) {
            board[x][y] = FieldMode.SANKED_SHIP;
        }
    }

    public void setShot(int x, int y) {
        if (!outOfBounds(x, y)) {
            board[x][y] = FieldMode.SHOT;
        }
    }

    public boolean outOfBounds(int x, int y) {
        return !(x >= 0 && x < board.length && y >= 0 && y < board[0].length);
    }

    /**
     * Diese Methode prüft, ob das Spielfeld eine gültige Besetzung von Schiffen hat.
     * Die Schiffe dürfen nicht über Eck, diagonal oder unmittelbar nebeneinander gebaut sein.
     * @return Ist das Spielfeld gülzig, so wird true zurückgegeben.
     */
    public boolean validBoard() {
        return false;
    }

}