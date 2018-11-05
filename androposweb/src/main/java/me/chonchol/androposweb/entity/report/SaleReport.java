package me.chonchol.androposweb.entity.report;


import org.springframework.stereotype.Component;

import java.sql.Date;


/**
 * Created by mehedi.chonchol on 30-Oct-18.
 */

public class SaleReport {

    private String date;
    private String customerName;
    private String phoneNo;
    private String productName;
    private String quantity;
    private String totalAmount;

    public SaleReport(String date, String customerName, String phoneNo, String productName, String quantity, String totalAmount) {
        this.date = date;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.productName = productName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
