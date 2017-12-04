package gui;

/**
 * Create graphical user interface (gui) for viewing, and catch actions the
 * user perform on this object. This object provides controls in a menu bar
 * and buttons. The gui is constructed by Swing object whereas the game is
 * drawn in a canvas.
 * All user actions generate a custom AntiTower Defense event, called ATDEvent.
 *
 * Created by dv15oes on 2016-11-30.
 */

import gamecomponents.GameAttributes;
import userinput.OnATDEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GUI {
    private Canvas gameCanvas = new Canvas();
    private JLabel creditsLabel = new JLabel("Credits: 0");
    private OnATDEvent oae;

    /**Construct and show the gui.
     * @param windowTitle The title on the window.
     * @param oae An OnATDEvent adapter class instance. This is the class that
     *            receives ATDEvents produced by this gui.
     */
    public GUI(String windowTitle, OnATDEvent oae, GameAttributes ga) {
        this.oae = oae;
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(windowTitle);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setJMenuBar(Menus.getMenus(oae, frame, ga));
            frame.add(setupPanels(oae));
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**Created the main panel. This is contructed by three subpanels; upper
     * tool panel, canvas and lower tool panel.
     * @param oae An OnATDEvent adapter class instance. This is the class that
     *            receives ATDEvents produced by this gui.
     * @return the main JPanel.
     */
    private JPanel setupPanels(OnATDEvent oae) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(UpperToolPanel.getPanel(oae), BorderLayout.NORTH);

        this.gameCanvas.setPreferredSize(new Dimension(800,600));
        this.gameCanvas.setMaximumSize(new Dimension(800,600));
        this.gameCanvas.setMinimumSize(new Dimension(800,600));
        this.gameCanvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new OnMouseAction(e, oae);
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}
            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        panel.add(this.gameCanvas, BorderLayout.CENTER);

        panel.add(LowerToolPanel.getPanel(oae, this.creditsLabel), BorderLayout.SOUTH);
        return panel;
    }

    /** Provide a reference to the game canvas object.
     *  @return the game canvas object.
     */
    public Canvas getCanvas() {
        return this.gameCanvas;
    }

    public JLabel getCreditsLabel() {
        return creditsLabel;
    }
}
