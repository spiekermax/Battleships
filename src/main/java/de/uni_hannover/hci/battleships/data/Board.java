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

    public boolean coordinatesOutOfBounds(int[][] cor) {
        for(int i = 0; i < cor.length; i++) {
            if(outOfBounds(cor[i][0], cor[i][1])){
                return true;
            }
        }
        return false;
    }

    public boolean isElementOfCoordinates(int[][] cor, int x, int y) {
        for(int i = 0; i < cor.length; i++) {
            if(x == cor[i][0] && y == cor[i][1]) {
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
    public boolean hasNoNeighbours(int[][] cor, int[] dir) {
        int[][] possible = getAllPossibleDirections();
        int x = -1; int y = -1;
        for(int i = 0; i < cor.length; i++) {
            x = cor[i][0]; y = cor[i][1];
            if(outOfBounds(x,y)) {continue;}
            for(int j = 0; j < possible.length; j++) {  //Läuft für jede Koordinate alle Richtungen ab
                if(possible[j][0] == dir[0] && possible[i][1] == dir[1]) {
                    if(isElementOfCoordinates(cor,
                            cor[i][0]+possible[j][0],
                            cor[i][1]+possible[j][1])) {  //Wenn Schiff(Koordinaten) in die Richtung geht, überspringe
                        continue;
                    } else if(board[cor[i][0]+possible[j][0]] [cor[i][1]+possible[j][1]]
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
                if(isShipOnField(cor[i][0]+possible[j][0], cor[i][1]+ possible[j][1])) { //Wenn umliegendes Schiff vorhanden
                    return false;
                }
            }
        }
        return true;
    }


    //Die Funktion prüft, ob es zwischen den Koordinaten jeweils immer nur eine Richtung gibt
    public boolean onlyOneDirection(int[][] cor) {
        if(cor.length <= 1) {return false;}
        int dx = 0; int dy = 0;
        for(int i = 0; i < cor.length; i++) {
            if(i == 1) {    //Erste Richtung gespeichert
                dx = cor[i][0] - cor[i-1][0];
                dy = cor[i][1] - cor[i-1][1];
            } else if(i > 1){   //Prüft, ob alle Folgenden auch immer dieselbe sind
                if(dx != cor[i][0] - cor[i-1][0]
                        || dy != cor[i][1] - cor[i-1][1]) {
                    return false;
                }
            }
        }
        return true;
    }

    //Diese Fkt. gibt die Differenz zweier Koordinaten aus.
    // Sie prüft nicht, ob diese überhaupt existieren.
    public int[] getDirection(int[][]cor) {
        int[] dir = new int[2];
        dir[0] = cor[1][0] - cor[0][0];
        dir[1] = cor[1][1] - cor[0][1];
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
    public boolean canSetShip(int[][] cor) {
        if(coordinatesOutOfBounds(cor)) {   //Wenn eine Koordinate nicht im Feld, return false
            return false;
        }
        for(int i = 0; i < cor.length; i++) {
            if (isShipOnField(cor[i][0], cor[i][1])) {  //Wenn ein Feld bereits durch Schuff belegt, return false
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