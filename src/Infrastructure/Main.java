package Infrastructure;

import Gui.GameGraphics;
import Gui.GameWindow;
import KeyHandler.KeyHandler;
import Sound.SoundMaker;

import javax.swing.*;

/** Die Main Klasse enthält die main-Methode und ist somit dafür zuständig, was passiert, wenn das Programm ausgeführt wird.
 *
 * @author Luca Treide
 */
public class Main {

    public static void main(String[] args) { //Spiel wird gestartet...
        SwingUtilities.invokeLater(() -> {
            GameManager game = new GameManager();
            game.init();
            GameWindow window = new GameWindow();
            GameGraphics graphics = new GameGraphics();
            window.add(graphics);
            KeyHandler key = new KeyHandler();
            key.game = game;
            key.window = window;
            graphics.addKeyListener(key);
            window.addKeyListener(key);
            graphics.window = window;
        });

        SoundMaker sound = new SoundMaker();
        GameManager.gameSounds = sound;
        try {
            sound.playBackgroundMusic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
