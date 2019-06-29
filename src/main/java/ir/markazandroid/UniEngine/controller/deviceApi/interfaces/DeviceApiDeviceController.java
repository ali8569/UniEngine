package ir.markazandroid.UniEngine.controller.deviceApi.interfaces;

import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ali on 6/10/2019.
 */
@RequestMapping(value = "/deviceApi/device", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface DeviceApiDeviceController {

    @GetMapping("/getMe")
    DeviceEntity getMe(DeviceEntity deviceEntity);

    @PostMapping("/data")
    ResponseEntity<ResponseObject> onData(DeviceEntity deviceEntity, @RequestBody JSONObject jsonObject);


}

