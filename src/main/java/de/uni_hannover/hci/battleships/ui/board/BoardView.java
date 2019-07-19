package de.uni_hannover.hci.battleships.ui.board;

// Internal dependencies
import de.uni_hannover.hci.battleships.data.Board;
import de.uni_hannover.hci.battleships.ui.board.cell.BoardViewCell;
import de.uni_hannover.hci.battleships.ui.board.cell.BoardViewCellColor;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewCellClickedEvent;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewCellHoveredEvent;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewMouseExitedEvent;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewRightClickedEvent;
import de.uni_hannover.hci.battleships.util.Vector2i;
import de.uni_hannover.hci.battleships.util.resource.R;

// Java
import java.io.IOException;

// JavaFX
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class BoardView extends GridPane
{
    /* CONSTANTS */

    private static final int BOARD_SIZE = 10;


    /* ATTRIBUTES */

    private boolean _isEnabled = true;
    private boolean _shipsVisible = true;

    private BoardViewCell _lastMouseTargetCell;


    /* LIFECYCLE */

    /**
     * Instanziiert ein Spielbrett-Objekt mit UI.
     */
    public BoardView()
    {
        // Load component layout
        FXMLLoader loader = new FXMLLoader(R.layout("component/board.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try { loader.load(); }
        catch(IOException e) { throw new RuntimeException("ERROR: Failed to load 'BoardView' layout!", e); }

        // Add cells
        for(int y = 0; y < BOARD_SIZE; ++y)
        {
            for(int x = 0; x < BOARD_SIZE; ++x)
            {
                this.add( new BoardViewCell(BoardViewCellColor.WATER) , x, y);
            }
        }

        // Append callbacks
        this.widthProperty().addListener(this::onSizeChange);
        this.heightProperty().addListener(this::onSizeChange);

        this.addEventHandler(MouseEvent.MOUSE_MOVED, this::onMouseMove);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClick);
        this.addEventHandler(MouseEvent.MOUSE_EXITED, this::onMouseExited);
    }


    /* CALLBACKS */

    /**
     * Wird aufgerufen, wann immer die Größe dieser Komponente verändert wird.
     * Passt die Größe des Spielbrettrasters automatisch so an, dass das Seitenverältnis gleich bleibt.
     * @param observableValue Die veränderte 'ObservableValue'.
     * @param oldValue Die alte Breite bzw. Höhe dieser Komponente (sollte nicht verwendet werden).
     * @param newValue Die neue Breite bzw. Höhe dieser Komponente (sollte nicht verwendet werden).
     */
    private void onSizeChange(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue)
    {
        // Calculate ideal cell size
        double cellSize = this.calcCellSize();

        // Adjust constraints to cell size
        RowConstraints rowConstraints = new RowConstraints();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        rowConstraints.setPrefHeight(cellSize);
        columnConstraints.setPrefWidth(cellSize);

        // Update constraints
        this.getRowConstraints().clear();
        this.getColumnConstraints().clear();
        for(int i = 0; i < BOARD_SIZE; ++i)
        {
            this.getRowConstraints().add(rowConstraints);
            this.getColumnConstraints().add(columnConstraints);
        }
    }

    /**
     * Wird augerufen, wann immer die Maus über diese Komponente bewegt wird.
     * Das anvisierte Feld wird gehighlighted.
     * @param e Die Eventinformationen.
     */
    private void onMouseMove(MouseEvent e)
    {
        // Get the cell, the mouse points at
        Vector2i currentMouseTargetCoords = this.calcCellCoords(e.getX(), e.getY());
        BoardViewCell currentMouseTargetCell = this.getCell(currentMouseTargetCoords);

        // Fire events
        this.fireEvent(new BoardViewCellHoveredEvent(currentMouseTargetCoords));

        // Disable hover-effect if BoardView is disabled
        if(!this.isEnabled()) return;

        // If the targeted cell changed
        if(currentMouseTargetCell != this.getLastMouseTargetCell())
        {
            // Remove highlighting from previously targeted cell
            if(this.getLastMouseTargetCell() != null)
                this.getLastMouseTargetCell().removeHighlighting();

            // Add highlighting to the currently targeted cell
            if(currentMouseTargetCell != null)
                currentMouseTargetCell.addHighlighting();

            // Declare cell as handled
            this.setLastMouseTargetCell(currentMouseTargetCell);
        }
    }

    /**
     * Wird aufgerufen, wann immer mit der Maus auf diese Komponente geklickt wird,
     * Sendet die zugehörigen Events.
     * @param e Die Eventinformationen.
     */
    private void onMouseClick(MouseEvent e)
    {
        Vector2i coords = this.calcCellCoords(e.getX(), e.getY());
        if(e.getButton() == MouseButton.PRIMARY)
        {
            // Fire 'board-view-cell-clicked' event, passing on the grid coordinates of the click.
            this.fireEvent(new BoardViewCellClickedEvent(coords));
        }
        else if(e.getButton() == MouseButton.SECONDARY)
        {
            this.fireEvent(new BoardViewRightClickedEvent(coords));
        }
    }

    /**
     * Wird aufgerufen, wann immer die Maus diese Komponente verlässt.
     * Entfernt jegliches Highlighting.
     * @param e Die Eventinformationen.
     */
    private void onMouseExited(MouseEvent e)
    {
        // Fire events
        this.fireEvent(new BoardViewMouseExitedEvent());

        // Remove all highlighting, once the mouse leaves this component
        if(this.getLastMouseTargetCell() != null)
            this.getLastMouseTargetCell().removeHighlighting();

        // Declare no cell as highlighted
        this.setLastMouseTargetCell(null);
    }


    /* METHODS */

    /**
     * Zeigt die Daten aus dem übergebenen Board 'board' an.
     * @param board Das darzustelende Board.
     */
    public void display(Board board)
    {
        for(int y = 0; y < BOARD_SIZE; ++y)
        {
            for(int x = 0; x < BOARD_SIZE; ++x)
            {
                if(this.getCell(x, y) == null)
                    throw new IndexOutOfBoundsException("ERROR: BoardView.display(): Board cell at " + new Vector2i(x ,y) + "does not exist!");

                switch(board.getCell(x, y))
                {
                    case WATER:
                        this.getCell(x, y).setDefaultColor(BoardViewCellColor.WATER);
                        break;
                    case SHIP:
                        if(this.getShipsVisible())
                            this.getCell(x, y).setDefaultColor(BoardViewCellColor.SHIP);
                        else
                            this.getCell(x, y).setDefaultColor(BoardViewCellColor.WATER);
                        break;
                    case HIT:
                        this.getCell(x, y).setDefaultColor(BoardViewCellColor.HIT);
                        break;
                    case MISS:
                        this.getCell(x, y).setDefaultColor(BoardViewCellColor.MISS);
                        break;
                    case GHOST_SHIP:
                        this.getCell(x, y).setDefaultColor(BoardViewCellColor.GHOST_SHIP);
                        break;
                }
            }
        }
    }

    /**
     * Berechnet die ideale Größe eines Feldes, abhängig von der tatsächlich erlaubten Größe dieser Komponente.
     * @return Die ideale Größe eines einzelnen Feldes.
     */
    private double calcCellSize()
    {
        return Math.min(this.getWidth(), this.getHeight()) / BOARD_SIZE;
    }

    /**
     * Berechnet die Koordinaten des anvisierten Feldes, abhängig von den übergebenen Mauskoordinaten.
     * @param mouseX Die X-Koordinate der Maus.
     * @param mouseY Die Y-Koordinate der Maus.
     * @return Die Koordinaten des anvisierten Feldes.
     */
    private Vector2i calcCellCoords(double mouseX, double mouseY)
    {
        double cellSize = this.calcCellSize();

        int x = (int) (mouseX / cellSize);
        int y = (int) (mouseY / cellSize);

        return new Vector2i(x, y);
    }


    /* GETTERS & SETTERS */

    /**
     * Gibt das Feld/Zelle an der spezifizierten Koordinate zurück.
     * @param x Die X-Koordinate der Zelle.
     * @param y Die Y-Koordinate der Zelle.
     * @return Die Zelle.
     */
    private BoardViewCell getCell(int x, int y)
    {
        if(x > BOARD_SIZE - 1 || y > BOARD_SIZE - 1) return null;

        return (BoardViewCell) this.getChildren().get(y * BOARD_SIZE + x);
    }

    /**
     * Gibt das Feld/Zelle an der spezifizierten Koordinate zurück.
     * @param coords Die Koordinaten der Zelle.
     * @return Die Zelle.
     */
    private BoardViewCell getCell(Vector2i coords)
    {
        return this.getCell(coords.getX(), coords.getY());
    }

    /**
     * Gibt zurück, ob diese BoardView aktiviert ist.
     * @return Ob, diese BoardView aktiviert ist.
     */
    public boolean isEnabled()
    {
        return this._isEnabled;
    }

    /**
     * Aktiviert/deaktiviert diese BoardView und entfernt dadurch Maus-Highlighting.
     * @param newIsEnabled Der neue Zustand.
     */
    public void setIsEnabled(boolean newIsEnabled)
    {
        this._isEnabled = newIsEnabled;
        if(this.getLastMouseTargetCell() != null) this.getLastMouseTargetCell().removeHighlighting();
    }

    /**
     * Gibt zurück, ob die Schiffe angeziegt werden.
     * @return Ob die Schiffe angezeigt werden.
     */
    public boolean getShipsVisible()
    {
        return this._shipsVisible;
    }

    /**
     * Setzt, ob die Schiffe angezeigt werden.
     * @param newShipsVisible Der neue Zustand
     */
    public void setShipsVisible(boolean newShipsVisible)
    {
        this._shipsVisible = newShipsVisible;
    }

    /**
     * Gibt die zuletzt anvisierte Zelle zurück.
     * @return Die Zelle
     */
    private BoardViewCell getLastMouseTargetCell()
    {
        return this._lastMouseTargetCell;
    }

    /**
     * Setzt die zuletzt anvisierte Zelle.
     * @param lastMouseTargetCell Der neue Zustand.
     */
    private void setLastMouseTargetCell(BoardViewCell lastMouseTargetCell)
    {
        this._lastMouseTargetCell = lastMouseTargetCell;
    }
}