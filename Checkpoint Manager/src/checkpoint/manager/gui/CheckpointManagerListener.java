/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkpoint.manager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving checkpointManager events.
 * The class that is interested in processing a checkpointManager
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCheckpointManagerListener<code> method. When
 * the checkpointManager event occurs, that object's appropriate
 * method is invoked.
 *
 * @author samuel
 */
public class CheckpointManagerListener implements ActionListener, ListSelectionListener {

    /** The parent. */
    private final CheckpointManagerGUI parent;

    /**
     * Instantiates a new checkpoint manager listener.
     *
     * @param parent the parent
     */
    CheckpointManagerListener(CheckpointManagerGUI parent) {
        this.parent = parent;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("CheckIn")) {
            parent.doCheckIn();
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        parent.updateOutput();
        parent.toggleMedicalCPInput();
    }
    
}
