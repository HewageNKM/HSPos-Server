package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.nadunkawishika.helloshoesapplicationserver.dto.SaleDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.SaleDetailDTO;
import com.nadunkawishika.helloshoesapplicationserver.entity.*;
import com.nadunkawishika.helloshoesapplicationserver.enums.Level;
import com.nadunkawishika.helloshoesapplicationserver.exception.customExceptions.NotFoundException;
import com.nadunkawishika.helloshoesapplicationserver.repository.*;
import com.nadunkawishika.helloshoesapplicationserver.service.SaleService;
import com.nadunkawishika.helloshoesapplicationserver.util.GenerateId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleDetailsRepository saleDetailsRepository;
    private final CustomerRepository customerRepository;
    private final StocksRepository stocksRepository;
    private final InventoryRepository inventoryRepository;
    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void addSale(SaleDTO dto) {
        Optional<Customer> customer = customerRepository.findByCustomerIdOrEmailOrContact(dto.getCustomerId().toLowerCase(), dto.getCustomerId().toLowerCase(), dto.getCustomerId().toLowerCase());

        List<SaleDetailDTO> saleDetailsList = dto.getSaleDetailsList();
        saleDetailsList.forEach(saleDTO -> {
            Item item = inventoryRepository.findById(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Inventory not found " + saleDTO.getItemId()));
            Stock stock = stocksRepository.findByItemId(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Stock not found " + saleDTO.getItemId()));
            if (saleDTO.getSize().equalsIgnoreCase("size40")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize40(stock.getSize40() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("size41")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize41(stock.getSize41() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("size42")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize42(stock.getSize42() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("size43")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize43(stock.getSize43() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("size44")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize44(stock.getSize44() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("size45")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize45(stock.getSize45() - saleDTO.getQuantity());
            }
            inventoryRepository.save(item);
            stocksRepository.save(stock);
        });

        Sale sale = Sale.builder().saleId(GenerateId.getId("SAL").toLowerCase()).date(LocalDate.now()).total(dto.getTotal()).paymentDescription(dto.getPaymentDescription()).time(LocalTime.now()).customer(customer.orElse(null)).build();
        saleRepository.save(sale);

        saleDetailsList.forEach(saleDTO -> {
            saleDetailsRepository
                    .save
                            (
                                    SaleDetails
                                            .builder()
                                            .sale(sale)
                                            .price(saleDTO.getPrice())
                                            .item(inventoryRepository.findById(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Inventory not found " + saleDTO.getItemId())))
                                            .qty(saleDTO.getQuantity())
                                            .saleDetailsId(GenerateId.getId("SALD").toLowerCase())
                                            .total(saleDTO.getTotal())
                                            .description(saleDTO.getDescription())
                                            .build()
                            );
        });

        customer.ifPresent(cus -> {
            cus.setRecentPurchaseDateAndTime(LocalDateTime.now());
            Double totalPoints = dto.getTotal() / 1000.0;
            totalPoints = Double.valueOf(df.format(totalPoints));
            cus.setTotalPoints(cus.getTotalPoints()+totalPoints);

            if (cus.getTotalPoints() < 50) {
                cus.setLevel(Level.New);
            } else if (cus.getTotalPoints()  >= 50 && cus.getTotalPoints() < 100) {
                cus.setLevel(Level.Bronze);
            } else if (cus.getTotalPoints()  >= 100 && cus.getTotalPoints() < 200) {
                cus.setLevel(Level.Silver);
            } else if (cus.getTotalPoints()  >= 200) {
                cus.setLevel(Level.Gold);
            }
            System.out.print(cus);
            customerRepository.save(cus);
        });
    }
}
