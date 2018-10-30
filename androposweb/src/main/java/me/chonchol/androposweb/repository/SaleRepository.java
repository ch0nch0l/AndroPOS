package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Sale;
import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Query(value = "SELECT B.order_date AS date, E.customer_name AS customerName, " +
            "E.phone_no AS phoneNo, D.product_name AS productName, C.quantity AS quantity, " +
            "C.total AS totalAmount " +
            "FROM SALE A " +
            "INNER JOIN QUOTATION B ON A.quotation_id = B.quotation_id " +
            "INNER JOIN QUOTATIONLIST C ON B.quotation_id = C.quotation_id " +
            "INNER JOIN PRODUCT D ON C.product_id = D.product_id " +
            "INNER JOIN CUSTOMER E ON B.customer_id = E.customer_id " +
            "WHERE B.order_date BETWEEN :from_date AND :to_Date ", nativeQuery = true)
    List<SaleReport> getSaleReportBydate(String fromDate, String toDate);


    @Query(value = "SELECT B.order_date AS date, C.total_price AS totalSaleAmount, " +
            "(D.cost * C.quantity) AS totalCostAmount " +
            "FROM SALE A " +
            "INNER JOIN QUOTATION B ON A.quotation_id = B.quotation_id " +
            "INNER JOIN QUOTATIONLIST C ON A.quotation_id = C.quotation_id " +
            "INNER JOIN PRODUCT D ON C.product_id = D.product_id " +
            "WHERE B.order_date BETWEEN :from_date AND :to_Date", nativeQuery = true)
    List<ProfitReport> getProfitReportByDate(String fromDate, String toDate);
}
