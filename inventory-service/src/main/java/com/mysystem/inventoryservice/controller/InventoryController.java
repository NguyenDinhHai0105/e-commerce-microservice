package com.mysystem.inventoryservice.controller;

import com.mysystem.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private InventoryService inventoryServiceImpl;

    @Autowired
    public InventoryController(InventoryService inventoryServiceImpl) {
        this.inventoryServiceImpl = inventoryServiceImpl;
    }

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryServiceImpl.isInStock(skuCode);
    }
}
