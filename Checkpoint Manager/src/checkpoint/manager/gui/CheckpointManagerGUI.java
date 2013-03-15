package checkpoint.manager.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import checkpoint.manager.FileIO;
import checkpoint.manager.datamodel.CPType;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.CheckpointManager;
import checkpoint.manager.datamodel.Entrant;
import checkpoint.manager.exceptions.ArgumentParseException;

/**
 * The Class CheckpointManagerGUI.
 */
@SuppressWarnings("serial")
public class CheckpointManagerGUI extends JFrame {
    
    /** The checkpoint list model to store checkpoints in the GUI. */
    private final DefaultListModel cpListModel;
    
    /** The checkpoint list to display checkpoints in order. */
    private JList JLCheckpointList;
    
    /** The entrant list to display entrants in order. */
    private JList JLEntrantList;
    
    /** The entrant list model to store the entrant list in the GUI. */
    private DefaultListModel entrantListModel;
    
    /** The checkbox for excluding an entrant. */
    private final JCheckBox chkMCExcluded;
    
    /** The button to check in and entrant. */
    private final JButton btnCheckIn;
    
    /** The arrival time of the entrant. */
    private final JSpinner JarrivalTime;
    
    /** The departure time of the entrant. */
    private final JSpinner JdepartureTime;
    
    /** The checkpoint manager GUI event listener. */
    private final CheckpointManagerListener chkptListener;
    
    /** The checkpoint manager to process the data model. */
    private CheckpointManager cpManager;
    
    /** The current entrant label. */
    private final JLabel currentEntrant;
    
    /** The current checkpoint label. */
    private final JLabel currentCheckpoint;
    
    /**
     * Instantiates a new checkpoint manager GUI.
     *
     * @param args the args from the command line
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     */
    public CheckpointManagerGUI(HashMap<String, String> args) throws FileNotFoundException, IOException {
        this.setSize(500, 600);
        
        currentEntrant = new JLabel("Current Entrant: ");
        currentCheckpoint = new JLabel("Current Checkpoint: ");
 
        try {
            cpManager = new CheckpointManager(args);
            if(!cpManager.updateTimes()) {
                JOptionPane.showMessageDialog(this, "Could not read the times file!", "Error!", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex, "Could not Parse Text times file!", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        chkptListener = new CheckpointManagerListener(this);
        cpListModel = new DefaultListModel();
        entrantListModel = new DefaultListModel();
        btnCheckIn = new JButton("Check In");
        chkMCExcluded = new JCheckBox("Exclude entrant for medical reasons");
        JarrivalTime = new JSpinner(new SpinnerDateModel());
        JdepartureTime = new JSpinner(new SpinnerDateModel());
        
        initGUI();
        
        JLCheckpointList.setSelectedIndex(0);
        JLEntrantList.setSelectedIndex(0);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 3));
        setVisible(true);
        pack();
    }
    
    /**
     * Initialises the GUI.
     */
    private void initGUI() {
        JPanel temp = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel centrePanel = new JPanel();
        JPanel leftPanel = new JPanel();
        
        //create list of checkpoints
        JLCheckpointList = new JList(cpListModel);       
        JLCheckpointList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        JLCheckpointList.setLayoutOrientation(JList.VERTICAL);
        
        //populate list of checkpoints
        for (Entry<Integer, Checkpoint> entry : cpManager.getCheckpoints().entrySet()) {
            Checkpoint chk = (Checkpoint) entry.getValue();
            cpListModel.addElement(chk.getId() + " " + chk.getType().toString());
        }
        
        JLCheckpointList.addListSelectionListener(chkptListener);
        JScrollPane listScroller = new JScrollPane(JLCheckpointList);
        listScroller.setPreferredSize(new Dimension(250, 300));
       
        //layout list of checkpoints
        temp.add(new JLabel("Checkpoints: "));
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(temp, BorderLayout.NORTH);
        temp = new JPanel();
        temp.add(listScroller);
        leftPanel.add(temp, BorderLayout.SOUTH);

        //create list of entrants
        JLEntrantList = new JList(entrantListModel);
        JLEntrantList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        JLEntrantList.setLayoutOrientation(JList.VERTICAL);
        refreshEntrants();
        
        JLEntrantList.addListSelectionListener(chkptListener);
        
        listScroller = new JScrollPane(JLEntrantList);
        listScroller.setPreferredSize(new Dimension(250, 300));
        
        //layout list of entrants
        rightPanel.setLayout(new BorderLayout());
        temp = new JPanel();
        temp.add(new JLabel("Entrants: "));
        rightPanel.add(temp);
        rightPanel.add(temp, BorderLayout.NORTH);
        temp = new JPanel();
        temp.add(listScroller);
        rightPanel.add(temp, BorderLayout.SOUTH);
           
        //create centre panel
        JarrivalTime.setModel(new SpinnerDateModel());
        JarrivalTime.setEditor(new JSpinner.DateEditor(JarrivalTime, "HH:mm"));
        JdepartureTime.setModel(new SpinnerDateModel());
        JdepartureTime.setEditor(new JSpinner.DateEditor(JdepartureTime, "HH:mm"));
        
        btnCheckIn.setActionCommand("CheckIn");
        btnCheckIn.addActionListener(chkptListener);
        
        //layout elements in centre panel
        centrePanel.setLayout(new BorderLayout());   
        
        temp = new JPanel();
        
        JPanel first = new JPanel();
        first.add(currentEntrant);
        temp.add(first);
        first = new JPanel();
        first.add(currentCheckpoint);
        temp.add(first);
        
        JPanel second = new JPanel();
        second.add(new JLabel("Arrival Time: "));
        second.add(JarrivalTime);
        temp.add(second);
        
        JPanel third = new JPanel();
        third.add(new JLabel("Dpearture Time: "));
        third.add(JdepartureTime);
        temp.add(third);
        
        JPanel fourth = new JPanel();
        fourth.add(chkMCExcluded);
        temp.add(fourth);
        
        JPanel fifth = new JPanel();
        fifth.add(btnCheckIn);
        temp.add(fifth);
        centrePanel.add(temp, BorderLayout.CENTER);
        centrePanel.setPreferredSize(new Dimension(300, 100));
        
        getContentPane().add(leftPanel);
        getContentPane().add(centrePanel);
        getContentPane().add(rightPanel);
    }
    
    /**
     * Parses the ID from the start of a list box item.
     *
     * @param list the list model
     * @param index the index of the selected item
     * @return the ID
     */
    private int parseIndex(DefaultListModel list, int index) {
        return (Integer.parseInt(list.get(index).toString().split("[a-z ]")[0]));
    }
    
    /**
     * Check in an entrant in response to a users click.
     */
    public void doCheckIn() {
        int index = JLEntrantList.getSelectedIndex();
        int entrantId = parseIndex(entrantListModel, index);
        index = JLCheckpointList.getSelectedIndex();
        int checkpointId = parseIndex(cpListModel, index);
        Checkpoint checkpoint = cpManager.getCheckpoint(checkpointId);
        
        Date arrivalTime = (Date) JarrivalTime.getValue();
        Date departureTime = null;
        boolean mcExcluded = chkMCExcluded.isSelected();
        boolean successful = false;
        boolean validInput = true;
        
        //reload the times file.
        try {
            successful = cpManager.updateTimes();
            if(!successful) {
                JOptionPane.showMessageDialog(this, "Could not reload times! Perhaps file was locked by another process?");
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
        }
        
        if(successful) {
	        //check if we're at a medical checkpoint
	        if(JdepartureTime.isEnabled()) {
	            departureTime = (Date) JdepartureTime.getValue();
	        }
	   
	        //check if the times entered were valid
	        if((checkpoint.getType()==CPType.MC && cpManager.compareTime(arrivalTime, departureTime))
	                || !cpManager.checkValidTime(entrantId, arrivalTime)) {
	            JOptionPane.showMessageDialog(this, "Invalid time data!");
	            validInput = false;
	        }
	        
	        if(validInput) {
	            //check if the entrant will be excluded with this update
	            if(cpManager.willExcludedEntrant(entrantId, checkpointId) || mcExcluded) {
	            	//confirm this with the user.
	                int confirm = JOptionPane.showConfirmDialog(this,
	                        "This will exclude the entrant. Are you sure?", 
	                        "Confirm Choice", JOptionPane.YES_NO_OPTION);
	                validInput = (confirm == JOptionPane.YES_OPTION) ? true : false;
	            }
	        }
        }
        
        if(validInput) {
        	
        	//perform the update
            try {
                successful = cpManager.checkInEntrant(entrantId, checkpointId,  arrivalTime, departureTime, mcExcluded);
                refreshEntrants();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            }

            //feedback to the user if successful
            if(successful) {
                JOptionPane.showMessageDialog(this, "Checked in!"); 
            } else {
                JOptionPane.showMessageDialog(this, "Could not check in entrant! Perhaps file was locked by another process?");
            }
            
            try {
				successful = cpManager.updateLog("Checked in entrant " + entrantId + " @ node " + checkpointId);
			} catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            }
            
            if(!successful) {
                JOptionPane.showMessageDialog(this, "Could not write to log file!");
            }
        }
    }
    
    /**
     * Update the GUI "currently selected" labels in response to user interaction.
     */
    public void updateOutput() {
        int index = JLCheckpointList.getSelectedIndex();
        
        if(index >= 0) {
            String currentChkpt = cpListModel.get(index).toString();
            currentCheckpoint.setText("Current Checkpoint: " + currentChkpt);
        }
        
        index = JLEntrantList.getSelectedIndex();
        if(index >= 0) {
            String entrant = entrantListModel.get(index).toString();
            currentEntrant.setText("Current Entrant: " + entrant);
        }
    }
    
    /**
     * Toggle input for a medical checkpoint
     */
    public void toggleMedicalCPInput() {
        int index = JLCheckpointList.getSelectedIndex();
        int cpId = (Integer.parseInt(cpListModel.get(index).toString().split("[a-z ]")[0]));
        if(cpManager.getCheckpoint(cpId).getType() == CPType.MC) {
            JdepartureTime.setEnabled(true);
            chkMCExcluded.setEnabled(true);
        } else {
            JdepartureTime.setEnabled(false);
            chkMCExcluded.setEnabled(false); 
        }
    }
    
    /**
     * The main method and entry point to the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            HashMap<String, String> cmdArgs;
            cmdArgs = FileIO.parseArgs(args);
            new CheckpointManagerGUI(cmdArgs);
        } catch (ArgumentParseException ex) {
            printHelp();
            System.exit(0);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    /**
     * Prints the help menu to the console.
     */
    private static void printHelp() {
            System.out.println("Checkpoint Manager -- Usage:");
            System.out.println("Please supply the following files using the given flags");
            System.out.println("    -E <entrants file>");
            System.out.println("    -C <courses file>");
            System.out.println("    -K <checkpoints file>");
            System.out.println("    -T <times file>");
            System.out.println("    -L <log file>");
    }

    /**
     * Refresh the list of entrants.
     */
    private void refreshEntrants() {
        entrantListModel = new DefaultListModel();
        Iterator<Entry<Integer,Entrant>> it = cpManager.getEntrants().entrySet().iterator();
        while (it.hasNext()) {
            Entrant e =  (Entrant) ((Entry<Integer, Entrant>) it.next()).getValue();
            if(!(e.isExcluded() || e.isFinished())) {
                entrantListModel.addElement(e.getId() + " " + e.getName());
            }
        }
        
        JLEntrantList.setModel(entrantListModel);
        JLEntrantList.setSelectedIndex(0);
    }
}
