package ir.markazandroid.UniEngine.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
public class ScheduleHasTimeLineEntityPK implements Serializable {
    private int scheduleId;
    private long timeLineId;

    @Column(name = "schedule_id", nullable = false)
    @Id
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Column(name = "time_line_id", nullable = false)
    @Id
    public long getTimeLineId() {
        return timeLineId;
    }

    public void setTimeLineId(long timeLineId) {
        this.timeLineId = timeLineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleHasTimeLineEntityPK that = (ScheduleHasTimeLineEntityPK) o;
        return scheduleId == that.scheduleId &&
                timeLineId == that.timeLineId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, timeLineId);
    }
}
