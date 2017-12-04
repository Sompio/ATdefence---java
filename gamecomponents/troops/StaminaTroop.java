package gamecomponents.troops;

import gamecomponents.LandOnReceiver;
import gameenvironment.Level;
import gameenvironment.Position;

/**
 * Created by Pierre on 2016-12-17.
 */
public class StaminaTroop extends Troop{

    private static final String STAMINA_TROOP = "Snowman.png";

    public StaminaTroop(Level level, LandOnReceiver receiver, Position pos, int health, int speed) {
        super(level, receiver, pos, health, speed);
        setElementImage(STAMINA_TROOP);
    }
}
