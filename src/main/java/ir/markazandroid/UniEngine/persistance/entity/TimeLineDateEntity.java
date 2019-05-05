package ir.markazandroid.UniEngine.persistance.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@Entity
@Table(name = "time_line_date", schema = "uni_engine")
public class TimeLineDateEntity implements Serializable {
    private int scheduleId;
    private long timeLineId;
    private Date date;
    private ScheduleHasTimeLineEntity scheduleHasTimeLine;

    @Id
    @Column(name = "schedule_id", nullable = false)
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Id
    @Column(name = "time_line_id", nullable = false)
    public long getTimeLineId() {
        return timeLineId;
    }

    public void setTimeLineId(long timeLineId) {
        this.timeLineId = timeLineId;
    }

    @Id
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeLineDateEntity that = (TimeLineDateEntity) o;
        return scheduleId == that.scheduleId &&
                timeLineId == that.timeLineId &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, timeLineId, date);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id", nullable = false,insertable = false,updatable = false),
            @JoinColumn(name = "time_line_id", referencedColumnName = "time_line_id", nullable = false,insertable = false,updatable = false)})
    public ScheduleHasTimeLineEntity getScheduleHasTimeLine() {
        return scheduleHasTimeLine;
    }

    public void setScheduleHasTimeLine(ScheduleHasTimeLineEntity scheduleHasTimeLine) {
        this.scheduleHasTimeLine = scheduleHasTimeLine;
    }
}
