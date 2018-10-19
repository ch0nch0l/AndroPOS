package me.chonchol.andropos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mehedi.chonchol on 18-Oct-18.
 */

public class Sale {

    private Integer saleId;
    private Quotation quotation;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }
}
