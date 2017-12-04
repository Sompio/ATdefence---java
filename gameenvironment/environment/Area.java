package gameenvironment.environment;

import gameenvironment.GameElement;
import gameenvironment.Position;

import java.awt.*;

/**
 * Created by Pierre on 2016-11-28.
 */
public abstract class Area extends GameElement {

    public Area(Position pos){
        super(pos);
        setBounds(pos.getX(), pos.getY(), getElementSize(), getElementSize());
    }

    public abstract void draw(Graphics g);

    public abstract boolean isWalkable();

}
