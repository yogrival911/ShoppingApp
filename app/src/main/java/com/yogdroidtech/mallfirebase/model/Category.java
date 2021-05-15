package com.yogdroidtech.mallfirebase.model;

import java.util.List;

public class Category {
    private String categoryName;
    private String catImgUrl;
    private List<String> subCategories;

    public Category(String categoryName, String catImgUrl) {
        this.categoryName = categoryName;
        this.catImgUrl = catImgUrl;
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
