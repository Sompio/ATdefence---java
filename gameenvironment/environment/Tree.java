package gameenvironment.environment;

import gameenvironment.Position;

import java.awt.*;

/**
 * Created by Pierre on 2016-12-16.
 */
public class Tree extends Area {

    private static final String imagePath = "christmastree.png";

    public Tree(Position pos) {
        super(pos);
        setElementImage(imagePath);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getElementImage(), get_Position().getX(),
                get_Position().getY(), getElementSize(),
                getElementSize(), null);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

}

