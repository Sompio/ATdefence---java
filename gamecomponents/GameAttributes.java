package gamecomponents;

import gameenvironment.Level;

/**
 * Created by rasblo on 2016-12-01.
 * Trådsäker
 */
public class GameAttributes {
    private boolean pause;
    private Level level;
    private Player player = new Player();

    public GameAttributes(){
        this.pause = false;
    }

    public GameAttributes(Level level, Player player){
        this.pause = false;
        this.level = level;
        this.player = player;
    }

    public synchronized boolean getPause(){
        return this.pause;
    }

    public synchronized void setPause(boolean pause){
        this.pause = pause;
    }

    public synchronized Level getLevel() {
        return this.level;
    }

    public synchronized void setLevel(Level level) {
        this.level = level;
    }

    public synchronized Player getPlayer() {
        return this.player;
    }

    public synchronized void setPlayer(Player player) {
        this.player = player;
    }
}
