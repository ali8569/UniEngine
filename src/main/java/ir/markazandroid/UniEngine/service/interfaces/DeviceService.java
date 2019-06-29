package ir.markazandroid.UniEngine.service.interfaces;

import ir.markazandroid.UniEngine.exception.NotFoundException;
import ir.markazandroid.UniEngine.persistance.cassandra.entity.DeviceData;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;

/**
 * Created by Ali on 6/9/2019.
 */
public interface DeviceService {

    DeviceEntity registerDevice(String name);

    DeviceEntity getDeviceByUUID(String UUID);

    DeviceEntity ownDevice(long userId, String name, String pass) throws NotFoundException;

    void saveDeviceData(DeviceData deviceData);


}
