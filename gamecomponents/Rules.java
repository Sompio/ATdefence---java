package gamecomponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by rasblo on 2016-12-01.
 */
public class Rules {
    private int creditsAtStart;
    private int creditsAtCompletion;
    private int numberOfUnitsToReachGoal;
    private int numberOfTowers;
    private HashMap<String, TowerRules> towerRules;
    private HashMap<String, TroopRules> troopRules;

    public Rules(){
        this.creditsAtStart = 0;
        this.creditsAtCompletion = 0;
        this.numberOfUnitsToReachGoal = 0;
        this.numberOfTowers = 0;
        this.towerRules = new HashMap<>();
        this.troopRules = new HashMap<>();
    }

    public void addTowerRules(String name, int damage, int coolDown, int rangeWidth, int rangeHeight){
        this.towerRules.put(name, new TowerRules(damage, coolDown, rangeWidth, rangeHeight));
    }

    public void addTroopRules(String name, int health, int speed, int cost){
        this.troopRules.put(name, new TroopRules(health, speed, cost));
    }

    public TowerRules getTowerRules(String tower){
        return this.towerRules.get(tower);
    }

    public TroopRules getTroopRules(String troop){
        return this.troopRules.get(troop);
    }

    public int getCreditsAtStart(){
        return this.creditsAtStart;
    }

    public void setCreditsAtStart(int initialCredits){
        this.creditsAtStart = initialCredits;
    }

    public int getCreditsAtCompletion(){
        return this.creditsAtCompletion;
    }

    public void setCreditsAtCompletion(int goalCredits){
        this.creditsAtCompletion = goalCredits;
    }

    public int getNumberOfUnitsToReachGoal(){
        return this.numberOfUnitsToReachGoal;
    }

    public void setNumberOfUnitsToReachGoal(int goalUnitAmount){
        this.numberOfUnitsToReachGoal = goalUnitAmount;
    }

    public int getNumberOfTowers(){
        return this.numberOfTowers;
    }

    public void setNumberOfTowers(int towerAmount){
        this.numberOfTowers = towerAmount;
    }
}
