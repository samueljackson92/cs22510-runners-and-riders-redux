package checkpoint.manager;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


public class CheckpointManager extends JFrame {
    private final JList checkpointList;
    
    public CheckpointManager(String[] args) {
        this.setSize(300, 300);
        JPanel rightPanel = new JPanel();
        JPanel centrePanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        
        //create left hand panel
        //contains data on checkpoints
        checkpointList = new JList(); //data has type Object[]
        checkpointList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        checkpointList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        checkpointList.setVisibleRowCount(0);
        
        JScrollPane listScroller = new JScrollPane(checkpointList);
        listScroller.setPreferredSize(new Dimension(250, 80));
        
        
        leftPanel.add(checkpointList);
        
        getContentPane().add(leftPanel);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 3));
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new CheckpointManager(args);
    }
}
