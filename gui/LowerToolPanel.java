package gui;

/**Defines the AntiTower Defense lower tool panel.
 *
 * Created by dv15oes on 2016-12-01.
 */

import resources.ImageLoader;
import userinput.OnATDEvent;

import javax.swing.*;
import java.awt.*;

public class LowerToolPanel {

    private ImageLoader il = new ImageLoader();
    private static final String GNOME_BUTTON = "GnomeButton.png";
    private static final String GNOME_ROLLOVER = "GnomeRollOver.png";
    private static final String TELEPORT_BUTTON = "TeleportButton.png";
    private static final String TELEPORT_ROLLOVER = "TeleportRollOver.png";
    private static final String RUDOLPH_BUTTON = "RudolphButton.png";
    private static final String RUDOLPH_ROLLOVER = "RudolphRollOver.png";
    private static final String SNOWMAN_BUTTON = "SnowmanButton.png";
    private static final String SNOWMAN_ROLLOVER = "SnowmanRollOver.png";

    /**Create the lower tool panel and add handlers to the buttons.
     * OnUserAction() is called upon user actions.
     * @param oae Adapter class that receives user events.
     * @return The lower tool panel.
     */
    public static JPanel getPanel(OnATDEvent oae, JLabel credits) {
        ImageLoader il = new ImageLoader();
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(credits);
        getButton(panel, oae, "Buy troops #1", SNOWMAN_BUTTON, SNOWMAN_ROLLOVER, il);
        getButton(panel, oae, "Buy troops #2", RUDOLPH_BUTTON, RUDOLPH_ROLLOVER, il);
        getButton(panel, oae, "Buy troops #3", GNOME_BUTTON, GNOME_ROLLOVER, il);
        getButton(panel, oae,"Launch teleporter" , TELEPORT_BUTTON, TELEPORT_ROLLOVER, il);
        return panel;
    }

    /**Create a general button and add an OnUserAction listener to it.
     * @param p Panel that holds the button.
     * @param oae Adapter class that receives user events.
     * @param label Text on the button.
     * @return The button object.
     */
    private static JButton getButton (JPanel p, OnATDEvent oae, String label,
                                      String imagePath, String rollOver,
                                      ImageLoader il) {
        JButton b = new JButton();
        b.setActionCommand(label);
        b.setBorder(null);
        b.setMargin(new Insets(-1, -1, -1, -1));
        b.setFocusPainted(false);
        System.out.println(imagePath);
        b.setIcon(new ImageIcon(il.loadImage(imagePath)));
        b.setRolloverIcon(new ImageIcon(il.loadImage(rollOver)));
        b.addActionListener(e -> new OnUserAction(e, oae));
        p.add(b);
        return b;
    }
}
