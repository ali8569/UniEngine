package ir.markazandroid.UniEngine.persistance.entity;

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
public class DeviceEntity implements Serializable {
    private long deviceId;
    private Long userId;
    private String name;
    private Timestamp createTime;
    private String uuid;
    private String fcmToken;
    private Timestamp lastVisit;
    private byte status;
    private String passKey;
    private Date assignDate;
    private String appVersion;
    private String simNumber;
    private Integer simOperator;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", nullable = false)
    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
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
    @Column(name = "name", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    @Column(name = "fcm_token", nullable = false, length = -1)
    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Basic
    @Column(name = "last_visit", nullable = true)
    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

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
                Objects.equals(fcmToken, that.fcmToken) &&
                Objects.equals(lastVisit, that.lastVisit) &&
                Objects.equals(passKey, that.passKey) &&
                Objects.equals(assignDate, that.assignDate) &&
                Objects.equals(appVersion, that.appVersion) &&
                Objects.equals(simNumber, that.simNumber) &&
                Objects.equals(simOperator, that.simOperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, userId, name, createTime, uuid, fcmToken, lastVisit, status, passKey, assignDate, appVersion, simNumber, simOperator);
    }

}
