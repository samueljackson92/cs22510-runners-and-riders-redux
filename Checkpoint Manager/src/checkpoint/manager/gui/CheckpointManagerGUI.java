package checkpoint.manager.gui;

import checkpoint.manager.FileIO;
import checkpoint.manager.datamodel.CPType;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.CheckpointManager;
import checkpoint.manager.datamodel.Entrant;
import checkpoint.manager.exceptions.ArguementParseException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
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

public class CheckpointManagerGUI extends JFrame {
    
    private final DefaultListModel cpListModel;
    private JList JLCheckpointList;
    private JList JLEntrantList;
    private DefaultListModel entrantListModel;
    private final JCheckBox chkExcluded;
    private final JButton btnCheckIn;
    private final JSpinner JarrivalTime;
    private final JSpinner JdepartureTime;
    private final CheckpointManagerListener chkptListener;
    private CheckpointManager cpManager;
    
    private final JLabel currentEntrant;
    private final JLabel currentCheckpoint;
    
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
        chkExcluded = new JCheckBox("Exclude entrant for medical reasons");
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
    
    private void initGUI() {
        JPanel temp = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel centrePanel = new JPanel();
        JPanel leftPanel = new JPanel();
        
        JLCheckpointList = new JList(cpListModel);       
        JLCheckpointList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        JLCheckpointList.setLayoutOrientation(JList.VERTICAL);
        
        
        for (Entry entry : cpManager.getCheckpoints().entrySet()) {
            Checkpoint chk = (Checkpoint) entry.getValue();
            cpListModel.addElement(chk.getId() + " " + chk.getType().toString());
        }
        JLCheckpointList.addListSelectionListener(chkptListener);

        JScrollPane listScroller = new JScrollPane(JLCheckpointList);
        listScroller.setPreferredSize(new Dimension(250, 300));
       
        temp.add(new JLabel("Checkpoints: "));
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(temp, BorderLayout.NORTH);
        temp = new JPanel();
        temp.add(listScroller);
        leftPanel.add(temp, BorderLayout.SOUTH);

        JLEntrantList = new JList(entrantListModel);
        JLEntrantList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        JLEntrantList.setLayoutOrientation(JList.VERTICAL);
                
        refreshEntrants();
        
        JLEntrantList.addListSelectionListener(chkptListener);
        
        listScroller = new JScrollPane(JLEntrantList);
        listScroller.setPreferredSize(new Dimension(250, 300));
        
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
//        JSpinner.DateEditor arrivalTimeEditor = new JSpinner.DateEditor(JarrivalTime, "HH:mm");
//        JarrivalTime.setModel(new SpinnerDateModel(d, null, null, Calendar.MINUTE));
//        JarrivalTime.setEditor(arrivalTimeEditor);

       
//        JSpinner.DateEditor departureTimeEditor = new JSpinner.DateEditor(JdepartureTime, "HH:mm");
//        JdepartureTime.setEditor(departureTimeEditor);
//        JdepartureTime.setEnabled(false);
        
        btnCheckIn.setActionCommand("CheckIn");
        btnCheckIn.addActionListener(chkptListener);
        
        centrePanel.setLayout(new BorderLayout());   
        
        temp = new JPanel();
        
        JPanel first = new JPanel();
        first.add(currentEntrant);
        temp.add(first);
        first = new JPanel();
        first.add(currentCheckpoint);
        temp.add(first);
        
        JPanel tempTop = new JPanel();
        tempTop.add(new JLabel("Arrival Time: "));
        tempTop.add(JarrivalTime);
        temp.add(tempTop);
        
        JPanel temp2 = new JPanel();
        temp2.add(new JLabel("Dpearture Time: "));
        temp2.add(JdepartureTime);
        temp.add(temp2);
        
        JPanel tempMiddle = new JPanel();
        tempMiddle.add(chkExcluded);
        temp.add(tempMiddle);
        
        JPanel tempBottom = new JPanel();
        tempBottom.add(btnCheckIn);
        temp.add(tempBottom);
        centrePanel.add(temp, BorderLayout.CENTER);
        centrePanel.setPreferredSize(new Dimension(300, 100));
        
        getContentPane().add(leftPanel);
        getContentPane().add(centrePanel);
        getContentPane().add(rightPanel);
    }
    
    private int parseIndex(DefaultListModel list, int index) {
        return (Integer.parseInt(list.get(index).toString().split("[a-z ]")[0]));
    }
    
    public void doCheckIn() {
        int index = JLEntrantList.getSelectedIndex();
        int entrantId = parseIndex(entrantListModel, index);
        index = JLCheckpointList.getSelectedIndex();
        int cpId = parseIndex(cpListModel, index);
        Checkpoint cp = cpManager.getCheckpoint(cpId);
        
        int checkpointId = cp.getId();
        Date arrivalTime = (Date) JarrivalTime.getValue();
        Date departureTime = null;
        boolean mcExcluded = chkExcluded.isSelected();
        boolean successful = false;
        boolean validInput = true;
        
        if(JdepartureTime.isEnabled()) {
            departureTime = (Date) JdepartureTime.getValue();
        }
        
        
        Calendar c = Calendar.getInstance();
        c.setTime(departureTime);
        c.set(1970, 1, 1);
        departureTime = c.getTime();
        
        c.setTime(arrivalTime);
        c.set(1970, 1, 1);
        arrivalTime = c.getTime();

        
        if(cp.getType()==CPType.MC) {
            System.out.println(arrivalTime);
            System.out.println(departureTime);
            
            if(arrivalTime.compareTo(departureTime) >= 0) {
                JOptionPane.showMessageDialog(this, "Invalid time data!");
                validInput = false;
            }
        }
        
        if(cpManager.willExcludedEntrant(entrantId, checkpointId) || mcExcluded) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "This will exclude the entrant. Are you sure?", 
                    "Confirm Choice", JOptionPane.YES_NO_OPTION);
            validInput = (confirm == JOptionPane.YES_OPTION) ? true : false;
        }
        
        if(validInput) {
            try {
                successful = cpManager.updateTimes();
                if(successful) {
                    successful = cpManager.checkInEntrant(entrantId, checkpointId,  arrivalTime, departureTime, mcExcluded);
                    refreshEntrants();
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error:", JOptionPane.ERROR_MESSAGE);
            }

            if(successful) {
                JOptionPane.showMessageDialog(this, "Checked in!"); 
            } else {
                JOptionPane.showMessageDialog(this, "Could not check in entrant! Perhaps file was locked?");
            }
        }
    }
    
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
    
    public void toggleExcludedCheckbox() {
        int index = JLCheckpointList.getSelectedIndex();
        int cpId = (Integer.parseInt(cpListModel.get(index).toString().split("[a-z ]")[0]));
        if(cpManager.getCheckpoint(cpId).getType() == CPType.MC) {
            JdepartureTime.setEnabled(true);
            chkExcluded.setEnabled(true);
        } else {
            JdepartureTime.setEnabled(false);
            chkExcluded.setEnabled(false); 
        }
    }
    
    public static void main(String[] args) {
        try {
            HashMap<String, String> cmdArgs;
            cmdArgs = FileIO.parseArgs(args);
            new CheckpointManagerGUI(cmdArgs);
        } catch (ArguementParseException ex) {
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
    
    private static void printHelp() {
            System.out.println("Checkpoint Manager -- Usage:");
            System.out.println("Please supply the following files using the given flags");
            System.out.println("    -E <entrants file>");
            System.out.println("    -C <courses file>");
            System.out.println("    -K <checkpoints file>");
            System.out.println("    -T <times file>");
    }

    private void refreshEntrants() {
        entrantListModel = new DefaultListModel();
        Iterator it = cpManager.getEntrants().entrySet().iterator();
        while (it.hasNext()) {
            Entrant e =  (Entrant) ((Entry) it.next()).getValue();
            if(!(e.isExcluded() || e.isFinished())) {
                entrantListModel.addElement(e.getId() + " " + e.getName());
            }
        }
        
        JLEntrantList.setModel(entrantListModel);
        JLEntrantList.setSelectedIndex(0);
    }
}
