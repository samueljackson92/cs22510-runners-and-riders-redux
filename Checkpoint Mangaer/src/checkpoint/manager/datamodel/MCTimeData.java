package checkpoint.manager.datamodel;

import java.util.Date;

public class MCTimeData extends CPTimeData {
    private Date departTime;

    /**
     * @return the departTime
     */
    public Date getDepartTime() {
        return departTime;
    }

    /**
     * @param departTime the departTime to set
     */
    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }
}
