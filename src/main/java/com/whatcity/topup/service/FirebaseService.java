package com.whatcity.topup.service;

import com.google.api.client.util.DateTime;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Date;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.whatcity.topup.model.firebase.NotifyModel;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.sql.Time;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {
    private final Firestore firestore;
    private final FirebaseMessaging firebaseMessaging;
    private EntityManager entityManager = null;

    public FirebaseService(Firestore firestore, FirebaseMessaging firebaseMessaging, EntityManager entityManager) {
        this.firestore = firestore;
        this.firebaseMessaging = firebaseMessaging;
        this.entityManager = entityManager;
    }

    public void sendNotification(String orderId, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle("แจ้งเตือนการชำระเงิน")
                .setBody("หมายเลขสั่งซื้อ : " + orderId)
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        firebaseMessaging.send(message);
    }

    public void saveNotification(NotifyModel notifyModel) throws ExecutionException, InterruptedException {

        Long seq = getSequenceFirebase();
        String documentName = notifyModel.getOrderId().toString();
        notifyModel.setId(seq);

        ApiFuture<WriteResult> collectionApiFuture = getUserCollection().document(documentName).set(notifyModel);
    }

    private CollectionReference getUserCollection() {
        return firestore.collection("whatcity_notify");
    }

    private Long getSequenceFirebase() {
        Long value = Long.parseLong(entityManager
                .createNativeQuery("SELECT nextval('firebase_seq') as nextval")
                .getSingleResult().toString());

        return value;
    }

//    @PostConstruct
//    public void getAccountFirebase(){
//        try {
//            FileInputStream serviceAccount =
//                    new FileInputStream("./src/main/java/com/whatcity/topup/firebase/whatcitynotify-firebase-account-key.json");
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://whatcitynotify-default-rtdb.asia-southeast1.firebasedatabase.app")
//                    .build();
//
//            FirebaseApp.initializeApp(options);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}

