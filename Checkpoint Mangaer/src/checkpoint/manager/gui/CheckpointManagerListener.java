/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkpoint.manager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author samuel
 */
public class CheckpointManagerListener implements ActionListener, ListSelectionListener {

    private final CheckpointManagerGUI parent;
    
    CheckpointManagerListener(CheckpointManagerGUI parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        parent.updateOutput();
        parent.toggleExcludedCheckbox();
    }
    
}
