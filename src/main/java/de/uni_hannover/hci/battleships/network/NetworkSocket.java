package de.uni_hannover.hci.battleships.network;

// Internal dependencies
import de.uni_hannover.hci.battleships.util.Vector2i;

// JavaFX
import javafx.scene.Node;


public interface NetworkSocket
{
    /**
     * TODO
     * @param message
     */
    public void sendMessage(String message);

    /**
     * TODO
     * @param vector
     */
    public void sendVector(Vector2i vector);

    /**
     * TODO
     * @return
     */
    public Node getEventEmitter();
}