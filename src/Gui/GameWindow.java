package Gui;

import Infrastructure.GameManager;
import Sound.SoundMaker;

import javax.sound.sampled.DataLine;
import javax.swing.*;
import java.awt.*;

/**
 * Diese Klasse ist zur Erstellung eines Spielefenster. Die Klasse erbt von JFrame, der 'Fensterklasse' aus Java Swing.
 *
 * @author Luca Treide
 */
public class GameWindow extends JFrame {

    /**
     * Breite und Höhe des Fensters wird hier gespeichert.
     */
    final int WIDTH, HEIGHT;

    /** Der Button, um die KI zu aktivieren bzw. zu deaktivieren.
     */
    final public JButton KIButton = new JButton("Spieler Wechsel!");

    /** Der Button, um die Musik zu aktivieren bzw. zu deaktivieren.
     */
    final public JButton musicButton = new JButton();

    /**
     * Konstruktor zur Erstellung eines Fensters.
     */
    public GameWindow() {
        WIDTH = 400;
        HEIGHT = 500;
        setTitle("Tetris");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        setVisible(true);

        KIButton.setOpaque(true);
        KIButton.setFocusable(false);
        KIButton.setBounds(250, 350, 125, 20);
        KIButton.setVisible(false);
        KIButton.setForeground(Color.BLACK);
        KIButton.addActionListener(l -> {
            if (!GameManager.computerIsPlaying) {
                GameManager.computerIsPlaying = true;
                GameManager.gameSpeed = 50;
            } else {
                GameManager.computerIsPlaying = false;
                GameManager.gameSpeed = 350;
            }
        });
        add(KIButton);

        musicButton.setOpaque(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setBorderPainted(false);
        musicButton.setFocusable(false);
        musicButton.setBounds(250, 225, 30, 30);
        musicButton.setVisible(true);
        musicButton.addActionListener(l -> {
            GameManager.musicOn = !GameManager.musicOn;
            if (GameManager.musicOn) {
                try {
                    GameManager.gameSounds.playBackgroundMusic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                SoundMaker.backgroundClip.stop();
            }
        });
        add(musicButton);
    }
}
