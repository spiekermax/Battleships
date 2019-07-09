package de.uni_hannover.hci.battleships.ui.board.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class BoardViewRightClickedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<BoardViewRightClickedEvent> EVENT_TYPE = new EventType<>("board-view-right-clicked");


    /* LIFECYCLE */

    public BoardViewRightClickedEvent()
    {
        super(EVENT_TYPE);
    }
}