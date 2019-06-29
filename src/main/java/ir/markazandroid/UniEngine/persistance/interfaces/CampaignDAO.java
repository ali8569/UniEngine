package ir.markazandroid.UniEngine.persistance.interfaces;

import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.LayoutEntity;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;

import java.util.List;

/**
 * Created by Ali on 4/14/2019.
 */
public interface CampaignDAO {

    void savePlayList(PlayListEntity playListEntity);
    void updatePlayList(PlayListEntity playListEntity);

    List<PlayListEntity> getUserPlayLists(long userId);

    PlayListEntity getPlayListById(long playListId);


    void saveCampaign(CampaignEntity campaignEntity);
    void updateCampaign(CampaignEntity campaignEntity);

    List<CampaignEntity> getUserCampaigns(long userId);

    CampaignEntity getCampaignById(long campaignId);

    /**
     * Cached value high performance
     *
     * @param userId
     * @return
     */
    long getUserCampaignCount(long userId);


    LayoutEntity getLayoutById(long layoutId);

    void saveLayout(LayoutEntity layoutEntity);

    void detach(Object o);


}
