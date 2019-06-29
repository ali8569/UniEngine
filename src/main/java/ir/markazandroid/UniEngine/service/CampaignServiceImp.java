package ir.markazandroid.UniEngine.service;

import ir.markazandroid.UniEngine.exception.ForbiddenException;
import ir.markazandroid.UniEngine.exception.NotFoundException;
import ir.markazandroid.UniEngine.media.content.ContentHolder;
import ir.markazandroid.UniEngine.media.layout.Layout;
import ir.markazandroid.UniEngine.media.layout.Region;
import ir.markazandroid.UniEngine.media.views.inits.ViewInitializr;
import ir.markazandroid.UniEngine.media.views.inits.ViewInitializrs;
import ir.markazandroid.UniEngine.persistance.entity.CampaignEntity;
import ir.markazandroid.UniEngine.persistance.entity.LayoutEntity;
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
    private final ViewInitializrs viewInitializrs;

    public CampaignServiceImp(CampaignDAO campaignDAO, ViewInitializrs viewInitializrs) {
        this.campaignDAO = campaignDAO;
        this.viewInitializrs = viewInitializrs;
    }

    //TODO check PlayList and File Permissions
    @Transactional
    @Override
    public void saveOrUpdatePlayList(PlayListEntity playListEntity) {
        calculateDurations(playListEntity);
        if (playListEntity.getPlayListId() != 0)
            campaignDAO.updatePlayList(playListEntity);
        else
            campaignDAO.savePlayList(playListEntity);
    }

    private void calculateDurations(PlayListEntity playListEntity) {
        playListEntity.getData().getEntities()
                .forEach(dataEntity -> {
                    playListEntity.getData().setDuration(
                            playListEntity.getData().getDuration() + dataEntity.getDuration());
                });

        playListEntity.setDuration(playListEntity.getData().getDuration());
    }

    @Override
    public List<PlayListEntity> getUserPlayLists(long userId) {
        return campaignDAO.getUserPlayLists(userId);
    }

    @Override
    public PlayListEntity getPlayListById(long playListId) {
        return campaignDAO.getPlayListById(playListId);
    }

    @Override
    public CampaignEntity obtainCampaign(long userId, long campaignId) {

        CampaignEntity campaignEntity;
        if (campaignId != 0) {
            campaignEntity = campaignDAO.getCampaignById(campaignId);
            if (campaignEntity == null) throw new NotFoundException();
            if (campaignEntity.getUserId() != userId) throw new ForbiddenException();
            campaignDAO.detach(campaignEntity);
        } else {
            campaignEntity = new CampaignEntity();
            campaignEntity.setUserId(userId);
        }

        return campaignEntity;
    }

    @Override
    public void initCampaignData(CampaignEntity campaignEntity) {
        LayoutEntity layoutEntity = campaignDAO.getLayoutById(campaignEntity.getLayoutId());
        if (layoutEntity == null || (layoutEntity.getUserId() != null && !layoutEntity.getUserId().equals(campaignEntity.getUserId())))
            throw new NotFoundException();

        Layout layout = new Layout(layoutEntity.getLayoutData());
        ContentHolder holder = new ContentHolder(layout);

        campaignEntity.getData().getViews().forEach(view -> {
            Region region = layout.findRegionById(view.getId());
            if (region == null) throw new RuntimeException("This Layout does not contain region " + view.getId());

            ViewInitializr initializr = viewInitializrs.getViewInitializrForView(view);
            if (initializr != null) initializr.doInit(view);

            holder.assignViewToRegion(region, view);
        });

        campaignEntity.setAndroidData(holder.generateData());
    }

    @Transactional
    @Override
    public void saveOrUpdateCampaign(CampaignEntity campaignEntity) {
        if (campaignEntity.getCampaignId() == 0)
            campaignDAO.saveCampaign(campaignEntity);
        else
            campaignDAO.updateCampaign(campaignEntity);
    }

    @Override
    public List<CampaignEntity> getUserCampaigns(long userId) {
        return campaignDAO.getUserCampaigns(userId);
    }

    @Transactional
    @Override
    public void saveLayout(LayoutEntity layoutEntity) {
        campaignDAO.saveLayout(layoutEntity);
    }

    @Override
    public LayoutEntity getLayoutById(long layoutId) {
        return campaignDAO.getLayoutById(layoutId);
    }
}
