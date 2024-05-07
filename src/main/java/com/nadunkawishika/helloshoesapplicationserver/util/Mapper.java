package com.nadunkawishika.helloshoesapplicationserver.util;

import com.nadunkawishika.helloshoesapplicationserver.dto.CustomerDTO;
import com.nadunkawishika.helloshoesapplicationserver.dto.SupplierDTO;
import com.nadunkawishika.helloshoesapplicationserver.entity.Customer;
import com.nadunkawishika.helloshoesapplicationserver.entity.Supplier;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Mapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public List<SupplierDTO> toSuppliersEntityToDTOs(List<Supplier> suppliers) {
        Stream<SupplierDTO> supplierDTOStream = suppliers.stream().map(supplier -> modelMapper.map(supplier, SupplierDTO.class));
        return supplierDTOStream.toList();
    }

    public SupplierDTO toSupplierEntityToDTO(Supplier suppliers) {
        return modelMapper.map(suppliers, SupplierDTO.class);
    }

    public CustomerDTO toCustomerEntityToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public List<CustomerDTO> toCustomersEntityToDTOs(List<Customer> customers) {
        Stream<CustomerDTO> customerDTOStream = customers.stream().map(customer -> modelMapper.map(customer, CustomerDTO.class));
        return customerDTOStream.toList();
    }

    public List<Customer> toCustomerDTOsToCustomerEntity(List<CustomerDTO> customerDTOS) {
        Stream<Customer> customerStream = customerDTOS.stream().map(customerDTO -> modelMapper.map(customerDTO, Customer.class));
        return customerStream.toList();
    }
}

