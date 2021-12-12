package com.whatcity.topup.repository;

import com.whatcity.topup.model.payload.TradePayload;
import com.whatcity.topup.model.table.WhatCityCommon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WhatCityCommonRepository extends JpaRepository<WhatCityCommon, Long> {

    @Query( value = " select * from whatcity_common " +
            " where item_name = ?1 ",
            nativeQuery = true)
    WhatCityCommon findByIdItemName(String itemName);
}
