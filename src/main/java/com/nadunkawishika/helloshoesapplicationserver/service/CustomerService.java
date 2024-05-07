package com.nadunkawishika.helloshoesapplicationserver.service;

import com.nadunkawishika.helloshoesapplicationserver.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getCustomers();

    CustomerDTO getCustomer(String id);

    List<CustomerDTO> filterCustomers(String pattern);

    void updateCustomer(String id, CustomerDTO dto);

    void addCustomer(CustomerDTO dto);

    void deleteCustomer(String id);

    List<CustomerDTO> filterCustomer(String pattern);
}
