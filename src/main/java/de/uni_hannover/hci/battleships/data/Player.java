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

    /**
     * Diese Methode prüft, ob alle Schiffe eines Spielers komplett versunken sind.
     * @return Falls der Spieler verloren hat, wird true zurückgegeben.
     */
    public boolean hasLost() {
        boolean lost = true;
        for(int i = 0; i < myShips.length; i++) {
            if(!myShips[i].haveSunk) {
                lost = false; break;
            }
        }
        return lost;
    }

    public boolean hasShot(int x, int y) {
        /*if(!myBoard.outOfBounds(x,y)) {   //Muss Netzwerk bestehen um zu prüfen, ob Gegner dort Boote hat

        }*/
        return false;
    }
}

