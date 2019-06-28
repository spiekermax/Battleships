package de.uni_hannover.hci.battleships.data;

/**
 * Diese Klasse beschreibt ein Spiel.
 */
public class Game {
    protected Player p1;
    protected Player p2;

    public Game() {
        p1 = new Player();
        p2 = new Player();
    }

    /**
     * Diese Methode prüft, ob beide Spieler ihre Schiffe richtig auf dem Spielfeld gesetzt haben.
     * @return Wenn beide eine gültige Konfiguration haben, wird true zurückgegeben.
     */
    public boolean validGame() {
        //return p1.getMyBoard().validBoard() && p2.getMyBoard().validBoard(); //?
        return false;
    }

}

