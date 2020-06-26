package com.example.temi.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
public class ServersideFireBaseService {

    private String dbUrl = "";
    private String configPath ="src/main/resources/firebase-adminsdk.json";

    public ServersideFireBaseService() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(configPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(dbUrl)
                    .build();
            FirebaseApp.initializeApp(options);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static final String ANDROID_NEWS_ICON_RESOURCE = "news_alert_icon";

    public String sendNotification(String title, String body, String topic) throws Exception {
        Message message = Message.builder()
                .setNotification(new Notification(title, body))
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                                .setIcon(ANDROID_NEWS_ICON_RESOURCE)
                                .build())
                        .build())
                .setTopic(topic)
                .build();
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }
}
