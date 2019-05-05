package ir.markazandroid.UniEngine.persistance.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Ali on 4/9/2019.
 */
@Entity
@Table(name = "privilege", schema = "uni_engine")
public class PrivilegeEntity implements Serializable, GrantedAuthority {

    public static final String ACCESS_USER_DASHBOARD="ACCESS_USER_DASHBOARD";
    public static final String ACCESS_ADMIN_DASHBOARD="ACCESS_ADMIN_DASHBOARD";
    public static final String ACCESS_PL_SERVICES="ACCESS_PL_SERVICES";
    public static final String STORAGE_WRITE_PREFIX="STORAGE_WRITE_PREFIX_";
    public static final String STORAGE_READ_PREFIX="STORAGE_READ_PREFIX_";

    private int privilegeId;
    private String value;
    private String describtion;
    private String name;
    
    public PrivilegeEntity(){}
    
    public PrivilegeEntity(String value){
        this.value=value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id", nullable = false)
    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 100)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "describtion", nullable = true, length = -1)
    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivilegeEntity that = (PrivilegeEntity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Transient
    @Override
    public String getAuthority() {
        return value;
    }
}
