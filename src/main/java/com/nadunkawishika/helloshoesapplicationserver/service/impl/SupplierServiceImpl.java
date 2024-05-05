package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.nadunkawishika.helloshoesapplicationserver.dto.SupplierDTO;
import com.nadunkawishika.helloshoesapplicationserver.entity.Supplier;
import com.nadunkawishika.helloshoesapplicationserver.exception.customExceptions.NotFoundException;
import com.nadunkawishika.helloshoesapplicationserver.repository.SupplierRepository;
import com.nadunkawishika.helloshoesapplicationserver.service.SupplierService;
import com.nadunkawishika.helloshoesapplicationserver.util.GenerateId;
import com.nadunkawishika.helloshoesapplicationserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Override
    public List<SupplierDTO> getSuppliers() {
        return mapper.toSuppliersEntityToDTOs(supplierRepository.findAll());
    }

    @Override
    public SupplierDTO getSupplier(String id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            LOGGER.info("Supplier Found: {}", supplier.get());
            return mapper.toSupplierEntityToDTO(supplier.get());
        } else {
            LOGGER.error("Supplier Not Found: {}", id);
            throw new NotFoundException("Supplier Not Found " + id);
        }
    }

    @Override
    public void updateSupplier(String id, SupplierDTO dto) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            Supplier spl = supplier.get();
            spl.setName(dto.getName());
            spl.setLane(dto.getLane());
            spl.setCity(dto.getCity());
            spl.setState(dto.getState());
            spl.setPostalCode(dto.getPostalCode());
            spl.setCountry(dto.getCountry());
            spl.setContactNo1(dto.getContactNo1());
            spl.setContactNo2(dto.getContactNo2());
            spl.setEmail(dto.getEmail());
            supplierRepository.save(spl);
            LOGGER.info("Supplier Updated: {}", spl);
        } else {
            LOGGER.error("Supplier Not Found: {}", id);
            throw new NotFoundException("Supplier Not Found" + id);
        }
    }

    @Override
    public void addSupplier(SupplierDTO dto) {
        dto.setSupplierId(GenerateId.getId("SUP").toLowerCase());
        Supplier supplier = mapper.toSupplierDTOsToSupplierEntity(dto);
        supplierRepository.save(supplier);
        LOGGER.info("Supplier Added: {}", supplier);
    }

    @Override
    public void deleteSupplier(String id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            supplierRepository.deleteById(id);
            LOGGER.info("Supplier Deleted: {}", supplier.get());
        } else {
            LOGGER.error("Supplier Not Found: {}", id);
            throw new NotFoundException("Supplier Not Found" + id);
        }
    }
}
