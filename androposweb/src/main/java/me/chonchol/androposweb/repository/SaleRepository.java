package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.Sale;
import me.chonchol.androposweb.entity.report.SaleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {


//    @Query(value = "SELECT new me.chonchol.androposweb.entity.report.SaleReport(B.date, E.customerName," +
//            " E.phoneNo, D.productName, C.quantity," +
//            " C.totalAmount)" +
//            " FROM sale A " +
//            " INNER JOIN quotation B ON A.quotation_id = B.quotation_id " +
//            " INNER JOIN quotationlist C on A.quotation_id = C.quotation_id " +
//            " INNER JOIN product D ON C.product_id = D.product_id " +
//            " INNER JOIN custmers E ON B.customer_id = E.customer_id " +
//            " WHERE (B.order_date BETWEEN :from_date AND :to_date) ", nativeQuery = true)
//    public List<SaleReport> getSaleReportByDate(@Param("from_date")String fromDate, @Param("to_date")String toDate);

    //public List<ProfitReport> getProfitReportByDate(@Param("from_date")String fromDate, @Param("to_date")String toDate);

}
