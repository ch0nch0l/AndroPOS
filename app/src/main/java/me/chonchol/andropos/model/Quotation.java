package me.chonchol.andropos.model;

import java.util.Date;

/**
 * Created by mehedi.chonchol on 18-Oct-18.
 */

public class Quotation {

    private Integer quotationId;

    private Customer customer;

    private Date orderDate;

    private Integer orderStatus;

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
