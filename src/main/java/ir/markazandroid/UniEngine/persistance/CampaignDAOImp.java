package ir.markazandroid.UniEngine.persistance;

import ir.markazandroid.UniEngine.JSONParser.Parser;
import ir.markazandroid.UniEngine.conf.CacheConfs;
import ir.markazandroid.UniEngine.media.layout.LayoutData;
import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.LayoutEntity;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.CampaignDAO;
import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ali on 4/14/2019.
 */
@Repository
public class CampaignDAOImp implements CampaignDAO {

    public static final String CACHE_KEY_USER_CAMPAIGN_COUNT = "key_user_campaign_count";

    @PersistenceContext
    private EntityManager entityManager;

    private final Parser parser;

    @Autowired
    public CampaignDAOImp(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void savePlayList(PlayListEntity playListEntity) {
        playListEntity.setDataString(parser.get(playListEntity.getData()).toString());
        entityManager.persist(playListEntity);
    }

    @Override
    public void updatePlayList(PlayListEntity playListEntity) {
        playListEntity.setDataString(parser.get(playListEntity.getData()).toString());
        entityManager.unwrap(Session.class).update(playListEntity);
    }

    @Override
    public List<PlayListEntity> getUserPlayLists(long userId) {
        List<PlayListEntity> list = entityManager.createQuery("from PlayListEntity where userId=:userId", PlayListEntity.class)
                .setParameter("userId", userId)
                .getResultList();
        list.parallelStream().forEach(playList -> playList.setData(parser.get(PlayListEntity.Data.class, new JSONObject(playList.getDataString()))));
        return list;
    }

    @Override
    public PlayListEntity getPlayListById(long playListId) {
        PlayListEntity playListEntity = entityManager.find(PlayListEntity.class, playListId);
        if (playListEntity == null) return null;

        playListEntity.setData(parser.get(PlayListEntity.Data.class, new JSONObject(playListEntity.getDataString())));
        return playListEntity;
    }


    @CacheEvict(cacheNames = CacheConfs.CACHE_NAME_COUNTS, key = "T(ir.markazandroid.UniEngine.persistance.CampaignDAOImp).CACHE_KEY_USER_CAMPAIGN_COUNT+'_'+#campaignEntity.userId")
    @Override
    public void saveCampaign(CampaignEntity campaignEntity) {
        setCampaignJsonStrings(campaignEntity);
        entityManager.persist(campaignEntity);
    }

    @Override
    public void updateCampaign(CampaignEntity campaignEntity) {
        setCampaignJsonStrings(campaignEntity);
        entityManager.unwrap(Session.class).update(campaignEntity);
    }

    @Override
    public List<CampaignEntity> getUserCampaigns(long userId) {
        List<CampaignEntity> list = entityManager.createQuery("from CampaignEntity where userId=:userId", CampaignEntity.class)
                .setParameter("userId", userId)
                .getResultList();

        list.parallelStream().forEach(this::setCampaignObjectsFromJsonString);
        return list;
    }

    @Override
    public CampaignEntity getCampaignById(long campaignId) {
        CampaignEntity campaignEntity = entityManager.find(CampaignEntity.class, campaignId);
        if (campaignEntity == null) return null;
        setCampaignObjectsFromJsonString(campaignEntity);
        return campaignEntity;
    }

    @Cacheable(cacheNames = CacheConfs.CACHE_NAME_COUNTS, key = "T(ir.markazandroid.UniEngine.persistance.CampaignDAOImp).CACHE_KEY_USER_CAMPAIGN_COUNT+'_'+#userId")
    @Override
    public long getUserCampaignCount(long userId) {
        return (Long) entityManager.createQuery("select count(*) from CampaignEntity where userId=:userId")
                .getSingleResult();
    }

    @Override
    public LayoutEntity getLayoutById(long layoutId) {
        LayoutEntity layoutEntity = entityManager.find(LayoutEntity.class, layoutId);
        if (layoutEntity == null) return null;
        layoutEntity.setLayoutData(parser.get(LayoutData.class, new JSONObject(layoutEntity.getData())));
        return layoutEntity;
    }

    @Override
    public void saveLayout(LayoutEntity layoutEntity) {
        layoutEntity.setData(parser.get(layoutEntity.getLayoutData()).toString());
        entityManager.persist(layoutEntity);
    }

    @Override
    public void detach(Object o) {
        entityManager.detach(o);
    }

    private void setCampaignJsonStrings(CampaignEntity campaignEntity) {
        if (campaignEntity.getAndroidData() != null)
            campaignEntity.setAndroidDataString(parser.get(campaignEntity.getAndroidData()).toString());
        if (campaignEntity.getData() != null)
            campaignEntity.setDataString(parser.get(campaignEntity.getData()).toString());
    }

    private void setCampaignObjectsFromJsonString(CampaignEntity campaignEntity) {
        if (campaignEntity.getAndroidDataString() != null)
            campaignEntity.setAndroidData(parser.get(CampaignEntity.AndroidData.class, new JSONObject(campaignEntity.getAndroidDataString())));
        if (campaignEntity.getDataString() != null)
            campaignEntity.setData(parser.get(CampaignEntity.Data.class, new JSONObject(campaignEntity.getDataString())));
    }

}
