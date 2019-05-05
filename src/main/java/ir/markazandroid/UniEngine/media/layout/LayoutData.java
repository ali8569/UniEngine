package ir.markazandroid.UniEngine.media.layout;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Ali on 4/16/2019.
 */
public class LayoutData implements Serializable{

    private ArrayList<SideData> sides;
    private ArrayList<RegionData> regions;

    public LayoutData(){
    }

    LayoutData(Layout layout){
        sides=layout.getSides().parallelStream().
                map(SideData::new)
                .collect(Collectors.toCollection(ArrayList::new));

        regions=layout.getRegions().parallelStream()
                .map(RegionData::new)
                .collect(Collectors.toCollection(ArrayList::new));


    }


    @JSON(classType = JSON.CLASS_TYPE_ARRAY,clazz = RegionData.class)
    public ArrayList<RegionData> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<RegionData> regions) {
        this.regions = regions;
    }


    @JSON(classType = JSON.CLASS_TYPE_ARRAY,clazz = SideData.class)
    public ArrayList<SideData> getSides() {
        return sides;
    }

    public void setSides(ArrayList<SideData> sides) {
        this.sides = sides;
    }

    public static class SideData implements Serializable {
        private int percent;
        private String orientation;

        public SideData(Side side) {
            this.percent = side.getPercent();
            this.orientation = side.getOrientation().name();
        }

        public SideData() {
        }

        @JSON
        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }

        @JSON
        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }
    }

    public static class RegionData implements Serializable{
        private String leftId;
        private String topId;
        private String rightId;
        private String bottomId;

        public RegionData(Region region) {
            this.leftId = region.getLeft().getId();
            this.topId = region.getTop().getId();
            this.rightId = region.getRight().getId();
            this.bottomId = region.getBottom().getId();
        }

        public RegionData() {
        }

        @JSON
        public String getTopId() {
            return topId;
        }

        public void setTopId(String topId) {
            this.topId = topId;
        }

        @JSON
        public String getLeftId() {
            return leftId;
        }

        public void setLeftId(String leftId) {
            this.leftId = leftId;
        }

        @JSON
        public String getRightId() {
            return rightId;
        }

        public void setRightId(String rightId) {
            this.rightId = rightId;
        }

        @JSON
        public String getBottomId() {
            return bottomId;
        }

        public void setBottomId(String bottomId) {
            this.bottomId = bottomId;
        }
    }

}
