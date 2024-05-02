package com.mysystem.inventoryservice.service.impl;

import com.mysystem.inventoryservice.dto.InventoryResponse;
import com.mysystem.inventoryservice.repository.InventoryRepository;
import com.mysystem.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
