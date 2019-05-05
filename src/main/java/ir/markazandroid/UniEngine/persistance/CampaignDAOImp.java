package ir.markazandroid.UniEngine.persistance;

import ir.markazandroid.UniEngine.JSONParser.Parser;
import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.persistance.interfaces.CampaignDAO;
import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Ali on 4/14/2019.
 */
@Repository
public class CampaignDAOImp implements CampaignDAO {

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
    public List<PlayListEntity> getUserPlayLists(int userId) {
        List<PlayListEntity> list = entityManager.createQuery("from PlayListEntity where userId=:userId", PlayListEntity.class)
                .setParameter("userId", userId)
                .getResultList();
        list.parallelStream().forEach(playList -> playList.setData(parser.get(PlayListEntity.Data.class, new JSONObject(playList.getDataString()))));
        return list;
    }


    @Override
    public void saveCampaign(CampaignEntity campaignEntity) {
        campaignEntity.setDataString(parser.get(campaignEntity.getData()).toString());
        entityManager.persist(campaignEntity);
    }

    @Override
    public void updateCampaign(CampaignEntity campaignEntity) {
        campaignEntity.setDataString(parser.get(campaignEntity.getData()).toString());
        entityManager.unwrap(Session.class).update(campaignEntity);
    }

    @Override
    public List<CampaignEntity> getUserCampaigns(int userId) {
        List<CampaignEntity> list = entityManager.createQuery("from CampaignEntity where userId=:userId", CampaignEntity.class)
                .setParameter("userId", userId)
                .getResultList();

        list.parallelStream().forEach(campaign -> campaign.setData(
                parser.get(CampaignEntity.Data.class,
                        new JSONObject(campaign.getDataString()))));
        return list;
    }
}
