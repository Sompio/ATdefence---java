package gamecomponents.troops;

import gamecomponents.LandOnReceiver;
import gameenvironment.Level;
import gameenvironment.Position;

/**
 * Created by Pierre on 2016-12-17.
 */
public class SpeedTroop extends Troop {

    private static final String SPEED_TROOP = "Rudolph.png";

    public SpeedTroop(Level level, LandOnReceiver receiver, Position pos, int health, int speed) {
        super(level, receiver, pos, health, speed);
        setElementImage(SPEED_TROOP);
    }


}
