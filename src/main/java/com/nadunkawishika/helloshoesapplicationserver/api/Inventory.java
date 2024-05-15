package com.nadunkawishika.helloshoesapplicationserver.api;

import com.nadunkawishika.helloshoesapplicationserver.dto.ItemDTO;
import com.nadunkawishika.helloshoesapplicationserver.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory/items")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class Inventory {
    private final Logger LOGGER = LoggerFactory.getLogger(Inventory.class);
    private final InventoryService inventoryService;


    @Secured({"ADMIN", "USER"})
    @GetMapping
    public List<ItemDTO> getAllItems( String pattern) {
       return inventoryService.getAllItems();
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/filter/{pattern}")
    public List<ItemDTO> filterItems(@PathVariable String pattern) {
        return inventoryService.filterItems(pattern);
    }

    @Secured({"ADMIN", "USER"})
    @PostMapping
    public void addItem(@RequestPart String dto, @RequestPart(required = false) MultipartFile image) {
        try {
            LOGGER.info("Add Item Request");
            inventoryService.addItem(dto, image);
        } catch (IOException e) {
            LOGGER.error("Add Item Request Failed: {}", e.getMessage());
        }
    }

    //Authorize Methods
    @Secured("ADMIN")
    @PutMapping("/{id}")
    public void updateItem(@PathVariable String id, @RequestPart String dto, @RequestPart(required = false) MultipartFile image) {
        try {
            LOGGER.info("Update Item Request");
            inventoryService.updateItem(id, dto, image);
        } catch (IOException e) {
            LOGGER.error("Update Item Request Failed: {}", e.getMessage());
        }
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        LOGGER.info("Delete Item Request");
        inventoryService.deleteItem(id);
    }
}
