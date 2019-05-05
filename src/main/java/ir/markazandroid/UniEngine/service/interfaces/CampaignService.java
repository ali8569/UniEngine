package ir.markazandroid.UniEngine.service.interfaces;

import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;

import java.util.List;

/**
 * Created by Ali on 4/14/2019.
 */
public interface CampaignService {

    void saveOrUpdatePlayList(PlayListEntity playListEntity);
    List<PlayListEntity> getUserPlayLists(int userId);

}
