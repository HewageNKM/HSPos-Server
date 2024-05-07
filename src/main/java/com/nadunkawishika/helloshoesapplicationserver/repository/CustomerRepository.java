package com.nadunkawishika.helloshoesapplicationserver.repository;

import com.nadunkawishika.helloshoesapplicationserver.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByContact(String contact);

    @Query(value = "SELECT * FROM Customers WHERE name LIKE %?1% OR email LIKE %?1% OR contact LIKE %?1% OR address LIKE %?1% OR city LIKE %?1% OR postal_code LIKE %?1% OR level LIKE %?1% OR totalPoints LIKE %?1% OR doj LIKE %?1% OR gender LIKE %?1%", nativeQuery = true)
    List<Customer> filterCustomers(String pattern);
}
