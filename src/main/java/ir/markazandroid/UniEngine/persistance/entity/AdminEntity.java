package ir.markazandroid.UniEngine.persistance.entity;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.object.PrivateStorageOwner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Ali on 4/9/2019.
 */
@JSON
@Entity
@Table(name = "admin", schema = "uni_engine")
public class AdminEntity implements Serializable, UserDetails, PrivateStorageOwner {
    private int adminId;
    private String name;
    private String username;
    private String password;
    private Integer roleId;
    private RoleEntity role;
    private ArrayList<? extends GrantedAuthority> authorities;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", nullable = false)
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @JSON
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JSON
    @Basic
    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role_id", nullable = true)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminEntity that = (AdminEntity) o;
        return adminId == that.adminId &&
                Objects.equals(name, that.name) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, name, username, password, roleId);
    }

    @JSON(classType = JSON.CLASS_TYPE_OBJECT,clazz = RoleEntity.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id",insertable = false,updatable = false)
    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity roleByRoleId) {
        this.role = roleByRoleId;
    }

    public void setAuthorities(ArrayList<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    /**
     * Should be called within active session from managed bean object
     */
    @Transient
    public void makePrivileges(){
        ArrayList<GrantedAuthority> authorities = getRole()!=null?getRole().getAllAuthorities():new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(PrivilegeEntity.STORAGE_WRITE_PREFIX+getStoragePrefix()+"/**"));
        authorities.add(new SimpleGrantedAuthority(PrivilegeEntity.STORAGE_READ_PREFIX+getStoragePrefix()+"/**"));
        setAuthorities(authorities);
    }

    @Transient
    @Override
    public String getStoragePrefix() {
        return adminId+"_"+username;
    }

    @Transient
    @Override
    public long getMaxCapacity() {
        //5 GB
        return 100*1024*1024L;
    }
}
