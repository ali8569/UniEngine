package ir.markazandroid.UniEngine.media.views;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;

import java.io.Serializable;

/**
 * Created by Ali on 4/22/2019.
 */
@JSON
public abstract class BasicView implements Serializable {

    public static final int DURATION_MATCH = -1;

    private String type;
    private String id;
    private int duration;

    public abstract Object getExtrasObject();

    @JSON
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JSON
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JSON
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
