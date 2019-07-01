package de.uni_hannover.hci.battleships.data;

/**
 * Diese Klasse beschreibt einen Spieler.
 */
public class Player {
    //private String name;
    protected Board myBoard;
    protected Board enemyBoard;

    protected Ship[] myShips;

    /**
     * Dieser Konstruktor erstellt einen neuen Spieler, welcher ein eigenes und gegnerisches Spielfeld 10x10 besitzt.
     */
    public Player() {
        this.myBoard = new Board(10,10);
        this.enemyBoard = new Board(10,10);

        this.myShips = new Ship[10];
    }

    public FieldMode[][] getMyBoard() { return this.myBoard.getBoard(); }

    public FieldMode[][] getEnemyBoard() { return this.enemyBoard.getBoard(); }

    public Ship getShip(int nr) {
        if(nr >= 0 && nr < myShips.length) {
            return myShips[nr];
        }
        return null;
    }

    /**
     * Diese Methode prüft, ob alle Schiffe eines Spielers komplett versunken sind.
     * @return Falls der Spieler verloren hat, wird true zurückgegeben.
     */
    public boolean hasLost() {
        for(int i = 0; i < myShips.length; i++) {
            if(!myShips[i].haveSunk) {  //Wenn ein einziges Schiff noch nicht versunken ist
                return false;
            }
        }
        return true;
    }

    /**
     * Diese Funktion prüft, ob das gegebene Schiff des Spielers versunken ist.
     * Wenn dies stimmt, wird gleichzeitig das Attribut haveSunk des betroffenen Schiffes auf true gesetzt.
     * @param s Das Schiff, welches geprüft werden soll, ob es komplett versunken ist
     * @return Gibt ein boolean zurück, ob das Schiff versunken ist
     */
    public boolean shipSank(Ship s) {
        for(int i = 0; i < s.coordinates.length; i++) {
            if(myBoard.board[s.coordinates[i][0]][s.coordinates[i][1]] != FieldMode.SANKED_SHIP) {
                return false;
            }
        }
        s.haveSunk = true;
        return true;
    }

    /**
     * Diese Funktion prüft, ob ein Schiff des Spielers getroffen wurde
     * @param x Die x Koordinate, wo der Gegner hingeschossen hat. Diese darf nicht über die boardgrenzen hinausgehen.
     * @param y Die y Koordinate, wo der Gegner hingeschossen hat. Diese darf nicht über die boardgrenzen hinausgehen
     * @return Es wird ein true zurückgegeben, falls der Gegner ein Schiff getroffen hat.
     */
    public boolean hasBeenShot(int x, int y) {
        if(!myBoard.outOfBounds(x,y)) {
            if(myBoard.board[x][y] == FieldMode.SHIP) {
                myBoard.board[x][y] = FieldMode.SANKED_SHIP;
                return true;
            } else if(myBoard.board[x][y] == FieldMode.OCEAN){
                myBoard.board[x][y] = FieldMode.SHOT;
            }
        }
        return false;
    }

    /**
     * Diese Funktion liest das bestehende eigene Board aus und erstellt die zugehörigen Schiffe.
     * Die Funktion setzt nicht die Zustände auf dem Board.
     */
    public void setMyShips() {
        myShips = myBoard.readBoard();
    }

    /**
     * Diese Funktion prüft, ob die angegebenen Schiffe des Spielers, den Spielregeln entsprechen.
     * D.h. dass es die richtige Anzahl an Schiffen mit den jeweiligen richtigen Längen gibt.
     * @return Gibt einen boolean zurück, ob dies stimmt.
     */
    public boolean validShipConstellation() {
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        for(int i = 0; i < myShips.length; i++) {
            if(myShips[i] != null) {
                switch(myShips[i].length) {
                    case 2: two++; break;
                    case 3: three++; break;
                    case 4: four++; break;
                    case 5: five++; break;
                    default: break;
                }
            }
        }
        return five == 1 &&
                four == 2 &&
                three == 3 &&
                two == 4;
    }
}

