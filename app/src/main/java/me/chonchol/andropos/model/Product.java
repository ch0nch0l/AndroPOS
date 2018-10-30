package me.chonchol.andropos.model;

import com.google.gson.annotations.SerializedName;

import ir.mirrajabi.searchdialog.core.Searchable;

public class Product implements Searchable{

    private Integer productId;
    private String productName;
    private String description;
    private String Image;
    private String code;
    private Subcategory subcategory;
    private Double cost;
    private Double price;
    @SerializedName("active")
    private Boolean isActive;

    public Product() {
    }

    public Product(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    @Override
    public String getTitle() {
        return productName;
    }
}
