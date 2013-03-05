
package checkpoint.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class FileIO {
    
    public HashMap<Integer, Entrant> readEntrants(String filename) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(filename);
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(new File(filename));
        HashMap<Integer, Entrant> entrants = new HashMap<Integer, Entrant>();
        
        if (fl != null) {
            while(in.hasNext()) {
                Entrant e = new Entrant();
                e.setId(in.nextInt());
                e.setCourse(in.next().charAt(0));
                e.setName(in.nextLine());
                entrants.put(e.getId(),e);
            }
        
            fl.release();
        }
        in.close();
        fis.close();
        
        return entrants;
    }
    
    public HashMap<Character, Course> readCourses(String filename) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(filename);
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis);
        
        HashMap<Character, Course> courses = new HashMap<Character, Course>();
        ArrayList<Integer> nodes = new ArrayList<Integer>();
        
        if (fl != null) {
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
            
            fl.release();
        }
        
        in.close();
        fis.close();
        
        return courses;
    }
    
    public void readCheckpointData(String filename, HashMap<Integer, Entrant> entrants) throws FileNotFoundException, ParseException, IOException {
        FileInputStream fis = new FileInputStream(filename);
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis);
        
        if(fl != null) {
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
            fl.release();
        }
        
        in.close();
        fis.close();
    }
    
    public ArrayList<Checkpoint> readCheckpoints(String filename) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(filename);
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis);
        
        ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
        
        if (fl != null) {
            while(in.hasNext()) {
                int id = in.nextInt();
                String type = in.next();
                if(!type.equals("JN")) {
                    checkpoints.add(new Checkpoint(id, type));
                }
            }
            fl.release();
        }
        
        in.close();
        fis.close();
        
        return checkpoints;
    }
}
