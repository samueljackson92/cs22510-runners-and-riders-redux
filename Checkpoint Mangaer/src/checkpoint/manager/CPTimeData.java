package checkpoint.manager;

import java.util.Date;

class CPTimeData {
    private int entrantId;
    private cpType type;
    private int node;
    private Date arrival_time;

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
    public cpType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(cpType type) {
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
    public Date getArrival_time() {
        return arrival_time;
    }

    /**
     * @param arrival_time the arrival_time to set
     */
    public void setArrival_time(Date arrival_time) {
        this.arrival_time = arrival_time;
    }
}   