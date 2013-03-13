package checkpoint.manager.datamodel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class CPTimeData.
 * Holds data about a single checkpoint time update.
 * 
 * @author Samuel Jackson (slj11@aber.ac.uk)
 */
public class CPTimeData implements Comparable<CPTimeData> {
    
    /** The entrant id of the entrant. */
    private int entrantId;
    
    /** The type of checkpoint. */
    private CPType type;
    
    /** The update type. One of the 5 types of updates allowed (T, I, A, D, E) . */
    private char updateType;
    
    /** The node that the checkpoint update occurred on. */
    private int node;
    
    /** The time the update occurred. */
    private Date time;
    
    /** The date formatter object. */
    private final SimpleDateFormat sdf;

    /**
     * Instantiates a new instance of a checkpoint time data object.
     */
    public CPTimeData() {
        sdf = new SimpleDateFormat("HH:mm");
    }

    /**
     * Gets the entrant's id.
     *
     * @return the entrantId
     */
    public int getEntrantId() {
        return entrantId;
    }

    /**
     * Sets the entrant id.
     *
     * @param entrantId the entrantId to set
     */
    public void setEntrantId(int entrantId) {
        this.entrantId = entrantId;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public CPType getType() {
        return type;
    }

    /**
     * Sets the type of update.
     *
     * @param type the type to set
     */
    public void setType(CPType type) {
        this.type = type;
    }

    /**
     * Gets the node that the update occurred on.
     *
     * @return the cpId
     */
    public int getNode() {
        return node;
    }

    /**
     * Sets the node that the update occurred on.
     *
     * @param checkpointId the cpId to set
     */
    public void setNode(int checkpointId) {
        this.node = checkpointId;
    }

    /**
     * Gets the time as a string.
     *
     * @return the time
     */
    public String getStringTime() {
        return sdf.format(time);
    }
    
    /**
     * Gets the time (Date) object.
     *
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time.
     *
     * @param time the new time
     */
    public void setTime(Date time) {

        this.time = time;
    }

    /**
     * Gets the update type. One of the 5 types of updates (T,I,A,D,E)
     *
     * @return the updateType
     */
    public char getUpdateType() {
        return updateType;
    }

    /**
     * Sets the update type. One of the 5 types of updates (T,I,A,D,E)
     *
     * @param updateType the updateType to set
     */
    public void setUpdateType(char updateType) {
        this.updateType = updateType;
    }

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(CPTimeData t) {
    	return sdf.format(time).compareTo(sdf.format(t.getTime()));
	}
}   
