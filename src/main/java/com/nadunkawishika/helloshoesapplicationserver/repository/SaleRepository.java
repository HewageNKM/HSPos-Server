package com.nadunkawishika.helloshoesapplicationserver.repository;

import com.nadunkawishika.helloshoesapplicationserver.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String>{
    @Query(value = "SELECT DISTINCT COUNT(sale_id) FROM sale WHERE date=CURRENT_DATE", nativeQuery = true)
    List<Object[]> findBillCount();
    @Query(value = "SELECT * FROM sale WHERE date=CURRENT_DATE", nativeQuery = true)
    Optional<List<Sale>> getAllTodaySales();
}
