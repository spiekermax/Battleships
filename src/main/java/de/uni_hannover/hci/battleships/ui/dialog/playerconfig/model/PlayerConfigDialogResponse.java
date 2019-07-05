package de.uni_hannover.hci.battleships.ui.dialog.playerconfig.model;


public class PlayerConfigDialogResponse
{
    /* ATTRIBUTES */

    private final PlayerConfigDialogResponseType _type;

    private final String _name;


    /* LIFECYCLE */

    /**
     * TODO
     * @param name
     */
    public PlayerConfigDialogResponse(PlayerConfigDialogResponseType type, String name)
    {
        this._type = type;

        this._name = name;
    }


    /* GETTERS & SETTERS */

    /**
     * TODO
     * @return
     */
    public boolean isAborted()
    {
        return this.getType() == PlayerConfigDialogResponseType.ABORT;
    }

    /**
     * TODO
     * @return
     */
    public boolean isValid()
    {
        return this.getType() == PlayerConfigDialogResponseType.VALID;
    }

    /**
     * TODO
     * @return
     */
    public PlayerConfigDialogResponseType getType()
    {
        return this._type;
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