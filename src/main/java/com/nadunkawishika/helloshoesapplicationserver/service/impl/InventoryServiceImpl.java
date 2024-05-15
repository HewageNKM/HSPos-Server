package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadunkawishika.helloshoesapplicationserver.dto.CustomDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.ItemDTO;
import com.nadunkawishika.helloshoesapplicationserver.entity.Item;
import com.nadunkawishika.helloshoesapplicationserver.entity.Stock;
import com.nadunkawishika.helloshoesapplicationserver.entity.Supplier;
import com.nadunkawishika.helloshoesapplicationserver.exception.customExceptions.NotFoundException;
import com.nadunkawishika.helloshoesapplicationserver.repository.InventoryRepository;
import com.nadunkawishika.helloshoesapplicationserver.repository.StocksRepository;
import com.nadunkawishika.helloshoesapplicationserver.repository.SupplierRepository;
import com.nadunkawishika.helloshoesapplicationserver.service.InventoryService;
import com.nadunkawishika.helloshoesapplicationserver.util.GenerateId;
import com.nadunkawishika.helloshoesapplicationserver.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
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
    private final StocksRepository stocksRepository;
    private final ObjectMapper objectMapper;
    private final ImageUtil imageUtil;
    private final SupplierRepository supplierRepository;
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(InventoryServiceImpl.class);
    private final DecimalFormat df = new DecimalFormat("0.00");


    @Override
    public List<ItemDTO> getAllItems() {
        LOGGER.info("Get All Items Request");
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : inventoryRepository.findAll()) {
            getItemDTOs(itemDTOS, item);
        }
        return itemDTOS;
    }

    private void getItemDTOs(List<ItemDTO> itemDTOS, Item item) {
        LOGGER.info("Item Found: {}", item.getItemId());
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
        LOGGER.info("Item Added: {}", item.getItemId());
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
        LOGGER.info("Item Updated: {}", item.getItemId());
    }

    @Override
    public void deleteItem(String id) {
        inventoryRepository.deleteById(id);
        LOGGER.info("Item Deleted: {}", id);
    }

    @Override
    public List<ItemDTO> filterItems(String pattern) {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : inventoryRepository.filterItems(pattern)) {
            getItemDTOs(itemDTOS, item);
        }
        LOGGER.info("Filtered Items");
        return itemDTOS;
    }

    @Override
    public ItemDTO getItem(String id) {
        LOGGER.info("Get Item Request: {}", id);
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

    @Override
    public List<CustomDTO> getAllStocks() {
        LOGGER.info("Get All Stocks Request");
        List<Object[]> stockDetails = stocksRepository.getStockDetails();
        return getCustomDTOS(stockDetails);
    }

    @Override
    public void updateStock(String id, CustomDTO dto) {
        Stock stock = stocksRepository.findById(id).orElseThrow(() -> new NotFoundException("Stock Not Found"));
        Item item = inventoryRepository.findById(stock.getItem().getItemId()).orElseThrow(() -> new NotFoundException("Item Not Found"));
        int totalStock = dto.getSize40() + dto.getSize41() + dto.getSize42() + dto.getSize43() + dto.getSize44() + dto.getSize45();
        item.setQuantity(totalStock);
        stock.setSize40(dto.getSize40());
        stock.setSize41(dto.getSize41());
        stock.setSize42(dto.getSize42());
        stock.setSize43(dto.getSize43());
        stock.setSize44(dto.getSize44());
        stock.setSize45(dto.getSize45());
        stock.setItem(item);

        stocksRepository.save(stock);
        LOGGER.info("Stock Updated: {}", stock.getStockId());
    }

    @Override
    public List<CustomDTO> filterStocks(String pattern) {
        List<Object[]> stockDetails = stocksRepository.filterStocks(pattern);
        LOGGER.info("Filtered Stocks");
        return getCustomDTOS(stockDetails);
    }

    private List<CustomDTO> getCustomDTOS(List<Object[]> stockDetails) {
        List<CustomDTO> customDTOS = new ArrayList<>();
        for (Object[] stockDetail : stockDetails) {
            CustomDTO dto = CustomDTO
                    .builder()
                    .stockId(stockDetail[0].toString())
                    .supplierId(stockDetail[1].toString())
                    .supplierName(stockDetail[2].toString())
                    .itemId(stockDetail[3].toString())
                    .description(stockDetail[4].toString())
                    .size40(Integer.parseInt(stockDetail[5].toString()))
                    .size41(Integer.parseInt(stockDetail[6].toString()))
                    .size42(Integer.parseInt(stockDetail[7].toString()))
                    .size43(Integer.parseInt(stockDetail[8].toString()))
                    .size44(Integer.parseInt(stockDetail[9].toString()))
                    .size45(Integer.parseInt(stockDetail[10].toString()))
                    .build();
            customDTOS.add(dto);
        }
        LOGGER.info("Custom DTOs Created");
        return customDTOS;
    }
}
