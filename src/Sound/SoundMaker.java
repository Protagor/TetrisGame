package Sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/** Diese Klasse dient dazu, die Ingame Sounds abspielen zu können.
 *
 * @author Luca Treide
 */
public class SoundMaker {
    /** Mit dieser Variable wird gespeichert, wie laut die abgespielte Musik sein soll.
     */
    public int value = -10;

    /** Zum Spielen der Hintergrundmusik.
     * @throws Exception
     */

    public static Clip backgroundClip;
    public static Clip clearClip;
    public static Clip gameOverClip;


    public void playBackgroundMusic() throws Exception {
        AudioInputStream audioInputStream = getAudioInputStream(new BufferedInputStream(SoundMaker.class.getResourceAsStream("TetrisMusic.wav")));

        Clip clip = AudioSystem.getClip();
        backgroundClip = clip;
        clip.open(audioInputStream);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(value);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    /** Zum Spielen der Musik, wenn eine Reihe voll wird.
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public void playClear() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioInputStream = getAudioInputStream(getClass().getResourceAsStream("clear.wav"));

        Clip clip = AudioSystem.getClip();
        clearClip = clip;
        clip.open(audioInputStream);

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10);

        clip.start();
    }

    /** Zum Spielen der Musik, wenn man das Spiel verliert.
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public void playGameOver() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioInputStream = getAudioInputStream(getClass().getResourceAsStream("gameover-1.wav"));

        Clip clip = AudioSystem.getClip();
        gameOverClip = clip;
        clip.open(audioInputStream);

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-5);

        clip.start();

    }
}
