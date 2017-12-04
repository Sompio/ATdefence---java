package gamecomponents.troops;

import gamecomponents.LandOnReceiver;
import gameenvironment.Level;
import gameenvironment.Position;

import java.awt.*;

/**
 * Created by Pierre on 2016-12-05.
 */

/**
 *
 */

public class TeleportTroop extends Troop {

    private Teleport teleport;
    private static final String IMAGE_PATH = "TeleportTroop.png";
    private boolean teleportEntranceDropped = false;
    private int originalSpeed;
    private int waitBeforeStart = 0;
    private static final int WAIT_TIME = 20;

    public TeleportTroop(Level level, LandOnReceiver receiver,
                         Position pos, int health, int speed) {
        super(level, receiver, pos, health, speed);
        setElementImage(IMAGE_PATH);
        originalSpeed = speed;
    }

    public void dropTeleportEntrance(){
        Position teleportPos = new Position(get_Position().getX(), get_Position().getY());
        teleport = new Teleport(teleportPos, getReceiver());
        teleportEntranceDropped = true;
    }

    public void dropTeleportExit(){
        Position teleportPos = new Position(get_Position().getX(), get_Position().getY());
        teleport.dropExit(teleportPos, getDirection());
        setSpeed(0);
    }

    public void removeTeleport(){
        if(teleport != null){
            teleport.setBounds(0,0,0,0);
            teleport = null;
        }
    }

    @Override
    public void move(){

        if(teleport != null && teleport.getPassedTroups() >=
                teleport.getAcceptedTroups()){
            waitBeforeStart++;
            if(waitBeforeStart == WAIT_TIME){
                setSpeed(originalSpeed);
                waitBeforeStart = 0;
            }
        }
        super.move();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if(teleportEntranceDropped){
            teleport.draw(g);
        }
    }
}
