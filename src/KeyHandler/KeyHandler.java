package KeyHandler;

import Game.TetrisStone;
import Gui.GameWindow;
import Infrastructure.GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Diese Klasse ist dafür zuständig, um die Tasteninputs des Benutzers zu verarbeiten.
 *
 * @author Luca Treide
 */
public class KeyHandler implements KeyListener {

    /** Hier wird der sich derzeitig bewegende Tetrisstein gespeichert.
     */
    TetrisStone movingStone;

    /** Hier wird ein Verweis auf das gerade operierende Objekt der Klasse GameTimer erstellt.
     */
    public GameManager game;

    /** Hier wird ein Verweis auf das Fenster erstellt.
     */
    public GameWindow window;

    @Override
    public void keyTyped(KeyEvent e) {} //Methode wird nicht benötigt.

    @Override
    public void keyReleased(KeyEvent e) {} //Methode wird nicht benötigt.

    /** Die Methode hanelt ab, was passiert, wenn eine bestimmte Taste gedrückt wird.
     * @param e In diesem Parameter wird unter anderem gespeichert, welche Taste gedrückt wurde.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        setMovingStone();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (movingStone.canMoveRight() && GameManager.inGame) {
                movingStone.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (movingStone.canMoveLeft() && GameManager.inGame) {
                movingStone.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (movingStone.canMoveDown() && GameManager.inGame) {
                movingStone.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE && GameManager.inGame) {
            movingStone.rotate();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (GameManager.menu) {
                GameManager.menu = false;
                GameManager.inGame = true;
                window.KIButton.setVisible(true);
            } else if (GameManager.lostScreen) {
                GameManager.lostScreen= false;
                game.init();
                GameManager.inGame = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!GameManager.pause && GameManager.inGame) {
                GameManager.inGame = false;
                GameManager.pause = true;
            } else if (GameManager.pause && !GameManager.inGame) {
                GameManager.inGame = true;
                GameManager.pause = false;
            }
        }
    }

    /** Setter für movingStone.
     */
    private void setMovingStone() {
        movingStone = GameManager.movingStone;
    }
}
