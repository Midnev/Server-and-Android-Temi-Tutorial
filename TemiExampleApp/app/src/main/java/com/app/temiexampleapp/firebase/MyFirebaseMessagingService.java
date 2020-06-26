package com.app.temiexampleapp.firebase;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

     String TAG = "Firebase Logs"; //로그 적을 이름
    public MyFirebaseMessagingService() { }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());//누구에게서 메세지가 보내졌는지 로깅
        // 메세지 내용이 있는지 확인 - 제목과 내용 둘다
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        // 메세지 notification이 있는지 확인 - 메세지 상세 내용
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        String firebaseTitle = remoteMessage.getNotification().getTitle();
        String firebaseText = remoteMessage.getNotification().getBody(); //메세지 내용만 가져온다.
        //do something
    }

}
