package me.chonchol.androposweb.implementation;

import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;
import me.chonchol.androposweb.repository.CustomRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class CustomRepositoryImpl implements CustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    String saleReportQuery = "SELECT B.order_date AS date, E.customer_name AS customerName," +
            " E.phone_no AS phoneNo, D.product_name AS productName, C.quantity AS quantity," +
            " C.total_price AS totalAmount" +
            " FROM SALE A " +
            " INNER JOIN QUOTATION B ON A.quotation_id = B.quotation_id " +
            " INNER JOIN QUOTATIONLIST C on A.quotation_id = C.quotation_id " +
            " INNER JOIN PRODUCT D ON C.product_id = D.product_id " +
            " INNER JOIN CUSTOMERS E ON B.customer_id = E.customer_id " +
            " WHERE (B.order_date BETWEEN :from_date AND :to_date) ";

    String profitReportQuery = "SELECT B.order_date AS date, C.total_price AS totalSaleAmount," +
            " (D.cost * C.quantity) AS totalCostAmount  " +
            " FROM SALE A  " +
            " INNER JOIN QUOTATION B ON A.quotation_id = B.quotation_id  " +
            " INNER JOIN QUOTATIONLIST C ON A.quotation_id = C.quotation_id  " +
            " INNER JOIN PRODUCT D ON C.product_id = D.product_id  " +
            " WHERE (B.order_date BETWEEN :from_date AND :to_date) ";

    @Override
    public List<SaleReport> getSaleReportByDate(String fromDate, String toDate) {
        Query query = entityManager.createNativeQuery(saleReportQuery, SaleReport.class);
        query.setParameter("from_date", fromDate);
        query.setParameter("to_date", toDate);
        return query.getResultList();
    }

    @Override
    public List<ProfitReport> getProfitReportByDate(String fromDate, String toDate) {
        Query query = entityManager.createNativeQuery(profitReportQuery, ProfitReport.class);
        query.setParameter("from_date", fromDate);
        query.setParameter("to_date", toDate);
        return query.getResultList();
    }

}
