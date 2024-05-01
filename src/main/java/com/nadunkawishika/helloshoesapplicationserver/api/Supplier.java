package com.nadunkawishika.helloshoesapplicationserver.api;

import com.nadunkawishika.helloshoesapplicationserver.dto.SupplierDTO;
import com.nadunkawishika.helloshoesapplicationserver.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@CrossOrigin
@EnableMethodSecurity(securedEnabled = true)
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

    //Authorize Methods
    @PostMapping
    @Secured("ADMIN")
    public void addSupplier(@Validated @RequestBody SupplierDTO supplierDTO) {
        System.out.println(supplierDTO.toString());
        supplierService.addSupplier(supplierDTO);
    }

    @PutMapping("/{id}")
    @Secured("ADMIN")
    public void addSupplier(@PathVariable String id, @RequestBody SupplierDTO supplierDTO) {
        supplierService.updateSupplier(id, supplierDTO);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    public void addSupplier(@PathVariable String id) {
        supplierService.deleteSupplier(id);
    }
}
