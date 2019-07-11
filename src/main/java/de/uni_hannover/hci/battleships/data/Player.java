package de.uni_hannover.hci.battleships.data;

import de.uni_hannover.hci.battleships.util.Vector2i;

import java.util.ArrayList;

/**
 * Diese Klasse beschreibt einen Spieler.
 */
public class Player {
    protected String name;
    protected Board myBoard;
    protected Board enemyBoard;
    protected boolean myTurn;
    protected boolean isReady;
    protected ArrayList<Integer> availableShips;

    protected Ship[] myShips;

    /**
     * Dieser Konstruktor erstellt einen neuen Spieler, welcher ein eigenes und gegnerisches Spielfeld 10x10 besitzt.
     */
    public Player(String name) {
        this.myBoard = new Board(10,10);
        this.enemyBoard = new Board(10,10);
        this.myTurn = false;
        this.name = name;
        this.isReady = false;

        this.availableShips = new ArrayList<>();
        this.availableShips.add(5);
        this.availableShips.add(4);
        this.availableShips.add(4);
        this.availableShips.add(3);
        this.availableShips.add(3);
        this.availableShips.add(3);
        this.availableShips.add(2);
        this.availableShips.add(2);
        this.availableShips.add(2);
        this.availableShips.add(2);

        this.myShips = new Ship[10];
    }

    public boolean getIsReady() {
        return this.isReady;
    }

    public void setIsReady(boolean b) {
        this.isReady = b;
    }
    
    public String getName() { return this.name; }

    public FieldMode[][] getMyBoard() { return this.myBoard.getBoard(); }

    public FieldMode[][] getEnemyBoard() { return this.enemyBoard.getBoard(); }

    public Ship getShip(int nr) {
        if(nr >= 0 && nr < myShips.length) {
            return myShips[nr];
        }
        return null;
    }

    public boolean addShip(Vector2i cor, Orientation or) {
        Ship s = myBoard.addShip(cor, or, availableShips.get(0));
        if(s != null) {
            availableShips.remove(0);
            for (int i = 0; i < myShips.length; i++) {
                if (myShips[i] != null) {
                    myShips[i] = s;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Diese Funktion wechselt den Spieler, der gerade am Zug ist und eine Eingabe tätitgen darf.
     */
    public void switchTurn() {
        myTurn = !myTurn;
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
            if(myBoard.board[s.coordinates[i].getX()][s.coordinates[i].getY()] != FieldMode.SANKED_SHIP) {
                return false;
            }
        }
        s.haveSunk = true;
        return true;
    }

    /**
     * Diese Funktion überprüft, ob der Gegner bei einem Schuss in Leere oder auf ein Schiff trifft
     * @param x Die x Koordinate, wo der Gegner hingeschossen hat. Diese darf nicht über die boardgrenzen hinausgehen.
     * @param y Die y Koordinate, wo der Gegner hingeschossen hat. Diese darf nicht über die boardgrenzen hinausgehen.
     * @return Gibt zurück, ob er ein Schiff trifft.
     */
    public boolean shipWillBeHit(int x, int y) {
        if(!myBoard.outOfBounds(x,y)) {
            if(myBoard.board[x][y] == FieldMode.SHIP) {
                return true;
            }
        }
        return false;
    }

    /**
     * Diese Funktion "schießt" auf das Feld und ändert es ab.
     * @param x Die x Koordinate, wo der Gegner hingeschossen hat. Diese darf nicht über die boardgrenzen hinausgehen.
     * @param y Die y Koordinate, wo der Gegner hingeschossen hat. Diese darf nicht über die boardgrenzen hinausgehen.
     */
    public void hasBeenShot(int x, int y) {
        if(!myBoard.outOfBounds(x,y)) {
            if(shipWillBeHit(x,y)) {
                myBoard.board[x][y] = FieldMode.SANKED_SHIP;
            } else {
                myBoard.board[x][y] = FieldMode.SHOT;
            }
        }
    }
}