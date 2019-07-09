package de.uni_hannover.hci.battleships.network;

// JavaFX
import javafx.scene.Node;


public interface NetworkSocket
{
    /**
     * TODO
     * @param message
     */
    public void sendString(String message);

    /**
     * TODO
     * @return
     */
    public Node getEventEmitter();
}