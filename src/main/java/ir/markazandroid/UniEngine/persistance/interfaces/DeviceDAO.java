package ir.markazandroid.UniEngine.persistance.interfaces;

import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;

import java.util.List;

/**
 * Created by Ali on 6/9/2019.
 */
public interface DeviceDAO {

    void saveDevice(DeviceEntity deviceEntity);

    void updateDevice(DeviceEntity deviceEntity, String... fields);

    DeviceEntity getDeviceById(long deviceId, String... fields);

    DeviceEntity getDeviceByName(String name);

    DeviceEntity getDeviceByUUID(String UUID);

    List<DeviceEntity> getUserDevices(long userId);

}
