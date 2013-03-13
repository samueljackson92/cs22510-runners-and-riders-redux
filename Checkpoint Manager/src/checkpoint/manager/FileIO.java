
package checkpoint.manager;

import checkpoint.manager.datamodel.CPTimeData;
import checkpoint.manager.datamodel.Checkpoint;
import checkpoint.manager.datamodel.Course;
import checkpoint.manager.datamodel.Entrant;
import checkpoint.manager.exceptions.ArgumentParseException;
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

/**
 * The Class FileIO.
 * Reads and writes files used during a race event.
 * 
 * @author Samuel Jackson (slj11@aber.ac.uk)
 */
public class FileIO {
    
    /** The simple date formatter */
    private SimpleDateFormat sdf;
    
    /** The names of each of the files passed as command line arguements. */
    private HashMap<String, String> filenames;
    
    /**
     * Instantiates a new instace of FileIO.
     *
     * @param args HashMap of filenames 
     */
    public FileIO (HashMap<String, String> args) {
        filenames = args;
        sdf = new SimpleDateFormat("HH:mm");
    }
    
    /**
     * Parses the command line arguments.
     *
     * @param args the command line arguments
     * @return HashMap of parse arguments
     * @throws ArgumentParseException the argument parse exception thrown if
     * arguments array cannot be parsed.
     */
    public static HashMap<String, String> parseArgs(String[] args) 
    		throws ArgumentParseException {
        HashMap<String, String> argsList = new HashMap<String, String>();
       
        if (args.length == 10) { //all arguments are required
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
                        throw new ArgumentParseException();
                }

                argsList.put(key, args[i+1]);
            }
        } else {
            throw new ArgumentParseException();
        }
        
        return argsList;
    }
    
    /**
     * Read in the entrant's file.
     *
     * @return the linked HashMap of entrant's, identified by an entrant's ID.
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     */
    public LinkedHashMap<Integer, Entrant> readEntrants() 
    		throws FileNotFoundException, IOException {
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
    
    /**
     * Read in the courses file.
     *
     * @param checkpoints the HashMap of nodes that are checkpoints (or medical checkpoints).
     * @return HashMap of courses, identified by the course ID.
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     */
    public HashMap<Character, Course> readCourses(LinkedHashMap<Integer, Checkpoint> checkpoints) 
    		throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new File(filenames.get("courses")));
        
        HashMap<Character, Course> courses = new HashMap<Character, Course>();

        while (in.hasNext()) {
            ArrayList<Integer> nodes = new ArrayList<Integer>();
            Course course = new Course();
            course.setId(in.next().charAt(0));

            while(in.hasNextInt()) {
                int node = in.nextInt();
                if(checkpoints.containsKey(node)) {
                    nodes.add(node);
                }
            }
            course.setNodes(nodes);
            courses.put(course.getId(), course);
        }
        
        in.close();
        
        return courses;
    }
    
    /**
     * Read checkpoint data.
     *
     * @param entrants the list of entrants to update.
     * @param courses the list of all courses.
     * @return PriorityQueue of CPTimeData objects, ordered by oldest time first.
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws ParseException the parse exception thrown when a date cannot be parsed.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     */
    public PriorityQueue<CPTimeData> readCheckpointData(
    		LinkedHashMap<Integer, Entrant> entrants, HashMap<Character, Course> courses) 
    		throws FileNotFoundException, ParseException, IOException {
        RandomAccessFile fis = new RandomAccessFile(filenames.get("times"), "rw");
        FileLock fl = fis.getChannel().tryLock();
        Scanner in = new Scanner(fis.getChannel());
        
        PriorityQueue<CPTimeData> times = null;
        Entrant entrant;
        
        //clear out the entrants times and reset
        for (Entry<Integer, Entrant> entry : entrants.entrySet()) {
            entrant = (Entrant) entry.getValue();
            entrant.setTimes(new ArrayList<CPTimeData>());
            entrant.resetPosition();
        }
        
        //if we have locked the file
        if(fl != null) {
            times  = new PriorityQueue<CPTimeData>();
            
            while (in.hasNext()) {
                CPTimeData chkpoint = new CPTimeData();
                char type = in.next().charAt(0);
                int node = in.nextInt();
                int entrantNo = in.nextInt();
                Date date = sdf.parse(in.next());
                entrant = entrants.get(entrantNo);
                
                //exclude entrant if necessary
                switch(type) {
                    case 'I':
                    case 'E':
                        entrant.setExcluded(true);
                        break;
                }

                //create checkpoint update data
                chkpoint.setUpdateType(type);
                chkpoint.setNode(node);
                chkpoint.setEntrantId(entrantNo);
                chkpoint.setTime(date);
                
                Course course = courses.get(entrant.getCourse());
                if(entrant.getPosition() >= course.getLength()-2) {
                    entrant.setFinished(true);
                }
                
                //update entrant and times list.
                entrant.incrementPosition();
                entrant.addTime(chkpoint);
                times.add(chkpoint);
            }
            
            fl.release();
        }
        
        in.close();
        fis.close();
        
        return times;
    }
    
    /**
     * Read in the checkpoints file.
     *
     * @return the LinkedHashMap of checkpoints (nodes) identified by ID.
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     */
    public LinkedHashMap<Integer, Checkpoint> readCheckpoints() 
    		throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new File(filenames.get("checkpoints")));
        
        LinkedHashMap<Integer, Checkpoint> checkpoints = new LinkedHashMap<Integer, Checkpoint>();
       
        while(in.hasNext()) {
            int id = in.nextInt();
            String type = in.next();  
            
            //ignore junctions
            if(!type.equals("JN")) {
                checkpoints.put(id, new Checkpoint(id, type));
            }
        }
        
        in.close();
  
        return checkpoints;
    }
    
    /**
     * Write out time data to the times file.
     *
     * @param writer the PrintWriter to use to output the time
     * @param data the data to output to file
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     */
    private void writeTimeData(PrintWriter writer, CPTimeData data) throws FileNotFoundException, IOException {
        String time = data.getStringTime();
        String output = data.getUpdateType() + " " + data.getNode() + " " + data.getEntrantId() + " " + time;
        writer.write(output + "\n");
        writer.flush();
    }

    /**
     * Write out the list of times to file.
     *
     * @param times the list of times to output.
     * @return true, if successful at writing
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     */
    public boolean writeTimes(PriorityQueue<CPTimeData> times) throws FileNotFoundException, IOException {
        FileOutputStream fis = new FileOutputStream(new File(filenames.get("times")));
        FileLock fl = fis.getChannel().tryLock();
        PrintWriter writer = new PrintWriter(fis);
        boolean writeSuccess = false;
        
        //we have file lock
        if(fl != null) {
            while (!times.isEmpty()) {
            	//get times in order of priority (oldest first)
                CPTimeData t = times.poll();
                writeTimeData(writer, t);
            }
            fl.release();
            writeSuccess = true;
        }
        
        fis.close();
        writer.close();

        
        return writeSuccess;
        
    }
    
    /**
     * Write to the log file.
     *
     * @param updateText the message to output to the log file
     * @throws FileNotFoundException exception thrown when file cannot be found.
     * @throws IOException Signals that an unexpected I/O exception has occurred.
     * @return true, if successful at writing
     */
    public boolean writeLog(String updateText) throws FileNotFoundException, IOException {
        String outputStr;
        Date time = new Date();
        FileOutputStream fis = new FileOutputStream(new File(filenames.get("log")), true);
        FileLock fl = fis.getChannel().tryLock();
        PrintWriter writer = new PrintWriter(fis);
        boolean writeSuccess = false;
        //we have file lock
        if(fl != null) {
            outputStr = sdf.format(time) + " CMP: " + updateText + "\n";
            writer.append(outputStr);
            writer.flush();
            writeSuccess = true;
        }
        fis.close();
        writer.close();
        
        return writeSuccess;
    }
}
