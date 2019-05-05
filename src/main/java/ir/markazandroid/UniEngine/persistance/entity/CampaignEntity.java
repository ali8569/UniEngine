package ir.markazandroid.UniEngine.persistance.entity;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@JSON
@Entity
@Table(name = "campaign", schema = "uni_engine", catalog = "")
public class CampaignEntity implements Serializable {
    private long campaignId;
    private long userId;
    private String dataString;
    private int duration;
    private Collection<TimeLineHasCampaignEntity> timeLineHasCampaigns;

    //transients
    private Data data;

    @JSON
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id", nullable = false)
    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "data", nullable = true, length = -1)
    public String getDataString() {
        return dataString;
    }

    public void setDataString(String data) {
        this.dataString = data;
    }

    @JSON
    @Basic
    @Column(name = "duration", nullable = true)
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignEntity that = (CampaignEntity) o;
        return campaignId == that.campaignId &&
                userId == that.userId &&
                Objects.equals(dataString, that.dataString) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId, userId, dataString, duration);
    }

    @OneToMany(mappedBy = "campaign")
    public Collection<TimeLineHasCampaignEntity> getTimeLineHasCampaigns() {
        return timeLineHasCampaigns;
    }

    public void setTimeLineHasCampaigns(Collection<TimeLineHasCampaignEntity> timeLineHasCampaignsByCampaignId) {
        this.timeLineHasCampaigns = timeLineHasCampaignsByCampaignId;
    }

    @Transient
    @JSON(classType = JSON.CLASS_TYPE_OBJECT,clazz = Data.class)
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JSON
    public static class Data{
        private String xml;
        private Map<String,Object> extras;

        public Data(String xml, Map<String, Object> extras) {
            this.xml = xml;
            this.extras = extras;
        }

        public Data() {
        }

        @JSON
        public Map<String, Object> getExtras() {
            return extras;
        }

        public void setExtras(Map<String, Object> extras) {
            this.extras = extras;
        }

        @JSON
        public String getXml() {
            return xml;
        }

        public void setXml(String xml) {
            this.xml = xml;
        }
    }
}
