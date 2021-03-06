package com.yogdroidtech.mallfirebase.model;

import androidx.lifecycle.LifecycleService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

import bolts.Bolts;

public class Products implements Serializable {

    private String productName;
    private String category;
    private String subCategory;
    private String id;
    private int markPrice;
    private int sellPrice;
    private Boolean isWishList;
    private String unit ;
    private int quantity;
    private List<String> imgUrl = new ArrayList<>();


    public Products() {
    }
    public Products(String productName, String category, String subCategory, String id, int markPrice, int sellPrice, Boolean isWishList, String unit) {
        this.productName = productName;
        this.category = category;
        this.subCategory = subCategory;
        this.id = id;
        this.markPrice = markPrice;
        this.sellPrice = sellPrice;
        this.isWishList = isWishList;
        this.unit = unit;
    }

    public Products(String productName, String category, String subCategory, String id, int markPrice, int sellPrice, Boolean isWishList, String unit, int quantity) {
        this.productName = productName;
        this.category = category;
        this.subCategory = subCategory;
        this.id = id;
        this.markPrice = markPrice;
        this.sellPrice = sellPrice;
        this.isWishList = isWishList;
        this.unit = unit;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(int markPrice) {
        this.markPrice = markPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Boolean getWishList() {
        return isWishList;
    }

    public void setWishList(Boolean wishList) {
        isWishList = wishList;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }
}
