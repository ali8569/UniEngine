package ir.markazandroid.UniEngine.persistance.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Ali on 4/15/2019.
 */
@Entity
@Table(name = "layout", schema = "uni_engine")
public class LayoutEntity implements Serializable {
    private long layoutId;
    private Long userId;
    private String data;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "layout_id", nullable = false)
    public long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(long layoutId) {
        this.layoutId = layoutId;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "data", nullable = false, length = -1)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayoutEntity that = (LayoutEntity) o;
        return layoutId == that.layoutId &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(layoutId, userId, data);
    }



}
