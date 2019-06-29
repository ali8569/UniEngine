package ir.markazandroid.UniEngine.persistance.cassandra.interfaces;

import ir.markazandroid.UniEngine.persistance.cassandra.entity.DeviceData;

/**
 * Created by Ali on 6/17/2019.
 */
public interface DeviceDataDAO {

    void saveDeviceData(DeviceData deviceData);
}
