package ir.markazandroid.UniEngine.controller.deviceApi.interfaces;

import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ali on 6/10/2019.
 */
@RequestMapping(value = "/deviceApi/campaign", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface DeviceApiCampaignController {

    @GetMapping("/get")
    CampaignEntity getDeviceAssoCampaign(DeviceEntity deviceEntity);
}

