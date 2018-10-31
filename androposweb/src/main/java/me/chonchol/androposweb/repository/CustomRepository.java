package me.chonchol.androposweb.repository;

import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;

import java.util.List;

public interface CustomRepository {

    List<SaleReport> getSaleReportByDate(String fromDate, String toDate);

    List<ProfitReport> getProfitReportByDate(String fromDate, String toDate);
}
