
package checkpoint.manager.datamodel;

import java.util.ArrayList;

/**
 * The Class Entrant.
 * Holds data about a single entrant in the event.
 * @author Samuel Jackson (slj11@aber.ac.uk)
 */
public class Entrant {
    
    /** The name of the entrant. */
    private String name;
    
    /** The course the entrant is on. */
    private char course;
    
    /** The id of the entrant. */
    private int id;
    
    /** The list of time updates an entrant has been checked in on. */
    private ArrayList<CPTimeData> times;
    
    /** Whether the entrant has been exlcuded or not. */
    private boolean excluded;
    
    /** Whether the entrant has finished or not. */
    private boolean finished;
    
    /** The position of the entrant on the course. */
    private int position;

    /**
     * Instantiates a new entrant.
     */
    public Entrant() {
        times = new ArrayList<CPTimeData>();
        excluded = false;
        finished = false;
        position = -1;
    }
    
    /**
     * Gets the name of this entrant.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this entrant.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course the entrant is on.
     *
     * @return the course
     */
    public char getCourse() {
        return course;
    }

    /**
     * Sets the course the entrant is on.
     *
     * @param course the course to set
     */
    public void setCourse(char course) {
        this.course = course;
    }

    /**
     * Gets the id of the entrant.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the entrant.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the times the entrant has been check in at.
     *
     * @return the times
     */
    public ArrayList<CPTimeData> getTimes() {
        return times;
    }

    /**
     * Sets the times the entrant has been check in at.
     *
     * @param times the times to set
     */
    public void setTimes(ArrayList<CPTimeData> times) {
        this.times = times;
    }
    
    /**
     * Adds a time update to the entrant
     *
     * @param cpData the cp data
     */
    public void addTime(CPTimeData cpData) {
        this.times.add(cpData);
    }

    /**
     * Checks if is excluded.
     *
     * @return the excluded
     */
    public boolean isExcluded() {
        return excluded;
    }

    /**
     * Sets the as excluded or not.
     *
     * @param excluded the excluded to set
     */
    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }

    /**
     * Gets the position of the entrant.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
    }
    
    /**
     * Reset position of the entrant.
     */
    public void resetPosition() {
        position = -1;
    }
    
    /**
     * Increment position of the entrant.
     */
    public void incrementPosition() {
        position++;
    }
    
    /**
     * Check if the entrant has started.
     *
     * @return true, if entrant has started
     */
    public boolean hasStarted() {
        return (times.size() > 0);
    }
    
    /**
     * Gets the latest time currently avalible for the entrant.
     *
     * @return the latest time
     */
    public CPTimeData getLatestTime() {
        return times.get(times.size()-1);
    }

    /**
     * Checks if is finished has finished.
     *
     * @return the finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets the finished as been finished or not.
     *
     * @param finished the finished to set
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
