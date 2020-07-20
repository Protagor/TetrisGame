package Game;

import Infrastructure.GameManager;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/** Diese Klasse behandelt die in Tetris vorkommen Felder, auf denen sich
 * die Tetrissteine bewegen. Alle Felder zusammen stellen das Spielfeld
 * von Tetris dar.
 *
 * @author Luca Treide
 */

public class Field {
    /** Das Spielfeld besteht aus 10*20 Feldern und wird in der Variable 'allFields' gespeichert
     */
    public static Field[][] allFields = new Field[10][20];

    /** Das Feld oben rechts besteht aus 4*4 Feldern und zeigt an, welcher Stein als nächstes kommt.
     */
    public static Field[][] exampleFields = new Field[4][4];

    /** In der Variable 'color' wird gespeichert, welche Farbe ein Feld gerade hat, bzw. der Stein der auf dem Feld liegt.
     */
    public Color color;

    /** 'isOccupied' zeigt an ob sich gerade ein Tetrisstein auf dem Feld befindet.
     */
    public boolean isOccupied = false;

    /** Gibt die Position des Feldes in Pixeln an.
     */
    public final int x, y;

    /** Gibt die Position des Feldes im Raster an.
     */
    public final int posX, posY;

    /** Konstruktor, der die Position in x und y als Position (in Pixeln) übernimmt.
     */
    public Field(int x, int y) {
        this.x = x;
        this.y = y;

        posX = (x - 21)/20;
        posY = (y - 21)/20;

    }

    /** Wird aufgerufen, wenn sich ein Tetrisstein über ein zuvor nicht besetztes Tetrisfeld bewegt.
     */
    public void becomeOccupied(Color c) {
        color = c;
        isOccupied = true;
    }

    /** Setzt alle Felder (allFields und exampleFields).
     */
    public static void setAllFields() {
        for (int i=0; i<10; i++) {                                          //alle Spielfelder...
            for (int j=0; j<20; j++) {
                allFields[i][j] = new Field(21+ i*20, 21+ j*20);
            }
        }
        for (int j=0; j<4; j++) {                                              //alle Felder des Rasters oben rechts
            for (int k=0; k<4; k++) {
                exampleFields[j][k] = new Field(1+270+j*20, 1+60+k*20);
            }
        }
    }
}
