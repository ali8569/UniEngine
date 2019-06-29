package ir.markazandroid.UniEngine.persistance.entity;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.media.views.BasicView;
import ir.markazandroid.UniEngine.media.views.PlayListView;
import ir.markazandroid.UniEngine.media.views.WebPageView;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@JSON
@Entity
@Table(name = "campaign", schema = "uni_engine")
public class CampaignEntity implements Serializable {
    private long campaignId;
    private long userId;
    private String androidDataString;
    private int duration;
    private Collection<TimeLineHasCampaignEntity> timeLineHasCampaigns;
    private String name;
    private Long layoutId;
    private String dataString;

    //transients
    private AndroidData androidData;
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
    @Column(name = "android_data_string", nullable = true, length = -1)
    public String getAndroidDataString() {
        return androidDataString;
    }

    public void setAndroidDataString(String data) {
        this.androidDataString = data;
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
                Objects.equals(androidDataString, that.androidDataString) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(campaignId, userId, androidDataString, duration);
    }

    @OneToMany(mappedBy = "campaign")
    public Collection<TimeLineHasCampaignEntity> getTimeLineHasCampaigns() {
        return timeLineHasCampaigns;
    }

    public void setTimeLineHasCampaigns(Collection<TimeLineHasCampaignEntity> timeLineHasCampaignsByCampaignId) {
        this.timeLineHasCampaigns = timeLineHasCampaignsByCampaignId;
    }

    @Transient
    @JSON(classType = JSON.CLASS_TYPE_OBJECT, clazz = AndroidData.class)
    public AndroidData getAndroidData() {
        return androidData;
    }

    public void setAndroidData(AndroidData androidData) {
        this.androidData = androidData;
    }

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "layout_id", nullable = true)
    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    @Basic
    @Column(name = "data_string", nullable = true, length = -1)
    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    @Transient
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JSON
    public static class AndroidData {
        private String xml;
        private Map<String,Object> extras;

        public AndroidData(String xml, Map<String, Object> extras) {
            this.xml = xml;
            this.extras = extras;
        }

        public AndroidData() {
        }

        @JSON(classType = JSON.CLASS_TYPE_MAP)
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

    @JSON
    public static class Data {

        private ArrayList<BasicView> views;

        @JSON(classType = JSON.CLASS_TYPE_ARRAY, clazz = BasicView.class
                , classTypes = @JSON.ClassType(parameterName = "type"
                , clazzes = {
                @JSON.Clazz(name = PlayListView.TYPE_PLAYLIST, clazz = PlayListView.class)
                , @JSON.Clazz(name = WebPageView.TYPE_WEB_PAGE, clazz = WebPageView.class)}
        ))
        public ArrayList<BasicView> getViews() {
            return views;
        }

        public void setViews(ArrayList<BasicView> views) {
            this.views = views;
        }
    }
}
