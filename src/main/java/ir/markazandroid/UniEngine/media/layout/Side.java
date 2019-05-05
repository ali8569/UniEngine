package ir.markazandroid.UniEngine.media.layout;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Ali on 4/16/2019.
 */
public final class Side implements Serializable {


    private int percent;
    private Orientation orientation;
    private String id;

    Side(int percent, Orientation orientation) {
        this.percent = percent;
        this.orientation = orientation;
        id=orientation.name()+"_"+percent;
    }

    Side(String id){
        String[] idParts = id.split("_");
        percent=Integer.parseInt(idParts[1]);
        orientation=Orientation.valueOf(idParts[0]);
        id=orientation.name()+"_"+percent;
    }

    public int getPercent() {
        return percent;
    }

    public Orientation getOrientation() {
        return orientation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Side)) return false;
        Side side = (Side) o;
        return percent == side.percent &&
                Objects.equals(orientation, side.orientation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(percent, orientation);
    }

    public String getId() {
        return id;
    }

    public boolean isParentSide(){
        return percent==0 || percent==100;
    }
}
