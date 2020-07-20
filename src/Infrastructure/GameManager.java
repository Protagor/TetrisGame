package Infrastructure;

import Game.Field;
import Game.StoneTypes;
import Game.TetrisStone;
import Sound.SoundMaker;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  Diese Klasse dient generell dazu, um das ganze Spiel zum Laufen zu bringen.
 *  Die Tetrissteine werden von hier aus geupdatet, das Spiel wird hier initialisiert und akutalisiert.
 *
 *  @author Luca Treide
 */
public class GameManager extends Timer {

    public static boolean musicOn = true;

    /** Umso größer der Wert gameSpeed ist, desto langsamer ist das Spiel.
     */
    public static int gameSpeed = 350;

    /** Gibt an, ob gerade die KI/ der Computer spielt oder nicht.
     */
    public static boolean computerIsPlaying;

    /** Speichert alle Tetrissteine zwischen, damit diese anschließend aus der 'stoneBag' zufällig ausgewählt werden können.
     */
    ArrayList<TetrisStone> stoneBag = new ArrayList<>();

    /** Gibt an, ob es mindestens eine volle Reihe gibt.
     */
    private boolean fullRow = false;
    /** Hier wird gespeichert, wie viele Reihen voll sind.
     */
    private int fullRowsNumber = 0;

    /** Hier wird der derzeitige Spielscore gespeichert.
     */
    public static int score;

    /** Hier wird Stein gespeichert, der sich im Moment bewegt.
     */
    public static TetrisStone movingStone;

    /** Hier wird der Stein gespeichert, der als nächstest kommt, wenn der derzeitig bewegende Stein sich nicht mehr bewegen kann.
     */
    public static TetrisStone nextMovingStone;

    public static ArrayList<TetrisStone> prevStone = new ArrayList<>();

    /** Hier wird das Objekt der Klasse SoundMaker gepspeichert, um Musik abspielen zu können.
     */
    public static SoundMaker gameSounds;

    /** Hier werden die möglichen derzeitigen Spielzustände gespeichert.
     */
    public static boolean inGame = false, menu = true, pause = false, lostScreen = false;


    /** Konstruktor, um den GameTimer zu erstellen / zu starten.
     */
    public GameManager() {
        scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    if (inGame) { //wenn das Spiel gerade läuft...
                        try {
                            movingStone.update();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (movingStone.isStatic && !movingStone.beingCalculated) {
                            checkIfRowIsFull();
                            if (fullRowsNumber == 1) {
                                score += 40;
                            } else if (fullRowsNumber == 2) {
                                score += 100;
                            } else if (fullRowsNumber == 3) {
                                score += 300;
                            } else if (fullRowsNumber == 4) {
                                score += 1200;
                            }

                            if (fullRow) {
                                try {
                                    if (musicOn) {
                                        gameSounds.playClear();
                                    }
                                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                                    e.printStackTrace();
                                }
                                fullRow = false;
                            }
                            if (lost()) {
                                lostScreen = true;
                                inGame = false;
                                GameManager.computerIsPlaying = false;
                                GameManager.gameSpeed = 350;
                                try {
                                    if (musicOn) {
                                        gameSounds.value = -100;
                                        gameSounds.playGameOver();
                                        gameSounds.value = -10;
                                    }
                                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (fullRowsNumber == 0) {
                                prevStone.add(movingStone);
                            } else {
                                prevStone = new ArrayList<>();
                            }
                            fullRowsNumber = 0;
                            TetrisStone temp = nextMovingStone;
                            TetrisStone.removeFieldsInExampleBox();
                            nextMovingStone = getRandomTetrisStone();
                            nextMovingStone.setFieldsInExampleBox();
                            movingStone = temp;
                        }

                    }
                try {
                    Thread.sleep(gameSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 50);
    }

    /**Sorgt dafür das Tetrisstein zufällig zurück gegeben werden, jedoch es nicht dazu kommen kann, das ein Stein drei mal hintereinander kommt.
     * @return Gibt einen zufälligen Tetrisstein zurück.
     */
    public TetrisStone getRandomTetrisStone() {

        if (stoneBag.size() == 0) {
            stoneBag.add(new TetrisStone(new Color(226, 17, 22), StoneTypes.L));
            stoneBag.add(new TetrisStone(new Color(71, 80, 255), StoneTypes.J));
            stoneBag.add(new TetrisStone(new Color(149, 144, 157), StoneTypes.T));
            stoneBag.add(new TetrisStone(new Color(255, 74, 216), StoneTypes.O));
            stoneBag.add(new TetrisStone(new Color(40, 178, 30), StoneTypes.I));
            stoneBag.add(new TetrisStone(new Color(255, 117, 24), StoneTypes.Z));
            stoneBag.add(new TetrisStone(new Color(43, 255, 237), StoneTypes.Z2));
        }

        int randomNumber = (int) (Math.random() * stoneBag.size());
        TetrisStone stone = stoneBag.get(randomNumber);
        stoneBag.remove(stone);
        return stone;


    }

    /** Prüft, ob es volle Reihen gibt und wenn dem so ist, werden diese Reihen entfernt.
     */
    public void checkIfRowIsFull() {
        boolean fullRowWasFound = false;
        for (int i=19; i >= 0; i--) {
            if (!fullRowWasFound) {
                boolean[] row = new boolean[10];
                for (int j = 0; j < 10; j++) {
                    row[j] = Field.allFields[j][i].isOccupied;
                }
                boolean rowIsFull = true;
                for (int k = 0; k < 10; k++) {
                    if (!row[k]) {
                        rowIsFull = false;
                    }
                }
                if (rowIsFull) {
                    fullRowWasFound = true;
                    fullRowsNumber++;
                    for (int l=0; l<10; l++) {
                        Field.allFields[l][i].isOccupied = false;
                    }
                } else if (fullRowsNumber == 0){
                    movingStone.setAllFieldsForPosition();
                }
            } else {
                for (int j=0; j<10; j++) {
                    if (Field.allFields[j][i].isOccupied && !Field.allFields[j][i + 1].isOccupied) {
                        Field.allFields[j][i + 1].color = Field.allFields[j][i].color;
                        Field.allFields[j][i].isOccupied = false;
                        Field.allFields[j][i + 1].isOccupied = true;
                    }
                }
            }
            if (fullRowWasFound) {
                fullRow = true;
                checkIfRowIsFull();
            }
        }
    }

    /**
     * @return Gibt 'true' zurück, wenn man verloren hat, und 'false', wenn man nicht verloren hat.
     */
    public boolean lost() {
        if (!computerIsPlaying) {
            for (int i = 0; i < 10; i++) {
                if (Field.allFields[i][0].isOccupied) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                if (Field.allFields[i][0].isOccupied) {
                    return true;
                }
            }
            for (int i = 0; i < 10; i++) {
                if (Field.allFields[i][1].isOccupied) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Hier wird das Spiel initialisiert, damit es bereit ist gestartet zu werden.
     */
    public void init() {
        Field.setAllFields();
        movingStone = getRandomTetrisStone();
        nextMovingStone = getRandomTetrisStone();
        nextMovingStone.setFieldsInExampleBox();
        score = 0;
    }
}
