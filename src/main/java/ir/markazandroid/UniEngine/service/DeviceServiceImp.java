package ir.markazandroid.UniEngine.service;

import ir.markazandroid.UniEngine.conf.session.PrincipalUpdateAction;
import ir.markazandroid.UniEngine.conf.session.SessionManager;
import ir.markazandroid.UniEngine.exception.NotFoundException;
import ir.markazandroid.UniEngine.exception.PhoneNameExistsException;
import ir.markazandroid.UniEngine.persistance.cassandra.entity.DeviceData;
import ir.markazandroid.UniEngine.persistance.cassandra.interfaces.DeviceDataDAO;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.DeviceDAO;
import ir.markazandroid.UniEngine.service.interfaces.DeviceService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Ali on 6/10/2019.
 */
@Service
public class DeviceServiceImp implements DeviceService {

    private final DeviceDAO deviceDAO;
    private final SessionManager sessionManager;
    private final Random random;
    private final DeviceDataDAO deviceDataDAO;

    public DeviceServiceImp(DeviceDAO deviceDAO, SessionManager sessionManager, Random random, DeviceDataDAO deviceDataDAO) {
        this.deviceDAO = deviceDAO;
        this.sessionManager = sessionManager;
        this.random = random;
        this.deviceDataDAO = deviceDataDAO;
    }

    @Transactional
    @Override
    public DeviceEntity registerDevice(String name) {
        if (deviceDAO.getDeviceByName(name) != null)
            throw new PhoneNameExistsException();

        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setName(name);
        deviceEntity.setUuid(UUID.randomUUID().toString());
        deviceEntity.setPassKey(getSaltString());
        deviceEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        deviceEntity.setStatus(DeviceEntity.STATUS_NOT_ASSIGNED);

        deviceDAO.saveDevice(deviceEntity);
        return deviceEntity;
    }

    @Transactional
    @Override
    public DeviceEntity getDeviceByUUID(String UUID) {
        DeviceEntity deviceEntity = deviceDAO.getDeviceByUUID(UUID);
        if (deviceEntity == null) return null;
        deviceEntity.makePrivileges();
        return deviceEntity;
    }

    @Transactional
    @Override
    public DeviceEntity ownDevice(long userId, String name, String pass) throws NotFoundException {
        DeviceEntity deviceEntity = deviceDAO.getDeviceByName(name);
        if (deviceEntity == null || !deviceEntity.getPassKey().equals(pass) || deviceEntity.getUserId() != null)
            throw new NotFoundException();

        deviceEntity.setUserId(userId);
        deviceEntity.setAssignDate(new Date(System.currentTimeMillis()));

        deviceDAO.updateDevice(deviceEntity);

        sessionManager.updatePrincipals(
                principal -> deviceEntity.getDeviceId() == principal.getDeviceId(),
                (PrincipalUpdateAction<DeviceEntity>) principal -> {
                    principal.setUserId(userId);
                });
        UserEntity onlineUser = sessionManager.getMainPrincipal(() -> UserEntity.buildUniqueKey(userId));
        if (onlineUser != null) onlineUser.getParams().setDeviceCount(onlineUser.getParams().getDeviceCount() + 1);

        return deviceEntity;
    }

    @Override
    public void saveDeviceData(DeviceData deviceData) {

        deviceDataDAO.saveDeviceData(deviceData);
    }

    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        int i = 0;
        while (salt.length() < 14) {// length of the random string.
            i++;
            if (((i) % 5) == 0 && i != 1) {
                salt.append("-");
                continue;
            }
            int index = (int) (random.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
