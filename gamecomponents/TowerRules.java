package gamecomponents;

/**
 * Created by rasblo on 2016-12-17.
 */
public class TowerRules {
    private int damage;
    private int coolDown;
    private int rangeWidth;
    private int rangeHeight;

    public TowerRules(int damage,int coolDown, int rangeWidth, int rangeHeight) {
        this.damage = damage;
        this.rangeWidth = rangeWidth;
        this.rangeHeight = rangeHeight;
        this.coolDown = coolDown;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getRangeWidth() {
        return this.rangeWidth;
    }

    public int getRangeHeight() {
        return this.rangeHeight;
    }

    public int getCoolDown() {
        return this.coolDown;
    }
}
