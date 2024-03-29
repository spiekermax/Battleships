package de.uni_hannover.hci.battleships.data;

// Internal dependencies
import de.uni_hannover.hci.battleships.util.Vector2i;


public class Board {

    public static final int BOARD_SIZE = 10;
    private final BoardCell[][] _cells = new BoardCell[BOARD_SIZE][BOARD_SIZE];

    public Board() {
        for(int y = 0; y < BOARD_SIZE; ++y) {
            for(int x = 0; x < BOARD_SIZE; ++x) {
                this.setCell(x, y, BoardCell.WATER);
            }
        }
    }

    public Board(Board other) {
        for(int y = 0; y < BOARD_SIZE; ++y) {
            for(int x = 0; x < BOARD_SIZE; ++x) {
                this.setCell(x, y, other.getCell(x, y));
            }
        }
    }

    /**
     * Diese Methode prüft, ob der Parameter noch innerhalb der Grenzen des Boards liegt.
     * @param n Der Integer, der überprüft wird
     * @return Ein boolean. True, falls der Parameter innerhalb liegt.
     */
    public static boolean isInBounds(int n) {
        return n >= 0 && n < BOARD_SIZE;
    }

    public static boolean isInBounds(int x, int y) {
        return Board.isInBounds(x) && Board.isInBounds(y);
    }

    public static boolean isInBounds(Vector2i coords) {
        return Board.isInBounds(coords.getX(), coords.getY());
    }

    public boolean areAllShipsHit() {
        for(int y = 0; y < BOARD_SIZE; ++y) {
            for (int x = 0; x < BOARD_SIZE; ++x) {
                if (this.getCell(y, x) == BoardCell.SHIP) {
                    return false;
                }
            }
        }
        return true;
    }

    public Vector2i getDirection(int x, int y) {
        int dx = x; int dy = y;

        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
               if(isInBounds(x+i, y+j)) {
                   if(i == 0 && j == 0) {continue;}
                   if (getCell(x + i, y + j) == BoardCell.SHIP || getCell(x + i, y + j) == BoardCell.HIT) {
                       return new Vector2i(i,j);
                   }
               }
            }
        }
        return new Vector2i(0,0);
    }

    public boolean isShipSunken(int x, int y) {
        if(getCell(x,y) != BoardCell.HIT) {return false;}

        Vector2i dirs = this.getDirection(x,y);

        for(int dx = x, dy = y;
            isInBounds(dx,dy) && getCell(dx,dy) != BoardCell.WATER && getCell(dx,dy) != BoardCell.MISS;
            dx = dx+dirs.getX(), dy = dy+dirs.getY()) {
            if(getCell(dx,dy) == BoardCell.SHIP) {
                return false;
            }
        }

        for(int dx = x, dy = y;
            isInBounds(dx,dy) && getCell(dx,dy) != BoardCell.WATER && getCell(dx,dy) != BoardCell.MISS;
            dx = dx+dirs.getX()*-1, dy = dy+dirs.getY()*-1) {
            if(getCell(dx,dy) == BoardCell.SHIP) {
                return false;
            }
        }
        return true;
    }

    public boolean isShipSunken(Vector2i coords) {
        return this.isShipSunken(coords.getX(), coords.getY());
    }

    /**
     * Diese Funktion prüft, ob der Spieler getroffen wurde.
     * @param x Die x-Koordinate des Schusses
     * @param y Die y-Koordinate des Schusses
     * @return Zugwiederholung = true
     */
    public boolean shoot(int x, int y) {
        switch(this.getCell(x, y)) {
            case HIT:
            case MISS:
                return true;
            case SHIP:
                this.setCell(x, y, BoardCell.HIT);
                return true;
            case WATER:
                this.setCell(x, y, BoardCell.MISS);
                return false;
        }
        return false;
    }

    public boolean shoot(Vector2i coords) {
        return this.shoot(coords.getX(), coords.getY());
    }

    /**
     * Diese Funktion prüft, ob die Koordinaten des potenziellen Schiffes gültig sind
     * @param x Die x-Koordinate, auf der das Schiff beginnt
     * @param y Die y-Koordinate, auf der das Schiff beginnt
     * @param shipCellType Die Art von Feldern, aus denen das Schiff bestehen soll
     * @param length Die Länge des Schiffes
     * @param orientation Die Orientierung des Schiffes, welche nur vertical oder horizontal sein kann
     * @return Gibt true zurück, falls das Schiff so gesetzt werden kann
     */
    public boolean addShip(int x, int y, BoardCell shipCellType, int length, ShipOrientation orientation) {
        for(int i = 0; i < length; ++i) {
            switch(orientation) {

                case HORIZONTAL:
                    if (!Board.isInBounds(x + i)) { return false; }  //Falls eine der Koordinate außerhalb liegt
                    if (this.getCell(x + i, y) != BoardCell.WATER) { return false; } //Falls das Feld bereits belegt ist

                    for (int a = (i == 0) ? -1 : 0; a <= 1; ++a) {  //Wenn die erste Koordinate betrachtet wird, muss zurückbetrachtet werden, x-Richtung
                        for (int b = -1; b <= 1; ++b) { //y-Richtung betrachten
                            if (a == 0 && b == 0) { continue; }

                            if (Board.isInBounds(x + i + a) && Board.isInBounds(y + b) && this.getCell(x + i + a, y + b) != BoardCell.WATER) {
                                return false;   //Falls diese betrachtete Zelle um die Koordinate herum nicht frei ist
                            }
                        }
                    }
                    break;

                case VERTICAL:
                    if (!Board.isInBounds(y + i)) { return false; } //Falls eine der Koordinate außerhalb liegt
                    if (this.getCell(x, y + i) != BoardCell.WATER) { return false; } //Falls das Feld bereits belegt ist

                    for (int a = (i == 0) ? -1 : 0; a <= 1; ++a) { //Wenn die erste Koordinate betrachtet wird, muss zurückbetrachtet werden, y-Richtung
                        for (int b = -1; b <= 1; ++b) { //x-Richtung betrachten
                            if (a == 0 && b == 0) { continue; }

                            if (Board.isInBounds(x + b) && Board.isInBounds(y + i + a) && this.getCell(x + b, y + i + a) != BoardCell.WATER) {
                                return false; //Falls diese betrachtete Zelle um die Koordinate herum nicht frei ist
                            }
                        }
                    }
                    break;
            }
        }

        for(int i = 0; i < length; ++i) {   //Ab hier wird das Schiff erstellt, da die Koordinaten gültig sind
            switch(orientation) {
                case HORIZONTAL:
                    this.setCell(x + i, y, shipCellType);
                    break;
                case VERTICAL:
                    this.setCell(x, y + i, shipCellType);
                    break;
            }
        }
        return true;
    }

    public boolean addShip(Vector2i coords, BoardCell shipCellType, int length, ShipOrientation orientation) {
        return this.addShip(coords.getX(), coords.getY(), shipCellType, length, orientation);
    }

    public boolean addShip(int x, int y, int length, ShipOrientation orientation) {
        return this.addShip(x, y, BoardCell.SHIP, length, orientation);
    }

    public boolean addShip(Vector2i coords, int length, ShipOrientation orientation) {
        return this.addShip(coords, BoardCell.SHIP, length, orientation);
    }

    public BoardCell getCell(int x, int y) {
        return this._cells[x][y];
    }

    public BoardCell getCell(Vector2i coords) {
        return this.getCell(coords.getX(), coords.getY());
    }

    public void setCell(int x, int y, BoardCell newCell) {
        this._cells[x][y] = newCell;
    }

    public void setCell(Vector2i coords, BoardCell newCell) {
        this.setCell(coords.getX(), coords.getY(), newCell);
    }
}