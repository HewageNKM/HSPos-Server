package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.nadunkawishika.helloshoesapplicationserver.dto.SupplierDTO;
import com.nadunkawishika.helloshoesapplicationserver.entity.Supplier;
import com.nadunkawishika.helloshoesapplicationserver.exception.customExceptions.NotFoundException;
import com.nadunkawishika.helloshoesapplicationserver.repository.SupplierRepository;
import com.nadunkawishika.helloshoesapplicationserver.service.SupplierService;
import com.nadunkawishika.helloshoesapplicationserver.util.GenerateId;
import com.nadunkawishika.helloshoesapplicationserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final Mapper mapper;


    @Override
    public List<SupplierDTO> getSuppliers() {
        return mapper.toSuppliersEntityToDTOs(supplierRepository.findAll());
    }

    @Override
    public SupplierDTO getSupplier(String id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            return mapper.toSupplierEntityToDTO(supplier.get());
        } else {
            throw new NotFoundException("Supplier Not Found " + id);
        }
    }

    @Override
    public void updateSupplier(String id, SupplierDTO dto) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            Supplier spl = supplier.get();
            spl.setName(dto.getName());
            spl.setAddress(dto.getAddress());
            spl.setContactNo1(dto.getContactNo1());
            spl.setContactNo2(dto.getContactNo2());
            spl.setEmail(dto.getEmail());
            supplierRepository.save(spl);
        } else {
            throw new NotFoundException("Supplier Not Found" + id);
        }
    }

    @Override
    public void addSupplier(SupplierDTO dto) {
        dto.setSupplierId(GenerateId.getId("SUP"));
        Supplier supplier = mapper.toSupplierDTOsToSupplierEntity(dto);
        supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(String id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            supplierRepository.deleteById(id);
        } else {
            throw new NotFoundException("Supplier Not Found" + id);
        }
    }
}
