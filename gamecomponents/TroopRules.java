package gamecomponents;

/**
 * Created by rasblo on 2016-12-17.
 */
public class TroopRules {
    private int health;
    private int speed;
    private int cost;

    public TroopRules(int health, int speed, int cost) {
        this.health = health;
        this.speed = speed;
        this.cost = cost;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
