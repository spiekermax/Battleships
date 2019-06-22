package de.uni_hannover.hci.battleships.ui.board;

// Internal dependencies
import de.uni_hannover.hci.battleships.resources.R;
import de.uni_hannover.hci.battleships.ui.board.cell.BoardViewCell;
import de.uni_hannover.hci.battleships.ui.board.cell.BoardViewCellColor;
import de.uni_hannover.hci.battleships.ui.board.events.BoardViewCellClickedEvent;
import de.uni_hannover.hci.battleships.util.Vector2i;

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
        double cellSize = this.parseCellSize();

        RowConstraints rowConstraints = new RowConstraints();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        rowConstraints.setPrefHeight(cellSize);
        columnConstraints.setPrefWidth(cellSize);

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
        Vector2i currentMouseTargetCoords = this.parseCellCoords(e.getX(), e.getY());
        BoardViewCell currentMouseTargetCell = this.getCell(currentMouseTargetCoords);

        if(currentMouseTargetCell == this.getLastMouseTargetCell()) return;

        if(this.getLastMouseTargetCell() != null)
            this.getLastMouseTargetCell().removeHighlighting();

        if(currentMouseTargetCell != null)
            currentMouseTargetCell.addHighlighting();

        this.setLastMouseTargetCell(currentMouseTargetCell);
    }

    /**
     * TODO
     * @param e
     */
    private void onMouseClick(MouseEvent e)
    {
        Vector2i coords = this.parseCellCoords(e.getX(), e.getY());
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
        if(this.getLastMouseTargetCell() != null)
            this.getLastMouseTargetCell().removeHighlighting();

        this.setLastMouseTargetCell(null);
    }


    /* METHODS */

    /**
     * TODO
     * @return
     */
    private double parseCellSize()
    {
        return Math.min(this.getWidth(), this.getHeight()) / BOARD_SIZE;
    }

    /**
     * TODO
     * @param mouseX
     * @param mouseY
     * @return
     */
    private Vector2i parseCellCoords(double mouseX, double mouseY)
    {
        double cellSize = this.parseCellSize();

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