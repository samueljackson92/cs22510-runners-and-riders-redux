
package checkpoint.manager;

import checkpoint.manager.datamodel.CPTimeData;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.Course;
import checkpoint.manager.datamodel.Entrant;
import checkpoint.manager.exceptions.ArguementParseException;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class FileIO {
    
    public static HashMap<String, String> parseArgs(String[] args) throws ArguementParseException {
        HashMap<String, String> argsList = new HashMap<String, String>();
       
        if (args.length == 8) {
            for (int i = 0; i < args.length; i+=2) {
                String key = "";
                switch(args[i].charAt(1)) {
                    case 'E':
                        key = "entrants";
                        break;
                    case 'T':
                        key = "times";
                        break;
                    case 'C':
                        key = "courses";
                        break;
                    case 'K':
                        key = "checkpoints";
                        break;
                    default:
                        throw new ArguementParseException();
                }

                argsList.put(key, args[i+1]);
            }
        } else {
            throw new ArguementParseException();
        }
        
        return argsList;
    }
    
    public LinkedHashMap<Integer, Entrant> readEntrants(String filename) throws FileNotFoundException, IOException {
        RandomAccessFile fis = new RandomAccessFile(filename, "rw");
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis.getChannel());
        LinkedHashMap<Integer, Entrant> entrants = new LinkedHashMap<Integer, Entrant>();
        
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
        RandomAccessFile fis = new RandomAccessFile(filename, "rw");
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis.getChannel());
        
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
    
    public void readCheckpointData(String filename, LinkedHashMap<Integer, Entrant> entrants) throws FileNotFoundException, ParseException, IOException {
        RandomAccessFile fis = new RandomAccessFile(filename, "rw");
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis.getChannel());
        
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
                        e.setExcluded(true);
                        break;
                    case 'I':
                        e.setExcluded(true);
                        break;
                }

                cp.setUpdateType(type);
                cp.setNode(node);
                cp.setEntrantId(entrantNo);
                cp.setTime(date);

                e.addTime(cp);
                e.incrementPosition();
            }
            fl.release();
        }
        
        in.close();
        fis.close();
    }
    
    public ArrayList<Checkpoint> readCheckpoints(String filename) throws FileNotFoundException, IOException {
        RandomAccessFile fis = new RandomAccessFile(filename, "rw");
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis.getChannel());
        
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
    
    public void writeTimeData(String filename, CPTimeData data) throws FileNotFoundException, IOException {
        FileOutputStream fis = new FileOutputStream(filename);
        FileLock fl = fis.getChannel().tryLock();
        PrintWriter pw = new PrintWriter(new BufferedOutputStream(fis));

        if (fl != null) {
            String time = data.getTime();
            String output = data.getType() + " " + data.getNode() + " " + data.getEntrantId() + " " + time;
            pw.append(output);
        }
    }
}
