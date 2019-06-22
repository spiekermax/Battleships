package de.uni_hannover.hci.battleships.ui.board.cell;

// Internal dependencies
import de.uni_hannover.hci.battleships.util.ColorCodeConverter;

// JavaFX
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


public class BoardViewCell extends AnchorPane
{
    /* ATTRIBUTES */

    private Color _defaultColor;
    private Color _highlightColor;

    private boolean _hasHighlighting = false;


    /* LIFECYCLE */

    /**
     * TODO
     * @param defaultColor
     */
    public BoardViewCell(Color defaultColor)
    {
        super();
        this.setDefaultColor(defaultColor);
    }


    /* METHODS */

    /**
     * TODO
     */
    public void addHighlighting()
    {
        if(this.hasHighlighting()) return;

        this.setHasHighlighting(true);
        this.applyStyling();
    }

    /**
     * TODO
     */
    public void removeHighlighting()
    {
        if(!this.hasHighlighting()) return;

        this.setHasHighlighting(false);
        this.applyStyling();
    }

    /**
     * TODO
     */
    private void applyStyling()
    {
        Color backgroundColor = this.hasHighlighting() ? this.getHighlightColor() : this.getDefaultColor();
        Color gridColor = this.getDefaultColor().deriveColor(0.0, 1.0, 0.8, 1.0);

        this.setStyle(
                "-fx-background-color: " + ColorCodeConverter.parseHex(backgroundColor) + "; " +
                "-fx-border-color: "     + ColorCodeConverter.parseHex(gridColor)       + "; " +
                "-fx-border-width: "     + "0 0 1 1"                                    + ";"
        );
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public Color getDefaultColor()
    {
        return this._defaultColor;
    }

    /**
     * TODO
     * @param newDefaultColor
     */
    public void setDefaultColor(Color newDefaultColor)
    {
        this._defaultColor = newDefaultColor;

        this.setHighlightColor( this.getDefaultColor().deriveColor(0.0, 1.0, 1.1, 1.0) );
        this.applyStyling();
    }

    /**
     * TODO
     * @return
     */
    public boolean hasHighlighting()
    {
        return this._hasHighlighting;
    }

    /**
     * TODO
     * @param hasHighlighting
     */
    private void setHasHighlighting(boolean hasHighlighting)
    {
        this._hasHighlighting = hasHighlighting;
    }

    /**
     * TODO
     * @return
     */
    public Color getHighlightColor()
    {
        return this._highlightColor;
    }

    /**
     * TODO
     * @param newHighlightColor
     */
    private void setHighlightColor(Color newHighlightColor)
    {
        this._highlightColor = newHighlightColor;
    }
}