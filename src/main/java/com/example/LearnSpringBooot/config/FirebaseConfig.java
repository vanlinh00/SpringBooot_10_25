package com.example.LearnSpringBooot.config;

import com.example.LearnSpringBooot.Utils.AppConstants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {

        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ClassPathResource(AppConstants.FIREBASE_SERVICE_ACCOUNT_PATH)
                        .getInputStream()
        );

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp firebaseApp;
        if (FirebaseApp.getApps().isEmpty()) {
            firebaseApp = FirebaseApp.initializeApp(options, AppConstants.FIREBASE_APP_NAME);
        } else {
            firebaseApp = FirebaseApp.getInstance(AppConstants.FIREBASE_APP_NAME);
        }

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}