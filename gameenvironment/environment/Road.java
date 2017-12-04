package gameenvironment.environment;

import gameenvironment.Position;

import java.awt.*;

/**
 * Created by Pierre on 2016-11-28.
 */
public class Road extends Area {

    private static final String IMAGE_PATH = "road.png";

    public Road(Position pos) {
        super(pos);
        setElementImage(IMAGE_PATH);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getElementImage(), get_Position().getX(),
                get_Position().getY(), getElementSize(), getElementSize(), null);
    }

    @Override
    public boolean isWalkable() {
        return true;
    }
}
