package gui;

/**This class responds to a Help or About user action in AntiTower Defense.
 * Every call generates an ATDEvent and places the ATDEvent object in a
 * SwingWorker. The ATDEvent is eventually passed to the OnATDEvent instance
 * provided as argument. This permits the game engine to, for example, pause
 * the game when Help or About is shown.
 * In parallel, an Help or About windows is shown. The latter action is
 * entirely controlled by this class.
 *
 * Created by dv15oes on 2016-12-08.
 */

import gamecomponents.GameAttributes;
import userinput.ATDEvent;
import userinput.OnATDEvent;
import highScoreDatabase.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.*;


class OnPopupWindowAction extends SwingWorker {
    private ATDEvent userEvent;
    private OnATDEvent oae;
    private JFrame frame;
    private HighScore highscoreToShow;
    private GameAttributes ga;
    private ArrayList<PlayerHighscore> aLHighscore;


    /**Constructor.
     * @param e event object.
     * @param oae Adapter class that receives user events.
     * @param frame The frame that draws this menu. Needed to provide a parent
     *              to the Help and About windows.
     */
    public OnPopupWindowAction(ActionEvent e, OnATDEvent oae, JFrame frame, GameAttributes ga) {
        this.ga = ga;
        this.oae = oae;
        this.userEvent = new ATDEvent(e.getActionCommand());
        this.frame = frame;
        execute();
    }

    /**Action to perform when there is available resources.
     * @return Always null; provided as formal requirement.
     * @throws Exception No exceptions are expected but provided as formal
     * requirement.
     */
    @Override
    protected Object doInBackground() throws Exception {
        this.oae.onATDEvent(this.userEvent); /* Send event to pause game. */
        switch (this.userEvent.getId()) {
            case "Help":
                showHelpPopup();
                break;
            case "About":
                showAboutPopup();
                break;
            case "Show Highscore":
                showHighscorePopup();
                break;
            case "Custom playground...":
                customPlayground();
                break;
        }
        this.oae.onATDEvent(this.userEvent); /* Send event to restart game. */
        return null;
    }

    /**Define and show the Help window.
     */
    private void showHelpPopup() {
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea();
        panel.setBackground(Color.BLACK);
        textArea.append("HOW TO PLAY:\n");
        textArea.append("When the game is started it's already loaded on the first level. \n");
        textArea.append("The player is able to buy different kind of units\n");
        textArea.append("HOW TO WIN: The units need to reach the goal\n");
        textArea.append("\nUNITS\n");
        textArea.append("   - Rudolph the reindeer, a fast and low-health unit\n");
        textArea.append("   - Snowie the snowman, a slow and high-health unit\n");
        textArea.append("   - nåt santa's little helper, a teleporter unit\n");
        textArea.append("\nUSAGE\n");
        textArea.append("   - Use mouse to buy the different kind of units\n");
        textArea.append("   - Use mouse to change the direction for the crossroads\n");
        textArea.append("\nRESOURCES\n");
        textArea.append("   - Credits. The player gets a certain amount of credits\n");
        textArea.append("      for each level\n");
        textArea.append("   - Time. is the amount of how long it took to complete a level\n");
        textArea.append("Good luck on your quest to save christmas\n");
        textArea.append("\n                             Merry christmas\n");
        panel.add(textArea);
        JOptionPane.showMessageDialog(this.frame, panel,
                "Anti-Tower Defense help", JOptionPane.PLAIN_MESSAGE);
    }

    /**Define and show the About window.
     */
    private void showAboutPopup() {
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea();
        panel.setBackground(Color.BLACK);
        textArea.append("   AUTHORS\n");
        textArea.append("JOE\n");
        textArea.append("Pierre\n");
        textArea.append("Rasmus\n");
        textArea.append("Per-Joel\n");
        textArea.append("More to come...\n");
        panel.add(textArea);
        JOptionPane.showMessageDialog(this.frame, panel,
                "About Anti-Tower Defense", JOptionPane.PLAIN_MESSAGE);
    }

    /**Define and show the High Score window.
     */
    private void showHighscorePopup() {

        JPanel panel = new JPanel();
        //highscore = new HighScore(ga.getLevel().getName(), ga.getPlayer().getName(), ga.getPlayer().getTime(), ga.getPlayer().getScore(), ga.getPlayer().getCreditsLabel());
        highscoreToShow = new HighScore("santaslevel", "Christmassaver", 985, 43, 84);
        highscoreToShow.connectToDB();
        JTextArea textArea = new JTextArea();
        aLHighscore = highscoreToShow.getHighscores(10);
        for(int i = 0; i < aLHighscore.size(); i++) {
            System.out.println(aLHighscore.get(i));
            textArea.append(aLHighscore.get(i).toString() + "\n");
        }

        panel.add(textArea);
        System.out.println(this.oae.getHigscores());
        JOptionPane.showMessageDialog(this.frame, panel,
                "Anti-Tower Defense Highscore", JOptionPane.PLAIN_MESSAGE);
    }

    private void customPlayground() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "AntiTower Defense playground (.xml)", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.userEvent.setId(chooser.getSelectedFile().getName());
        }
    }
}