package ir.markazandroid.UniEngine.notification;



import ir.markazandroid.UniEngine.JSONParser.annotations.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Ali on 30/11/2017.
 */
public final class Notification implements Serializable {
    private String to;
    private ArrayList<String> tokens;
    private LinkedHashMap<String,String> data;
    private NotificationBody notificationBody;
    private String appPackage;


    private Notification() {
    }

    @JSON
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @JSON(name = "registration_ids",classType = JSON.CLASS_TYPE_ARRAY)
    public ArrayList<String> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<String> tokens) {
        this.tokens = tokens;
    }

    @JSON(classType = "map")
    public LinkedHashMap<String, String> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, String> data) {
        this.data = data;
    }

    @JSON(name = "notification",classType = JSON.CLASS_TYPE_OBJECT,clazz = NotificationBody.class)
    public NotificationBody getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(NotificationBody notificationBody) {
        this.notificationBody = notificationBody;
    }

    @JSON(name = "restricted_package_name")
    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public static final class NotificationBody implements Serializable{
        private String title;
        private String body;

        private NotificationBody() {

        }

        @JSON
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @JSON
        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    public static class Builder{

        private Notification notification;
        private NotificationBody notificationBody;

        public Builder(){
            notification=new Notification();
            notificationBody=new NotificationBody();
            notification.setNotificationBody(notificationBody);
        }

        public Builder toTopic(String topic){
            notification.setTo("/topics/"+topic);
            return this;
        }
        public Builder toDevice(String token){
            notification.setTo(token);
            return this;
        }

        public Builder toMultiDevices(ArrayList<String> tokens){
            if (tokens.size()==1) return toDevice(tokens.get(0));
            notification.setTokens(tokens);
            return this;
        }

        public Builder data(String key ,String value){
            if (notification.getData()==null) notification.setData(new LinkedHashMap<>());
            notification.getData().put("gcm_notifapp_"+key,value);
            return this;
        }
        public Builder appPackage(String appPackage){
            notification.setAppPackage(appPackage);
            return this;
        }
        public Builder title(String title){
            notificationBody.setTitle(title);
            return this;
        }
        public Builder body(String body){
            notificationBody.setBody(body);
            return this;
        }

        public Notification build(){
            return notification;
        }
    }
}
