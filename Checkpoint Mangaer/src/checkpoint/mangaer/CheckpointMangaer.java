package checkpoint.mangaer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CheckpointMangaer {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileIO f = new FileIO();
        f.readEntrants();
    }
}
