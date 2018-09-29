package me.chonchol.andropos.model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class Category {

    //@SerializedName("cat_id")
    private Integer catId;
    //@SerializedName("cat_name")
    private String catName;
    @SerializedName("active")
    private Boolean isActive;

    public Category() {
    }

    public Category(Integer catId, String catName, Boolean isActive) {
        this.catId = catId;
        this.catName = catName;
        this.isActive = isActive;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
