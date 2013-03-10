
package checkpoint.manager;

import checkpoint.manager.datamodel.CPTimeData;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.Course;
import checkpoint.manager.datamodel.Entrant;
import checkpoint.manager.exceptions.ArguementParseException;
import java.io.File;
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
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FileIO {
    private SimpleDateFormat sdf;
    private HashMap<String, String> filenames;
    public FileIO (HashMap<String, String> args) {
        filenames = args;
        sdf = new SimpleDateFormat("hh:mm");
    }
    public static HashMap<String, String> parseArgs(String[] args) throws ArguementParseException {
        HashMap<String, String> argsList = new HashMap<String, String>();
       
        if (args.length == 10) {
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
                    case 'L':
                        key = "log";
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
    
    public LinkedHashMap<Integer, Entrant> readEntrants() throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new File(filenames.get("entrants")));
        LinkedHashMap<Integer, Entrant> entrants = new LinkedHashMap<Integer, Entrant>();
        
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
    
    public HashMap<Character, Course> readCourses(LinkedHashMap<Integer, Checkpoint> checkpoints) throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new File(filenames.get("courses")));
        
        HashMap<Character, Course> courses = new HashMap<Character, Course>();

        while (in.hasNext()) {
            ArrayList<Integer> nodes = new ArrayList<Integer>();
            Course c = new Course();
            c.setId(in.next().charAt(0));

            while(in.hasNextInt()) {
                int node = in.nextInt();
                if(checkpoints.containsKey(node)) {
                    nodes.add(node);
                }
            }
            c.setLength(nodes.size());
            c.setNodes(nodes);
            courses.put(c.getId(), c);
        }
        
        in.close();
        
        return courses;
    }
    
    public PriorityQueue<CPTimeData> readCheckpointData(LinkedHashMap<Integer, Entrant> entrants, HashMap<Character, Course> courses) throws FileNotFoundException, ParseException, IOException {
        RandomAccessFile fis = new RandomAccessFile(filenames.get("times"), "rw");
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis.getChannel());
        
        PriorityQueue<CPTimeData> times = null;
        
        //clear out the entrants times
        for (Entry entry : entrants.entrySet()) {
            Entrant e = (Entrant) entry.getValue();
            e.setTimes(new ArrayList<CPTimeData>());
        }
        
        if(fl != null) {
            times  = new PriorityQueue<CPTimeData>();
            while (in.hasNext()) {
                CPTimeData cp = new CPTimeData();
                char type = in.next().charAt(0);
                int node = in.nextInt();
                int entrantNo = in.nextInt();
                Date date = sdf.parse(in.next());
                Entrant e = entrants.get(entrantNo);
                
                if(e.getTimes().isEmpty()) {
                    e.resetPosition();
                }
                
                switch(type) {
                    case 'I':
                    case 'E':
                        e.setExcluded(true);
                        break;
                }

                cp.setUpdateType(type);
                cp.setNode(node);
                cp.setEntrantId(entrantNo);
                cp.setTime(date);
                
                Course course = courses.get(e.getCourse());
                if(e.getPosition() >= course.getLength()-2) {
                    e.setFinished(true);
                }
                
                e.incrementPosition();
                e.addTime(cp);
                times.add(cp);
            }
            fl.release();
        }
        
        in.close();
        fis.close();
        
        return times;
    }
    
    public LinkedHashMap<Integer, Checkpoint> readCheckpoints() throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new File(filenames.get("checkpoints")));
        
        LinkedHashMap<Integer, Checkpoint> checkpoints = new LinkedHashMap<Integer, Checkpoint>();
       
        while(in.hasNext()) {
            int id = in.nextInt();
            String type = in.next();    
            if(!type.equals("JN")) {
                checkpoints.put(id, new Checkpoint(id, type));
            }
        }
        
        in.close();
  
        return checkpoints;
    }
    
    private void writeTimeData(PrintWriter pw, CPTimeData data) throws FileNotFoundException, IOException {
        String time = data.getStringTime();
        String output = data.getUpdateType() + " " + data.getNode() + " " + data.getEntrantId() + " " + time;
        pw.write(output + "\n");
        pw.flush();
    }

    public boolean writeTimes(PriorityQueue<CPTimeData> times) throws FileNotFoundException, IOException {
        FileOutputStream fis = new FileOutputStream(new File(filenames.get("times")));
        FileLock fl = fis.getChannel().tryLock();
        PrintWriter pw = new PrintWriter(fis);
        boolean writeSuccess = false;
        
        if(fl != null) {
            for (CPTimeData t : times) {
                writeTimeData(pw, t);
            }
            fl.release();
            writeSuccess = true;
        }
        
        fis.close();
        pw.close();

        
        return writeSuccess;
        
    }
    
    public void writeLog(String updateText) throws FileNotFoundException, IOException {
        String output;
        Date time = new Date();
        FileOutputStream fis = new FileOutputStream(new File(filenames.get("log")), true);
        FileLock fl = fis.getChannel().tryLock();
        PrintWriter pw = new PrintWriter(fis);
        
        if(fl != null) {
            output = sdf.format(time) + " CMP: " + updateText;
            pw.append(output + "\n");
            pw.flush();
        }
        fis.close();
        pw.close();
    }
}
