package com.yogdroidtech.mallfirebase.model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private String categoryName;
    private String catImgUrl;
    private List<String> subCategories;

    public Category(String categoryName, String catImgUrl, List<String> subCategories) {
        this.categoryName = categoryName;
        this.catImgUrl = catImgUrl;
        this.subCategories = subCategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCatImgUrl() {
        return catImgUrl;
    }

    public void setCatImgUrl(String catImgUrl) {
        this.catImgUrl = catImgUrl;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }
}
