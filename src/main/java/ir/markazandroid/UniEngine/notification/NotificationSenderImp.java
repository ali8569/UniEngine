package ir.markazandroid.UniEngine.notification;

import ir.markazandroid.UniEngine.JSONParser.Parser;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by Ali on 30/11/2017.
 */

public class NotificationSenderImp implements NotificationSender {

    private static final String PROVIDER_URL="https://fcm.googleapis.com/fcm/send";

    private OkHttpClient client;
    private String authKey;
    private Parser parser;

    public NotificationSenderImp(OkHttpClient client, String authKey, Parser parser) throws NoSuchMethodException {
        this.client = client;
        this.authKey = authKey;
        this.parser = parser;

        parser.addClass(Notification.class);
        parser.addClass(Notification.NotificationBody.class);
    }

    @Override
    public void sendNotification(Notification notification){
        Request request = new Request.Builder()
                .url(PROVIDER_URL)
                .post(RequestBody.create(MediaType.parse("application/json")
                        ,parser.get(notification).toString()))
                .header("Authorization","key="+authKey)
                .header("Content-Type","application/json")
                .build();
        //System.out.println(parser.get(notification).toString(1));
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("Notif Error: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) System.err.println("Notif Response Error: "+response.body().string());
                response.close();
            }
        });

    }

    @Override
    public Response sendNotificationOnThread(Notification notification) throws IOException {
        Request request = new Request.Builder()
                .url(PROVIDER_URL)
                .post(RequestBody.create(MediaType.parse("application/json")
                        ,parser.get(notification).toString()))
                .header("Authorization","key="+authKey)
                .header("Content-Type","application/json")
                .build();
        //System.out.println(parser.get(notification).toString(1));
        return client.newCall(request).execute();

    }

}
