package de.uni_hannover.hci.battleships.data;

public class Game {
    protected Player p1;
    protected Player p2;

    public Game() {
        p1 = new Player();
        p2 = new Player();
    }

    public boolean hasShot(int x, int y) {
        return false;
    }

    public boolean validGame() {
        return false;
    }
}
