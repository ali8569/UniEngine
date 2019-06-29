package ir.markazandroid.UniEngine.controller.userApi;

import ir.markazandroid.UniEngine.controller.userApi.interfaces.UserApiCampaignController;
import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.service.interfaces.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ali on 6/8/2019.
 */
@RestController
public class UserApiCampaignControllerImp implements UserApiCampaignController {

    private final CampaignService campaignService;

    public UserApiCampaignControllerImp(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Override
    public ResponseEntity<ResponseObject> saveOrUpdateCampaign(UserEntity userEntity, CampaignWSData data) {
        CampaignEntity campaignEntity = campaignService.obtainCampaign(userEntity.getUserId(), data.getCampaignId());

        campaignEntity.setName(data.getName());
        campaignEntity.setDuration(data.getDuration());
        campaignEntity.setLayoutId(data.getLayoutId());
        campaignEntity.setData(data.getData());

        campaignService.initCampaignData(campaignEntity);

        boolean isUpdate = campaignEntity.getCampaignId() != 0;
        campaignService.saveOrUpdateCampaign(campaignEntity);

        return new ResponseObject.Builder().status(200).message(String.format("Campaign %s Successfully.", isUpdate ? "Updated" : "Saved")).timestamp(System.currentTimeMillis()).build();
    }

    @Override
    public List<CampaignWSData> getUserCampaigns(UserEntity userEntity) {
        List<CampaignEntity> campaignEntities = campaignService.getUserCampaigns(userEntity.getUserId());
        return campaignEntities.stream()
                .map(this::campaignToData)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignWSData getCampaignDetails(UserEntity userEntity, long campaignId) {
        CampaignEntity campaignEntity = campaignService.obtainCampaign(userEntity.getUserId(), campaignId);
        CampaignWSData data = campaignToData(campaignEntity);
        data.setData(campaignEntity.getData());
        return data;
    }

    private CampaignWSData campaignToData(CampaignEntity campaignEntity) {
        CampaignWSData data = new CampaignWSData();
        data.setCampaignId(campaignEntity.getCampaignId());
        data.setDuration(campaignEntity.getDuration());
        data.setName(campaignEntity.getName());
        data.setLayoutId(campaignEntity.getLayoutId());
        return data;
    }
}
