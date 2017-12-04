package gameenvironment;

import resources.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Pierre on 2016-12-02.
 */
public abstract class GameElement extends Rectangle {

    private static int elementSize = 40;
    private Position pos;
    private BufferedImage elementImage;
    private ImageLoader il = new ImageLoader();


    public GameElement(Position pos) {
        this.pos = pos;
    }

    public static int getElementSize() {
        return elementSize;
    }

    public static void setElementSize(int elementSize) {
        GameElement.elementSize = elementSize;
    }

    public Position get_Position() {
        return pos;
    }

    public void set_Position(Position pos) {
        this.pos = pos;
    }

    public BufferedImage getElementImage() {
        return elementImage;
    }

    public void setElementImage(String imagePath) {
        this.elementImage = il.loadImage(imagePath);
    }

    public abstract void draw(Graphics g);

}
