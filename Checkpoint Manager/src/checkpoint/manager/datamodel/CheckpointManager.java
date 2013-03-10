package checkpoint.manager.datamodel;

import checkpoint.manager.FileIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

public class CheckpointManager {
    
    private final FileIO fio;        
    private LinkedHashMap<Integer, Entrant> entrants;
    private LinkedHashMap<Integer, Checkpoint> checkpoints;
    private HashMap<Character, Course> courses;
    private PriorityQueue<CPTimeData> times;
    
    public CheckpointManager(HashMap<String, String> args) 
            throws FileNotFoundException, IOException, ParseException {
        
        fio = new FileIO(args);
        entrants = fio.readEntrants();
        checkpoints = fio.readCheckpoints();
        courses = fio.readCourses(checkpoints);
    }
    
    public boolean willExcludedEntrant(int entrantId, int chkptId) {
                
        Entrant e = getEntrant(entrantId);
        Course c = courses.get(e.getCourse());
        
        if(e.hasStarted()) {
            if(c.getNode(e.getPosition()+1)  != chkptId 
                && e.getLatestTime().getUpdateType() != 'A') {
                return true;
            }
        }

        return false;
    }
    
    public boolean updateTimes() 
            throws FileNotFoundException, ParseException, IOException {
        times = fio.readCheckpointData(entrants, courses);
        
        //Failed to acquire lock or not
        return (times != null);
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
         
        if(!e.isExcluded()) {
            time = new CPTimeData();
            time.setTime(arrivalDate);

            
            if (cp.getType() == CPType.MC) {
                time.setTime(departureDate);
                addArrivalTime(entrantId, chkptId, arrivalDate);
                updateType = 'D';
            }
            
            if(e.hasStarted()) {
                lastTime = e.getTimes().get(e.getPosition());
                if(course.getNode(e.getPosition()+1) != chkptId
                        && lastTime.getUpdateType() != 'A') {
                    e.setExcluded(true);
                    updateType = 'I';
                }
            }

            if (mcExcluded) {
                e.setExcluded(true);
                updateType = 'E';
            }
            
            if(e.getPosition() >= course.getLength()-2) {
                e.setFinished(true);
            }

            time.setEntrantId(entrantId);
            time.setNode(chkptId);
            time.setType(cp.getType());
            time.setUpdateType(updateType);

            e.incrementPosition();
            e.addTime(time);
            times.add(time);
            checkedIn = fio.writeTimes(times);
            fio.writeLog("Checked in entrant " + time.getEntrantId() + " @ " + time.getNode());
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
        times.add(arrivalTime);
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
    public LinkedHashMap<Integer, Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
