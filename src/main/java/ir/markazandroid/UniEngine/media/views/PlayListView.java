package ir.markazandroid.UniEngine.media.views;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.media.annotations.View;
import ir.markazandroid.UniEngine.persistance.entity.PlayListEntity;

/**
 * Created by Ali on 4/22/2019.
 */
@View("ImageView")
public class PlayListView {

    private PlayListEntity.Data data;


    @JSON(classType = JSON.CLASS_TYPE_OBJECT,clazz = PlayListEntity.Data.class)
    public PlayListEntity.Data getData() {
        return data;
    }

    public void setData(PlayListEntity.Data data) {
        this.data = data;
    }

}
