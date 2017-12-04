package gui;

/**Defines the AntiTower Defense lower tool panel.
 * Created by dv15oes on 2016-12-01.
 */

import userinput.OnATDEvent;

import javax.swing.*;
import java.awt.*;

public class UpperToolPanel {
    /**Create the upper tool panel and add handlers to the buttons.
     * OnUserAction() is called upon user actions.
     * @param oae Adapter class that receives user events.
     * @return The upper tool panel.
     */
    public static JPanel getPanel(OnATDEvent oae) {
        JPanel panel = new JPanel(new FlowLayout());
        getButton(panel, oae, "New game");
        getButton(panel, oae, "Restart playground");
        getButton(panel, oae, "Pause game");

        return panel;
    }

    /**Create a general button and add an OnUserAction listener to it.
     * @param p Panel that holds the button.
     * @param oae Adapter class that receives user events.
     * @param label Text on the button.
     * @return The button object.
     */
    private static JButton getButton (JPanel p, OnATDEvent oae, String label) {
        JButton b = new JButton(label);
        b.addActionListener(e -> new OnUserAction(e, oae));
        p.add(b);
        return b;
    }
}
