package gameenvironment.environment;

import gamecomponents.LandOnReceiver;
import gamecomponents.towers.TroopObserver;
import gamecomponents.troops.Direction;
import gameenvironment.Position;
import resources.ImageLoader;
import gamecomponents.troops.Troop;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by Pierre on 2016-12-07.
 */
public class Crossroad extends Area implements TroopObserver {

    private LinkedList<Direction> possiblePaths = new LinkedList<>();
    private Direction currentPath;

    private ImageLoader il = new ImageLoader();
    private static final String imagePath = "road.png";
    private BufferedImage north;
    private BufferedImage east;
    private BufferedImage south;
    private BufferedImage west;

    private ArrayList<Troop> visited = new ArrayList<>();
    private Rectangle sensor1;
    private Rectangle sensor2;
    private static final int SENSOR_SIZE = 5;

    public Crossroad(Position pos){
        super(pos);
        setBounds(x, y, getElementSize(), getElementSize());
        setElementImage(imagePath);
        north = il.loadImage("north.png");
        east = il.loadImage("east.png");
        south = il.loadImage("south.png");
        west = il.loadImage("west.png");
        setSensors();
    }

    private void setSensors(){
        int sensor2X = get_Position().getX() + getElementSize() - SENSOR_SIZE;
        int sensor2Y = get_Position().getY() + getElementSize() - SENSOR_SIZE;
        sensor1 = new Rectangle(get_Position().getX(), get_Position().getY(), SENSOR_SIZE, SENSOR_SIZE);
        sensor1.setBounds(get_Position().getX(), get_Position().getY(), SENSOR_SIZE, SENSOR_SIZE);
        sensor2 = new Rectangle(sensor2X, sensor2Y, SENSOR_SIZE, SENSOR_SIZE);
        sensor2.setBounds(sensor2X, sensor2Y, SENSOR_SIZE, SENSOR_SIZE);
    }

    public void addDirection(Direction d) {
        possiblePaths.addLast(d);
        currentPath = d;
    }

    public void changeDirection(Position pos){
        if(contains(pos.getX(), pos.getY())){
            currentPath = possiblePaths.getFirst();
            possiblePaths.addLast(possiblePaths.removeFirst());
        }
    }

    public void addAsObserver(LandOnReceiver receiver){
        receiver.addObserver(this);
    }

    @Override
    public void landOn(Troop troop) {
        if(troop.intersects(sensor1) && troop.intersects(sensor2)){
            if(visited.contains(troop)){
                return;
            }else {
                troop.get_Position().setY(get_Position().getY());
                troop.get_Position().setX(get_Position().getX());
                troop.setDirection(currentPath);
            }
            visited.add(troop);
        }else if(visited.contains(troop)){
            visited.remove(troop);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getElementImage(), get_Position().getX(),
                get_Position().getY(), getElementSize(), getElementSize(), null);
        switch (currentPath) {
            case NORTH:
                g.drawImage(north, get_Position().getX(), get_Position().getY(), getElementSize(), getElementSize(), null);
                break;
            case EAST:
                g.drawImage(east, get_Position().getX(), get_Position().getY(), getElementSize(), getElementSize(), null);
                break;
            case SOUTH:
                g.drawImage(south, get_Position().getX(), get_Position().getY(), getElementSize(), getElementSize(), null);
                break;
            case WEST:
                g.drawImage(west, get_Position().getX(), get_Position().getY(), getElementSize(), getElementSize(), null);
                break;
        }
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

}
