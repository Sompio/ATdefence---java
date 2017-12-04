package gamecomponents.towers;

import gameenvironment.GameElement;
import gamecomponents.LandOnReceiver;
import gamecomponents.troops.Troop;
import gameenvironment.Position;

import java.awt.*;

/**
 * Created by Pierre on 2016-12-02.
 */

/**
 * The class Tower fires when a troop is in range of fire. Tower implements the
 * interface TroopObserver to be notified when a troop is present.
 */

public class Tower extends GameElement implements TroopObserver {

    private LandOnReceiver receiver;
    private int rangeWidth;
    private int rangeHeight;
    private Rectangle fireArea;
    private static final String IMAGE_PATH = "Tower.png";

    private int damage;

    private int coolDown;
    private boolean fireIsPossible = false;

    private int targetX;
    private int targetY;

    private int renderFire = 0;
    private static final int RENDERING_TIME = 5;

    private static final int FIRE_SHOT = 0;
    private int timeToFire = 0;

    /**
     * The constructor initializes all parameters.
     * @param receiver - notifies tower when troop is present.
     * @param pos - Position of tower.
     * @param rangeWidth
     * @param rangeHeight
     * @param damage - done by the tower.
     * @param coolDown - before next fire is possible.
     */

    public Tower(LandOnReceiver receiver, Position pos, int damage, int coolDown, int rangeWidth,
                 int rangeHeight){
        super(pos);
        this.receiver = receiver;
        receiver.addObserver(this);
        this.rangeWidth = rangeWidth;
        this.rangeHeight = rangeHeight;
        this.damage = damage;
        this.coolDown = coolDown;
        setElementImage(IMAGE_PATH);
        setRangeOfFire();
    }

    /**
     * Method setRangeOfFire sets the bounds of the rectangle that represents
     * the towers range of fire.
     */

    private void setRangeOfFire(){
        int xCord = (rangeWidth - getElementSize()) / 2;
        xCord = get_Position().getX() - xCord;
        System.out.println("x = "  + xCord + " x2 = " + get_Position().getX());
        int yCord = (rangeWidth - getElementSize()) / 2;
        yCord = get_Position().getY() - yCord;
        System.out.println("rangeWidth = " + rangeWidth);
        fireArea = new Rectangle(xCord, yCord, rangeWidth, rangeHeight);
        fireArea.setBounds(fireArea);
    }


    public void fire(Troop troop, Graphics g) {
        if(enemyInRangeOfFire(troop) && checkIfNoCoolDown()){
            targetX = troop.get_Position().getX() + (getElementSize()/2);
            targetY = troop.get_Position().getY() + (getElementSize()/2);
            fireIsPossible = true;
            troop.reduceHealth(damage);
            timeToFire = coolDown;
        }
    }

    /**
     * Method enemyInRangeOfFire checks if troop intersects with the towers
     * range of fire.
     * @param troop
     * @return true if troop is in range otherwise false.
     */

    private boolean enemyInRangeOfFire(Troop troop){
        if(troop.intersects(fireArea)){
        }
        return troop.intersects(fireArea);
    }

    /**
     * Method checkIfNoCoolDown - if no cooldown the tower is ready to fire and
     * true is returned. Otherwise cooldown is decreased and false is returned.
     * @return true or false.
     */

    private boolean checkIfNoCoolDown(){
        if(timeToFire <= FIRE_SHOT){
            return true;
        }else{
            fireIsPossible = false;
            return false;
        }
    }

    /**
     * Method drawFire
     * @param g Graphics object.
     */

    private void drawFire(Graphics g){
        /*Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(get_Position().getX(), get_Position().getY(), targetX, targetY);*/
        g.setColor(Color.RED);
        g.drawLine(get_Position().getX()+getElementSize()/2, get_Position().getY() + getElementSize()/2, targetX, targetY);
        fireIsPossible = false;
    }

    /**
     * Method draw - draws the layout of tower and fire if possible.
     * Method Overrides the inherited draw method from GameElement.
     * @param g Graphics object.
     */

    @Override
    public void draw(Graphics g) {
        g.drawImage(getElementImage(), get_Position().getX(),
                get_Position().getY(), null);

        //ritar ut rangen om man vill se den för testning
/*
        g.drawRect( get_Position().getX() - ((rangeWidth - getElementSize()) / 2), get_Position().getY()-((rangeWidth -
                getElementSize()) / 2), rangeWidth, rangeHeight);*/
        if(fireIsPossible || renderFire > 0){
            drawFire(g);
            renderFire--;
        }
        timeToFire--;
    }

    /**
     * Method landOn checks if troop is in range of fire and no cooldown.
     * If so is the case the parameters for the position of the target is set
     * and the damage is reduced from the troop's health.
     * @param troop
     */

    @Override
    public void landOn(Troop troop) {
        if(enemyInRangeOfFire(troop) && checkIfNoCoolDown()){
            targetX = troop.get_Position().getX() + (getElementSize()/2);
            targetY = troop.get_Position().getY() + (getElementSize()/2);
            fireIsPossible = true;
            renderFire = RENDERING_TIME;
            troop.reduceHealth(damage);
            timeToFire = coolDown;
        }
    }
}
