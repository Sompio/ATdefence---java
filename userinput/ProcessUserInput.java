package userinput;

import gamecomponents.*;
import gameenvironment.Level;
import gameenvironment.LoadLevel;
import gameenvironment.Position;
import gui.ErrorMessage;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.in;

/**
 * Created by dv15oes on 2016-12-05.
 */
public class ProcessUserInput {
    private GameAttributes ga;
    private GameObjects go;
    private JLabel creditsLabel;
    private List<List> highscores = new ArrayList<>();
    private boolean popupWindowIsUp = false;
    private List<String> levelList = new LinkedList();
    private int teleportButtonPushed = 0;

    public ProcessUserInput(GameAttributes ga, GameObjects go) {
        this.ga = ga;
        this.go = go;

        createLevel("1");
    }

    public void onATDEvent(ATDEvent oae) {
        switch (oae.getId()) {
            case "MouseClick":
                onMouseClick(oae.getX(), oae.getY());
                break;
            case "New game":
                onNewGame();
                break;
            case "Restart playground":
                onRestart();
                break;
            case "Custom playground...":
                onCustomLevel();
                break;
            case "Show Highscore":
                onHighscore();
                break;
            case "Pause game":
                onPause();
                break;
            case "Quit game":
                onQuit();
                break;
            case "Buy troops #1":
                onBuyTroup1();
                break;
            case "Buy troops #2":
                onBuyTroup2();
                break;
            case "Buy troops #3":
                onBuyTroup3();
                break;
            case "Launch teleporter":
                onTeleporter();
                break;
            case "Help":
                onHelp();
                break;
            case "About":
                onAbout();
                break;
            default:
                onSelectLevel(oae);
        }
    }

    public void setCreditsLabel(JLabel creditsLabel) {
        this.creditsLabel = creditsLabel;
        ga.getPlayer().setCreditsLabel(creditsLabel);
    }

    private void onNewGame() {
        ga.setPlayer(new Player());
        ga.getPlayer().setCreditsLabel(creditsLabel);
        ga.getPlayer().setCredits(ga.getLevel().getRules().getCreditsAtStart());
        createLevel("1");
    }

    private void onSelectLevel(ATDEvent oae) {
        createLevel(oae.getId());

    }

    private void onRestart() {
        ga.getPlayer().setCredits(ga.getLevel().getRules().getCreditsAtStart());
        createLevel(this.ga.getLevel().getName());
    }

    private void onHighscore() {setPause();}

    private void onCustomLevel() {
        if (!this.ga.getPause()){
            setPause();
        }
    }

    private void onPause() {
        if (this.popupWindowIsUp || !this.ga.getPause()) {
            this.ga.setPause(true);
        } else {
            this.ga.setPause(false);
        }
        System.out.println("Pause is set " + this.ga.getPause());
    }

    private void onHelp() {
        setPause();
    }

    private void onAbout() {
        setPause();
    }

    private void onQuit() {
        System.exit(0);
    }

    private void onBuyTroup1() {
        int cost = ga.getLevel().getRules().getTroopRules("Stamina").getCost();
        if (!this.ga.getPause() && ga.getPlayer().getCredits() >= cost) {
            ga.getPlayer().withdrawCredits(cost);
            TroopRules staminaTroop = this.ga.getLevel().getRules().getTroopRules("Stamina");
            go.addStaminaTroop(this.ga.getLevel(), this.ga.getLevel().getStartPos(),
                    staminaTroop.getHealth(), staminaTroop.getSpeed());
            System.out.println("Buy Stamina");
        }
    }

    private void onBuyTroup2() {
        if (!this.ga.getPause()) {
            TroopRules speedTroop = this.ga.getLevel().getRules().getTroopRules("Speed");
            go.addSpeedTroop(this.ga.getLevel(), this.ga.getLevel().getStartPos(),
                    speedTroop.getHealth(), speedTroop.getSpeed());
            System.out.println("Buy Speed");
        }
    }

    private void onBuyTroup3() {
        if (!this.ga.getPause()) {
            teleportButtonPushed = 0;
            TroopRules teleportTroop = this.ga.getLevel().getRules().getTroopRules("Teleporter");
            go.addTeleportTroup(this.ga.getLevel(), this.ga.getLevel().getStartPos(),
                    teleportTroop.getHealth(), teleportTroop.getSpeed());
            //Teleportertruppp skapas här
            //Får bara finnas en åt gången på spelplanen.
            //Kan möjligtvis kontrolleras med isAlive.
            System.out.println("Buy Teleporter");
        }
    }


    private void onMouseClick(int x, int y) {
        Position pos = new Position(x, y);
        go.changeDirectionCrossRoad(pos);
    }

    private void onTeleporter() {

        if(go.isTeleporterAlive()){
            System.out.println(teleportButtonPushed++);
            switch (teleportButtonPushed) {
                case 1 :
                    System.out.println("släpper teleporten");
                    go.dropTeleportEntrance();
                    break;
                case 2 :
                    System.out.println("släpper exit");
                    go.dropTeleportExit();
                    break;
            }
        }
    }

    private void createLevel(String levelName) {
        String filepath = "";
        if (levelName.toUpperCase().endsWith(".XML")) {
            filepath = levelName;
        }
        createLevel(levelName, filepath);
    }

    private void createLevel(String levelName, String filePath) {
        this.go.clear();
        try {
            /* Load levels. An empty string will load default levels. */
            LoadLevel loader = new LoadLevel(filePath);
            String firstName = "";
            for(String name : loader.getLevelNames()){
                if ("".equals(firstName)) {
                   firstName = name;
                }
                if (!this.levelList.contains(name)) {
                    this.levelList.add(name);
                }
            }
            if (!filePath.isEmpty()) {
                levelName = firstName;
                System.out.println("isEMPTY WORKS");
            }
            Level level = new Level(loader.getLevel(Integer.parseInt(levelName)), loader.getRules(Integer.parseInt(levelName)), levelName);
            this.ga.setLevel(level);
            this.go.createGoal(level.getGoalPos(), level.getRules().getNumberOfUnitsToReachGoal(), level.getRules().getCreditsAtCompletion());

            for(Position p : level.getTowerPositions()){
                TowerRules tower = level.getRules().getTowerRules("Laser");
                System.out.println(tower.getRangeWidth());
                this.go.addTower(p, tower.getDamage(), tower.getCoolDown(), tower.getRangeWidth(), tower.getRangeHeight());
            }
            this.go.addCrossRoads(level.getCrossroads());

        } catch (IOException e) {
            ErrorMessage msg = new ErrorMessage();
            msg.display("AntiTower Defense Error", "IO read error");
        } catch (ClassNotFoundException | InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            ErrorMessage msg = new ErrorMessage();
            msg.display("AntiTower Defense Error", "Could not build level. Is the XML file correctly defined?");
        }
        this.ga.setPause(false);
    }

    private void setPause() {
        this.popupWindowIsUp = !this.popupWindowIsUp;
        onPause();
    }

    public List getLevels() {
        return this.levelList;
    }

    public List getHighscores() { return this.highscores; }

    public int getCredits() { return this.ga.getPlayer().getCredits(); }
}
