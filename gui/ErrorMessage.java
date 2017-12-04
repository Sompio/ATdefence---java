package gui;

import javax.swing.*;

/**
 * Created by Pierre on 2016-11-27.
 */
public class ErrorMessage extends JOptionPane {
    public void display(String title, String cause){
        showMessageDialog(null, cause, title, JOptionPane.ERROR_MESSAGE);
    }
}
