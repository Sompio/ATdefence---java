package gamecomponents.troops;

import gameenvironment.GameElement;
import gamecomponents.LandOnReceiver;
import gamecomponents.towers.TroopObserver;
import gameenvironment.Position;

import java.awt.*;

/**
 * Created by Pierre on 2016-12-05.
 */

/**
 * The class Teleport is used by the TeleportTroop. One entrance and one exit
 * is dropped that allows regular Troops to be teleported between the two
 * locations.
 */

public class Teleport extends GameElement implements TroopObserver {

    private Position teleportToPos;
    private int passedTroups = 0;
    private static final int ACCEPTED_TROUPS = 5;
    private static final String imagePath = "teleport.png";
    private Direction direction;

    /**
     * Constructor adds teleporter as a TroopObserver
     * and initializes attributes. Sets the bounds of the teleport entrance.
     * @param pos - Position of entrance.
     * @param receiver - notifies teleport when troop is present.
     */

    public Teleport(Position pos, LandOnReceiver receiver){
        super(pos);
        receiver.addObserver(this);
        setBounds(pos.getX(), pos.getY(), getElementSize(), getElementSize());
        teleportToPos = new Position(-1, -1);
        setElementImage(imagePath);
    }

    /**
     * Method dropExit - drops the exit at the position of TeleportTroop.
     * @param pos
     * @param direction
     */

    public void dropExit(Position pos, Direction direction){
        teleportToPos.setX(pos.getX());
        teleportToPos.setY(pos.getY());
        this.direction = direction;
    }

    /**
     * Method getPassedTroups - returns the number of troops that has passed
     * the teleport.
     * @return passedTroups
     */

    public int getPassedTroups() {
        return passedTroups;
    }

    /**
     * Method getAcceptedTroups - returns the number of troops that the teleport
     * lets through.
     * @return ACCEPTED_TROUPS
     */

    public static int getAcceptedTroups() {
        return ACCEPTED_TROUPS;
    }

    /**
     * Method landOn specifies what happens when the troop lands on the
     * teleport.
     * @param troop
     */

    @Override
    public void landOn(Troop troop) {
        if(troop.intersects(this) && (teleportToPos.getX() >= 0)
                && (teleportToPos.getY() >= 0) && (passedTroups < ACCEPTED_TROUPS)){
            passedTroups++;
            troop.get_Position().setX(teleportToPos.getX());
            troop.get_Position().setY(teleportToPos.getY());
            troop.setDirection(direction);
        }
    }

    /**
     * Method draw - draws the entrance and the exit.
     * @param g
     */

    @Override
    public void draw(Graphics g) {
        if(passedTroups < ACCEPTED_TROUPS){
            g.drawImage(getElementImage(), get_Position().getX(),
                    get_Position().getY(),
                    getElementSize(), getElementSize(), null);
            if((teleportToPos.getX() >= 0) && (teleportToPos.getY() >= 0)){
                g.drawImage(getElementImage(), teleportToPos.getX(),
                        teleportToPos.getY(),
                        getElementSize(), getElementSize(), null);
            }
        }
    }
}
