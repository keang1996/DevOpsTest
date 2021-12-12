package com.whatcity.topup.model.table;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trade")
@Data
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trade_id")
    private Long tradeId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "trade_date")
    private Date tradeDate;

    @Column(name = "trade_time")
    private Date tradeTime;

    @Column(name = "receipt")
    private String receipt;

    @Column(name = "prove_status")
    private Boolean proveStatus;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "status")
    private String status;

    @Column(name = "item_code")
    private String itemCode;

    @Transient
    private String statusName;
    @Transient
    private String amount;

}
