package checkpoint.manager.datamodel;

import checkpoint.manager.FileIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

/**
 * The Class CheckpointManager.
 * Main management class to the underlying data model.
 * Manages the processing and updating of data from user input via the GUI
 * into the data files.
 * @author Samuel Jackson (slj11@aber.ac.uk)
 */
public class CheckpointManager {
    
    /** The FileIO object to write to files. */
    private final FileIO fio;        
    
    /** The LinkedHashMap of entrants. Entrant ID used as key. */
    private LinkedHashMap<Integer, Entrant> entrants;
    
    /** The LinkedHashMap of checkpoints. Checkpoint ID used as key */
    private LinkedHashMap<Integer, Checkpoint> checkpoints;
    
    /** The HashMap of courses. Course ID used as key */
    private HashMap<Character, Course> courses;
    
    /** The PriorityQueue of times. Oldest time has highest priority */
    private PriorityQueue<CPTimeData> times;
    
    /**
     * Instantiates a new checkpoint manager.
     *
     * @param args the arguments supplied via the command line.
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     * @throws ParseException the parse exception thrown by failing to parse a date.
     */
    public CheckpointManager(HashMap<String, String> args) 
            throws FileNotFoundException, IOException, ParseException {
        
        fio = new FileIO(args);
        entrants = fio.readEntrants();
        checkpoints = fio.readCheckpoints();
        courses = fio.readCourses(checkpoints);
    }
    
    /**
     * Check if updating an entrant to the given checkpoint ID will cause the
     * entrant to be excluded.
     *
     * @param entrantId the entrant's id
     * @param chkptId the checkpoint id
     * @return true, if successful
     */
    public boolean willExcludedEntrant(int entrantId, int chkptId) {
                
        Entrant entrant = getEntrant(entrantId);
        Course course = courses.get(entrant.getCourse());
        
        if(!entrant.isFinished()) {
            if(course.getNode(entrant.getPosition()+1)  != chkptId 
                && (!entrant.hasStarted() || entrant.getLatestTime().getUpdateType() != 'A')) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Re-read the times file and update all entrants with a new set of times.
     *
     * @return true, if successful in reading the file
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws ParseException the parse exception if a date could not be parsed.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     */
    public boolean updateTimes() 
            throws FileNotFoundException, ParseException, IOException {
        times = fio.readCheckpointData(entrants, courses);
        
        //Failed to acquire lock or not
        return (times != null);
    }
    
    /**
     * Check compare the time part of two instances of a date object
     *
     * @param time the first time to be compared
     * @param time2 the second time to be compared
     * @return true, if the time is valid
     */
    public boolean compareTime(Date time, Date time2) {
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	return sdf.format(time).compareTo(sdf.format(time2)) >= 0;
    }
    
    /**
     * Check if the supplied time is a valid time.
     *
     * @param entrantId the entrant ID
     * @param time the time to be checked.
     * @return true, if the time is valid
     */
    public boolean checkValidTime(int entrantId, Date time) {
        Entrant entrant = getEntrant(entrantId);
        if(entrant.hasStarted()) {
            if(compareTime(entrant.getLatestTime().getTime(), time)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Check in entrant.
     *
     * @param entrantId the entrant ID
     * @param chkptId the checkpoint ID
     * @param arrivalTime the arrival time of the entrant
     * @param departureTime the departure time of the entrant
     * @param mcExcluded the flag for if the entrant is exlcuded for medical reasons
     * @return true, if successful at writing data to file.
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     * @throws ParseException the parse exception if a date could not be parsed.
     */
    public boolean checkInEntrant(int entrantId, int chkptId, 
            Date arrivalTime, Date departureTime, boolean mcExcluded) 
            throws FileNotFoundException, IOException, ParseException {
        
        boolean checkedIn = false;
        Date checkInTime;
        Entrant entrant = entrants.get(entrantId);
        Checkpoint chkpoint = checkpoints.get(chkptId);
        Course course = courses.get(entrant.getCourse());
        char updateType = 'T';
         
        if(!entrant.isExcluded()) {
            checkInTime = arrivalTime;
            
            //set arrival time if medical checkpoint
            if (chkpoint.getType() == CPType.MC) {
            	checkInTime  = departureTime;
                addEntrantTime(entrantId, chkptId, arrivalTime, 'A', CPType.MC);
                updateType = 'D';
            }   
            
            CPType type = (updateType == 'D') ? CPType.MC : CPType.CP;
            
            //exclude entrant if they failed for medical reasons
            if (mcExcluded) {
                entrant.setExcluded(true);
                updateType = 'E';
            }
            
            //exclude entrant if they came to wrong checkpoint
            if(willExcludedEntrant(entrant.getId(), chkpoint.getId())) {
            	entrant.setExcluded(true);
            	updateType = 'I';
            }
            
            //check if the entrant is after this update
            if(entrant.getPosition() >= course.getLength()-2) {
                entrant.setFinished(true);
            }
            
            addEntrantTime(entrantId, chkptId, checkInTime, updateType, type);
            entrant.incrementPosition();
            checkedIn = fio.writeTimes(times);
        }
        
        return checkedIn;
    }
    
    /**
     * Output an update to the log file.
     * @param output the output to add to the log file.
     * @return true, if updating the log file was successful
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     * @throws FileNotFoundException exception thrown when file cannot be found.
     */
    public boolean updateLog(String output) throws FileNotFoundException, IOException {
    	return fio.writeLog(output);
    }
    
    /**
     * Creates a time update and adds it to the list of times and the entrant's
     * time list.
     *
     * @param entrantId the entrant ID
     * @param chkptId the checkpoint ID
     * @param date the time of the update
     * @param updateType the type of update (T, I, A, D, E)
     * @param type the type of checkpoint.
     */
    private void addEntrantTime(int entrantId, int chkptId, Date date, char updateType, CPType type) {
        CPTimeData time = new CPTimeData();
        time.setTime(date);
        time.setEntrantId(entrantId);
        time.setType(type);
        time.setUpdateType(updateType);
        time.setNode(chkptId);
        entrants.get(entrantId).addTime(time);
        times.add(time);
    }
    
    /**
     * Gets an entrant with the given ID.
     *
     * @param id the ID of the entrant
     * @return the entrant with the given ID
     */
    public Entrant getEntrant(int id) {
        return getEntrants().get(id);
    }
    
    /**
     * Gets a checkpoint with the given ID
     *
     * @param id the ID of the checkpoint
     * @return the checkpoint with the given ID
     */
    public Checkpoint getCheckpoint(int id) {
        return getCheckpoints().get(id);
    }

    /**
     * Gets the list of entrants.
     *
     * @return the entrant list
     */
    public HashMap<Integer, Entrant> getEntrants() {
        return entrants;
    }

    /**
     * Gets the list of checkpoints.
     *
     * @return the checkpoint list
     */
    public LinkedHashMap<Integer, Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
