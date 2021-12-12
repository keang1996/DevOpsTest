package com.whatcity.topup.service;

import com.whatcity.topup.model.table.WhatCityCommon;
import com.whatcity.topup.repository.WhatCityCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WhatCityCommonService {

    @Autowired
    WhatCityCommonRepository whatCityCommonRepository;

    public Optional<WhatCityCommon> findById(Long id){
        return whatCityCommonRepository.findById(id);
    }

    public WhatCityCommon findByIdItemName(String itemName){
        return whatCityCommonRepository.findByIdItemName(itemName);
    }
}
