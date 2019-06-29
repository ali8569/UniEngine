package ir.markazandroid.UniEngine.persistance.entity;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.conf.session.MainPrincipalObject;
import ir.markazandroid.UniEngine.object.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Ali on 4/8/2019.
 */
@Entity
@Table(name = "device", schema = "uni_engine")
@JSON
public class DeviceEntity extends User implements Serializable, MainPrincipalObject {

    public static final byte STATUS_NOT_ASSIGNED = 1;
    public static final byte STATUS_ACTIVATED = 2;
    public static final byte STATUS_DISABLED = -1;

    private long deviceId;
    private Long userId;
    private String name;
    private Timestamp createTime;
    private String uuid;
    private Timestamp lastVisit;
    private byte status;
    private String passKey;
    private Date assignDate;
    private String appVersion;
    private String simNumber;
    private Integer simOperator;
    private Integer roleId;
    private RoleEntity role;

    @JSON
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", nullable = false)
    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    @JSON
    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JSON
    @Basic
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Basic
    @Column(name = "uuid", nullable = false, length = 36)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "last_visit", nullable = true)
    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    @JSON
    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }


    @Basic
    @Column(name = "pass_key", nullable = true, length = 14)
    public String getPassKey() {
        return passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    @JSON(classType = JSON.CLASS_TYPE_TIMESTAMP)
    @Basic
    @Column(name = "assign_date", nullable = true)
    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    @Basic
    @Column(name = "app_version", nullable = true, length = -1)
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Basic
    @Column(name = "sim_number", nullable = true, length = -1)
    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    @Basic
    @Column(name = "sim_operator", nullable = true)
    public Integer getSimOperator() {
        return simOperator;
    }

    public void setSimOperator(Integer simOperator) {
        this.simOperator = simOperator;
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
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceEntity that = (DeviceEntity) o;
        return deviceId == that.deviceId &&
                status == that.status &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(lastVisit, that.lastVisit) &&
                Objects.equals(passKey, that.passKey) &&
                Objects.equals(assignDate, that.assignDate) &&
                Objects.equals(appVersion, that.appVersion) &&
                Objects.equals(simNumber, that.simNumber) &&
                Objects.equals(simOperator, that.simOperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, userId, name, createTime, uuid, lastVisit, status, passKey, assignDate, appVersion, simNumber, simOperator);
    }

    @Transient
    @Override
    public String getStoragePrefix() {
        return deviceId + "_" + name;
    }

    @Transient
    @Override
    public long getMaxCapacity() {
        //5 GB
        return 50 * 1024 * 1024L;
    }

    @Transient
    @Override
    public String getUniqueKey() {
        return getClass().getSimpleName() + "_" + deviceId;
    }
}
