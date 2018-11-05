package me.chonchol.androposweb.entity.report;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by mehedi.chonchol on 30-Oct-18.
 */

@Component
public class ProfitReport {

    private Date date;
    private Double totalSaleAmount;
    private Double totalCostAmount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(Double totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }

    public Double getTotalCostAmount() {
        return totalCostAmount;
    }

    public void setTotalCostAmount(Double totalCostAmount) {
        this.totalCostAmount = totalCostAmount;
    }
}
