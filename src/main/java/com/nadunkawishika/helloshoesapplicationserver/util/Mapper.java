package com.nadunkawishika.helloshoesapplicationserver.util;

import com.nadunkawishika.helloshoesapplicationserver.entity.Supplier;
import com.nadunkawishika.helloshoesapplicationserver.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Mapper {
    private ModelMapper modelMapper;

    public List<SupplierDTO> toSuppliersEntityToDTOs(List<Supplier> suppliers) {
        Stream<SupplierDTO> supplierDTOStream = suppliers.stream().map(supplier -> modelMapper.map(supplier, SupplierDTO.class));
        return supplierDTOStream.toList();
    }

    public SupplierDTO toSupplierEntityToDTO(Supplier suppliers) {
        return modelMapper.map(suppliers, SupplierDTO.class);
    }

    public List<Supplier> toSupplierDTOsToEntity(List<SupplierDTO> supplierDTOS) {
        Stream<Supplier> supplierStream = supplierDTOS.stream().map(supplierDTO -> modelMapper.map(supplierDTO, Supplier.class));
        return supplierStream.toList();
    }

    public Supplier toSupplierDTOsToSupplierEntity(SupplierDTO supplierDTO) {
        return modelMapper.map(supplierDTO, Supplier.class);
    }

}

