package de.uni_hannover.hci.battleships.data;

import de.uni_hannover.hci.battleships.util.Vector2i;

import java.util.Vector;

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

    public Ship addShip(Vector2i cor, Orientation or, int length) {
        int x = cor.getX();
        int y = cor.getY();
        Vector2i[] arr = new Vector2i[length];
        arr[0] = cor;

        if(or == Orientation.DOWN) {    //Wenn Schiff runter geht
            if(outOfBounds(x, y+length)) {  //Prüft, ob es über die Grenzen hinausgeht
                return null;
            }
            for(int i = 1; i < length; i++) {   //Erstelle die Koordinaten des Schiffes
                arr[i] = new Vector2i(x, y+i);
            }
            return new Ship(arr, this);
        } else {        //Wenn Schiff nach rechts geht
            if(outOfBounds(x+length, y)) {  //Prüft, ob es über die Grenzen hinausgeht
                return null;
            }
            for(int i = 1; i < length; i++) {   //Erstellt die Koordinaten des Schiffes
                arr[i] = new Vector2i(x+i, y);
            }
            return new Ship(arr, this);
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

    public Board copyBoard() {
        Board b = new Board(this.getWidth(), this.getHeight());
        for(int i = 0; i < this.getWidth(); i++) {
            for(int j = 0; j < this.getHeight(); j++) {
                b.board[i][j] = this.board[i][j];
            }
        }
        return b;
    }

    public boolean isShipOnField(int x, int y) {
        if(!outOfBounds(x,y) && board[x][y] == FieldMode.SHIP) {
            return true;
        }
        return false;
    }

    public int[][] getAllPossibleDirections() {
        int[][] dir = {
                {1,1}, {-1,-1},
                {-1,1}, {1,-1},
                {1,0}, {0,1},
                {-1,0}, {0,-1}
        };
        return dir;
    }

    public boolean coordinatesOutOfBounds(Vector2i[] cor) {
        for(int i = 0; i < cor.length; i++) {
            if(outOfBounds(cor[i].getX(), cor[i].getY())){
                return true;
            }
        }
        return false;
    }

    public boolean isElementOfCoordinates(Vector2i[] cor, int x, int y) {
        for(int i = 0; i < cor.length; i++) {
            if(x == cor[i].getX() && y == cor[i].getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Diese Funktion prüft, ob ein Schiff bzw dessen Koordinaten benachbarte Schiffe haben.
     * Ist eine Koordinate nicht im Feld, so wird diese nicht beachtet und übersprungen.
     * @param cor Die Koordinaten des Schiffes
     * @param dir Die Richtung in der die Schiffe gebaut sind
     * @return Gibt true zurück, falls ein Schiff keine Nachbarn hat.
     */
    public boolean hasNoNeighbours(Vector2i[] cor, int[] dir) {
        int[][] possible = getAllPossibleDirections();
        int x = -1; int y = -1;
        for(int i = 0; i < cor.length; i++) {
            x = cor[i].getX(); y = cor[i].getY();
            if(outOfBounds(x,y)) {continue;}
            for(int j = 0; j < possible.length; j++) {  //Läuft für jede Koordinate alle Richtungen ab
                if(possible[j][0] == dir[0] && possible[i][1] == dir[1]) {
                    if(isElementOfCoordinates(cor,
                            cor[i].getX()+possible[j][0],
                            cor[i].getY()+possible[j][1])) {  //Wenn Schiff(Koordinaten) in die Richtung geht, überspringe
                        continue;
                    } else if(board[cor[i].getX()+possible[j][0]] [cor[i].getY()+possible[j][1]]
                            == FieldMode.SHIP){ //Falls Schiff nicht in eigener Richtung, muss prüfen, ob in dieser Richtung ein fremdes Schiff steht
                        return false;
                    }
                }
                if(i > 0) { //Wenn mind. 2. Knoten des Schiffes
                    int dirxr = dir[0] * -1;
                    int diryr = dir[1] * -1;
                    if(possible[j][0] == dirxr && possible[i][1] == diryr) {  //Soll nicht mehr rückwärts betrachten von der Richtung des eigenen Schiffes
                        continue;
                    }
                }
                if(isShipOnField(cor[i].getX()+possible[j][0], cor[i].getY()+ possible[j][1])) { //Wenn umliegendes Schiff vorhanden
                    return false;
                }
            }
        }
        return true;
    }


    //Die Funktion prüft, ob es zwischen den Koordinaten jeweils immer nur eine Richtung gibt
    public boolean onlyOneDirection(Vector2i[] cor) {
        if(cor.length <= 1) {return false;}
        int dx = 0; int dy = 0;
        for(int i = 0; i < cor.length; i++) {
            if(i == 1) {    //Erste Richtung gespeichert
                dx = cor[i].getX() - cor[i-1].getX();
                dy = cor[i].getY() - cor[i-1].getY();
            } else if(i > 1){   //Prüft, ob alle Folgenden auch immer dieselbe sind
                if(dx != cor[i].getX() - cor[i-1].getX()
                        || dy != cor[i].getY() - cor[i-1].getY()) {
                    return false;
                }
            }
        }
        return true;
    }

    //Diese Fkt. gibt die Differenz zweier Koordinaten aus.
    // Sie prüft nicht, ob diese überhaupt existieren.
    public int[] getDirection(Vector2i[] cor) {
        int[] dir = new int[2];
        dir[0] = cor[1].getX() - cor[0].getX();
        dir[1] = cor[1].getY() - cor[0].getY();
        return dir;
    }

    /**
     * Diese Funktion prüft, ob eine angegebene Richtung eine gültige ist
     * @param dx Die x-Richtung
     * @param dy Die y-Richtung
     * @return Gibt zurück, ob dies stimmt
     */
    public boolean validDirection(int dx, int dy) {
        if(dx == 0 && dy == 0 || dx == 1 && dy == 1 || dx == -1 && dy == -1
                || dx == 1 && dy == -1 || dx == -1 && dy == 1) {
            return false;
        }
        return true;
    }

    /**
     * Diese Funktion prüft, ob die übergegebenen Koordinaten ein korrektess Schiff bilden könne.
     * Dafür müssen alle Felder überprüft werden, ob diese frei von anderen Schiffen sind.
     * Hinzu darf es keine Schiffe um Eck oder diagonal geben sowie keine angrenzenden Schiffe.
     * @param cor Die Koordinaten müssen in der richtigen Reihenfolge stehen.
     * @return Gibt zurück, ob die Koordinaten so möglich sind.
     */
    public boolean canSetShip(Vector2i[] cor) {
        if(coordinatesOutOfBounds(cor)) {   //Wenn eine Koordinate nicht im Feld, return false
            return false;
        }
        for(int i = 0; i < cor.length; i++) {
            if (isShipOnField(cor[i].getX(), cor[i].getY())) {  //Wenn ein Feld bereits durch Schuff belegt, return false
                return false;
            }
        }
        int[] direction;
        if(onlyOneDirection(cor)) { //Prüft, dass nicht um Eck gebaut (nur eine legale Richtung)
            direction = getDirection(cor);
        } else {
            return false;
        }
        if(direction.length != 0 && validDirection(direction[0], direction[1]) //Prüft, ob gültige Richtung
                && hasNoNeighbours(cor, direction)) {    //Prüft, ob keine Schiffe drumrum
            return true;
        }
        return false;
    }
}