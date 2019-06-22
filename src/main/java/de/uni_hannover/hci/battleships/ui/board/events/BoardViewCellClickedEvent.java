package de.uni_hannover.hci.battleships.ui.board.events;

// Internal dependencies
import de.uni_hannover.hci.battleships.util.Vector2i;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class BoardViewCellClickedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<BoardViewCellClickedEvent> EVENT_TYPE = new EventType<>("board-view-cell-clicked");


    /* ATTRIBUTES */

    private final Vector2i _coords;


    /* LIFECYCLE */

    /**
     * TODO
     * @param x
     * @param y
     */
    public BoardViewCellClickedEvent(int x, int y)
    {
        super(EVENT_TYPE);
        this._coords = new Vector2i(x, y);
    }

    /**
     * TODO
     * @param coords
     */
    public BoardViewCellClickedEvent(Vector2i coords)
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