package com.nadunkawishika.helloshoesapplicationserver.repository;

import com.nadunkawishika.helloshoesapplicationserver.entity.SaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleDetailsRepository extends JpaRepository<SaleDetails, String> {

    @Query(value = "SELECT item_id, COUNT(*) AS item_count FROM sale_details WHERE sale_id IN (SELECT sale_id FROM sale WHERE sale.date >= CURDATE() - INTERVAL ?1 DAY) AND sale_details.total > 0 GROUP BY item_id ORDER BY item_count DESC LIMIT 1;", nativeQuery = true)
    Optional<SaleDetails> findPopularItem(Integer range);
}
