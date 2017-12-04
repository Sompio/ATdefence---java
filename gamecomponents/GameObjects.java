package gamecomponents;

import gamecomponents.towers.Tower;
import gamecomponents.troops.SpeedTroop;
import gamecomponents.troops.StaminaTroop;
import gamecomponents.troops.TeleportTroop;
import gamecomponents.troops.Troop;
import gameenvironment.Level;
import gameenvironment.Position;
import gameenvironment.environment.Crossroad;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dv15oes on 2016-12-12.
 */

public class GameObjects {

    private ArrayList<Troop> troops = new ArrayList();
    private TeleportTroop teleporter = null;
    private ArrayList<Tower> towers = new ArrayList();
    private ArrayList<Crossroad> crossRoads = new ArrayList();
    private LandOnReceiver lr = new LandOnReceiver();
    private Goal goal;

    public synchronized void addStaminaTroop(Level level, Position pos, int health, int speed) {
        Troop t = new StaminaTroop(level, this.lr, pos, health, speed);
        troops.add(t);
    }

    public synchronized void addSpeedTroop(Level level, Position pos, int health, int speed) {
        Troop t = new SpeedTroop(level, this.lr, pos, health, speed);
        troops.add(t);
    }

    public synchronized boolean addTeleportTroup(Level level, Position pos, int health, int speed) {
        if(teleporter == null){
            TeleportTroop t = new TeleportTroop(level, this.lr, pos,  health, speed);
            troops.add(t);
            teleporter = t;
            return true;
        }
        return false;
    }

    public synchronized void dropTeleportEntrance() {
        teleporter.dropTeleportEntrance();
    }

    public synchronized void dropTeleportExit() {
        teleporter.dropTeleportExit();
    }

    public synchronized boolean isTeleporterAlive() {
        if (teleporter == null) {
            System.out.println("kommer in i null teleport");
            return false;
        }
        return teleporter.isAlive();
    }

    public synchronized void removeTroup(ArrayList<Troop> removableTroops) {
        for(Troop t : removableTroops){
            if(!troops.isEmpty()){
                if(t instanceof TeleportTroop){
                    System.out.println("Tar bort teleport");
                    teleporter.removeTeleport();
                    teleporter = null;
                }
                troops.remove(t);
            }
        }
    }

    public synchronized void drawTroup(Graphics g) {
        if(!troops.isEmpty()){
            for (Troop t : troops) {
                t.draw(g);
            }
        }
    }

    public synchronized void moveTroup() {
        ArrayList<Troop> removableTroops = new ArrayList<>();
        for (Troop t : troops) {
            if(!t.isAlive()){
                removableTroops.add(t);
            }
            else {
                t.move();
            }
        }
        removeTroup(removableTroops);
    }

    public synchronized void addTower(Position position, int damage, int coolDown, int rangeWidth, int rangeHeight) {
        Tower t = new Tower(lr, position, damage, coolDown, rangeWidth, rangeHeight);
        towers.add(t);
    }

    public synchronized void drawTowers(Graphics g) {
        for (Tower t : towers) {
            t.draw(g);
        }
    }

    public synchronized void addCrossRoads(ArrayList<Crossroad> crossroads) {
        for(Crossroad c : crossroads){
            c.addAsObserver(lr);
            crossRoads.add(c);
        }
    }

    public synchronized void drawCrossRoad(Graphics g) {
        for (Crossroad c : crossRoads) {
            c.draw(g);
        }
    }

    public synchronized void changeDirectionCrossRoad(Position pos){
        for (Crossroad c : crossRoads) {
            c.changeDirection(pos);
        }
    }

    public synchronized void createGoal(Position pos, int numberOfUnitsToReachGoal,int creditsOnGoal){
        goal = new Goal(pos, numberOfUnitsToReachGoal, creditsOnGoal, lr);
    }

    public synchronized void drawGoal(Graphics g) {
        goal.draw(g);
    }

    public synchronized Goal getGoal() {
        return goal;
    }

    public synchronized void clear(){
        troops.clear();
        teleporter = null;
        towers = new ArrayList();
        crossRoads = new ArrayList();
        lr = new LandOnReceiver();
    }
}
