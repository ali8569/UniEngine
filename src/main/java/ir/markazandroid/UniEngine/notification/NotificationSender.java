package ir.markazandroid.UniEngine.notification;

import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Ali on 30/11/2017.
 */
public interface NotificationSender {

    void sendNotification(Notification notification);
    Response sendNotificationOnThread(Notification notification) throws IOException;
}
