package de.uni_hannover.hci.battleships.ui.board.event;

// Internal dependencies
import de.uni_hannover.hci.battleships.util.Vector2i;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class BoardViewCellHoveredEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<BoardViewCellHoveredEvent> EVENT_TYPE = new EventType<>("board-view-cell-hovered");


    /* ATTRIBUTES */

    private final Vector2i _coords;


    /* LIFECYCLE */

    /**
     * TODO
     * @param x
     * @param y
     */
    public BoardViewCellHoveredEvent(int x, int y)
    {
        super(EVENT_TYPE);
        this._coords = new Vector2i(x, y);
    }

    /**
     * TODO
     * @param coords
     */
    public BoardViewCellHoveredEvent(Vector2i coords)
    {
        super(EVENT_TYPE);
        this._coords = coords;
    }


    /* METHODS */

    /**
     * TODO
     * @return
     */
    public int getX()
    {
        return this._coords.getX();
    }

    /**
     * TODO
     * @return
     */
    public int getY()
    {
        return this._coords.getY();
    }

    /**
     * TODO
     * @return
     */
    public Vector2i getCoords()
    {
        return this._coords;
    }
}