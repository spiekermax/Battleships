package de.uni_hannover.hci.battleships.util;

// Java
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
     * @param string
     * @return
     */
    public static Vector2i fromString(String string)
    {
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(string);

        Integer x = null, y = null;
        int matchCount = 0;
        while(matcher.find())
        {
            if(matchCount == 0) x = Integer.parseInt(matcher.group());
            else if(matchCount == 1) y = Integer.parseInt(matcher.group());
            else throw new IllegalArgumentException("ERROR: Vector2i.fromString(): Illegal encoding!");

            ++matchCount;
        }

        return new Vector2i( Objects.requireNonNull(x), Objects.requireNonNull(y) );
    }

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