package checkpoint.manager.gui;

import checkpoint.manager.FileIO;
import checkpoint.manager.datamodel.CPTimeData;
import checkpoint.manager.datamodel.CPType;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.Course;
import checkpoint.manager.datamodel.Entrant;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

public class CheckpointManager {
    
    private final FileIO fio;
    private final HashMap<String, String> filenames;
        
    private LinkedHashMap<Integer, Entrant> entrants;
    private ArrayList<Checkpoint> checkpoints;
    private HashMap<Character, Course> courses;
    private PriorityQueue<CPTimeData> times;
    
    public CheckpointManager(HashMap<String, String> args) 
            throws FileNotFoundException, IOException, ParseException {
        filenames = args;
        fio = new FileIO();
        entrants = fio.readEntrants(args.get("entrants"));
        checkpoints = fio.readCheckpoints(args.get("checkpoints"));
        courses = fio.readCourses(args.get("courses"));
        
        updateTimes();
    }
    
    public boolean willExcludedEntrant(int entrantId, int chkptId) {
                
        Entrant e = getEntrant(entrantId);
        Course c = courses.get(e.getCourse());
        
        if(e.hasStarted()) {
            if(c.getNode(e.getPosition()+1)  != chkptId 
                || e.getLatestTime().getUpdateType() != 'A') {
                return true;
            }
        }

        return false;
    }
    
    public void updateTimes() 
            throws FileNotFoundException, ParseException, IOException {
        times = fio.readCheckpointData(filenames.get("times"), entrants);
    }
    
    public boolean checkInEntrant(int entrantId, int chkptId, 
            Date arrivalDate, Date departureDate, boolean mcExcluded) 
            throws FileNotFoundException, IOException, ParseException {
        
        boolean checkedIn = false;
        Entrant e = entrants.get(entrantId);
        Checkpoint cp = checkpoints.get(chkptId);
        Course course = courses.get(e.getCourse());
        CPTimeData lastTime;
        CPTimeData time;
        char updateType = 'T';
        
        //reload checkpoint data in case of change
        updateTimes();
         
        if(!e.isExcluded()) {
            time = new CPTimeData();
            time.setTime(new Date());

            
            if (cp.getType() == CPType.MC) {
                addArrivalTime(entrantId, chkptId, arrivalDate);
                updateType = 'D';
            }
            
            if(e.hasStarted()) {
                lastTime = e.getTimes().get(e.getPosition());
                if(course.getNode(e.getPosition()+1) != chkptId
                        || lastTime.getUpdateType() != 'A') {
                    e.setExcluded(true);
                    updateType = 'I';
                }
            }

            if (mcExcluded) {
                e.setExcluded(true);
                updateType = 'E';
            }

            time.setEntrantId(entrantId);
            time.setNode(chkptId);
            time.setType(cp.getType());
            time.setUpdateType(updateType);

            e.addTime(time);
            times.add(time);
            fio.writeTimes(filenames.get("times"), times);
            
            checkedIn = true;
        }
        
        return checkedIn;
    }
    
    private void addArrivalTime(int entrantId, int chkptId, Date arrivalDate) {
        CPTimeData arrivalTime = new CPTimeData();
        arrivalTime.setTime(arrivalDate);
        arrivalTime.setEntrantId(entrantId);
        arrivalTime.setType(CPType.MC);
        arrivalTime.setUpdateType('A');
        arrivalTime.setNode(chkptId);
        entrants.get(entrantId).addTime(arrivalTime);
    }
    
    public Entrant getEntrant(int index) {
        return getEntrants().get(index);
    }
    
    public Checkpoint getCheckpoint(int index) {
        return getCheckpoints().get(index);
    }

    /**
     * @return the entrantList
     */
    public HashMap<Integer, Entrant> getEntrants() {
        return entrants;
    }

    /**
     * @return the checkpoints
     */
    public ArrayList<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
