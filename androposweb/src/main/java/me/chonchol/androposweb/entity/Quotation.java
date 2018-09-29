package me.chonchol.androposweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "QUOTATION")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quotation_id")
    private Integer quotationId;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customers customer;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_status")
    private Integer orderStatus;

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
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
