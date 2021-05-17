package com.yogdroidtech.mallfirebase;

import com.yogdroidtech.mallfirebase.model.Products;

public interface CartRemoveListner {
    void onCartRemove(Products product, int position);
}
