package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(value = "SELECT * FROM STOCK A WHERE A.quantity > :quantity", nativeQuery = true)
    public List<Stock> getAvailableStockList(@Param("quantity") Integer quantity);
}
