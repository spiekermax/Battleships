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

    public int[][] getAllPossibleDirections() {
        //int[][] dir = new int[8][2];
        int[][] dir = {
                {1,1}, {-1, -1},
                {-1,1}, {1,-1},
                {1,0}, {0,1},
                {-1,0}, {0, -1}
        };
        return dir;
    }

    /**
     * Diese Funktion prüft, ob es von einem bestimmten Feld aus, daneben ein weiteres Schiff gibt.
     * @param x Die x-Koordinate vom betrachtenden Feld
     * @param y Die y-Koordinate vom betrachtenden Feld
     * @param dx Die x-Richtung, die batrechtet werden soll
     * @param dy Die y-Richtung, die betrachtet werden soll
     * @return Gibt ein boolean zurück, ob es stimmt
     */
    public boolean shipInDirection(int x, int y, int dx, int dy) {
        if(!outOfBounds(x,y) && !outOfBounds(x+dx, y+dy)) {
            if(this.board[x+dx][y+dy] == FieldMode.SHIP) {
                return true;
            }
        }
        return false;
    }

    /**
     * Diese Funktion prüft, ob die angegebene Richtung gültig ist laut der Spielregeln.
     * D.h. es darf keine diagonal platzsierten Schiffe auf dem board geben.
     * @param dx x-Richtung
     * @param dy y-Richtung
     * @return Gibt ein boolean zurück, ob dies stimmt
     */
    public boolean invalidDirection(int dx, int dy) {
        return dx == 0 && dy == 0 || dx == 1 && dy == 1 || dx == -1 && dy == -1
                || dx == -1 && dy == 1 || dx == 1 && dy == -1;
    }

    /**
     * Diese Funktion prüft, ob die Schiffe auf dem Board in der richtigen Richtung gesetzt wurden.
     * D.h. wenn es Schiffe gibt, welche diagonal oder um Eck liegen bzw. direkt angrenzende Schiffe haben,
     * kann das Spielbrett verworfen werden.
     * @param x Die x-Koordinate, von der das Schiff geprüft wird
     * @param y Die y-Koordinate, von der das Schiff geprüft wird
     * @return Gibt ein integer-array aus, wo die x und y Richtungen beschrieben sind.
     * Ist etwas ungültig, so wird null ausgegeben.
     */
    public int[] testIfDirectionExists(int x, int y) {
        int directionCount = 0; //Zählt Anzahl der Richtungen von dem Feld aus, in die ein Schiff existiert
        int[] result = new int[2];  //Speichert die Richtung, in der ein Schiff existiert
        int[][] directions = getAllPossibleDirections();
        int dx = 0; int dy = 0;

        for(int i = 0; i < directions.length; i++) {    //Läuft über alle möglichen Richtungen
            dx = directions[i][0]; dy = directions[i][1];
            if (shipInDirection(x, y, dx, dy)) {    //Prüft, ob in der Richtung ein Schiff liegt
                if(invalidDirection(dx,dy) || directionCount > 0) { //Wenn ja, dann prüft, ob ungülitge Richtung ist bzw. es mehr als eine richtige Richtung gibt (Schiff um Eck gebaut?)
                    System.err.printf("Es gibt ungültige Schiffe");
                } else {
                    directionCount++;
                    result[0] = dx; result[1] = dy;  //Außerdem sollten directions nur in rechte richtung und unten gehen, habe aber bisher alle drin
                }
            }
            if(i == directions.length - 1 && directionCount == 1) { //Wenn es nur eine Richtung gibt und alle Richtungen schon durchlaufen
                return result;
            }
        }
       return null;
    }

    /**
     * Diese Funktion zählt die Länge eines Schiffes von einem Feld aus in eine bestimmte Richtung.
     * @param x Die x-Koordinate, wo das Schiff beginnt
     * @param y Die y-Koordinate, wo das Schiff beginnt
     * @param dx Die x-Richtung, in der das Schiff liegt
     * @param dy Die y-Richtung, in der das Schiff liegt
     * @return Es wird die Länge des Schiffes zurückgegeben.
     */
    public int countLength(int x, int y, int dx, int dy) {
        int result = 0;
        for(int i = 0; !outOfBounds(x,y) && !outOfBounds(x+i*dx, y+i*dy); i++) {
            if(board[x+i*dx][y+i*dy] == FieldMode.SHIP) {
                result++;
            }
        }
        return result;
    }

    /**
     * Diese Funktion prüft, ob es das angegebene Feld, bereits von Schiffen besetzt ist.
     * @param ships Ein Array von Schiffen, welches durchlaufen wird um zu testen,
     *              ob eines der dort existierendes Schiffe bereits auf dem Feld steht.
     * @param x Die x-Koordinate des betrachteten Felds
     * @param y Die y-Koordinate des betrachteten Felds
     * @return Gibt ein boolean zurück, ob bereits ein Schiff auf dem Feld definiert ist
     */
    public boolean shipExists(Ship[] ships, int x, int y) {
        for(int i = 0; i < ships.length; i++) {
            if(ships[i] != null) {
                for(int j = 0; j < ships[i].coordinates.length; j++) {
                    if (ships[i].coordinates[j][0] == x || ships[i].coordinates[j][1] == y) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Diese Funktion liest ein Board aus und prüft, ob die gesetzten Schiffe auf diesem den Regeln entsprechen.
     * @return Es wird ein Array von ausgelesenen Schiffen zurückgegeben.
     * Wenn zu viele Schiffe markiert wurden oder etwas anderes nicht stimmt, wird null zurpckgegeben.
     */
    public Ship[] readBoard() {
        int index = 0;
        Ship[] ships = new Ship[10];
        int[] direction;
        for(int i = 0; i < this.getHeight(); i++) {
            for(int j = 0; j < this.getWidth(); j++) {
                if (board[j][i] == FieldMode.SHIP) {
                    direction = this.testIfDirectionExists(j,i);
                    if(direction != null && !shipExists(ships, j, i)) {    //So lange kein Schiff bisher existiert && Richtung existiert
                        //Erstelle neues Schiff mit der Länge in die Richtung
                        if(index < 10) {    //Nur so lange das Array nicht voll ist
                            int lengthOfShip = countLength(j, i, direction[0], direction[1]);
                            ships[index] = new Ship(lengthOfShip);
                            index++;
                        } else {
                            index++;
                        }
                    }
                }
            }
            if(i == this.getHeight()- 1 && index == 9) {    //Nur wenn es genau 10 Schiffe auf Feld gab
                return ships;
            }
        }
        return null;
    }
}