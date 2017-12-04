package gameenvironment.environment;

import gameenvironment.Position;

import java.awt.*;
import java.util.Random;

/**
 * Created by Pierre on 2016-11-28.
 */
public class Snow extends Area {

    private static final String SNOW_PATH = "snow.png";
    private static final String TREE_PATH = "christmastree.png";
    private static final String MOUNTAIN_PATH = "mountain.png";
    private static final int SNOW_PROBABILITY = 80;
    private static final int TREE_PROBABILITY = 97;
    private static final int MOUNTAIN_PROBABILITY = 100;
    private static final int TOTAL_PROBABILITY = 100;


    public Snow(Position pos) {
        super(pos);
        generateTerrain();
    }

    private void generateTerrain(){
        Random random = new Random();
        int terrain = random.nextInt(TOTAL_PROBABILITY);
        if(terrain < SNOW_PROBABILITY){
            setElementImage(SNOW_PATH);
        }else if(terrain < TREE_PROBABILITY){
            setElementImage(TREE_PATH);
        }else if(terrain < MOUNTAIN_PROBABILITY){
            setElementImage(MOUNTAIN_PATH);
        }
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
