
package checkpoint.manager;

import java.util.ArrayList;

class Entrant {
    private String name;
    private char course;
    private int id;
    private ArrayList<CPTimeData> times;
    private boolean excluded = false;

    public Entrant() {
        times = new ArrayList<CPTimeData>();
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
}
