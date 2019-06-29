package ir.markazandroid.UniEngine.controller.deviceApi;

import ir.markazandroid.UniEngine.controller.deviceApi.interfaces.DeviceApiCampaignController;
import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.DeviceEntity;
import ir.markazandroid.UniEngine.service.interfaces.CampaignService;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ali on 6/22/2019.
 */
@RestController
public class DeviceApiCampaignControllerImp implements DeviceApiCampaignController {
    private final CampaignService campaignService;

    public DeviceApiCampaignControllerImp(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Override
    public CampaignEntity getDeviceAssoCampaign(DeviceEntity deviceEntity) {
//        if (deviceEntity.getUserId()!=null)
        return campaignService.getUserCampaigns(/*deviceEntity.getUserId()*/1L)
                .stream().findFirst().orElse(null);
        //      return null;
    }
}
