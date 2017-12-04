package userinput;

/**
 * Adapter class that passes ATDEvents to arbitrary classes, and to provide
 * a list of playground names to the gui's menu and a highscore list, from an
 * arbitrary class.
 *
 * Created by dv15oes on 2016-12-06.
 */

import java.util.List;

public class OnATDEvent {
    ProcessUserInput pui;

    /**
     * Constructor
     * @param pui Instance of class that provides a list of playground names,
     *            and receives ATDEvent objects upon user actions in the gui.
     */
    public OnATDEvent(ProcessUserInput pui) {
        this.pui = pui;
    }

    /**
     * Method that is called by the gui upon user events. When called, it
     * receives an ATDEvent as argument. This method is intended to be
     * overloaded.
     * @param ae An ATDEvent
     */
    public void onATDEvent(ATDEvent ae) {
        this.pui.onATDEvent(ae);
    }

    /**
     * Method called by the gui to achieve a list of available playground
     * names, which are shown in the Select Playground submenu.
     * @return List of playground names.
     */
    public List<String> getLevels() {
        return this.pui.getLevels();
    }

    public List<List> getHigscores() { return this.pui.getHighscores(); }

    public int getCredits() { return this.pui.getCredits(); }
}
