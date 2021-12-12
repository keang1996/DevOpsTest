package com.whatcity.topup.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.opencensus.resource.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class FirebaseInitializer {
    @PostConstruct
    public void initialize() throws IOException, URISyntaxException {
//        ClassPathResource cpr = new ClassPathResource("firebase-service-account.json");
//        File file = ResourceUtils.getFile("classpath:firebase-service-account.json");

//        BufferedReader reader = new BufferedReader(new InputStreamReader(cpr.getInputStream()));
//        FileCopyUtils.copyToString(reader);

//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        InputStream input = classLoader.getResourceAsStream("src/main/resources/firebase-service-account.json");

//        URL resource = ResourceUtils.getURL("classpath:firebase-service-account.json");
        File file = new ClassPathResource("firebase-service-account.json").getFile();
//        FileInputStream input = new FileInputStream(file);

        FileInputStream serviceAccount = new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://whatcitynotify-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();

        FirebaseApp.initializeApp(options);
    }
}