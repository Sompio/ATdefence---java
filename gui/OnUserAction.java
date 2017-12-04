package gui;

/**This class responds to a user action in AntiTower Defense.
 * Every call generates an ATDEvent and places the ATDEvent object in a
 * SwingWorker. The ATDEvent is eventually passed to the OnATDEvent instance
 * provided as argument.
 */

import userinput.OnATDEvent;
import userinput.ATDEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by dv15oes on 2016-12-04.
 */
public class OnUserAction extends SwingWorker {
    private ATDEvent userEvent;
    private OnATDEvent oae;

    /**Constructor.
     * @param e event object.
     * @param oae Adapter class that receives user events.
     */
    protected OnUserAction(ActionEvent e, OnATDEvent oae) {
        this.oae = oae;
        this.userEvent = new ATDEvent(e.getActionCommand());
        execute();
    }

    /**Action to perform when there is available resources.
     * @return Always null; provided as formal requirement.
     * @throws Exception No exceptions are expected but provided as formal
     * requirement.
     */
    @Override
    protected Object doInBackground() throws Exception {
        this.oae.onATDEvent(this.userEvent);
        return null;
    }
}