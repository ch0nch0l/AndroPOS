package me.chonchol.androposweb.service;

import me.chonchol.androposweb.dao.IReportDao;
import me.chonchol.androposweb.entity.report.ProfitReport;
import me.chonchol.androposweb.entity.report.SaleReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportService implements IReportService {

    @Autowired
    private IReportDao reportDao;

    @Override
    public List<SaleReport> getSaleReportByDate(Date fromDate, Date toDate) throws Exception {
        return reportDao.getSaleReportByDate(fromDate, toDate);
    }

    @Override
    public List<ProfitReport> getProfitReportByDate(String fromDate, String toDate) throws Exception {
        return null;
    }
}
