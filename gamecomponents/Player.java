package gamecomponents;

import javax.swing.*;

/**
 * Created by dv15oes on 2016-12-12.
 */
public class Player {
    private String name;
    private int credits;
    private JLabel creditsLabel;
    private String labelPrefix = "Credits: ";
    private int score;
    private int levelsSucceeded;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
        updateLabel();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevelsSucceeded() {
        return levelsSucceeded;
    }

    public void setLevelsSucceeded(int levelsSucceeded) {
        this.levelsSucceeded = levelsSucceeded;
    }

    public void setCreditsLabel(JLabel creditsLabel) {
        this.creditsLabel = creditsLabel;
    }

    private void updateLabel(){
        creditsLabel.setText(labelPrefix + credits);
    }

    public void addCredits(int amount){
        credits += amount;
        updateLabel();
    }

    public void withdrawCredits(int amount){
        credits -= amount;
        updateLabel();
    }

}
