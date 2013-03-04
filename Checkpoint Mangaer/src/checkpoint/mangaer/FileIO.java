
package checkpoint.mangaer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO {
    public ArrayList readEntrants() throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader("/home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/event_3/entrants.txt"));
        ArrayList<Entrant> entrants = new ArrayList<Entrant>();
        String line;
        
        while((line = in.readLine()) != null) {
            Entrant e = new Entrant();
            e.setId((int) line.charAt(0));
            e.setCourse(line.charAt(2));
            e.setName(line.substring(3));
        }
        
        for (Entrant e : entrants) {
            System.out.println(e.getId() + e.getName() + e.getCourse());
        }
        
        return null;
    }
}
