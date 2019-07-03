package de.uni_hannover.hci.battleships.data;

/**
 * Diese Klasse beschreibt ein Spiel.
 */
public class Game {
    protected Player p1;
    protected Player p2;
    protected Player turn;

    public Game() {
        p1 = new Player();
        p2 = new Player();
        turn = p1;
    }

    public Player getTurn() {
        return turn;
    }

    /**
     * Diese Funktion wechselt den Spieler, der gerade am Zug ist und eine Eingabe tätitgen darf.
     */
    public void switchTurn() {
        if(turn == p1) {
            turn = p2;
        } else {
            turn = p1;
        }
    }

    public boolean isOver() {
        return p1.hasLost() || p2.hasLost();
    }
}