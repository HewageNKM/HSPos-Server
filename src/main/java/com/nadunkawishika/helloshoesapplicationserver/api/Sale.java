package com.nadunkawishika.helloshoesapplicationserver.api;

import com.nadunkawishika.helloshoesapplicationserver.dto.RefundDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.SaleDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.SaleDetailDTO;
import com.nadunkawishika.helloshoesapplicationserver.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class Sale {
    private final SaleService saleService;
    private final Logger LOGGER = LoggerFactory.getLogger(Sale.class);

    @Secured({"ADMIN","USER"})
    @PostMapping
    public void addSale(@Validated @RequestBody SaleDTO sale) {
        LOGGER.info("Sale request received");
        saleService.addSale(sale);
    }

    @Secured({"ADMIN","USER"})
    @GetMapping("/{id}")
    public SaleDTO getSale(@PathVariable String id) {
        LOGGER.info("Get a sale request received");
        return saleService.getSale(id);
    }

    @Secured({"ADMIN","USER"})
    @GetMapping("/items")
    public List<SaleDetailDTO> getSaleItem(@RequestParam(name = "itemId") String itemId, @RequestParam(name = "orderId") String orderId) {
        LOGGER.info("Get sale item request received");
        return saleService.getSaleItem(orderId,itemId);
    }

    @Secured({"ADMIN","USER"})
    @PostMapping("/refund")
    public void refundSaleItem(@Validated @RequestBody RefundDTO dto) {
        LOGGER.info("Refund sale item request received");
        saleService.refundSaleItem(dto);
    }
}
