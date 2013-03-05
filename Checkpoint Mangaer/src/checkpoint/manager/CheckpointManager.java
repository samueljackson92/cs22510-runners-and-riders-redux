package checkpoint.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckpointManager {
    
    public static void main(String[] args) {
        FileIO f = new FileIO();
        try {
            f.readCourses();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CheckpointManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CheckpointManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
