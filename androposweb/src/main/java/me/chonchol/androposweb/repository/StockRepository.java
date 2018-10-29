package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(value = "SELECT * FROM STOCK A WHERE A.quantity > :quantity", nativeQuery = true)
    public List<Stock> getAvailableStockList(@Param("quantity") Integer quantity);

    @Query(value = "SELECT * FROM STOCK A WHERE A.quantity <= :quantity", nativeQuery = true)
    public List<Stock> getStockAlertStockList(@Param("quantity") Integer quantity);

    @Modifying
    @Query(value = "UPDATE STOCK A SET A.quantity = A.quantity - :quantity WHERE A.product_id = :product_id", nativeQuery = true)
    void updateStockByProductId(@Param("product_id") Integer productId, @Param("quantity") Integer quantity);
}
