package ir.markazandroid.UniEngine.persistance.entity;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.object.EFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@JSON
@Entity
@Table(name = "play_list", schema = "uni_engine")
public class PlayListEntity implements Serializable {
    private long userId;
    private long playListId;
    private int duration;
    private String name;

    //Should not be Accessed outside DAO Layer
    private String dataString;

    //Transients
    private Data data;

    @JSON
    @Basic
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @JSON
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "play_list_id", nullable = false)
    public long getPlayListId() {
        return playListId;
    }

    public void setPlayListId(long playListId) {
        this.playListId = playListId;
    }

    @JSON
    @Basic
    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "data", nullable = false, length = -1)
    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayListEntity that = (PlayListEntity) o;
        return userId == that.userId &&
                playListId == that.playListId &&
                duration == that.duration &&
                Objects.equals(dataString, that.dataString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, playListId, duration, dataString);
    }

    @JSON(classType = JSON.CLASS_TYPE_OBJECT,clazz = Data.class)
    @Transient
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @JSON
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JSON
    public static class Data implements Serializable{
        private ArrayList<DataEntity> entities;
        private int duration;

        @JSON(classType = JSON.CLASS_TYPE_ARRAY,clazz = DataEntity.class
                ,classTypes = @JSON.ClassType(parameterName = "dataType"
                    ,clazzes = {@JSON.Clazz(name = Image.DATA_TYPE_IMAGE,clazz = Image.class),
                                @JSON.Clazz(name = Video.DATA_TYPE_VIDEO,clazz = Video.class)}))
        public ArrayList<DataEntity> getEntities() {
            return entities;
        }

        public void setEntities(ArrayList<DataEntity> entities) {
            this.entities = entities;
        }

        @JSON
        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    @JSON
    public static class DataEntity implements Serializable{
        private EFile eFile;
        private int order;
        private int duration;
        private String dataType;

        @JSON(classType = JSON.CLASS_TYPE_OBJECT, clazz = EFile.class)
        public EFile geteFile() {
            return eFile;
        }

        public void seteFile(EFile eFile) {
            this.eFile = eFile;
        }

        @JSON
        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        @JSON
        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @JSON
        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
    }

    @JSON
    public static class Video extends DataEntity{
        public static final String DATA_TYPE_VIDEO="video";
        public Video(){
            setDataType(DATA_TYPE_VIDEO);
        }
    }

    @JSON
    public static class Image extends DataEntity implements Serializable{
        public static final String DATA_TYPE_IMAGE="image";

        private String scaleType;

        public Image(){
            setDataType(DATA_TYPE_IMAGE);
        }

        @JSON
        public String getScaleType() {
            return scaleType;
        }

        public void setScaleType(String scaleType) {
            this.scaleType = scaleType;
        }
    }
}
