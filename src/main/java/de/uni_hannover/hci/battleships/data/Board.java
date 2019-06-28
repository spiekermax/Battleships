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

    public boolean shipInDirection(int x, int y, int dx, int dy) {
        if(!outOfBounds(x,y) && !outOfBounds(x+dx, y+dy)) {
            if(this.board[x+dx][y+dy] == FieldMode.SHIP) {
                return true;
            }
        }
        return false;
    }

    public boolean invalidDirection(int dx, int dy) {
        return dx == 0 && dy == 0 || dx == 1 && dy == 1 || dx == -1 && dy == -1
                || dx == -1 && dy == 1 || dx == 1 && dy == -1;
    }

    public int[] testIfDirectionExists(int x, int y) {
        int[][] directions = getAllPossibleDirections();
        int dx = 0; int dy = 0;
        for(int i = 0; i < directions.length; i++) {
            dx = directions[i][0]; dy = directions[i][1];
            if (shipInDirection(x, y, dx, dy)) {
                if(invalidDirection(dx,dy)) {
                    System.err.printf("Es gibt ungültige Schiffe");
                } else {
                    int[] result = new int[2];
                    result[0] = dx; result[1] = dy;   //Wichtig für mich: Hat nicht getestet, ob um eck gebaut ist
                    return result;  //Außerdem sollten directions nur in rechte richtung und unten gehen, habe aber bisher alle drin
                }
            }
        }
       return null;
    }

    public int countLength(int x, int y, int dx, int dy) {
        int result = 0;
        for(int i = 0; !outOfBounds(x,y) && !outOfBounds(x+i*dx, y+i*dy); i++) {
            if(board[x+i*dx][y+i*dy] == FieldMode.SHIP) {
                result++;
            }
        }
        return result;
    }

    public Ship[] readBoard() {
        int index = 0;
        Ship[] ships = new Ship[10];
        int[] direction;
        for(int i = 0; i < this.getHeight(); i++) {
            for(int j = 0; j < this.getWidth(); j++) {
                if (board[j][i] == FieldMode.SHIP) {
                    direction = this.testIfDirectionExists(j,i);
                    if(direction != null /*&& !shipExists(ships, j, i)*/) {    //So lange kein Schiff bisher existiert, wie realisere ich?
                        //Erstelle neues Schiff mit der Länge in die Richtung
                        int lengthOfShip = countLength(j, i, direction[0], direction[1]);
                        ships[index] = new Ship(lengthOfShip);
                        index++;
                    }
                }
            }
        }
        return ships;
    }
}