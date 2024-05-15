package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadunkawishika.helloshoesapplicationserver.dto.ItemDTO;
import com.nadunkawishika.helloshoesapplicationserver.entity.Item;
import com.nadunkawishika.helloshoesapplicationserver.entity.Stock;
import com.nadunkawishika.helloshoesapplicationserver.entity.Supplier;
import com.nadunkawishika.helloshoesapplicationserver.exception.customExceptions.NotFoundException;
import com.nadunkawishika.helloshoesapplicationserver.repository.InventoryRepository;
import com.nadunkawishika.helloshoesapplicationserver.repository.SupplierRepository;
import com.nadunkawishika.helloshoesapplicationserver.service.InventoryService;
import com.nadunkawishika.helloshoesapplicationserver.util.GenerateId;
import com.nadunkawishika.helloshoesapplicationserver.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ObjectMapper objectMapper;
    private final ImageUtil imageUtil;
    private final SupplierRepository supplierRepository;
    private final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    public List<ItemDTO> getAllItems() {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : inventoryRepository.findAll()) {
            getItemDTOs(itemDTOS, item);
        }
        return itemDTOS;
    }

    private void getItemDTOs(List<ItemDTO> itemDTOS, Item item) {
        ItemDTO dto = ItemDTO
                .builder()
                .itemId(item.getItemId())
                .description(item.getDescription())
                .image(item.getImage())
                .expectedProfit(item.getExpectedProfit())
                .profitMargin(item.getProfitMargin())
                .quantity(item.getQuantity())
                .supplierName(item.getSupplierName())
                .supplierId(item.getSupplier().getSupplierId())
                .buyingPrice(item.getBuyingPrice())
                .sellingPrice(item.getSellingPrice())
                .category(item.getCategory())
                .build();
        itemDTOS.add(dto);
    }

    @Override
    public void addItem(String itemDTO, MultipartFile image) throws IOException {
        ItemDTO dto = objectMapper.readValue(itemDTO, ItemDTO.class);
        String id = (dto.getOccasion() + dto.getVerities() + dto.getGender() + GenerateId.getId("")).toLowerCase();
        String stockId = GenerateId.getId("STK").toLowerCase();

        Double expectedProfit = dto.getSellingPrice() - dto.getBuyingPrice();
        Double profitMargin = (expectedProfit / dto.getBuyingPrice()) * 100;
        profitMargin = Double.parseDouble(df.format(profitMargin));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId().toLowerCase()).orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        Stock stock = Stock
                .builder()
                .stockId(stockId)
                .size40(0)
                .size41(0)
                .size42(0)
                .size43(0)
                .size44(0)
                .size45(0)
                .build();

        Item item = Item
                .builder()
                .description(dto.getDescription().toLowerCase())
                .itemId(id)
                .category((dto.getOccasion() + "/" + dto.getVerities() + "/" + dto.getGender()).toLowerCase())
                .image(image != null ? imageUtil.encodeImage(image) : null)
                .buyingPrice(dto.getBuyingPrice())
                .sellingPrice(dto.getSellingPrice())
                .quantity(0)
                .supplierName(supplier.getName())
                .expectedProfit(expectedProfit)
                .profitMargin(profitMargin)
                .supplier(supplier)
                .stock(stock)
                .build();

        stock.setItem(item);
        inventoryRepository.save(item);

    }

    @Override
    public void updateItem(String id, String itemDTO, MultipartFile image) throws IOException {
        ItemDTO dto = objectMapper.readValue(itemDTO, ItemDTO.class);
        Item item = inventoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Item Not Found"));
        Supplier supplier = supplierRepository.findById(dto.getSupplierId().toLowerCase()).orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        Double expectedProfit = dto.getSellingPrice() - dto.getBuyingPrice();
        Double profitMargin = (expectedProfit / dto.getBuyingPrice()) * 100;
        profitMargin = Double.parseDouble(df.format(profitMargin));

        item.setDescription(dto.getDescription().toLowerCase());
        item.setCategory((dto.getOccasion() + "/" + dto.getVerities() + "/" + dto.getGender()).toLowerCase());
        item.setBuyingPrice(dto.getBuyingPrice());
        item.setSellingPrice(dto.getSellingPrice());
        item.setSupplierName(supplier.getName());
        item.setExpectedProfit(expectedProfit);
        item.setProfitMargin(profitMargin);
        item.setSupplier(supplier);
        item.setImage(image != null ? imageUtil.encodeImage(image) : null);
        inventoryRepository.save(item);
    }

    @Override
    public void deleteItem(String id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public List<ItemDTO> filterItems(String pattern) {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : inventoryRepository.filterItems(pattern)) {
            getItemDTOs(itemDTOS, item);
        }
        return itemDTOS;
    }

    @Override
    public ItemDTO getItem(String id) {
        Item item = inventoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Item Not Found"));
        return ItemDTO
                .builder()
                .itemId(item.getItemId())
                .description(item.getDescription())
                .image(item.getImage())
                .expectedProfit(item.getExpectedProfit())
                .profitMargin(item.getProfitMargin())
                .quantity(item.getQuantity())
                .supplierName(item.getSupplierName())
                .supplierId(item.getSupplier().getSupplierId())
                .buyingPrice(item.getBuyingPrice())
                .sellingPrice(item.getSellingPrice())
                .category(item.getCategory())
                .build();
    }
}
