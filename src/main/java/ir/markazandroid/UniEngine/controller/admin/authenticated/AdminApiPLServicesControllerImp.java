package ir.markazandroid.UniEngine.controller.admin.authenticated;

import ir.markazandroid.UniEngine.JSONParser.Parser;
import ir.markazandroid.UniEngine.controller.admin.authenticated.interfaces.AdminApiPLServicesController;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.service.interfaces.DeviceService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ali on 6/10/2019.
 */
@RestController
public class AdminApiPLServicesControllerImp implements AdminApiPLServicesController {

    private final DeviceService deviceService;
    private final Parser parser;

    public AdminApiPLServicesControllerImp(DeviceService deviceService, Parser parser) {
        this.deviceService = deviceService;
        this.parser = parser;
    }

    @Override
    public JSONObject registerDevice(String name) {
        DeviceEntity deviceEntity = deviceService.registerDevice(name);
        JSONObject outObject = parser.get(deviceEntity);
        outObject.put("passKey", deviceEntity.getPassKey());
        outObject.put("uuid", deviceEntity.getUuid());
        return outObject;
    }
}
