
package checkpoint.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class FileIO {
    
    public HashMap<Integer, Entrant> readEntrants() throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new File("/home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/event_3/entrants.txt"));
        HashMap<Integer, Entrant> entrants = new HashMap<Integer, Entrant>();

        while(in.hasNext()) {
            Entrant e = new Entrant();
            e.setId(in.nextInt());
            e.setCourse(in.next().charAt(0));
            e.setName(in.nextLine());
            entrants.put(e.getId(),e);
        }
        
        in.close();
        
        return entrants;
    }
    
    public HashMap<Character, Course> readCourses() throws FileNotFoundException {
        Scanner in = new Scanner(new File("/home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/event_3/courses.txt"));
        HashMap<Character, Course> courses = new HashMap<Character, Course>();
        ArrayList<Integer> nodes = new ArrayList<Integer>();
        
        while (in.hasNext()) {
            Course c = new Course();
            c.setId(in.next().charAt(0));
            c.setLength(in.nextInt());
            
            while(in.hasNextInt()) {
                nodes.add(in.nextInt());
            }
            c.setNodes(nodes);
            courses.put(c.getId(), c);
        }
        
        in.close();
        
        return courses;
    }
    
    public void readCheckpointData(HashMap<Integer, Entrant> entrants) throws FileNotFoundException, ParseException {
        Scanner in = new Scanner(new File("/home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/event_3/cp_times_1.txt"));
        
        while (in.hasNext()) {
            CPTimeData cp = new CPTimeData();
            char type = in.next().charAt(0);
            int node = in.nextInt();
            int entrantNo = in.nextInt();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            Date date = sdf.parse(in.next());
          
            Entrant e = entrants.get(entrantNo);
            e.setTimes(new ArrayList<CPTimeData>());
            
            switch(type) {
                case 'E':
                    cp = new MCTimeData();
                    e.setExcluded(true);
                    break;
                case 'I':
                    e.setExcluded(true);
                    break;
                case 'D':
                    cp = new MCTimeData(); 
                    break;
            }
            
            cp.setNode(type);
            cp.setNode(node);
            cp.setEntrantId(entrantNo);
            cp.setArrival_time(date);
            
            e.addTime(cp);
        }
    }
}
