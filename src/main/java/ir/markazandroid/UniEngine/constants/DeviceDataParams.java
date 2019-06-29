package ir.markazandroid.UniEngine.constants;

import ir.markazandroid.UniEngine.persistance.cassandra.entity.DeviceData;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by Ali on 6/17/2019.
 */
@Component
public class DeviceDataParams {

    public static final int TYPE_STRING = 1;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_INT = 3;

    public static final String PARAM_DISTANCE = "distance";
    public static final String PARAM_TEMPERATURE = "temperature";
    public static final String PARAM_HUMIDITY = "humidity";
    public static final String PARAM_ALL_FANS = "all_fans";
    public static final String PARAM_LATITUDE = "latitude";
    public static final String PARAM_LONGITUDE = "longitude";
    public static final String PARAM_CPU_USAGE = "cpu_usage";
    public static final String PARAM_RAM_USAGE = "ram_usage";
    public static final String PARAM_STORAGE_USAGE = "storage_usage";


    private HashMap<String, Integer> params;

    public DeviceDataParams() {
        params = new HashMap<>();
        params.put(PARAM_DISTANCE, TYPE_INT);
        params.put(PARAM_TEMPERATURE, TYPE_INT);
        params.put(PARAM_HUMIDITY, TYPE_INT);
        params.put(PARAM_ALL_FANS, TYPE_INT);
        params.put(PARAM_LATITUDE, TYPE_FLOAT);
        params.put(PARAM_LONGITUDE, TYPE_FLOAT);
        params.put(PARAM_CPU_USAGE, TYPE_FLOAT);
        params.put(PARAM_RAM_USAGE, TYPE_FLOAT);
        params.put(PARAM_STORAGE_USAGE, TYPE_FLOAT);
    }

    public void setParamIntoObject(DeviceData deviceData, String param, Object value) {
        int type = params.get(param);
        switch (type) {
            case TYPE_STRING:
                deviceData.getValuesString().put(param, value.toString());
                break;
            case TYPE_INT:
                deviceData.getValuesInt().put(param, (int) value);
                break;
            case TYPE_FLOAT:
                deviceData.getValuesFloat().put(param, (float) value);
                break;
        }
    }
}
