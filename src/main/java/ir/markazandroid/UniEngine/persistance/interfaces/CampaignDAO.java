package ir.markazandroid.UniEngine.persistance.interfaces;

import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;

import java.util.List;

/**
 * Created by Ali on 4/14/2019.
 */
public interface CampaignDAO {

    void savePlayList(PlayListEntity playListEntity);
    void updatePlayList(PlayListEntity playListEntity);
    List<PlayListEntity> getUserPlayLists(int userId);


    void saveCampaign(CampaignEntity campaignEntity);
    void updateCampaign(CampaignEntity campaignEntity);
    List<CampaignEntity> getUserCampaigns(int userId);

}
