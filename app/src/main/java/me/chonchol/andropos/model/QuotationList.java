package me.chonchol.andropos.model;

/**
 * Created by mehedi.chonchol on 18-Oct-18.
 */

public class QuotationList {

    private Integer quoteListId;
    private Quotation quotation;
    private Product product;
    private Integer quantity;
    private Double totalPrice;

    public Integer getQuoteListId() {
        return quoteListId;
    }

    public void setQuoteListId(Integer quoteListId) {
        this.quoteListId = quoteListId;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
