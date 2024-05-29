package com.nadunkawishika.helloshoesapplicationserver.repository;

import com.nadunkawishika.helloshoesapplicationserver.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
    @Query(value = "SELECT DISTINCT COUNT(sale_id) FROM sale WHERE created_at LIKE %?1%", nativeQuery = true)
    List<Object[]> findBillCount(String date);

    @Query(value = "SELECT * FROM sale WHERE created_at LIKE %?1%", nativeQuery = true)
    Optional<List<Sale>> getAllTodaySales(String date);

    @Query(value = "SELECT * FROM sale ORDER BY created_at DESC LIMIT 1;", nativeQuery = true)
    Optional<Sale> findLatestInvoice();
}
