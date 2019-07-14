package de.uni_hannover.hci.battleships.datav2;

// Internal dependencies
import de.uni_hannover.hci.battleships.util.Vector2i;


public class Board
{
    /* CONSTANTS */

    public static final int BOARD_SIZE = 10;


    /* ATTRIBUTES */

    private final BoardCell[][] _cells = new BoardCell[BOARD_SIZE][BOARD_SIZE];


    /* LIFECYCLE */

    public Board()
    {
        for(int y = 0; y < BOARD_SIZE; ++y)
        {
            for(int x = 0; x < BOARD_SIZE; ++x)
            {
                this.setCell(y, x, BoardCell.WATER);
            }
        }
    }


    /* METHODS */

    public boolean isInBounds(int n)
    {
        return n >= 0 && n < BOARD_SIZE;
    }

    public boolean areAllShipsHit()
    {
        for(int y = 0; y < BOARD_SIZE; ++y)
            for(int x = 0; x < BOARD_SIZE; ++x)
                if(this.getCell(y, x) == BoardCell.SHIP) return false;

        return true;
    }

    /**
     *
     * @param x
     * @param y
     * @return Zugwiederholung = true
     */
    public boolean shoot(int x, int y)
    {
        switch(this.getCell(x, y))
        {
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

    public boolean shoot(Vector2i coords)
    {
        return this.shoot(coords.getX(), coords.getY());
    }

    public boolean addShip(int x, int y, int length, ShipOrientation orientation)
    {
        for(int i = 0; i < length; ++i)
        {
            switch(orientation)
            {
                case HORIZONTAL:
                    if(!this.isInBounds(x + i)) return false;
                    if(this.getCell(x + i, y) != BoardCell.WATER) return false;

                    for(int a = (i == 0) ? -1 : 0; a <= 1; ++a)
                    {
                        for(int b = -1; b <= 1; ++b)
                        {
                            if(a == 0 && b == 0) continue;

                            if(this.isInBounds(x + i + a) && this.isInBounds(y + b) && this.getCell(x + i + a, y + b) != BoardCell.WATER) return false;
                        }
                    }
                    break;
                case VERTICAL:
                    if(!this.isInBounds(y + i)) return false;
                    if(this.getCell(x, y + i) != BoardCell.WATER) return false;

                    for(int a = (i == 0) ? -1 : 0; a <= 1; ++a)
                    {
                        for(int b = -1; b <= 1; ++b)
                        {
                            if(a == 0 && b == 0) continue;

                            if(this.isInBounds(x + b) && this.isInBounds(y + i + a) && this.getCell(x + b, y + i + a) != BoardCell.WATER) return false;
                        }
                    }
                    break;
            }
        }

        for(int i = 0; i < length; ++i)
        {
            switch(orientation)
            {
                case HORIZONTAL:
                    this.setCell(x + i, y, BoardCell.SHIP);
                    break;
                case VERTICAL:
                    this.setCell(x, y + i, BoardCell.SHIP);
                    break;
            }
        }

        return true;
    }

    public boolean addShip(Vector2i coords, int length, ShipOrientation orientation)
    {
        return this.addShip(coords.getX(), coords.getY(), length, orientation);
    }


    /* GETTERS & SETTERS */

    public BoardCell getCell(int x, int y)
    {
        return this._cells[x][y];
    }

    public BoardCell getCell(Vector2i coords)
    {
        return this.getCell(coords.getX(), coords.getY());
    }

    public void setCell(int x, int y, BoardCell newCell)
    {
        this._cells[x][y] = newCell;
    }

    public void setCell(Vector2i coords, BoardCell newCell)
    {
        this.setCell(coords.getX(), coords.getY(), newCell);
    }
}