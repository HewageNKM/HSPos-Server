package com.nadunkawishika.helloshoesapplicationserver.api;

import com.nadunkawishika.helloshoesapplicationserver.dto.SaleDTO;
import com.nadunkawishika.helloshoesapplicationserver.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class Sale {
    private final SaleService saleService;
    private final Logger LOGGER = LoggerFactory.getLogger(Sale.class);

    @PostMapping
    public void addSale(@Validated @RequestBody SaleDTO sale) {
        LOGGER.info("Sale request received");
        saleService.addSale(sale);
    }
}
