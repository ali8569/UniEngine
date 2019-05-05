package ir.markazandroid.UniEngine.persistance.entity;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.object.PrivateStorageOwner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Ali on 30/01/2018.
 */
@JSON
@Entity
@Table(name = "user", schema = "uni_engine")
public class UserEntity implements Serializable, UserDetails, PrivateStorageOwner {

    public static final int STATUS_DISABLED=-2;
    public static final int STATUS_NOT_VERIFIED=-1;
    public static final int STATUS_VERIFIED=1;
    public static final int STATUS_ACTIVE=2;

    private long userId;
    private String username;
    private String password;
    private String passwordRetype;
    private Timestamp createTime;
    private ArrayList<? extends GrantedAuthority> authorities;
    private int status;
    private String phone;
    private String email;
    private String phone2;
    private String firstName;
    private String lastName;
    private Integer roleId;
    private RoleEntity role;

    @JSON
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @JSON
    @Basic
    @Column(name = "username", nullable = false, length = 200)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(ArrayList<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JSON(classType = JSON.CLASS_TYPE_TIMESTAMP)
    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, createTime);
    }

    @Transient
    public String getPasswordRetype() {
        return passwordRetype;
    }

    public void setPasswordRetype(String passwordRetype) {
        this.passwordRetype = passwordRetype;
    }

    @JSON
    @Basic
    @Column(name = "status", nullable = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JSON
    @Basic
    @Column(name = "phone", nullable = true, length = 13)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JSON
    @Basic
    @Column(name = "email", nullable = true, length = 200)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone2", nullable = true, length = 13)
    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = -1)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = -1)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "role_id", nullable = true)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id",insertable = false,updatable = false)
    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Transient
    @Override
    public String getStoragePrefix() {
        return userId+"_"+username;
    }

    @Transient
    @Override
    public long getMaxCapacity() {
        //5 GB
        return 5*1024*1024*1024L;
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
}
