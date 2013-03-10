package checkpoint.manager.datamodel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CPTimeData implements Comparable {
    private int entrantId;
    private CPType type;
    private char updateType;
    private int node;
    private Date time;
    private final SimpleDateFormat sdf;

    public CPTimeData() {
        sdf = new SimpleDateFormat("HH:mm");
    }

    /**
     * @return the entrantId
     */
    public int getEntrantId() {
        return entrantId;
    }

    /**
     * @param entrantId the entrantId to set
     */
    public void setEntrantId(int entrantId) {
        this.entrantId = entrantId;
    }

    /**
     * @return the type
     */
    public CPType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CPType type) {
        this.type = type;
    }

    /**
     * @return the cpId
     */
    public int getNode() {
        return node;
    }

    /**
     * @param cpId the cpId to set
     */
    public void setNode(int cpId) {
        this.node = cpId;
    }

    /**
     * @return the arrival_time
     */
    public String getStringTime() {
        return sdf.format(time);
    }
    
    private Date getTime() {
        return time;
    }

    /**
     * @param arrival_time the arrival_time to set
     */
    public void setTime(Date time) {

        this.time = time;
    }

    /**
     * @return the updateType
     */
    public char getUpdateType() {
        return updateType;
    }

    /**
     * @param updateType the updateType to set
     */
    public void setUpdateType(char updateType) {
        this.updateType = updateType;
    }

    @Override
    public int compareTo(Object t) {
        Date compareDate = ((CPTimeData) t).getTime();
        return time.compareTo(compareDate);
    }
}   