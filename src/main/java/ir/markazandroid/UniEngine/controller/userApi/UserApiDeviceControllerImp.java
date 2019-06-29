package ir.markazandroid.UniEngine.controller.userApi;

import ir.markazandroid.UniEngine.controller.userApi.interfaces.UserApiDeviceController;
import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.service.interfaces.DeviceService;
import ir.markazandroid.UniEngine.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ali on 6/15/2019.
 */
@RestController
public class UserApiDeviceControllerImp implements UserApiDeviceController {

    private final DeviceService deviceService;
    private final UserService userService;

    public UserApiDeviceControllerImp(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @Override
    public DeviceEntity ownDevice(UserEntity userEntity, String name, String pass) {
        return deviceService.ownDevice(userEntity.getUserId(), name, pass);
    }

    @Override
    public ResponseEntity getDevicesStatus(UserEntity userEntity) {
        int onlineDevices = userService.getUserOnlineDevices(userEntity.getUserId()).size();

        return new ResponseObject.Builder().param("online", onlineDevices)
                .param("total", userEntity.getParams().getDeviceCount()).build();
    }
}
