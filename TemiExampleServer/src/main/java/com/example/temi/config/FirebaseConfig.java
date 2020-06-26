package com.example.temi.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FirebaseConfig {
    @Bean
    public DatabaseReference firebaseDatabase(){
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }
}
