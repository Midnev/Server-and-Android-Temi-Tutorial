package com.app.temiexampleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.temiexampleapp.http.HttpManager;
import com.app.temiexampleapp.temi.TemiManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private String topic;
    private HttpManager httpManager;
    private TemiManager temiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temiManager = new TemiManager();
        temiManager.init();
        topic = "";// 구독할 토픽 서버에서 보내는것과 일치해야함
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           //완료했을 경우 실행할 내용
                       }
                   });

        httpManager = new HttpManager("http://192.168.0.0:8080");
        httpManager.postHttpRequest("/url","?val=param?val2=param");
        httpManager.getHttpRequest("/url","?val=param?val=param");
    }
}
