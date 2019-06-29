package ir.markazandroid.UniEngine.persistance.entity;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.conf.session.MainPrincipalObject;
import ir.markazandroid.UniEngine.object.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Ali on 30/01/2018.
 */
@JSON
@Entity
@Table(name = "user", schema = "uni_engine")
public class UserEntity extends User implements Serializable, MainPrincipalObject {

    public static final int STATUS_DISABLED=-2;
    public static final int STATUS_NOT_VERIFIED=-1;
    public static final int STATUS_VERIFIED=1;
    public static final int STATUS_ACTIVE=2;

    private long userId;
    private String passwordRetype;
    private Timestamp createTime;
    private int status;
    private String phone;
    private String email;
    private String phone2;
    private String firstName;
    private String lastName;
    private Integer roleId;
    private RoleEntity role;

    //Transients
    private Params params;

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
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }


    @Basic
    @Column(name = "password", nullable = false, length = 50)
    @Override
    public String getPassword() {
        return password;
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

    @Transient
    @Override
    public String getUniqueKey() {
        return buildUniqueKey(userId);
    }

    @Transient
    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public static String buildUniqueKey(long userId) {
        return UserEntity.class.getSimpleName() + "_" + userId;
    }


    @JSON
    public static class Params {
        private int deviceCount;

        @JSON
        public int getDeviceCount() {
            return deviceCount;
        }

        public void setDeviceCount(int deviceCount) {
            this.deviceCount = deviceCount;
        }
    }
}
