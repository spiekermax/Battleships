package de.uni_hannover.hci.battleships.util;


public class Vector2i
{
    /* ATTRIBUTES */

    private int _x;
    private int _y;


    /* LIFECYCLE */

    /**
     * TODO
     */
    public Vector2i()
    {
        this(0, 0);
    }

    /**
     * TODO
     * @param x
     * @param y
     */
    public Vector2i(int x, int y)
    {
        this.setX(x);
        this.setY(y);
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public int getX()
    {
        return this._x;
    }

    /**
     * TODO
     * @param newX
     */
    public void setX(int newX)
    {
        this._x = newX;
    }

    /**
     * TODO
     * @return
     */
    public int getY()
    {
        return this._y;
    }

    /**
     * TODO
     * @param newY
     */
    public void setY(int newY)
    {
        this._y = newY;
    }


    /* MISCELLANEOUS */

    /**
     * TODO
     * @return
     */
    @Override
    public String toString()
    {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}