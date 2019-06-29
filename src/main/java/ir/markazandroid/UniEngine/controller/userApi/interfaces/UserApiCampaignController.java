package ir.markazandroid.UniEngine.controller.userApi.interfaces;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ali on 4/13/2019.
 */
@RequestMapping(value = "/userApi/campaign", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserApiCampaignController {

    @PostMapping(value = "/saveOrUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseObject> saveOrUpdateCampaign(UserEntity userEntity, @RequestBody CampaignWSData data);

    @GetMapping("/list")
    List<CampaignWSData> getUserCampaigns(UserEntity userEntity);

    @GetMapping("/get")
    CampaignWSData getCampaignDetails(UserEntity userEntity, long campaignId);

    @JSON
    class CampaignWSData implements Serializable {

        private long layoutId;
        private long campaignId;
        private String name;
        private int duration;
        private CampaignEntity.Data data;

        public CampaignWSData() {
        }


        @JSON
        public long getLayoutId() {
            return layoutId;
        }

        public void setLayoutId(long layoutId) {
            this.layoutId = layoutId;
        }

        @JSON
        public long getCampaignId() {
            return campaignId;
        }

        public void setCampaignId(long campaignId) {
            this.campaignId = campaignId;
        }


        @JSON
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JSON
        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @JSON(classType = JSON.CLASS_TYPE_OBJECT, clazz = CampaignEntity.Data.class)
        public CampaignEntity.Data getData() {
            return data;
        }

        public void setData(CampaignEntity.Data data) {
            this.data = data;
        }

    }

}
