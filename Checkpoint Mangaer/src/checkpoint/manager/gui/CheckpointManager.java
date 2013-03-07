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

public class CheckpointManager {
    
    private final FileIO fio;
    private LinkedHashMap<Integer, Entrant> entrants;
    private ArrayList<Checkpoint> checkpoints;
    private HashMap<Character, Course> courses;
    private final HashMap<String, String> filenames;
    
    public CheckpointManager(HashMap<String, String> args) throws FileNotFoundException, IOException, ParseException {
        filenames = args;
        fio = new FileIO();
        entrants = fio.readEntrants(args.get("entrants"));
        checkpoints = fio.readCheckpoints(args.get("checkpoints"));
        courses = fio.readCourses(args.get("courses"));
        
        fio.readCheckpointData(args.get("times"), entrants);
        
    }
    
    public void checkInEntrant(int entrantId, int chkptId, boolean mcExcluded) throws FileNotFoundException, IOException {
        Entrant e = entrants.get(entrantId);
        Checkpoint c = checkpoints.get(chkptId);
        Course course = courses.get(e.getCourse());
        CPTimeData lastTime = e.getTimes().get(e.getTimes().size()-1);
        CPTimeData time;
        char updateType = 'T';
        
        time = new CPTimeData();
        time.setTime(new Date());
        
        if (c.getType() == CPType.MC) {
            if(lastTime.getUpdateType() == 'A') {
                updateType = 'D';
            } else {
                updateType = 'A';
            }
        }
        
        if(course.getNode(e.getPosition()+1) != chkptId
                && lastTime.getUpdateType() != 'A') {
            e.setExcluded(true);
            updateType = 'I';
        }
        
        if (mcExcluded) {
            e.setExcluded(true);
            updateType = 'E';
        }

        time.setEntrantId(entrantId);
        time.setNode(chkptId);
        time.setType(c.getType());
        time.setUpdateType(updateType);
       
        fio.writeTimeData(filenames.get("times"), time);
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
