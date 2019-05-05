package ir.markazandroid.UniEngine.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
public class TimeLineHasCampaignEntityPK implements Serializable {
    private long timeLineId;
    private long campaignId;

    @Column(name = "time_line_id", nullable = false)
    @Id
    public long getTimeLineId() {
        return timeLineId;
    }

    public void setTimeLineId(long timeLineId) {
        this.timeLineId = timeLineId;
    }

    @Column(name = "campaign_id", nullable = false)
    @Id
    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeLineHasCampaignEntityPK that = (TimeLineHasCampaignEntityPK) o;
        return timeLineId == that.timeLineId &&
                campaignId == that.campaignId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeLineId, campaignId);
    }
}
