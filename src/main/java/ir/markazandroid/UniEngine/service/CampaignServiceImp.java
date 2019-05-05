package ir.markazandroid.UniEngine.service;

import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.CampaignDAO;
import ir.markazandroid.UniEngine.service.interfaces.CampaignService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Ali on 4/14/2019.
 */
@Service
public class CampaignServiceImp implements CampaignService {

    private final CampaignDAO campaignDAO;

    public CampaignServiceImp(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Transactional
    @Override
    public void saveOrUpdatePlayList(PlayListEntity playListEntity) {
        calculateDurations(playListEntity);
        if (playListEntity.getPlayListId()!=0)
            campaignDAO.updatePlayList(playListEntity);
        else
            campaignDAO.savePlayList(playListEntity);
    }

    private void calculateDurations(PlayListEntity playListEntity) {
        playListEntity.getData().getEntities()
                .forEach(dataEntity ->{
                        playListEntity.getData().setDuration(
                                playListEntity.getData().getDuration()+dataEntity.getDuration());
                });
    }

    @Override
    public List<PlayListEntity> getUserPlayLists(int userId) {
        return campaignDAO.getUserPlayLists(userId);
    }
}
