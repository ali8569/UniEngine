package ir.markazandroid.UniEngine.persistance.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@Entity
@Table(name = "time_line", schema = "uni_engine")
public class TimeLineEntity implements Serializable {
    private long timeLineId;
    private Long userId;
    private Collection<TimeLineHasCampaignEntity> timeLineHasCampaigns;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_line_id", nullable = false)
    public long getTimeLineId() {
        return timeLineId;
    }

    public void setTimeLineId(long timeLineId) {
        this.timeLineId = timeLineId;
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
        TimeLineEntity that = (TimeLineEntity) o;
        return timeLineId == that.timeLineId &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeLineId, userId);
    }

    @OneToMany(mappedBy = "timeLine")
    public Collection<TimeLineHasCampaignEntity> getTimeLineHasCampaigns() {
        return timeLineHasCampaigns;
    }

    public void setTimeLineHasCampaigns(Collection<TimeLineHasCampaignEntity> timeLineHasCampaigns) {
        this.timeLineHasCampaigns = timeLineHasCampaigns;
    }
}
