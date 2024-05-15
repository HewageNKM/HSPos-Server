package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.nadunkawishika.helloshoesapplicationserver.dto.SupplierDTO;
import com.nadunkawishika.helloshoesapplicationserver.entity.Item;
import com.nadunkawishika.helloshoesapplicationserver.entity.Supplier;
import com.nadunkawishika.helloshoesapplicationserver.exception.customExceptions.NotFoundException;
import com.nadunkawishika.helloshoesapplicationserver.repository.InventoryRepository;
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
    private final InventoryRepository itemRepository;
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
    public List<SupplierDTO> filterSuppliers(String pattern) {
        pattern = pattern.toLowerCase();
        LOGGER.info("Filter Suppliers Request: {}", pattern);
        List<Supplier> suppliers = supplierRepository.filterByPattern(pattern);
        LOGGER.info("Filtered Suppliers ID");
        return mapper.toSuppliersEntityToDTOs(suppliers);
    }

    @Override
    public void updateSupplier(String id, SupplierDTO dto) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new NotFoundException("Supplier Not Found" + id));
        supplier.getItems().forEach(item -> {
            item.setSupplierName(dto.getName().toLowerCase());
        });
        supplier.setName(dto.getName().toLowerCase());
        supplier.setLane(dto.getLane().toLowerCase());
        supplier.setCity(dto.getCity().toLowerCase());
        supplier.setState(dto.getState().toLowerCase());
        supplier.setPostalCode(dto.getPostalCode());
        supplier.setCountry(dto.getCountry().toLowerCase());
        supplier.setContactNo1(dto.getContactNo1());
        supplier.setContactNo2(dto.getContactNo2());
        supplier.setEmail(dto.getEmail().toLowerCase());
        supplierRepository.save(supplier);
        LOGGER.info("Supplier Updated: {}", supplier.getSupplierId());
    }

    @Override
    public void addSupplier(SupplierDTO dto) {
        dto.setSupplierId(GenerateId.getId("SUP").toLowerCase());
        Supplier supplier = Supplier.
                builder()
                .supplierId(dto.getSupplierId())
                .name(dto.getName().toLowerCase())
                .lane(dto.getLane().toLowerCase())
                .city(dto.getCity().toLowerCase())
                .state(dto.getState().toLowerCase())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry().toLowerCase())
                .contactNo1(dto.getContactNo1())
                .contactNo2(dto.getContactNo2())
                .email(dto.getEmail().toLowerCase())
                .build();
        supplierRepository.save(supplier);
        LOGGER.info("Supplier Added ID:{}", supplier.getSupplierId());
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
