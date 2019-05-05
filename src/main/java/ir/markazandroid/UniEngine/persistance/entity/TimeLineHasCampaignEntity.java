package ir.markazandroid.UniEngine.persistance.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@Entity
@Table(name = "time_line_has_campaign", schema = "uni_engine", catalog = "")
@IdClass(TimeLineHasCampaignEntityPK.class)
public class TimeLineHasCampaignEntity {
    private long timeLineId;
    private long campaignId;
    private String times;
    private TimeLineEntity timeLine;
    private CampaignEntity campaign;

    @Id
    @Column(name = "time_line_id", nullable = false)
    public long getTimeLineId() {
        return timeLineId;
    }

    public void setTimeLineId(long timeLineId) {
        this.timeLineId = timeLineId;
    }

    @Id
    @Column(name = "campaign_id", nullable = false)
    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    @Basic
    @Column(name = "times", nullable = false, length = -1)
    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeLineHasCampaignEntity that = (TimeLineHasCampaignEntity) o;
        return timeLineId == that.timeLineId &&
                campaignId == that.campaignId &&
                Objects.equals(times, that.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeLineId, campaignId, times);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_line_id", referencedColumnName = "time_line_id", nullable = false,insertable = false,updatable = false)
    public TimeLineEntity getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(TimeLineEntity timeLine) {
        this.timeLine = timeLine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", referencedColumnName = "campaign_id", nullable = false,insertable = false,updatable = false)
    public CampaignEntity getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignEntity campaign) {
        this.campaign = campaign;
    }
}
