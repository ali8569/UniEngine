package ir.markazandroid.UniEngine.object;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;

import java.io.Serializable;

/**
 * Created by Ali on 2/18/2019.
 */
@JSON
public class InternetPackage implements Serializable {

    public static final int OPERATOR_IRANCELL=1;
    public static final int OPERATOR_HAMRAH=2;

    private String rowID;
    private int serviceId;
    private String providerTitle;
    private String serviceName;
    private long servicePrice;
    private String profileName;
    private int profileTypeID;
    private String profileTitle;
    private String volume;
    private String durationKey;
    private String durationName;
    private String description;

    private int operatorId;
    private long rp;


    @JSON(name = "RowID")
    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    @JSON(name = "ServiceID")
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @JSON(name ="ProviderTitle" )
    public String getProviderTitle() {
        return providerTitle;
    }

    public void setProviderTitle(String providerTitle) {
        this.providerTitle = providerTitle;
    }

    @JSON(name = "ServiceName")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @JSON(name ="ServicePrice" )
    public long getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(long servicePrice) {
        this.servicePrice = servicePrice;
    }

    @JSON(name = "ProfileName")
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    @JSON(name = "ProfileTypeID")
    public int getProfileTypeID() {
        return profileTypeID;
    }

    public void setProfileTypeID(int profileTypeID) {
        this.profileTypeID = profileTypeID;
    }

    @JSON(name = "ProfileTitle")
    public String getProfileTitle() {
        return profileTitle;
    }

    public void setProfileTitle(String profileTitle) {
        this.profileTitle = profileTitle;
    }

    @JSON(name = "Volume")
    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @JSON(name = "DurationKey")
    public String getDurationKey() {
        return durationKey;
    }

    public void setDurationKey(String durationKey) {
        this.durationKey = durationKey;
    }

    @JSON(name = "DurationName")
    public String getDurationName() {
        return durationName;
    }

    public void setDurationName(String durationName) {
        this.durationName = durationName;
    }

    @JSON(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public long getRp() {
        return rp;
    }

    public void setRp(long rp) {
        this.rp = rp;
    }
}
