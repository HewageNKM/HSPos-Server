package com.nadunkawishika.helloshoesapplicationserver.service;

import com.nadunkawishika.helloshoesapplicationserver.dto.OverViewDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.RefundDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.SaleDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.SaleDetailDTO;

import java.util.List;

public interface SaleService {
    void addSale(SaleDTO sale);

    SaleDTO getSale(String id);

    List<SaleDetailDTO> getSaleItem(String id, String itemId);

    void refundSaleItem(RefundDTO dto);

    OverViewDTO getOverview();
}
