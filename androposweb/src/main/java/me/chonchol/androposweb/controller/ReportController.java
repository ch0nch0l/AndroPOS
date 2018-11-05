package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.report.SaleReport;
import me.chonchol.androposweb.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @GetMapping("/sale/{from_date}/{to_date}")
    public List<SaleReport> getSaleReportByDate(@PathVariable("from_date") @DateTimeFormat(pattern="dd-MM-yyyy") Date fromDate, @PathVariable("to_date") @DateTimeFormat(pattern="dd-MM-yyyy") Date toDate) throws Exception {
        return reportService.getSaleReportByDate(fromDate, toDate);
    }


}
