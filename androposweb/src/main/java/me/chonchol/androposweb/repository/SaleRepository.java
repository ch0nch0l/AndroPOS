package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Sale;
import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>, CustomRepository {

//    @Query(" SELECT new me.chonchol.androposweb.entity.report.SaleReport(B.order_date, E.customer_name, E.phone_no, " +
//            " D.product_name, C.quantity, C.total_price) " +
//            " FROM SALE A " +
//            " INNER JOIN QUOTATION B ON A.quotation_id = B.quotation_id " +
//            " INNER JOIN QUOTATIONLIST C on A.quotation_id = C.quotation_id " +
//            " INNER JOIN PRODUCT D ON C.product_id = D.product_id " +
//            " INNER JOIN CUSTOMERS E ON B.customer_id = E.customer_id " +
//            " WHERE (B.order_date BETWEEN :from_date AND :to_date) ")
//    List<SaleReport> getSaleReportByDate(@Param("from_date") String fromDate, @Param("to_date") String toDate);
//
//
//    @Query("SELECT B.order_date AS date, C.total_price AS totalSaleAmount," +
//            "(D.cost * C.quantity) AS totalCostAmount  " +
//            "FROM SALE A  " +
//            "INNER JOIN QUOTATION B ON A.quotation_id = B.quotation_id  " +
//            "INNER JOIN QUOTATIONLIST C ON A.quotation_id = C.quotation_id  " +
//            "INNER JOIN PRODUCT D ON C.product_id = D.product_id  " +
//            "WHERE (B.order_date BETWEEN :from_date AND :to_date) ")
//    List<ProfitReport> getProfitReportByDate(@Param("from_date") String fromDate, @Param("to_date") String toDate);
}
