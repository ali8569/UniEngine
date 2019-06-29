package ir.markazandroid.UniEngine.controller.userApi.interfaces;

import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ali on 4/13/2019.
 */
@RequestMapping(value = "/userApi/device", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserApiDeviceController {

    @PostMapping("/own")
    DeviceEntity ownDevice(UserEntity userEntity, @RequestParam String name, @RequestParam String pass);

    @GetMapping("/status")
    ResponseEntity getDevicesStatus(UserEntity userEntity);

    //@GetMapping("/list")
    //List<DeviceEntity> getDeviceList(UserEntity userEntity);


}
