package gui;

/**This class responds to a menu user action in AntiTower Defense.
 * Every call generates an ATDEvent and places the ATDEvent object in a
 * SwingWorker. The ATDEvent is eventually passed to the OnATDEvent instance
 * provided as argument.
 */

import userinput.ATDEvent;
import userinput.OnATDEvent;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by dv15oes on 2016-12-05.
 */
public class OnMouseAction extends SwingWorker {
    private ATDEvent userEvent;
    private OnATDEvent oae;


    /**Constructor.
     * @param e event object.
     * @param oae Adapter class that receives user events.
     */
    protected OnMouseAction(MouseEvent e, OnATDEvent oae) {
        this.oae = oae;
        this.userEvent = new ATDEvent("MouseClick");
        this.userEvent.setX(e.getX());
        this.userEvent.setY(e.getY());
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
