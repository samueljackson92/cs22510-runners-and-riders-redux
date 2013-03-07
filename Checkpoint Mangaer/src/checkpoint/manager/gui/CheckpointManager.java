package checkpoint.manager.gui;

import checkpoint.manager.FileIO;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.Entrant;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CheckpointManager {
    
    private final FileIO fio;
    private LinkedHashMap<Integer, Entrant> entrants;
    private ArrayList<Checkpoint> checkpoints;
    
    public CheckpointManager(HashMap<String, String> args) throws FileNotFoundException, IOException {
        fio = new FileIO();
        entrants = fio.readEntrants(args.get("entrants"));
        checkpoints = fio.readCheckpoints(args.get("checkpoints"));
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
