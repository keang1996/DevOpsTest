package com.whatcity.topup.model.firebase;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import com.google.type.DateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
public class NotifyModel {
    private Long id;
    @ServerTimestamp
    private Timestamp createDateTime;
    private String message;
    private String orderId;
}
