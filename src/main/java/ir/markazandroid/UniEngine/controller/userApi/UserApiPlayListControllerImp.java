package ir.markazandroid.UniEngine.controller.userApi;

import ir.markazandroid.UniEngine.controller.userApi.interfaces.UserApiPlayListController;
import ir.markazandroid.UniEngine.object.ResponseObject;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.persistance.entity.UserEntity;
import ir.markazandroid.UniEngine.service.interfaces.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Ali on 5/22/2019.
 */
@RestController
public class UserApiPlayListControllerImp implements UserApiPlayListController {

    private final CampaignService campaignService;

    @Autowired
    public UserApiPlayListControllerImp(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Override
    public List<PlayListEntity> getPlayLists(UserEntity userEntity) {
        return campaignService.getUserPlayLists(userEntity.getUserId());
    }

    @Override
    public ResponseEntity<ResponseObject> saveOrUpdatePlayList(UserEntity userEntity, PlayListEntity playListEntity) {
        try {
            validatePlayList(playListEntity);
        } catch (Exception e) {
            return new ResponseObject.Builder().status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage()).timestamp(System.currentTimeMillis()).build();
        }
        boolean isUpdate = playListEntity.getPlayListId() != 0;
        playListEntity.setUserId(userEntity.getUserId());

        campaignService.saveOrUpdatePlayList(playListEntity);
        return new ResponseObject.Builder().status(200).message(String.format("PlayList %s Successfully.", isUpdate ? "Updated" : "Saved")).timestamp(System.currentTimeMillis()).build();
    }

    private void validatePlayList(PlayListEntity playListEntity) throws RuntimeException {
        playListEntity.getData().setDuration(0);
        if (playListEntity.getName() == null) playListEntity.setName("بی نام");
        if (playListEntity.getData() == null
                || playListEntity.getData().getEntities() == null
                || playListEntity.getData().getEntities().isEmpty())
            throw new RuntimeException("پلی لیست نمی تواند خالی باشد.");

        playListEntity.getData().getEntities()
                .forEach(dataEntity -> {
                    if (dataEntity.geteFile() == null || dataEntity.geteFile().geteFileId() == null)
                        throw new RuntimeException("اشکال در انتخاب فایل. لطفا فایل ها را چک کنید.");

                    dataEntity.geteFile().setUrl(null);
                    dataEntity.geteFile().setLastModified(0);
                });
    }
}
