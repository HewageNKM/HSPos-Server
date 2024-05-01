package com.nadunkawishika.helloshoesapplicationserver.api.auth;

import com.nadunkawishika.helloshoesapplicationserver.dto.SupplierDTO;
import com.nadunkawishika.helloshoesapplicationserver.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class Supplier {
    private final SupplierService supplierService;

    @GetMapping
    public List<SupplierDTO> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierDTO getSupplier(@PathVariable String id) {
        return supplierService.getSupplier(id);
    }

    @PostMapping()
    @Secured("ADMIN")
    public void addSupplier(@Validated @RequestBody SupplierDTO dto) {
        supplierService.addSupplier(dto);
    }

    @PutMapping("/{id}")
    @Secured("ADMIN")
    public void addSupplier(@PathVariable String id, @RequestBody SupplierDTO dto) {
        supplierService.updateSupplier(id, dto);
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public void addSupplier(@PathVariable String id) {
        supplierService.deleteSupplier(id);
    }
}
