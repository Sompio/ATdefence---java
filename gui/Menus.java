package gui;

/**
 * Create the AntiTower Defense menu and catch user actions on this menu.
 *
 * Created by dv15oes on 2016-12-01.
 */

import gamecomponents.GameAttributes;
import userinput.OnATDEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Menus {

    /**Create the complete menu bar and its listeners.
     * OnUserAction() or OnPopupWindowAction() is called upon user actions.
     * @param oae Adapter class that receives user events.
     * @param frame The frame that draws this menu. Needed to provide a parent
     *              to the Help and About windows.
     * @return The AntiTower defense menubar.
     */
    public static JMenuBar getMenus(OnATDEvent oae, JFrame frame,
                                    GameAttributes ga) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(getGameMenu(oae, frame, ga));
        menuBar.add(getHelpMenu(oae, frame, ga));
        return menuBar;
    }

    /**Create the Game menu.
     * @param oae Adapter class that receives user events.
     * @return Menu for game control.
     */
    private static JMenu getGameMenu(OnATDEvent oae, JFrame frame,
                                     GameAttributes ga) {
        JMenu gameMenu = new JMenu("Game");
        gameMenu.add(getNewMenuItem(oae, "New game", KeyEvent.VK_N));
        gameMenu.add(getNewMenuItem(oae, "Restart playground",
                KeyEvent.VK_R));
        gameMenu.add(getPopupWindowMenuItem(oae, frame, "Custom playground...",
        KeyEvent.VK_O, ga));
        gameMenu.addSeparator();
        gameMenu.add(getPopupWindowMenuItem(oae, frame,"Show Highscore",
                KeyEvent.VK_S, ga));
        gameMenu.addSeparator();
        gameMenu.add(getNewMenuItem(oae, "Pause game", KeyEvent.VK_P));
        gameMenu.addSeparator();
        gameMenu.add(getNewMenuItem(oae, "Quit game", KeyEvent.VK_Q));
        // Re-create the levels submenu on every menu action.
        gameMenu.addChangeListener(e -> createLevelsSubmenu(gameMenu,
                "Select playground", oae, 2));
        return gameMenu;
    }

    /**Create submenu to select game level. This submenu is only visible if
     * there is more than one level available. Items in the submenu are the
     * items provided in the OnATDEvent.getLevels() list of levels.
     * @param menu Parent menu.
     * @param label Text on the parent's menu item.
     * @param oae Adapter class that receives user events.
     * @param menuPosition This menu's position in the parent menu.
     */
    private static void createLevelsSubmenu(JMenu menu, String label,
                                            OnATDEvent oae, int menuPosition) {
        /* Remove the old submenu if it exists. */
        Component c = menu.getMenuComponent(menuPosition);
        if (c instanceof JMenu) {
            menu.remove(menuPosition);
        }
        /* Create new submenu. */
        JMenu submenu = new JMenu(label);
        if (oae.getLevels().size() > 1) {
            for (String level : oae.getLevels()) {
                JMenuItem levelMenuItem = new JMenuItem(level);
                levelMenuItem.addActionListener(e -> new OnUserAction(e, oae));
                submenu.add(levelMenuItem);
            }
            menu.add(submenu, menuPosition);
        }
    }

    /**Create a general menu item.
     * @param oae Adapter class that receives user events.
     * @param label Text on the menu item.
     * @param keyEvent Key event connected to the menu item.
     * @return The new menu item.
     */
    private static JMenuItem getNewMenuItem(OnATDEvent oae, String label,
                                            int keyEvent) {
        JMenuItem mi = new JMenuItem(label, keyEvent);
        mi.addActionListener(e -> new OnUserAction(e, oae));
        return mi;
    }

    /**Create the Help menu.
     *
     * @param oae Adapter class that receives user events.
     * @param frame The frame that draws this menu. Needed to provide a parent
     *              to the Help and About windows.
     * @return Menu to call the game's help and about functions.
     */
    private static JMenu getHelpMenu(OnATDEvent oae, JFrame frame,
                                     GameAttributes ga) {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(getPopupWindowMenuItem(oae, frame, "Help",
                KeyEvent.VK_H, ga));
        helpMenu.add(getPopupWindowMenuItem(oae, frame, "About",
                KeyEvent.VK_A, ga));
        return helpMenu;
    }

    /**Create a Highscore, Help or About menu item.
     * @param oae Adapter class that receives user events.
     * @param frame The frame that draws this menu. Needed to provide a parent
     *              to the Help and About windows.
     * @param label Text on the menu item.
     * @param keyEvent Key event connected to the menu item.
     * @return The new menu item.
     */
    private static JMenuItem getPopupWindowMenuItem(OnATDEvent oae,
                                                    JFrame frame,
                                                    String label,
                                                    int keyEvent,
                                                    GameAttributes ga) {
        JMenuItem mi = new JMenuItem(label, keyEvent);
        mi.addActionListener(e -> new OnPopupWindowAction(e, oae, frame, ga));
        return mi;
    }

}