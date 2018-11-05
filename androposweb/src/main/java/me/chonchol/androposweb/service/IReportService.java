package me.chonchol.androposweb.service;

import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;

import java.util.Date;
import java.util.List;

public interface IReportService {

    public List<SaleReport> getSaleReportByDate(Date fromDate, Date toDate) throws Exception;

    public List<ProfitReport> getProfitReportByDate(String fromDate, String toDate) throws Exception;
}
