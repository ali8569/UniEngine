package ir.markazandroid.UniEngine.persistance.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@Entity
@Table(name = "schedule_has_time_line", schema = "uni_engine")
@IdClass(ScheduleHasTimeLineEntityPK.class)
public class ScheduleHasTimeLineEntity {
    private int scheduleId;
    private long timeLineId;
    private byte croned;
    private String cron;
    private ScheduleEntity schedule;
    private TimeLineEntity timeLine;
    private Collection<TimeLineDateEntity> timeLineDates;

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

    @Basic
    @Column(name = "croned", nullable = false)
    public byte getCroned() {
        return croned;
    }

    public void setCroned(byte croned) {
        this.croned = croned;
    }

    @Basic
    @Column(name = "cron", nullable = true, length = -1)
    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleHasTimeLineEntity that = (ScheduleHasTimeLineEntity) o;
        return scheduleId == that.scheduleId &&
                timeLineId == that.timeLineId &&
                croned == that.croned &&
                Objects.equals(cron, that.cron);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, timeLineId, croned, cron);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id", nullable = false,insertable = false,updatable = false)
    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_line_id", referencedColumnName = "time_line_id", nullable = false,insertable = false,updatable = false)
    public TimeLineEntity getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(TimeLineEntity timeLine) {
        this.timeLine = timeLine;
    }

    @OneToMany(mappedBy = "scheduleHasTimeLine")
    public Collection<TimeLineDateEntity> getTimeLineDates() {
        return timeLineDates;
    }

    public void setTimeLineDates(Collection<TimeLineDateEntity> timeLineDates) {
        this.timeLineDates = timeLineDates;
    }
}
