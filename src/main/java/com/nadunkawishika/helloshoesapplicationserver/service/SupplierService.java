package com.nadunkawishika.helloshoesapplicationserver.service;

import com.nadunkawishika.helloshoesapplicationserver.dto.SupplierDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SupplierService {
    List<SupplierDTO> getSuppliers();
    SupplierDTO getSupplier(String id);
    List<SupplierDTO> filterSuppliers(String pattern);
    void updateSupplier(String id, SupplierDTO dto);
    void addSupplier(SupplierDTO dto);
    void deleteSupplier(String id);
}
