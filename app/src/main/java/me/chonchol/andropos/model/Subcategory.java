package me.chonchol.andropos.model;

import com.google.gson.annotations.SerializedName;

public class Subcategory {

    private Integer subcatId;

    private String subcatName;

    @SerializedName("active")
    private Boolean isActive;

    private Category category;

    public Integer getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(Integer subcatId) {
        this.subcatId = subcatId;
    }

    public String getSubcatName() {
        return subcatName;
    }

    public void setSubcatName(String subcatName) {
        this.subcatName = subcatName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Integer catId) {
        this.category = category;
    }
}
