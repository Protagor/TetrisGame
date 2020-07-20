package Game;

import Infrastructure.GameManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static Infrastructure.GameManager.inGame;

/** Diese Klasse handelt die in Tetris vorkommen Tetrissteine ab.
 *
 * @author Luca Treide
*/
public class TetrisStone {

    /** Gibt an, ob die KI gerade für diesen Stein die beste Position berechnet.
     */
    public boolean beingCalculated = false;

    /** Gibt an, ob für die KI schon die beste Position berechnet wurde.
     */
    boolean alreadyCalculated = false;

    /** Gibt alle möglichen Endpositionen für einen Tetrisstein an. (Wichtig für die KI)
     */
    ArrayList<double[]>  allPossibleEndingLocations = new ArrayList<>();

    /** Farbe eines Tetrissteins.
     */
    final Color color;

    /** Typ eines Tetrissteins
     */
    final StoneTypes type;

    /** Hier wird gespeichert, ob ein Tetrisstein noch in der Lage ist, sich zu bewegen.
     */
    public boolean isStatic = false;

    /** Hier wird die Startposition eines Tetrissteins gespeichert.
     */
    public Point position = new Point(3, -4);


    /** Hier wird gespeichert, in welcher Rotation sich ein Tetrisstein gerade befindet.
     */
    int rotationNumber = 0;

    /**Hier werden die Felder eines Tetrissteins wie folgt gespeichert:
     *
     *        [false][true][false]
     *        [false][true][false]
     *        [false][true][true]   wäre z.B. der Stein mit dem Typ 'L'
     *
     */
    boolean[][] fields;

    /**
     * Konstruktor eines Tetrissteins
     * @param color Farbe des Tetrissteins
     * @param type Typ des Tetrissteins
     */
    public TetrisStone(Color color, StoneTypes type) {
        this.color = color;
        this.type = type;

        if (type != StoneTypes.O && type != StoneTypes.I) { //je nachdem welchen Typ ein Tetrisstein hat, werden die 'fields' nun festgelegt
            fields = new boolean[3][3];
            if (type == StoneTypes.L) {
                fields[1][0] = true;
                fields[1][1] = true;
                fields[1][2] = true;
                fields[2][2] = true;
            } else if (type == StoneTypes.J) {
                fields[1][0] = true;
                fields[1][1] = true;
                fields[1][2] = true;
                fields[0][2] = true;
            } else if (type == StoneTypes.Z) {
                fields[0][1] = true;
                fields[1][1] = true;
                fields[1][2] = true;
                fields[2][2] = true;
            } else if (type == StoneTypes.Z2) {
                fields[2][1] = true;
                fields[1][1] = true;
                fields[1][2] = true;
                fields[0][2] = true;
            } else if (type == StoneTypes.T) {
                fields[0][2] = true;
                fields[1][1] = true;
                fields[1][2] = true;
                fields[2][2] = true;
            }
        } else if (type == StoneTypes.O) {
            fields = new boolean[2][2];
            fields[0][0] = true;
            fields[0][1] = true;
            fields[1][0] = true;
            fields[1][1] = true;
        } else {
            fields = new boolean[4][4];
            fields[1][0] = true;
            fields[1][1] = true;
            fields[1][2] = true;
            fields[1][3] = true;
        }
    }

    /**
     * Diese Methode prüft, ob sich ein Stein nach rechts bewegen kann.
     * @return Die Methode gibt 'true' oder 'false' zurück, je nachdem, ob sich der Stein nach rechts bewegen kann oder nicht.
     */
    public boolean canMoveRight() {
            if (position.getY() < 0) {  //Wenn sich der Tetrisstein noch nicht vollkommen im Feld befindet, kann er sich nicht nach rechts bewegen.
                return false;
            }

            if (type != StoneTypes.O && type != StoneTypes.I) {                                 //im Nachfolgenden wird, je nachdem wie die 'fields' gesetzt und welche Felder besetzt sind, berechnet, ob der Tetrisstein sich nach rechts bewegen kann.
                if ((fields[2][0] || fields[2][1] || fields[2][2]) && position.getX() == 7) {
                    return false;
                } else if ((fields[1][0] || fields[1][1] || fields[1][2]) && position.getX() == 8) {
                    return false;
                } else if ((fields[0][0] || fields[0][1] || fields[0][2]) && position.getX() == 9) {
                    return false;
                }
                if (fields[2][0]) {
                    if (Field.allFields[(int) (position.getX() + 3)][(int) position.getY()].isOccupied) {
                        return false;
                    }
                } else if (fields[1][0]) {
                    if (Field.allFields[(int) (position.getX() + 2)][(int) position.getY()].isOccupied) {
                        return false;
                    }
                } else if (fields[0][0]) {
                    if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY()].isOccupied) {
                        return false;
                    }
                }
                if (fields[2][1]) {
                    if (Field.allFields[(int) (position.getX() + 3)][(int) position.getY() + 1].isOccupied) {
                        return false;
                    }
                } else if (fields[1][1]) {
                    if (Field.allFields[(int) (position.getX() + 2)][(int) position.getY() + 1].isOccupied) {
                        return false;
                    }
                } else if (fields[0][1]) {
                    if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY() + 1].isOccupied) {
                        return false;
                    }
                }
                if (fields[2][2]) {
                    if (Field.allFields[(int) (position.getX() + 3)][(int) position.getY() + 2].isOccupied) {
                        return false;
                    }
                } else if (fields[1][2]) {
                    if (Field.allFields[(int) (position.getX() + 2)][(int) position.getY() + 2].isOccupied) {
                        return false;
                    }
                } else if (fields[0][2]) {
                    if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY() + 2].isOccupied) {
                        return false;
                    }
                }
                return true;
            } else if (type == StoneTypes.I) {
                if ((fields[3][0] || fields[3][1] || fields[3][2] || fields[3][3]) && position.getX() == 6) {
                    return false;
                } else if (position.getX() == 8) {
                    return false;
                }
                if (fields[3][1]) {
                    if (Field.allFields[(int) position.getX() + 4][(int) position.getY() + 1].isOccupied) {
                        return false;
                    }
                }
                if (fields[1][0]) {
                    if (Field.allFields[(int) position.getX() + 2][(int) position.getY()].isOccupied) {
                        return false;
                    } else if (Field.allFields[(int) position.getX() + 2][(int) position.getY() + 1].isOccupied) {
                        return false;
                    } else if (Field.allFields[(int) position.getX() + 2][(int) position.getY() + 2].isOccupied) {
                        return false;
                    } else if (Field.allFields[(int) position.getX() + 2][(int) position.getY() + 3].isOccupied) {
                        return false;
                    }
                }
                return true;
            } else {
                if (position.getX() == 8) {
                    return false;
                }
                if (Field.allFields[(int) position.getX() + 2][(int) position.getY()].isOccupied) {
                    return false;
                } else if (Field.allFields[(int) position.getX() + 2][(int) position.getY() + 1].isOccupied) {
                    return false;
                }
                return true;
            }
    }

    /**
     * Diese Methode prüft, ob sich ein Stein nach links bewegen kann.
     * @return Die Methode gibt 'true' oder 'false' zurück, je nachdem, ob sich der Stein nach links bewegen kann oder nicht.
     */
    public boolean canMoveLeft() {
        if (position.getY() < 0) {              //Wenn sich der Tetrisstein noch nicht vollkommen im Feld befindet, kann er sich nicht nach links bewegen.
            return false;
        }

            if (type != StoneTypes.O && type != StoneTypes.I) {                                  //im Nachfolgenden wird, je nachdem wie die 'fields' gesetzt und welche Felder besetzt sind, berechnet, ob der Tetrisstein sich nach rechts bewegen kann.
                if ((fields[0][0] || fields[0][1] || fields[0][2]) && position.getX() == 0) {
                    return false;
                } else if ((fields[1][0] || fields[1][1] || fields[1][2]) && position.getX() == -1) {
                    return false;
                } else if ((fields[2][0] || fields[2][1] || fields[2][2]) && position.getX() == -2) {
                    return false;
                }
                if (fields[0][0]) {
                    if (Field.allFields[(int) (position.getX() - 1)][(int) position.getY()].isOccupied) {
                        return false;
                    }
                } else if (fields[1][0]) {
                    if (Field.allFields[(int) (position.getX())][(int) position.getY()].isOccupied) {
                        return false;
                    }
                } else if (fields[2][0]) {
                    if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY()].isOccupied) {
                        return false;
                    }
                }
                if (fields[0][1]) {
                    if (Field.allFields[(int) (position.getX() - 1)][(int) position.getY() + 1].isOccupied) {
                        return false;
                    }
                } else if (fields[1][1]) {
                    if (Field.allFields[(int) (position.getX())][(int) position.getY() + 1].isOccupied) {
                        return false;
                    }
                } else if (fields[2][1]) {
                    if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY() + 1].isOccupied) {
                        return false;
                    }
                }
                if (fields[0][2]) {
                    if (Field.allFields[(int) (position.getX() - 1)][(int) position.getY() + 2].isOccupied) {
                        return false;
                    }
                } else if (fields[1][2]) {
                    if (Field.allFields[(int) (position.getX())][(int) position.getY() + 2].isOccupied) {
                        return false;
                    }
                } else if (fields[2][2]) {
                    if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY() + 2].isOccupied) {
                        return false;
                    }
                }
                return true;
            } else if (type == StoneTypes.I) {

                if (fields[0][1] && position.getX() == 0) {
                    return false;
                } else if (position.getX() == -1) {
                    return false;
                }


                if (fields[0][1]) {
                    if (Field.allFields[(int) position.getX() - 1][(int) position.getY() + 1].isOccupied) {
                        return false;
                    }
                }

                if (fields[1][0]) {
                    if (Field.allFields[(int) position.getX()][(int) position.getY()].isOccupied) {
                        return false;
                    } else if (Field.allFields[(int) position.getX()][(int) position.getY() + 1].isOccupied) {
                        return false;
                    } else if (Field.allFields[(int) position.getX()][(int) position.getY() + 2].isOccupied) {
                        return false;
                    } else if (Field.allFields[(int) position.getX()][(int) position.getY() + 3].isOccupied) {
                        return false;
                    }
                }
                return true;
            } else {
                if (position.getX() == 0) {
                    return false;
                }
                if (Field.allFields[(int) position.getX() - 1][(int) position.getY()].isOccupied) {
                    return false;
                } else if (Field.allFields[(int) position.getX() - 1][(int) position.getY() + 1].isOccupied) {
                    return false;
                }
                return true;
            }
    }

    /**
     * Diese Methode prüft, ob sich ein Stein nach unten bewegen kann.
     * @return Die Methode gibt 'true' oder 'false' zurück, je nachdem, ob sich der Stein nach unten bewegen kann oder nicht.
     */
    public boolean canMoveDown() {

            if (type != StoneTypes.O && type != StoneTypes.I) {                                 //im Nachfolgenden wird, je nachdem wie die 'fields' gesetzt und welche Felder besetzt sind, berechnet, ob der Tetrisstein sich nach unten bewegen kann.
                if ((fields[0][2] || fields[1][2] || fields[2][2]) && position.getY() == 17) {
                    return false;
                } else if ((fields[0][1] || fields[1][1] || fields[2][1]) && position.getY() == 18) {
                    return false;
                } else if ((fields[0][0] || fields[1][0] || fields[2][0]) && position.getY() == 19) {
                    return false;
                }
                if (fields[0][2]) {
                    if ((int) position.getY() + 3 >= 0) {
                        if (Field.allFields[(int) (position.getX())][(int) position.getY() + 3].isOccupied) {
                            return false;
                        }
                    }
                } else if (fields[0][1]) {
                    if ((int) position.getY() + 2 >= 0) {
                        if (Field.allFields[(int) (position.getX())][(int) position.getY() + 2].isOccupied) {
                            return false;
                        }
                    }
                } else if (fields[0][0]) {
                    if ((int) position.getY() + 1 >= 0) {
                        if (Field.allFields[(int) (position.getX())][(int) position.getY() + 1].isOccupied) {
                            return false;
                        }
                    }
                }
                if (fields[1][2]) {
                    if ((int) position.getY() + 3 >= 0) {
                        if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY() + 3].isOccupied) {
                            return false;
                        }
                    }
                } else if (fields[1][1]) {
                    if ((int) position.getY() + 2 >= 0) {
                        if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY() + 2].isOccupied) {
                            return false;
                        }
                    }
                } else if (fields[1][0]) {
                    if ((int) position.getY() + 1 >= 0) {
                        if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY() + 1].isOccupied) {
                            return false;
                        }
                    }
                }
                if (fields[2][2]) {
                    if ((int) position.getY() + 3 >= 0) {
                        if (Field.allFields[(int) (position.getX() + 2)][(int) position.getY() + 3].isOccupied) {
                            return false;
                        }
                    }
                } else if (fields[2][1]) {
                    if ((int) position.getY() + 2 >= 0) {
                        if (Field.allFields[(int) (position.getX() + 2)][(int) position.getY() + 2].isOccupied) {
                            return false;
                        }
                    }
                } else if (fields[2][0]) {
                    if ((int) position.getY() + 1 >= 0) {
                        if (Field.allFields[(int) (position.getX() + 2)][(int) position.getY() + 1].isOccupied) {
                            return false;
                        }
                    }
                }
                return true;

            } else if (type == StoneTypes.I) {
                if (fields[0][1] && position.getY() == 18) {
                    return false;
                } else if (fields[1][0] && position.getY() == 16) {
                    return false;
                }
                if (fields[1][0]) {
                    if ((int) position.getY() + 4 >= 0) {
                        if (Field.allFields[(int) (position.getX() + 1)][(int) position.getY() + 4].isOccupied) {
                            return false;
                        }
                    }
                }
                if (fields[0][1]) {
                    if ((int) position.getY() + 2 >= 0) {
                        if (Field.allFields[(int) position.getX()][(int) position.getY() + 2].isOccupied) {
                            return false;
                        } else if (Field.allFields[(int) position.getX() + 1][(int) position.getY() + 2].isOccupied) {
                            return false;
                        } else if (Field.allFields[(int) position.getX() + 2][(int) position.getY() + 2].isOccupied) {
                            return false;
                        } else if (Field.allFields[(int) position.getX() + 3][(int) position.getY() + 2].isOccupied) {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                if (position.getY() == 18) {
                    return false;
                }
                if ((int) position.getY() + 2 >= 0) {
                    if (Field.allFields[(int) position.getX()][(int) position.getY() + 2].isOccupied) {
                        return false;
                    } else if (Field.allFields[(int) position.getX() + 1][(int) position.getY() + 2].isOccupied) {
                        return false;
                    }
                }
                return true;
            }
    }

    /** Diese Methode setzt die Felder eines Tetrissteins in der Box oben rechts, wo angezeigt wird, welcher Tetrisstein als nächstes kommt.
     */
    public void setFieldsInExampleBox() {
        if (type != StoneTypes.I && type != StoneTypes.O) {                     //Für den entsprechenden Steintypen mit seiner Größe werden nun die 'fields' durchgegangen und entsprechend wird der Stein in die 'exampleBox' gesetzt.
            for (int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
                    if (fields[i][j]) {
                        Field.exampleFields[i][j].becomeOccupied(color);
                    }
                }
            }
        } else if (type == StoneTypes.I) {
            for (int i=0; i<4; i++) {
                for (int j=0; j<4; j++) {
                    if (fields[i][j]) {
                        Field.exampleFields[i][j].becomeOccupied(color);
                    }
                }
            }
        } else {
            for (int i=0; i<2; i++) {
                for (int j=0; j<2; j++) {
                    if (fields[i][j]) {
                        Field.exampleFields[i][j].becomeOccupied(color);
                    }
                }
            }
        }
    }

    /** Diese Methode entfernt die Felder eines Tetrissteins in der Box oben rechts, wo angezeigt wird, welcher Tetrisstein als nächstes kommt.
     */
    public static void removeFieldsInExampleBox() {
        for (int i=0; i<4; i++) {                               //alle Felder in der 'exampleBox' werden 'isOccupied = false' gesetzt.
            for (int j=0; j<4; j++) {
                Field.exampleFields[i][j].isOccupied = false;
            }
        }
    }

    /** Diese Methode setzt die Felder eines Tetrissteins auf dem Spielfeld, je nachdem, wie die Position und der Typ des Steins ist.
     */
    public void setAllFieldsForPosition() {
        if (isStatic) return;

        if (type != StoneTypes.O && type != StoneTypes.I) {                                //Für den entsprechende Steintyp werden die 'fields' durchgegangen und dementsprechend die richtigen Felder besetzt.
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (fields[i][j]) {
                        if (position.getY() == -2) {
                            if (j == 2) {
                                Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                            }
                        } else if (position.getY() == -1) {
                            if (j > 0) {
                                Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                            }
                        } else if (position.getY() >= 0) {
                            Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                        }
                    }
                }
            }
        } else if (type == StoneTypes.I) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (fields[i][j]) {
                        if (position.getY() == -3) {
                            if (j == 3) {
                                    Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                            }
                        } else if (position.getY() == -2) {
                            if (j >= 2) {
                                    Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                            }
                        } else if (position.getY() == -1) {
                            if (j >= 1) {
                                    Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                            }
                        } else if (position.getY() >= 0) {
                                Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (fields[i][j]) {
                        if (position.getY() == -1) {
                            if (j == 1) {
                                    Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                            }
                        } else if (position.getY() >= 0) {
                                Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].becomeOccupied(color);
                        }
                    }
                }
            }
        }
    }

    /** Diese Methode entfernt die Felder eines Tetrissteins auf dem Spielfeld, je nachdem, wie die Position und der Typ des Steins ist.
     */
    public void clearFieldsForPosition() {
        if (type != StoneTypes.O && type != StoneTypes.I) {                     //Für den entsprechende Steintyp werden die 'fields' durchgegangen und dementsprechend die richtigen Felder wieder zurückgesetzt.
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (fields[i][j]) {
                        if (j + position.getY() >= 0) {
                            Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].isOccupied = false;
                        }
                    }
                }
            }
        } else if (type == StoneTypes.I) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (fields[i][j]) {
                        if (j + position.getY() >= 0) {
                            Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].isOccupied = false;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (fields[i][j]) {
                        if (j + position.getY() >= 0) {
                            Field.allFields[(int) (i + position.getX())][(int) (j + position.getY())].isOccupied = false;
                        }
                    }
                }
            }
        }
    }

    /** Methode, damit sich der Tetrisstein nach unten bewegt.
     */
    public void moveDown() {
        clearFieldsForPosition();
        position = new Point((int) position.getX(), (int) position.getY() + 1);
        setAllFieldsForPosition();
    }

    /** Methode, damit sich der Tetrisstein nach rechts bewegt.
     */
    public void moveRight() {
        clearFieldsForPosition();
        position = new Point((int) position.getX()+1, (int) position.getY());
        setAllFieldsForPosition();
    }

    /** Methode, damit sich der Tetrisstein nach links bewegt.
     */
    public void moveLeft() {
        clearFieldsForPosition();
        position = new Point((int) position.getX()-1, (int) position.getY());
        setAllFieldsForPosition();
    }

    /** Methode, damit sich der Tetrisstein einmal dreht.
     */
    public void rotate() {
        if (type == StoneTypes.L) {                                             //im Nachfolgenden wird für den enstprechenden Steintyp und die entsprechende Rotationsnummer der Stein rotiert, also seine 'fields' werden neu gesetzt und es werden mögliche Kollision überprüft
            if (rotationNumber == 0) {
                clearFieldsForPosition();
                boolean[][] newFields = new boolean[3][3];
                newFields[0][2] = true;
                newFields[0][1] = true;
                newFields[1][1] = true;
                newFields[2][1] = true;

                if (position.getX() > -1) {
                    if (rotateIsPossible(fields, newFields)) {
                        fields = new boolean[3][3];
                        fields[0][2] = true;
                        fields[0][1] = true;
                        fields[1][1] = true;
                        fields[2][1] = true;
                        rotationNumber = 1;
                    }
                }
                setAllFieldsForPosition();
            } else if (rotationNumber == 1) {
                clearFieldsForPosition();
                if (position.getY() < 17) {
                    if (Field.allFields[(int) (position.getX())][(int) (position.getY())].isOccupied && !Field.allFields[(int) (position.getX() + 1)][(int) (position.getY() + 2)].isOccupied && !Field.allFields[(int) (position.getX() + 1)][(int) (position.getY() + 3)].isOccupied
                            || Field.allFields[(int) (position.getX() + 1)][(int) (position.getY() + 2)].isOccupied && !Field.allFields[(int) (position.getX() + 1)][(int) (position.getY() + 2)].isOccupied && !Field.allFields[(int) (position.getX() + 1)][(int) (position.getY() + 3)].isOccupied ) {
                        position = new Point((int) (position.getX()), (int) (position.getY() + 1));
                    }
                }
                if (!Field.allFields[(int) position.getX()][(int) (position.getY())].isOccupied
                        && !Field.allFields[(int) (position.getX() + 1)][(int) (position.getY())].isOccupied
                        && !Field.allFields[(int) position.getX() + 1][(int) (position.getY() + 2)].isOccupied) {
                    fields = new boolean[3][3];
                    fields[0][0] = true;
                    fields[1][0] = true;
                    fields[1][1] = true;
                    fields[1][2] = true;
                    rotationNumber = 2;
                }
                setAllFieldsForPosition();
            } else if (rotationNumber == 2) {
                clearFieldsForPosition();
                if (position.getX() == 8) {
                    if (!Field.allFields[(int) (position.getX() - 1)][(int) (position.getY() + 1)].isOccupied
                            && !Field.allFields[(int) position.getX()][(int) (position.getY() + 1)].isOccupied) {
                        position = new Point((int) (position.getX() - 1), (int) position.getY());
                    }
                } else if(position.getX() != 0) {
                    if ((Field.allFields[(int) (position.getX() + 2)][(int) position.getY()].isOccupied
                        || Field.allFields[(int) (position.getX() + 2)][(int) position.getY() + 1].isOccupied)
                        && !Field.allFields[(int) (position.getX() - 1)][(int) (position.getY() + 1)].isOccupied
                        && !Field.allFields[(int) position.getX()][(int) (position.getY() + 1)].isOccupied) {
                        position = new Point((int) (position.getX() - 1), (int) position.getY());
                    }
                }
                if (!Field.allFields[(int) (position.getX())][(int) (position.getY() + 1)].isOccupied
                        && !Field.allFields[(int) (position.getX() + 2)][(int) position.getY()].isOccupied
                        && !Field.allFields[(int) (position.getX() + 2)][(int) position.getY() + 1].isOccupied) {
                    fields = new boolean[3][3];
                    fields[2][0] = true;
                    fields[2][1] = true;
                    fields[1][1] = true;
                    fields[0][1] = true;
                    rotationNumber = 3;
                }
                setAllFieldsForPosition();
            } else if (rotationNumber == 3) { //not fully optimized
                clearFieldsForPosition();
                boolean[][] newFields = new boolean[3][3];
                newFields[1][0] = true;
                newFields[1][1] = true;
                newFields[1][2] = true;
                newFields[2][2] = true;
                if (position.getY() < 18) {
                    if (rotateIsPossible(fields, newFields)) {
                        fields = new boolean[3][3];
                        fields[1][0] = true;
                        fields[1][1] = true;
                        fields[1][2] = true;
                        fields[2][2] = true;
                        rotationNumber = 0;
                    }
                }
                setAllFieldsForPosition();
            }
        } else if (type == StoneTypes.J) { //not fully optimized
            switch (rotationNumber) {
                case 0:
                    clearFieldsForPosition();
                    boolean[][] newFields = new boolean[3][3];
                    newFields[0][0] = true;
                    newFields[0][1] = true;
                    newFields[1][1] = true;
                    newFields[2][1] = true;
                    if (position.getX() < 8) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[3][3];
                            fields[0][0] = true;
                            fields[0][1] = true;
                            fields[1][1] = true;
                            fields[2][1] = true;
                            rotationNumber = 1;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
                case 1:
                    clearFieldsForPosition();
                    newFields = new boolean[3][3];
                    newFields[2][0] = true;
                    newFields[1][0] = true;
                    newFields[1][1] = true;
                    newFields[1][2] = true;
                    if (position.getY() < 18) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[3][3];
                            fields[2][0] = true;
                            fields[1][0] = true;
                            fields[1][1] = true;
                            fields[1][2] = true;
                            rotationNumber = 2;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
                case 2:
                    clearFieldsForPosition();
                    newFields = new boolean[3][3];
                    newFields[0][1] = true;
                    newFields[1][1] = true;
                    newFields[2][1] = true;
                    newFields[2][2] = true;
                    if (position.getX() > -1) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[3][3];
                            fields[0][1] = true;
                            fields[1][1] = true;
                            fields[2][1] = true;
                            fields[2][2] = true;
                            rotationNumber = 3;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
                case 3:
                    clearFieldsForPosition();
                    newFields = new boolean[3][3];
                    newFields[0][2] = true;
                    newFields[1][2] = true;
                    newFields[1][1] = true;
                    newFields[1][0] = true;
                    if (rotateIsPossible(fields, newFields)) {
                        fields = new boolean[3][3];
                        fields[0][2] = true;
                        fields[1][2] = true;
                        fields[1][1] = true;
                        fields[1][0] = true;
                        rotationNumber = 0;
                    }
                    setAllFieldsForPosition();
                    break;
            }
        } else if (type == StoneTypes.T) { //not fully optimized
            switch (rotationNumber) {
                case 0:
                    clearFieldsForPosition();
                    boolean[][] newFields = new boolean[3][3];
                    newFields[0][0] = true;
                    newFields[0][1] = true;
                    newFields[0][2] = true;
                    newFields[1][1] = true;
                    if (rotateIsPossible(fields, newFields)) {
                        fields = new boolean[3][3];
                        fields[0][0] = true;
                        fields[0][1] = true;
                        fields[0][2] = true;
                        fields[1][1] = true;
                        rotationNumber = 1;
                    }
                    setAllFieldsForPosition();
                    break;
                case 1:
                    clearFieldsForPosition();
                    newFields = new boolean[3][3];
                    newFields[0][0] = true;
                    newFields[1][0] = true;
                    newFields[2][0] = true;
                    newFields[1][1] = true;
                    if (position.getX() < 8) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[3][3];
                            fields[0][0] = true;
                            fields[1][0] = true;
                            fields[2][0] = true;
                            fields[1][1] = true;
                            rotationNumber = 2;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
                case 2:
                    clearFieldsForPosition();
                    newFields = new boolean[3][3];
                    newFields[2][0] = true;
                    newFields[2][1] = true;
                    newFields[2][2] = true;
                    newFields[1][1] = true;
                    if (position.getY() < 18) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[3][3];
                            fields[2][0] = true;
                            fields[2][1] = true;
                            fields[2][2] = true;
                            fields[1][1] = true;
                            rotationNumber = 3;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
                case 3:
                    clearFieldsForPosition();
                    newFields = new boolean[3][3];
                    newFields[0][2] = true;
                    newFields[1][2] = true;
                    newFields[2][2] = true;
                    newFields[1][1] = true;
                    if (position.getX() > -1) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[3][3];
                            fields[0][2] = true;
                            fields[1][2] = true;
                            fields[2][2] = true;
                            fields[1][1] = true;
                            rotationNumber = 0;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
            }
        } else if (type == StoneTypes.I) {
            switch (rotationNumber) {
                case 0:
                    clearFieldsForPosition();
                    boolean[][] newFields = new boolean[4][4];
                    newFields[0][1] = true;
                    newFields[1][1] = true;
                    newFields[2][1] = true;
                    newFields[3][1] = true;
                    if (position.getX() > -1 && position.getX() < 7) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[4][4];
                            fields[0][1] = true;
                            fields[1][1] = true;
                            fields[2][1] = true;
                            fields[3][1] = true;
                            rotationNumber = 1;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
                case 1:
                    clearFieldsForPosition();
                    newFields = new boolean[4][4];
                    newFields[1][0] = true;
                    newFields[1][1] = true;
                    newFields[1][2] = true;
                    newFields[1][3] = true;
                    if (position.getY() < 17) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[4][4];
                            fields[1][0] = true;
                            fields[1][1] = true;
                            fields[1][2] = true;
                            fields[1][3] = true;
                            rotationNumber = 0;
                        }
                    }
                    setAllFieldsForPosition();
            }
        } else if (type == StoneTypes.Z) {
            switch (rotationNumber) {
                case 0:
                    clearFieldsForPosition();
                    boolean[][] newFields = new boolean[3][3];
                    newFields[1][0] = true;
                    newFields[1][1] = true;
                    newFields[0][1] = true;
                    newFields[0][2] = true;
                    if (rotateIsPossible(fields, newFields)) {
                        fields = new boolean[3][3];
                        fields[1][0] = true;
                        fields[1][1] = true;
                        fields[0][1] = true;
                        fields[0][2] = true;
                        rotationNumber = 1;
                    }
                    setAllFieldsForPosition();
                    break;
                case 1:
                    clearFieldsForPosition();
                    newFields = new boolean[3][3];
                    newFields[0][1] = true;
                    newFields[1][1] = true;
                    newFields[1][2] = true;
                    newFields[2][2] = true;
                    if (position.getX() < 8) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[3][3];
                            fields[0][1] = true;
                            fields[1][1] = true;
                            fields[1][2] = true;
                            fields[2][2] = true;
                            rotationNumber = 0;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
            }
        } else if (type == StoneTypes.Z2) {
            switch (rotationNumber) {
                case 0:
                    clearFieldsForPosition();
                    boolean[][] newFields = new boolean[3][3];
                    newFields[1][0] = true;
                    newFields[1][1] = true;
                    newFields[2][1] = true;
                    newFields[2][2] = true;
                    if (rotateIsPossible(fields, newFields)) {
                        fields = new boolean[3][3];
                        fields[1][0] = true;
                        fields[1][1] = true;
                        fields[2][1] = true;
                        fields[2][2] = true;
                        rotationNumber = 1;
                    }
                    setAllFieldsForPosition();
                    break;
                case 1:
                    clearFieldsForPosition();
                    newFields = new boolean[3][3];
                    newFields[0][2] = true;
                    newFields[1][1] = true;
                    newFields[1][2] = true;
                    newFields[2][1] = true;
                    if (position.getX() > -1) {
                        if (rotateIsPossible(fields, newFields)) {
                            fields = new boolean[3][3];
                            fields[0][2] = true;
                            fields[1][1] = true;
                            fields[1][2] = true;
                            fields[2][1] = true;
                            rotationNumber = 0;
                        }
                    }
                    setAllFieldsForPosition();
                    break;
            }
        }
    }

    /**
     * Diese Methode prüft, ob es für den Stein möglich ist sich in seinem jetzigen Zustand einmal zu drehen.
     * @param oldFields Die Felder, die der entsprechende Stein vor der Rotation hatte.
     * @param newFields Die Felder, die der entsprechende Stein nach der Rotation haben würde.
     * @return Gibt 'true' zurück, wenn möglich, 'false', wenn nicht möglich.
     */
    private boolean rotateIsPossible(boolean[][] oldFields, boolean[][] newFields) {

            if (type != StoneTypes.I) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (oldFields[i][j] != newFields[i][j]) {
                            if (Field.allFields[(int) (position.getX() + i)][(int) (position.getY() + j)].isOccupied) {
                                return false;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (oldFields[i][j] != newFields[i][j]) {
                            if (Field.allFields[(int) (position.getX() + i)][(int) (position.getY() + j)].isOccupied) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;

    }

    /** Legt alle möglichen Positionen des Tetrissteins fest. (Für die KI)
     */
    public void setAllPossibleEndingLocations() {
        for (int i = 0; i < 5; i++) {  //Tetrisstein wird in die richtige Position gebracht.
            if (canMoveDown()) {
                moveDown();
            }
        }
        Point startPosition = position.getLocation();           //Startposition wird zwischengespeichert
        for (int r=0; r<4; r++) {     //folgende Anweisen werden 4 mal, für jede Rotation, wiederholt

                position = startPosition.getLocation();     //der Stein wird an die Startposition gesetzt
                try {
                    rotate(); //der Stein wird wenn möglich rotiert
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    continue;
                }

            if (fields[0][0] || fields[0][1] || fields[0][2]) {
                for (int x = 0; x < 10; x++) {  //jede mögliche x-Position für den Tetrisstein wird durchgegangen

                    while (position.getX() != x) {      //Tetrisstein wird an die X-Koordinate positioniert
                        if (x > position.getX()) {
                            if (canMoveRight()) {
                                moveRight();
                            } else {
                                break;
                            }
                        } else if (x < position.getX()) {
                            if (canMoveLeft()) {
                                moveLeft();
                            } else break;
                        }
                    }

                    while (canMoveDown()) {         //Nach Positionierung bewegt er sich nach Unten zu seiner finalen Position
                        moveDown();
                    }

                    double[] posAndRot = new double[3];            //seine Endposition wird in die Variable 'allPossibleEndingLocations' gespeichert.
                    posAndRot[0] = position.getX(); //X-Koordinate wird gespeichert
                    posAndRot[1] = rotationNumber; //Rotation wird gespeichert
                    posAndRot[2] = getFitness(); //Fitness, also wie gut die Position ist, wird gespeichert.
                    allPossibleEndingLocations.add(posAndRot);

                    if (allPossibleEndingLocations.size() > 1) {    //wenn zwei mal die genau gleichen 'moves' gefunden wurden, kann man die Schleife unterbrechen
                        if (allPossibleEndingLocations.get(allPossibleEndingLocations.size() - 2)[0] == (allPossibleEndingLocations.get(allPossibleEndingLocations.size() - 1)[0])) {
                            allPossibleEndingLocations.remove(allPossibleEndingLocations.size()-1);
                            clearFieldsForPosition();
                            break;
                        }
                    }

                    clearFieldsForPosition();

                    position = startPosition.getLocation(); //Position wird auf Startpostion gesetzt

                    setAllFieldsForPosition();
                }
            } else {        //siehe oben
                for (int x = -1; x < 10; x++) {

                    while (position.getX() != x) {
                        if (x > position.getX()) {
                            if (canMoveRight()) {
                                moveRight();
                            } else {
                                break;
                            }
                        } else if (x < position.getX()) {
                            if (canMoveLeft()) {
                                moveLeft();
                            } else break;
                        }
                    }

                    while (canMoveDown()) {
                        moveDown();
                    }

                    double[] posAndRot = new double[3];
                    posAndRot[0] = position.getX();
                    posAndRot[1] = rotationNumber;
                    posAndRot[2] = getFitness();
                    allPossibleEndingLocations.add(posAndRot);

                    if (allPossibleEndingLocations.size() > 1) {
                        if (allPossibleEndingLocations.get(allPossibleEndingLocations.size() - 2)[0] == (allPossibleEndingLocations.get(allPossibleEndingLocations.size() - 1)[0])) {
                            allPossibleEndingLocations.remove(allPossibleEndingLocations.size()-1);
                            clearFieldsForPosition();
                            break;
                        }
                    }

                    clearFieldsForPosition();

                    position = startPosition.getLocation();

                    setAllFieldsForPosition();
                }
            }
        }

        position = new Point(3, -4); //Standardposition jedes Tetrisstein wird erneut gesetzt.


    }

    /** Ermittelt für eine Liste, die Tetrissteinmoves nach einem bestimmte Schema speichert (X-Koordinate, Rotation, Fitness), die besten drei Moves.
     * @param list Die enstprechende Liste, die die Tetrissteinmoves speichert.
     * @return  Gibt die drei besten Moves zurück.
     */
    public ArrayList<double[]> filterListForThreeBestMoves(ArrayList<double[]> list) {
        ArrayList<double[]> threeBestMoves = new ArrayList<>();

        for (int i=0; i<list.size(); i++) {
            if (threeBestMoves.size() < 3) {
                threeBestMoves.add(list.get(i));
            } else {
                double[] worstMove;
                if (threeBestMoves.get(0)[2] < threeBestMoves.get(1)[2] && threeBestMoves.get(0)[2] < threeBestMoves.get(2)[2]) {
                    worstMove = threeBestMoves.get(0);
                } else if (threeBestMoves.get(1)[2] < threeBestMoves.get(0)[2] && threeBestMoves.get(1)[2] < threeBestMoves.get(2)[2]) {
                    worstMove = threeBestMoves.get(1);
                } else {
                    worstMove = threeBestMoves.get(2);
                }
                if ((threeBestMoves.get(0)[0] != list.get(i)[0]
                        || threeBestMoves.get(0)[1] != list.get(i)[1]
                        || threeBestMoves.get(0)[2] != list.get(i)[2]) &&
                        (threeBestMoves.get(1)[0] != list.get(i)[0]
                                || threeBestMoves.get(1)[1] != list.get(i)[1]
                                || threeBestMoves.get(1)[2] != list.get(i)[2]) &&
                        (threeBestMoves.get(2)[0] != list.get(i)[0]
                                || threeBestMoves.get(2)[1] != list.get(i)[1]
                                || threeBestMoves.get(2)[2] != list.get(i)[2])) {
                    if (list.get(i)[2] > worstMove[2]) {
                        threeBestMoves.remove(worstMove);
                        threeBestMoves.add(list.get(i));
                    }
                }

            }
        }

        return threeBestMoves;
    }


    /** Hier wird nun der beste Move für einen Tetrisstein geprüft, unter Betrachtung des Tetrisstein, der als nächstest kommt.
     * @param nextStone Der nächste Tetrisstein wird hier übergeben.
     * @return Der beste Tetrismove wird zurückgegeben.
     */
    public double[] getBestMoveByCheckingTwoStones(TetrisStone nextStone) {

        System.out.println("Started searching for best move! Current stone: "+type.toString());

        beingCalculated = true;             //Stein wird berechnet...
        nextStone.beingCalculated = true;

        double currentBestFitness = -1000;      //Beste Fitness wird auf -1000 gesetzt (in der Hoffnug das es mindestens eine Tetrisposition gibt, die diese Fitness übertrifft).
        double[] currentBestMove = new double[2];

        clearFieldsForPosition();               //Tetrisstein wird in Position gebracht
        position = new Point(3, -3);
        setAllFieldsForPosition();

        setAllPossibleEndingLocations();
        allPossibleEndingLocations = new ArrayList<>(filterListForThreeBestMoves(allPossibleEndingLocations));  //besten drei Moves werden gspeichert
        System.out.println("All possible moves for current stone: ");
        allPossibleEndingLocations.forEach(d -> System.out.print(d[0] + "|" + d[1] + " ; "));

        for (int i=0; i<allPossibleEndingLocations.size(); i++) {  //die besten Position für Stein Nr.1 werden durchgegangen
            position = new Point(3, 0);             //Stein Nr. 1 wird in Position gebracht

            setAllFieldsForPosition();

            while (rotationNumber != allPossibleEndingLocations.get(i)[1]) {
                rotate();
            }

            while (position.getX() != allPossibleEndingLocations.get(i)[0]) {
                if (position.getX() < allPossibleEndingLocations.get(i)[0]) {
                    if (canMoveRight()) {
                        moveRight();
                    }
                } else if (position.getX() > allPossibleEndingLocations.get(i)[0]) {
                    if (canMoveLeft()) {
                        moveLeft();
                    }
                }
            }


            while (canMoveDown()) {
                moveDown();
            }

            System.out.println();
            System.out.println("Next Stone is calculated for current Stone on position "+position.getX()+"|"+position.getY());
            nextStone.position = new Point(3, 0);
            nextStone.setAllFieldsForPosition();

            nextStone.setAllPossibleEndingLocations();


            for (int k=0; k<nextStone.allPossibleEndingLocations.size(); k++) {  //nun werden alle Moves für Stein Nr. 2  berechnet und die besten gespeichert

                nextStone.position = new Point(3, 0);
                nextStone.setAllFieldsForPosition();

                while (nextStone.rotationNumber != nextStone.allPossibleEndingLocations.get(k)[1]) {
                    nextStone.rotate();
                }

                while (nextStone.position.getX() != nextStone.allPossibleEndingLocations.get(k)[0]) {
                    if (nextStone.position.getX() < nextStone.allPossibleEndingLocations.get(k)[0]) {
                        if (nextStone.canMoveRight()) {
                            nextStone.moveRight();
                        }
                    } else if (nextStone.position.getX() > nextStone.allPossibleEndingLocations.get(k)[0]) {
                        if (nextStone.canMoveLeft()) {
                            nextStone.moveLeft();
                        }
                    }
                }

                while (nextStone.canMoveDown()) {
                    nextStone.moveDown();
                }

                if (currentBestFitness < nextStone.getFitness()) {
                    currentBestFitness = nextStone.getFitness();
                    currentBestMove = new double[2];
                    currentBestMove[0] = position.getX();
                    currentBestMove[1] = rotationNumber;
                    System.out.println("Current Best Move: "+currentBestMove[0]+"|"+currentBestMove[1] + " Fitness: "+getFitness());
                }

                nextStone.clearFieldsForPosition();
            }


            nextStone.clearFieldsForPosition();

            clearFieldsForPosition();

        }

        nextStone.position = new Point(3, -4);

        System.out.println(":"+currentBestMove[0]+"|"+currentBestMove[1]);

        position = new Point(3, -2);

        beingCalculated = false;
        nextStone.beingCalculated = false;

        return currentBestMove;

    }

    /** Es wird bewertet, wie gut eine bestimmte Position ist. (Für die KI)
     * @return Umso besser die Position, umso höher der Rückgabewert.
     */
    public double getFitness() {
        double aggregateHeight = 0;
        int completeLines = 0;
        int holes = 0;
        int bumpiness = 0;

        int[] heights = new int[10];  //aggregateHeight wird gesetzt...
        for(int i=0; i<10; i++) {
            for (int k=0; k<20; k++) {
                if (Field.allFields[i][k].isOccupied ) {
                    heights[i] = 20 - k;
                    break;
                }
            }
        }
        aggregateHeight = Arrays.stream(heights).average().orElse(Double.NaN);

        for (int i=0; i<20; i++) {  //completeLines wird gesetzt...
            if (isRowFull(i)) {
                completeLines++;
            }
        }


        for(int c = 0; c < 10; c++){    //holes wird gesetzt...
            boolean block = false;
            for(int r = 0; r < 20; r++){
                if (Field.allFields[c][r].isOccupied) {
                    block = true;
                }else if (!Field.allFields[c][r].isOccupied && block){
                    holes++;
                }
            }
        }

        for (int i=0; i<9; i++) { //bumpiness wird gesetzt...
            bumpiness += Math.abs(heights[i] - heights[i+1]);
        }

        double v = (-0.510066 * aggregateHeight) + (0.760666 * completeLines) + (-1 * holes) + (-0.184483 * bumpiness); //Fitness wird berechnet

        return v;
    }

    /** Es wird geprüft, ob eine Reihe voll ist. (Für die KI)
     * @param row Welche Reihe soll geprüft werden.
     * @return Gibt 'true' zurück, wenn die Reihe voll ist, 'false', wenn nicht.
     */
    public boolean isRowFull(int row) {
        for (int i=0; i<10; i++) {
            if (!Field.allFields[i][row].isOccupied) {
                return false;
            }
        }
        return true;
    }


    /** Diese Methode ist wichtig für den GameLoop, denn sie wird regelmäßig immer wieder aufgerufen, um den Tetrisstein 'upzudaten'.
     */
    public void update() throws InterruptedException {
        if (GameManager.computerIsPlaying) { //Wenn die KI spielt...
            if (!alreadyCalculated ) { //Stein wird berechnet...
                double[] bestMove = getBestMoveByCheckingTwoStones(GameManager.nextMovingStone);
                alreadyCalculated = true;
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(25);
                    for (int k = 0; k< GameManager.prevStone.size(); k++) {
                        GameManager.prevStone.get(k).setAllFieldsForPosition();
                    }

                    if (canMoveDown()) {
                        moveDown();
                    } else {
                        isStatic = true;
                    }
                }
                if (!isStatic) {
                    while (rotationNumber != bestMove[1]) {
                        rotate();
                    }
                    while (position.getX() != bestMove[0]) {
                        if (bestMove[0] > position.getX()) {
                            Thread.sleep(25);
                            moveRight();
                        } else if (bestMove[0] < position.getX()) {
                            Thread.sleep(25);
                            moveLeft();
                        }
                    }
                }
            }
        }
        if (!isStatic) { //Wenn der Stein noch nicht fest ist...
            if (canMoveDown()) { //er bewegt sich solange er kann nach unten
                moveDown();
                setAllFieldsForPosition();
            } else {
                setAllFieldsForPosition();
                isStatic = true; //wenn er sich nicht nach unten bewegen kann, wird 'isStatic=true' gesetzt
            }
        }
    }
}
