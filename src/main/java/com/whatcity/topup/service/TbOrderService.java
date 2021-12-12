package com.whatcity.topup.service;

import com.whatcity.topup.model.table.TbOrder;
import com.whatcity.topup.repository.TbOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbOrderService {

    @Autowired
    TbOrderRepository tbOrderRepository;

    public List<TbOrder> findAllTbOrder(){
        return tbOrderRepository.findAll();
    }

    public TbOrder save(TbOrder tbOrder){
        return tbOrderRepository.save(tbOrder);
    }

}
