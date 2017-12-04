package highScoreDatabase;
/**
 * Created by per-joelsompio on 16/12/16.
 * A class which is used to help out the Highscore-class to store
 * all information as an object. is later returned to the GUI as
 * an arraylist with objects.
 */

public class PlayerHighscore {
    private String levelName;
    private String playerName;
    private int time;
    private int score;
    private int credits;

    public PlayerHighscore() {


    }

    public String playerToString() {
        return playerName;
    }

    public String scoreToString() {
        return Integer.toString(this.score);
    }

    @Override
    public String toString() {
        return this.playerName + "  " + this.score;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public void setLevelName(String s) {
        this.levelName = s;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String s) {
        this.playerName = s;
    }

    public int getPTime() {
        return this.time;
    }

    public void setPTime(int i) {
        this.time = i;
    }

    public int getPScore() {
        return this.score;
    }

    public void setPScore(int i) {
        this.score = i;
    }

    public int getPCredits() {
        return this.credits;
    }

    public void setPCredits(int i) {
        this.credits = i;
    }
}
