package ir.markazandroid.UniEngine.persistance.cassandra.entity;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Ali on 6/17/2019.
 */
@JSON
@Table(name = "device_data")
public class DeviceData implements Serializable {

    @PartitionKey
    @Column(name = "device_id")
    private long deviceId;

    @PartitionKey(1)
    private String date;

    @ClusteringColumn
    private Timestamp time;

    @Column(name = "values_string")
    private HashMap<String, String> valuesString;

    @Column(name = "values_int")
    private HashMap<String, Integer> valuesInt;

    @Column(name = "values_float")
    private HashMap<String, Float> valuesFloat;

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public HashMap<String, String> getValuesString() {
        if (valuesString == null) valuesString = new HashMap<>();
        return valuesString;
    }

    public void setValuesString(HashMap<String, String> valuesString) {
        this.valuesString = valuesString;
    }

    public HashMap<String, Integer> getValuesInt() {
        if (valuesInt == null) valuesInt = new HashMap<>();
        return valuesInt;
    }

    public void setValuesInt(HashMap<String, Integer> valuesInt) {
        this.valuesInt = valuesInt;
    }

    public HashMap<String, Float> getValuesFloat() {
        if (valuesFloat == null) valuesFloat = new HashMap<>();
        return valuesFloat;
    }

    public void setValuesFloat(HashMap<String, Float> valuesFloat) {
        this.valuesFloat = valuesFloat;
    }

    public static String getDate(Timestamp time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return String.format(Locale.US, "%02d-%02d", calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
}
