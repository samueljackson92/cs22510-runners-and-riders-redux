
package checkpoint.manager.datamodel;

import java.util.ArrayList;

public class Entrant {
    private String name;
    private char course;
    private int id;
    private ArrayList<CPTimeData> times;
    private boolean excluded;
    private boolean finished;
    private int position;

    public Entrant() {
        times = new ArrayList<CPTimeData>();
        excluded = false;
        finished = false;
        position = -1;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the course
     */
    public char getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(char course) {
        this.course = course;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the times
     */
    public ArrayList<CPTimeData> getTimes() {
        return times;
    }

    /**
     * @param times the times to set
     */
    public void setTimes(ArrayList<CPTimeData> times) {
        this.times = times;
    }
    
    public void addTime(CPTimeData cpData) {
        this.times.add(cpData);
    }

    /**
     * @return the excluded
     */
    public boolean isExcluded() {
        return excluded;
    }

    /**
     * @param excluded the excluded to set
     */
    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }
    
    public void resetPosition() {
        position = -1;
    }
    
    public void incrementPosition() {
        position++;
    }
    
    public boolean hasStarted() {
        return (times.size() > 0);
    }
    
    public CPTimeData getLatestTime() {
        return times.get(times.size()-1);
    }

    /**
     * @return the finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @param finished the finished to set
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
