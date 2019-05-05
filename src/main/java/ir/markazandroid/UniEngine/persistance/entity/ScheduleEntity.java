package ir.markazandroid.UniEngine.persistance.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@Entity
@Table(name = "schedule", schema = "uni_engine")
public class ScheduleEntity implements Serializable {
    private int scheduleId;
    private Byte type;
    private Long userId;
    private Collection<ScheduleHasTimeLineEntity> scheduleHasTimeLines;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleEntity that = (ScheduleEntity) o;
        return scheduleId == that.scheduleId &&
                Objects.equals(type, that.type) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, type, userId);
    }


    @OneToMany(mappedBy = "schedule")
    public Collection<ScheduleHasTimeLineEntity> getScheduleHasTimeLines() {
        return scheduleHasTimeLines;
    }

    public void setScheduleHasTimeLines(Collection<ScheduleHasTimeLineEntity> scheduleHasTimeLines) {
        this.scheduleHasTimeLines = scheduleHasTimeLines;
    }
}
