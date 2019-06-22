package de.uni_hannover.hci.battleships.resources;

// Java
import java.net.URL;


public class R
{
    /* CONSTANTS */

    private static final String ID_PREFIX = "#";
    private static final String LAYOUT_PATH = "/layout/";
    private static final String DRAWABLE_PATH = "/drawable/";


    /* FUNCTIONS */

    /**
     * Gibt den Selektor-String für die gegebene Layout-ID zurück.
     * @param id Die Layout-ID.
     * @return Den Selektor-String.
     */
    public static String id(String id)
    {
        return ID_PREFIX + id;
    }

    /**
     * Gibt den Pfad der gegebenen Layoutdatei zurück.
     * @param layout Der Name der Layoutdatei (mit ".fxml")
     * @return Der Pfad.
     */
    public static URL layout(String layout)
    {
        return R.class.getResource(LAYOUT_PATH + layout);
    }

    /**
     * Gibt den Pfad der gegebenen Bilddatei zurück.
     * @param drawable Der Name der Bilddatei (mit Dateiendung).
     * @return Der Pfad.
     */
    public static URL drawable(String drawable)
    {
        return R.class.getResource(DRAWABLE_PATH + drawable);
    }
}