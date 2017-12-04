package gamecomponents;

import gamecomponents.towers.TroopObserver;
import gamecomponents.troops.Troop;
import gameenvironment.GameElement;
import gameenvironment.Position;

import java.awt.*;

/**
 * Created by dv15oes on 2016-12-12.
 */
public class Goal extends GameElement implements TroopObserver {

    private int credits;
    private int creditsOnGoal;
    private int numberOfUnitsToReachGoal;
    private int troopsReachedGoal;
    private static final String imagePath = "goal2.png";

    public Goal(Position pos, int numberOfUnitsToReachGoal, int creditsOnGoal, LandOnReceiver lr) {
        super(pos);
        credits = 0;
        this.creditsOnGoal = creditsOnGoal;
        this.numberOfUnitsToReachGoal = numberOfUnitsToReachGoal;
        lr.addObserver(this);
        setElementImage(imagePath);
        setBounds(get_Position().getX(), get_Position().getY(), getElementSize(), getElementSize());
    }

    public int getAndResetCredits() {
        int withdrawCredits = credits;
        credits = 0;
        return withdrawCredits;
    }

    public boolean checkIfLevelIsComplete(){
        return troopsReachedGoal >= numberOfUnitsToReachGoal;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getElementImage(), get_Position().getX(),
                get_Position().getY(), getElementSize(),
                getElementSize(), null);
    }

    @Override
    public void landOn(Troop troop) {
        if(troop.intersects(this)) {
            troop.setAlive(false);
            credits += creditsOnGoal;
            troopsReachedGoal++;
        }
    }
}
