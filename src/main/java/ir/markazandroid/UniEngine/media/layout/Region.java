package ir.markazandroid.UniEngine.media.layout;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Ali on 4/16/2019.
 */
public final class Region implements Serializable {
    private Side left;
    private Side top;
    private Side right;
    private Side bottom;
    private String id;

    Region(Side left, Side top, Side right, Side bottom) {
        if (!left.getOrientation().equals(Orientation.vertical) || !right.getOrientation().equals(Orientation.vertical)
                || !top.getOrientation().equals(Orientation.horizontal) || !bottom.getOrientation().equals(Orientation.horizontal))
            throw new RuntimeException("wrong orientations");

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        id = StringUtils.arrayToDelimitedString(new String[]{left.getId(), top.getId(), right.getId(), bottom.getId()}, "_");
    }

    public Side getLeft() {
        return left;
    }

    public Side getTop() {
        return top;
    }

    public Side getRight() {
        return right;
    }

    public Side getBottom() {
        return bottom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;
        Region region = (Region) o;
        return Objects.equals(left, region.left) &&
                Objects.equals(top, region.top) &&
                Objects.equals(right, region.right) &&
                Objects.equals(bottom, region.bottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, top, right, bottom);
    }

    public String getId() {
        return id;
    }

    public boolean isDependentOnSide(Side side) {
        return getLeft().equals(side)
                || getTop().equals(side)
                || getRight().equals(side)
                || getBottom().equals(side);
    }
}
