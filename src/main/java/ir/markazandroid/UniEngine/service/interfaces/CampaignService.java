package ir.markazandroid.UniEngine.service.interfaces;

import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.LayoutEntity;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;

import java.util.List;

/**
 * Created by Ali on 4/14/2019.
 */
public interface CampaignService {

    void saveOrUpdatePlayList(PlayListEntity playListEntity);

    List<PlayListEntity> getUserPlayLists(long userId);

    PlayListEntity getPlayListById(long playListId);

    CampaignEntity obtainCampaign(long userId, long campaignId);

    void initCampaignData(CampaignEntity campaignEntity);

    void saveOrUpdateCampaign(CampaignEntity campaignEntity);

    List<CampaignEntity> getUserCampaigns(long userId);

    void saveLayout(LayoutEntity layoutEntity);

    LayoutEntity getLayoutById(long layoutId);


}
