package gamecomponents.troops;

import gamecomponents.LandOnReceiver;
import gameenvironment.GameElement;
import gameenvironment.Level;
import gameenvironment.Position;

import java.awt.*;

/**
 * Created by Pierre on 2016-11-30.
 */
public class Troop extends GameElement {

    private Level level;
    private LandOnReceiver receiver;

    private Direction direction;

    private int speed;
    private double health;
    private HealthBar healthBar;
    private boolean isAlive = true;

    private int upperBoundary;
    private int lowerBoundary;
    private int rightBoundary;
    private int leftBoundary;

    public Troop(Level level, LandOnReceiver receiver,
                 Position pos, int health, int speed) {
        super(pos);
        this.level = level;
        this.receiver = receiver;
        this.health = health;
        healthBar = new HealthBar(getElementSize() - 5, 5, health);
        this.speed = speed;
        direction = Direction.EAST;
    }

    public LandOnReceiver getReceiver() {
        return receiver;
    }

    public void setMovingBoundaries(){
        upperBoundary = (get_Position().getY() - 1)/getElementSize();
        rightBoundary = (get_Position().getX() + getElementSize() + 1)/getElementSize();
        leftBoundary = (get_Position().getX() - 1)/getElementSize();
        lowerBoundary = (get_Position().getY() + getElementSize() + 1)/getElementSize();
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(){
        setMovingBoundaries();
        //if-satsen kollar så gubben inte hamnar utanför mappen.
        if(!(rightBoundary == level.getLevelMatrix().length) && !(lowerBoundary == level.getLevelMatrix()[0].length)){
            switch(direction){
                case NORTH:
                    makeMoveWhenFacingNorth();
                    break;
                case EAST:
                    makeMoveWhenFacingEast();
                    break;
                case SOUTH:
                    makeMoveWhenFacingSouth();
                    break;
                case WEST:
                    makeMoveWhenFacingWest();
                    break;
            }
            setBounds(get_Position().getX(), get_Position().getY(), getElementSize(), getElementSize());
            receiver.notifyObserver(this);
        }
    }

    public void makeMoveWhenFacingNorth(){
        if(northIsPossible()){
            moveNorth();
        } else {
            if(!(get_Position().getY()%getElementSize() == 0)){
                get_Position().setY(((get_Position().getY() / getElementSize()) * getElementSize()) + getElementSize());
            }
            if(eastIsPossible()){
                moveEast();
            }else if(westIsPossible()){
                moveWest();
            }else if(southIsPossible()){
                moveSouth();
            }
        }
    }

    public void makeMoveWhenFacingEast(){
        if(eastIsPossible()){
            moveEast();
        }else {
            if (!(get_Position().getX()%getElementSize() == 0)) {
                get_Position().setX(((get_Position().getX() + speed) / getElementSize()) * getElementSize());
            }
            if(northIsPossible()){
                moveNorth();
            }else if(southIsPossible()){
                moveSouth();
            }else if(westIsPossible()){
                moveWest();
            }
        }
    }

    public void makeMoveWhenFacingSouth(){
        if(southIsPossible()){
            moveSouth();
        }else {
            if (!(get_Position().getY()%getElementSize() == 0)) {
                get_Position().setY(((get_Position().getY() + speed) / getElementSize()) * getElementSize());
            }
            if(eastIsPossible()){
                moveEast();
            }else if(westIsPossible()){
                moveWest();
            }else if(northIsPossible()){
                moveNorth();
            }
        }
    }

    public void makeMoveWhenFacingWest(){
        if(westIsPossible()){
            moveWest();
        }else{
            if (!(get_Position().getX()%getElementSize() == 0)) {
                get_Position().setX(((get_Position().getX() / getElementSize()) * getElementSize()) + getElementSize());
            }
            if(northIsPossible()){
                moveNorth();
            }else if(southIsPossible()){
                moveSouth();
            }else if(eastIsPossible()){
                moveEast();
            }
        }
    }

    private boolean northIsPossible(){
        //System.out.println("x = " + get_Position().getX() + " y = " + get_Position().getY());
        try{
            return level.getLevelMatrix()[(get_Position().getX()+ speed)/getElementSize()][upperBoundary].isWalkable();
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    private boolean eastIsPossible(){
        //System.out.println("x = " + get_Position().getX() + " y = " + get_Position().getY());
        try{
            return level.getLevelMatrix()[rightBoundary][(get_Position().getY()+ speed)/getElementSize()].isWalkable();
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    private boolean southIsPossible(){
        //System.out.println("x = " + get_Position().getX() + " y = " + get_Position().getY());
        try{
            return level.getLevelMatrix()[(get_Position().getX()+ speed)/getElementSize()][lowerBoundary].isWalkable();
        }catch (IndexOutOfBoundsException e){
            System.out.println("i catch-satsen");
            return false;
        }
    }

    private boolean westIsPossible(){
        //System.out.println("x = " + get_Position().getX() + " y = " + get_Position().getY());
        try{
            return level.getLevelMatrix()[leftBoundary][(get_Position().getY()+ speed)/getElementSize()].isWalkable();
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    private void moveNorth(){
        get_Position().setY(get_Position().getY() - speed);
        direction = Direction.NORTH;
    }

    private void moveEast(){
        get_Position().setX(get_Position().getX() + speed);
        direction = Direction.EAST;
    }

    private void moveSouth(){
        get_Position().setY(get_Position().getY() + speed);
        direction = Direction.SOUTH;
    }

    private void moveWest(){
        get_Position().setX(get_Position().getX() - speed);
        direction = Direction.WEST;
    }

    private void moveToBoundary(){
        get_Position().setX(get_Position().getX());
    }

    public void draw(Graphics g){
        if(health > 0){
            g.drawImage(getElementImage(), get_Position().getX(), get_Position().getY(), getElementSize(), getElementSize(), null);
            healthBar.drawHealthBar(g, get_Position().getX(), get_Position().getY(), health);
        }
    }

    public void reduceHealth(int damage){
        health = health - damage;
        if(health <= 0){
            isAlive = false;
        }
    }
}
