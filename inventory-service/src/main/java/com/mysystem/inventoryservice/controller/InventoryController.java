package com.mysystem.inventoryservice.controller;

import com.mysystem.inventoryservice.dto.InventoryResponse;
import com.mysystem.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private InventoryService inventoryServiceImpl;

    @Autowired
    public InventoryController(InventoryService inventoryServiceImpl) {
        this.inventoryServiceImpl = inventoryServiceImpl;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public  List<InventoryResponse> isInStock(@RequestParam("skuCode") List<String> skuCode) {
        return inventoryServiceImpl.isInStock(skuCode);
    }
}
