package de.uni_hannover.hci.battleships.ui.board.event;

// JavaFX
import javafx.event.Event;
import javafx.event.EventType;


public class BoardViewMouseExitedEvent extends Event
{
    /* CONSTANTS */

    public static final EventType<BoardViewMouseExitedEvent> EVENT_TYPE = new EventType<>("board-view-mouse-exited");


    /* LIFECYCLE */

    public BoardViewMouseExitedEvent()
    {
        super(EVENT_TYPE);
    }
}