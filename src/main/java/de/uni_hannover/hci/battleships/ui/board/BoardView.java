package de.uni_hannover.hci.battleships.ui.board;

// Internal dependencies
import de.uni_hannover.hci.battleships.data.Board;
import de.uni_hannover.hci.battleships.ui.board.cell.BoardViewCell;
import de.uni_hannover.hci.battleships.ui.board.cell.BoardViewCellColor;
import de.uni_hannover.hci.battleships.ui.board.event.BoardViewCellClickedEvent;
import de.uni_hannover.hci.battleships.util.Vector2i;
import de.uni_hannover.hci.battleships.util.resource.R;

// Java
import java.io.IOException;

// JavaFX
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class BoardView extends GridPane
{
    /* CONSTANTS */

    private static final int BOARD_SIZE = 10;


    /* ATTRIBUTES */

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
     * Wird aufgerufen, wann immer die größe dieser Komponente verändert wird.
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
     * TODO
     * @param e
     */
    private void onMouseMove(MouseEvent e)
    {
        // Get the cell, the mouse points at
        Vector2i currentMouseTargetCoords = this.calcCellCoords(e.getX(), e.getY());
        BoardViewCell currentMouseTargetCell = this.getCell(currentMouseTargetCoords);

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
     * TODO
     * @param e
     */
    private void onMouseClick(MouseEvent e)
    {
        // Fire 'board-view-cell-clicked' event, passing on the grid coordinates of the click.
        Vector2i coords = this.calcCellCoords(e.getX(), e.getY());
        this.fireEvent(new BoardViewCellClickedEvent(coords));

        // Demo code only
        this.getCell(coords).setDefaultColor(BoardViewCellColor.HIT);
    }

    /**
     * TODO
     * @param e
     */
    private void onMouseExited(MouseEvent e)
    {
        // Remove all highlighting, once the mouse leaves this component
        if(this.getLastMouseTargetCell() != null)
            this.getLastMouseTargetCell().removeHighlighting();

        // Declare no cell as highlighted
        this.setLastMouseTargetCell(null);
    }


    /* METHODS */

    /**
     * TODO
     * @param board
     */
    public void display(Board board)
    {
        for(int y = 0; y < BOARD_SIZE; ++y)
        {
            for(int x = 0; x < BOARD_SIZE; ++x)
            {
                if(this.getCell(x, y) == null)
                    throw new IndexOutOfBoundsException("ERROR: BoardView.display(): Board cell at " + new Vector2i(x ,y) + "does not exist!");

                switch(board.getField(x, y))
                {
                    case OCEAN:
                        this.getCell(x, y).setDefaultColor(BoardViewCellColor.WATER);
                        break;
                    case SHIP:
                        if(this.getShipsVisible())
                            this.getCell(x, y).setDefaultColor(BoardViewCellColor.SHIP);
                        else
                            this.getCell(x, y).setDefaultColor(BoardViewCellColor.WATER);
                        break;
                    case SANKED_SHIP: // <- Bitte, bitte Grammatikfehler korregieren :/
                        this.getCell(x, y).setDefaultColor(BoardViewCellColor.HIT);
                        break;
                    case SHOT:
                        this.getCell(x, y).setDefaultColor(BoardViewCellColor.MISS);
                        break;
                }
            }
        }
    }

    /**
     * TODO
     * @return
     */
    private double calcCellSize()
    {
        return Math.min(this.getWidth(), this.getHeight()) / BOARD_SIZE;
    }

    /**
     * TODO
     * @param mouseX
     * @param mouseY
     * @return
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
     * TODO
     * @param x
     * @param y
     * @return
     */
    private BoardViewCell getCell(int x, int y)
    {
        if(x > BOARD_SIZE - 1 || y > BOARD_SIZE - 1) return null;

        return (BoardViewCell) this.getChildren().get(y * BOARD_SIZE + x);
    }

    /**
     * TODO
     * @param coords
     * @return
     */
    private BoardViewCell getCell(Vector2i coords)
    {
        return this.getCell(coords.getX(), coords.getY());
    }

    /**
     * TODO
     * @return
     */
    public boolean getShipsVisible()
    {
        return this._shipsVisible;
    }

    /**
     * TODO
     * @param newShipsVisible
     */
    public void setShipsVisible(boolean newShipsVisible)
    {
        this._shipsVisible = newShipsVisible;
    }

    /**
     * TODO
     * @return
     */
    private BoardViewCell getLastMouseTargetCell()
    {
        return this._lastMouseTargetCell;
    }

    /**
     * TODO
     * @param lastMouseTargetCell
     */
    private void setLastMouseTargetCell(BoardViewCell lastMouseTargetCell)
    {
        this._lastMouseTargetCell = lastMouseTargetCell;
    }
}