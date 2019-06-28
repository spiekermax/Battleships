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
        boolean lost = true;
        for(int i = 0; i < myShips.length; i++) {
            if(!myShips[i].haveSunk) {
                lost = false; break;
            }
        }
        return lost;
    }

    public boolean shipSank(Ship s) {
        for(int i = 0; i < s.coordinates.length; i++) {
            if(myBoard.board[s.coordinates[i][0]][s.coordinates[i][1]] != FieldMode.SANKED_SHIP) {
                return false;
            }
        }
        s.haveSunk = true;
        return true;
    }

    public boolean hasShot(int x, int y) {
        if(!enemyBoard.outOfBounds(x,y)) {
            if(enemyBoard.board[x][y] == FieldMode.SHIP) {
                enemyBoard.board[x][y] = FieldMode.SANKED_SHIP;
                return true;
            } else if(enemyBoard.board[x][y] == FieldMode.OCEAN){
                enemyBoard.board[x][y] = FieldMode.SHOT;
            }
        }
        return false;
    }

    public void setMyShips() {
        myShips = myBoard.readBoard();
    }

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

