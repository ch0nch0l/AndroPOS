package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomRepository {

    public List<SaleReport> getSaleReportByDate(@Param("from_date")String fromDate, @Param("to_date")String toDate);

    public List<ProfitReport> getProfitReportByDate(@Param("from_date")String fromDate, @Param("to_date")String toDate);
}
