package me.chonchol.androposweb.dao;

import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;

import java.util.Date;
import java.util.List;

public interface IReportDao {

    public List<SaleReport> getSaleReportByDate(Date fromDate, Date toDate);

    public List<ProfitReport> getProfitReportByDate(String fromDate, String toDate);
}
