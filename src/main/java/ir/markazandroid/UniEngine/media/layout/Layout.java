package ir.markazandroid.UniEngine.media.layout;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ali on 4/16/2019.
 */
public class Layout implements Serializable {

    private Map<String,Side> sides;
    private Map<String,Region> regions;

    public Layout(){
        sides=new HashMap<>();
        regions=new HashMap<>();
        initParentSides();
    }

    public Layout(LayoutData layoutData){
        this();
        layoutData.getSides().forEach(sideData ->
                        newSide(sideData.getPercent(),Orientation.valueOf(sideData.getOrientation())));

        layoutData.getRegions().forEach(regionData ->
                newRegion(findSideById(regionData.getLeftId()),
                        findSideById(regionData.getTopId()),
                        findSideById(regionData.getRightId()),
                        findSideById(regionData.getBottomId())));

    }

    private void initParentSides(){
        newSide(0,Orientation.horizontal);
        newSide(0,Orientation.vertical);
        newSide(100,Orientation.horizontal);
        newSide(100,Orientation.vertical);
    }

    public Side newSide(int percent,Orientation orientation){
        Side newSide = new Side(percent,orientation);
        if (sides.containsKey(newSide.getId()))
            return sides.get(newSide.getId());
        else{
            sides.put(newSide.getId(),newSide);
            return newSide;
        }
    }

    public Region newRegion(Side left,Side top,Side right,Side bottom){
        Region reRegion = new Region(left, top, right, bottom);
        if (regions.containsKey(reRegion.getId()))
            return regions.get(reRegion.getId());
        else{
            regions.put(reRegion.getId(),reRegion);
            return reRegion;
        }
    }

    public Side getTopParentSide(){
        return findSideById(Orientation.horizontal+"_0");
    }

    public Side getLeftParentSide(){
        return findSideById(Orientation.vertical+"_0");
    }

    public Side getRightParentSide(){
        return findSideById(Orientation.vertical+"_100");
    }

    public Side getBottomParentSide(){
        return findSideById(Orientation.horizontal+"_100");
    }

    public List<Region> getSideDependentRegions(Side side){
        return regions.values().parallelStream().filter(region ->
                region.isDependentOnSide(side)
        ).distinct().collect(Collectors.toList());
    }

    public boolean removeSide(Side side){
        Side removedSide = sides.remove(side.getId());
        if (removedSide!=null){
            getSideDependentRegions(removedSide)
                    .forEach(region -> regions.remove(region.getId()));
            return true;
        }
        else return false;
    }

    public Side findSideById(String id){
        return sides.get(id);
    }

    public Region findRegionById(String id) {
        return regions.get(id);
    }

    public LayoutData toLayoutData(){
        return new LayoutData(this);
    }

    public Collection<Region> getRegions() {
        return Collections.unmodifiableCollection(regions.values());
    }

    public Collection<Side> getSides() {
        return Collections.unmodifiableCollection(sides.values());
    }
}
