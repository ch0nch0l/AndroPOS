package me.chonchol.androposweb.dao;

import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ReportDao extends BaseDao implements IReportDao {

    @Override
    public List<SaleReport> getSaleReportByDate(Date fromDate, Date toDate) {
        String query = "SELECT CAST(B.order_date AS CHAR) AS date, CAST(E.customer_name AS CHAR) AS customerName, CAST(E.phone_no AS CHAR) AS phoneNo, CAST(D.product_name AS CHAR) AS productName, CAST(C.quantity AS CHAR) AS quantity, CAST(C.total_price AS CHAR) AS totalAmount FROM SALE AS A INNER JOIN QUOTATION B ON A.quotation_id = B.quotation_id INNER JOIN QUOTATIONLIST C on A.quotation_id = C.quotation_id INNER JOIN PRODUCT D ON C.product_id = D.product_id INNER JOIN CUSTOMERS E ON B.customer_id = E.customer_id WHERE (B.order_date BETWEEN :from_date AND :to_date)";

        Query pQuery = em.createNativeQuery(query, "SaleReportResult");
        pQuery.setParameter("from_date", fromDate);
        pQuery.setParameter("to_date", toDate);
        return pQuery.getResultList();
    }

    @Override
    public List<ProfitReport> getProfitReportByDate(String fromDate, String toDate) {
        return null;
    }
}
