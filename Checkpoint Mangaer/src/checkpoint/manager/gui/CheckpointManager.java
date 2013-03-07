package checkpoint.manager.gui;

import checkpoint.manager.FileIO;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.Entrant;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckpointManager {
    private final FileIO fio;
    private HashMap<Integer, Entrant> entrantList;
    private ArrayList<Checkpoint> checkpoints;
    
    public CheckpointManager(HashMap<String, String> args) throws FileNotFoundException, IOException {
        fio = new FileIO();
        entrantList = fio.readEntrants(args.get("entrants"));
        checkpoints = fio.readCheckpoints(args.get("checkpoints"));
    }
    
    public Entrant getEntrant(int index) {
        return entrantList.get(index);
    }
    
    public Checkpoint getCheckpoint(int index) {
        return checkpoints.get(index);
    }
}
