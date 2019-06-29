package ir.markazandroid.UniEngine.media.views;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.exception.NotFoundException;
import ir.markazandroid.UniEngine.media.annotations.View;
import ir.markazandroid.UniEngine.media.views.inits.ViewInitializr;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;
import ir.markazandroid.UniEngine.service.interfaces.CampaignService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Ali on 4/22/2019.
 */
@JSON
@View("ir.markazandroid.masteradvertiser.views.PlayListView")
public class PlayListView extends BasicView {

    public static final String TYPE_PLAYLIST = "playlist";

    private PlayListEntity.Data data;
    private long playListId;

    public PlayListView() {
        setType(TYPE_PLAYLIST);
    }

    @JSON(classType = JSON.CLASS_TYPE_OBJECT,clazz = PlayListEntity.Data.class)
    public PlayListEntity.Data getData() {
        return data;
    }

    public void setData(PlayListEntity.Data data) {
        this.data = data;
    }

    @JSON
    public long getPlayListId() {
        return playListId;
    }

    public void setPlayListId(long playListId) {
        this.playListId = playListId;
    }

    @Override
    public Object getExtrasObject() {
        return this;
    }


    @Component(TYPE_PLAYLIST + "_initializr")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    static class PlayListInitializr implements ViewInitializr {

        private final CampaignService campaignService;

        PlayListInitializr(CampaignService campaignService) {
            this.campaignService = campaignService;
        }

        @Override
        public void doInit(BasicView basicView) {
            PlayListView playListView = (PlayListView) basicView;
            PlayListEntity playListEntity = campaignService.getPlayListById(playListView.getPlayListId());
            if (playListEntity == null)
                throw new NotFoundException("PlayList " + playListView.getPlayListId() + " Not Found");

            playListView.setData(playListEntity.getData());
            playListView.setDuration(playListEntity.getDuration());
        }
    }
}
