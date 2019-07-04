package de.uni_hannover.hci.battleships.ui.dialog.playerconfig.model;


public class PlayerConfig
{
    /* ATTRIBUTES */

    private final boolean _isValid;

    private final String _name;


    /* LIFECYCLE */

    /**
     * TODO
     * @param name
     */
    public PlayerConfig(boolean isValid, String name)
    {
        this._isValid = isValid;

        this._name = name;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public boolean isValid()
    {
        return this._isValid;
    }

    /**
     * TODO
     * @return
     */
    public String getName()
    {
        return this._name;
    }
}