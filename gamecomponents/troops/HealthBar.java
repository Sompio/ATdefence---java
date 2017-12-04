package gamecomponents.troops;

import java.awt.*;

/**
 * Created by dv15pln on 2016-12-04.
 */


/**
 * Class HealthBar is used to represent the health status of the troop.
 */

public class HealthBar {

    private double fullHealth;
    private double reduceRatio;

    private int healthBarWidth;
    private int healthBarHeight;
    private double reducableHealthBarWidth;
    private int xCordHealthBar;
    private int yCordHealthBar;
    private int xCordHealthBarBorder;
    private int yCordHealthBarBorder;

    /**
     * Constructor initializes the parameters.
     * @param healthBarWidth
     * @param healthBarHeight
     * @param fullHealth
     */

    public HealthBar(int healthBarWidth, int healthBarHeight, double fullHealth){
        this.healthBarWidth = healthBarWidth;
        this.healthBarHeight = healthBarHeight;
        this.fullHealth = fullHealth;
    }

    /**
     * Method drawHealthBar
     * @param g - Graphics object.
     * @param x - coordinate of health bar.
     * @param y - coordinate of health bar.
     * @param health
     */

    public void drawHealthBar(Graphics g, int x, int y, double health){
        updateHealthBar(x, y, health);
        if(greaterThen66Percent(health)){
            drawGreenHealthBar(g);
        }else if(greaterThen40Percent(health)){
            drawYellowHealthBar(g);
        }else if(lessThen40Percent(health)){
            drawRedHealthBar(g);
        }
        drawHealthBarBorder(g);
    }

    /**
     * Method updateHealthBar positions the health bar and calculates the
     * ratio to reduce the fill of the bar.
     * @param x - coordinate of troop.
     * @param y - coordinate of troop.
     * @param health
     */

    private void updateHealthBar(int x, int y, double health){
        reduceRatio = health/fullHealth;
        reducableHealthBarWidth = healthBarWidth * reduceRatio;
        xCordHealthBar = x + 4;
        yCordHealthBar = y - 9;
        xCordHealthBarBorder = x + 3;
        yCordHealthBarBorder = y - 10;
    }

    /**
     * Method greaterThen66Percent checks if health > 66%.
     * @param health
     * @return true or false.
     */

    private boolean greaterThen66Percent(double health){
        return health > fullHealth/1.5;
    }

    /**
     * Method greaterThen40Percent checks if health > 40%.
     * @param health
     * @return true or false.
     */

    private boolean greaterThen40Percent(double health){
        return health > fullHealth/2.5;
    }

    /**
     * Method lessThen40Percent checks if health < 40%.
     * @param health
     * @return true or false.
     */

    private boolean lessThen40Percent(double health){
        return health <= fullHealth/2.5;
    }

    /**
     * Method drawGreenHealthBar fills the health bar with green color.
     * @param g Graphics object
     */

    private void drawGreenHealthBar(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(xCordHealthBar, yCordHealthBar, (int)reducableHealthBarWidth,
                healthBarHeight);
    }

    /**
     * Method drawYellowHealthBar fills the health bar with yello color.
     * @param g Graphics object
     */

    private void drawYellowHealthBar(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillRect(xCordHealthBar, yCordHealthBar, (int)reducableHealthBarWidth,
                healthBarHeight);
    }

    /**
     * Method drawRedHealthBar fills the health bar with red color.
     * @param g Graphics object
     */

    private void drawRedHealthBar(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(xCordHealthBar, yCordHealthBar, (int)reducableHealthBarWidth,
                healthBarHeight);
    }

    /**
     * Method drawHealthBarBorder
     * @param g Graphics object
     */

    private void drawHealthBarBorder(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(xCordHealthBarBorder, yCordHealthBarBorder,
                healthBarWidth, healthBarHeight);
    }


}
