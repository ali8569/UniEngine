package ir.markazandroid.UniEngine.media.views;

import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;
import ir.markazandroid.UniEngine.media.annotations.View;

/**
 * Created by Ali on 4/22/2019.
 */
@JSON
@View("ir.markazandroid.masteradvertiser.views.WebPageView")
public class WebPageView extends BasicView {

    public static final String TYPE_WEB_PAGE = "webpage";

    private String homeUrl;

    public WebPageView() {
        setType(TYPE_WEB_PAGE);
    }


    @Override
    public Object getExtrasObject() {
        return this;
    }

    @JSON
    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }
}
