package com.whatcity.topup.repository;

import com.whatcity.topup.model.payload.TradePayload;
import com.whatcity.topup.model.table.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query( value = " select t.order_id as orderId , t.trade_date as tradeDate , t.trade_time as tradeTime, t.status as status , " +
            " case when t.status = '1' then 'รอดำเนินการแนบสลิป'   " +
            " when t.status = '2' then 'กำลังดำเนินการ'   " +
            " when t.status = '3' then 'ตรวจสอบใบเสร็จเรียบร้อย'  " +
            " else '' end as statusName,  " +
            " tbo.amount as amount , t.item_code as itemCode from trade t " +
            " join tb_order tbo on t.order_id = tbo.order_ref  " +
            " where tbo.steam_id = ?1 ",
            nativeQuery = true)
    List<TradePayload> findTradeByIdSteam(String steamId);

    @Query(value = " select * from trade where order_id = ?1 ",nativeQuery = true)
    Trade queryTradeByOrderId(String orderId);
}
