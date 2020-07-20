package Gui;

import Game.Field;
import Infrastructure.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Diese Klasse ist zuständig für die Grafiken des Spiels, damit diese auf das Spielfenster gezeichnet werden.
 * Sie erbt von der Klasse JLabel, die einen Component in Java Swing darstellt. (einer Java Zeichnungs Bibliothek)
 *
 * @author Luca Treide
 */
public class GameGraphics extends JLabel {

    /** Diese Variable speichert, auf welchem Fenster gezeichnet werden soll.
     */
    public GameWindow window;

    /**
     * Mithilfe dieser Methode aus Java Swing, können alle Dinge auf das Fenster gezeichnet werden.
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);                //Hintergrund wird schwarz gemacht
        g.fillRect(0, 0, window.WIDTH, window.HEIGHT);

        g.setColor(Color.WHITE);                //Raster wird erstellt
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                g.drawRect(20 + i * 20, 20 + j * 20, 20, 20);
            }
        }
        for (int i = 0; i < 4; i++) {           //Raster für Feld rechts oben wird erstellt
            for (int j = 0; j < 4; j++) {
                g.drawRect(270 + i * 20, 60 + j * 20, 20, 20);
            }
        }

        if (GameManager.inGame || GameManager.pause || GameManager.lostScreen) { //wenn sich das Spiel in einem dieser Spielstände befindet

            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score:"+ GameManager.score, 250, 200);

            for (int i = 0; i < 10; i++) {                                      //Felder werden gezeichnet, also die Tetrisstein
                for (int j = 0; j < 20; j++) {
                    if (Field.allFields[i][j].isOccupied) {
                        g.setColor(Field.allFields[i][j].color);
                        g.fillRect(Field.allFields[i][j].x, Field.allFields[i][j].y, 19, 19);
                    }
                }
            }

            for (int j = 0; j < 4; j++) {                                           //Tetrisstein im Feld rechts oben wird gezeichnet
                for (int k = 0; k < 4; k++) {
                    if (Field.exampleFields[j][k].isOccupied) {
                        g.setColor(Field.exampleFields[j][k].color);
                        g.fillRect(Field.exampleFields[j][k].x, Field.exampleFields[j][k].y, 19, 19);
                    }
                }
            }

            BufferedImage music = null;                                         //Musik-Aus/Musik-An Button wird hier gezeichnet
            try {
                music = ImageIO.read(GameGraphics.class.getResourceAsStream("Musik.png"));
            } catch (IOException e) {
                System.out.println("Datei 'Musik.png' konnte nicht geladen werden!");
            }
            g.drawImage(music, 250, 225, 30, 30, null);
            if (GameManager.musicOn) {
                g.setColor(new Color(0, 0, 0, 50));
            } else {
                g.setColor(new Color(0, 0, 0, 95));
            }
            g.fillRect(250, 225, 30, 30);

            if (GameManager.lostScreen) {                               //Wenn das Spiel verloren wurde wird ein GameOver Screen gezeigt
                g.setColor(new Color(57, 57, 57, 139));
                g.fillRect(0,0, window.WIDTH, window.HEIGHT);
                g.setColor(new Color(226, 17, 22));
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("Game Over!", 50, 175);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Drücke ENTER zum Neustart!", 50, 218);
            }

            if (GameManager.pause) {                                   //Pause-Screen
                g.setColor(new Color(57, 57, 57, 90));
                g.fillRect(0, 0, window.getWidth(), window.getHeight());
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("Pause", 125, window.getHeight()/2);
            }
        }
        else if (GameManager.menu) {                                    //Erstellung des Menüs
            g.setColor(new Color(26, 13, 124));
            g.fillRect(0,0,400, 500);
            BufferedImage tetrisImage = null;
            try {
                tetrisImage = ImageIO.read(GameGraphics.class.getResourceAsStream("TetrisScreen.png"));
            } catch (IOException e) {
                System.out.println("Datei 'TetrisScreen.png' konnte nicht geladen werden!");
            }
            g.drawImage(tetrisImage, 50, 50, tetrisImage.getWidth()/4, tetrisImage.getHeight()/4, null);
            g.setColor(Color.BLACK);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Drücke ENTER, um das Spiel zu starten!", 10, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Navigiere den Tetrisstein mit den Pfeiltasten!", 10, 350);
            g.drawString("Drehe den Tetrisstein mit der Leerzeichentaste!", 10, 375);
            g.drawString("Drücke ESCAPE, um das Spiel zu pausieren!", 10, 400);
        }

        repaint();
    }
}
