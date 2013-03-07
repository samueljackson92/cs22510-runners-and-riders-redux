package checkpoint.manager.gui;

import checkpoint.manager.FileIO;
import checkpoint.manager.datamodel.CPType;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.Entrant;
import checkpoint.manager.exceptions.ArguementParseException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private final DefaultListModel entrantListModel;
    private final JCheckBox chkExcluded;
    private final JButton btnCheckIn;
    private final JSpinner JarrivalTime;
    private final CheckpointManagerListener chkptListener;
    private final CheckpointManager cpManager;
    
    private final JLabel currentEntrant;
    private final JLabel currentCheckpoint;
    
    public CheckpointManagerGUI(HashMap<String, String> args) throws FileNotFoundException, IOException {
        this.setSize(500, 600);
        
        currentEntrant = new JLabel("Current Entrant: ");
        currentCheckpoint = new JLabel("Current Checkpoint: ");
 
        chkptListener = new CheckpointManagerListener(this);
        cpManager = new CheckpointManager(args);
        cpListModel = new DefaultListModel();
        entrantListModel = new DefaultListModel();
        btnCheckIn = new JButton("Check In");
        chkExcluded = new JCheckBox("Exclude entrant for medical reasons");
        JarrivalTime = new JSpinner( new SpinnerDateModel() );
        
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
        
        
        for (Checkpoint chk : cpManager.getCheckpoints()) {
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
                
        Iterator it = cpManager.getEntrants().entrySet().iterator();
        while (it.hasNext()) {
            Entrant e =  (Entrant) ((Entry) it.next()).getValue();
            entrantListModel.addElement(e.getId() + " " + e.getName());
        }
        
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
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(JarrivalTime, "HH:mm:ss");
        JarrivalTime.setEditor(timeEditor);
        JarrivalTime.setValue(new Date());
        JarrivalTime.setEnabled(false);
        
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
        if(cpManager.getCheckpoint(index).getType() == CPType.MC) {
            JarrivalTime.setEnabled(true);
            chkExcluded.setEnabled(true);
        } else {
            JarrivalTime.setEnabled(false);
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
}
