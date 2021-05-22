package com.yogdroidtech.mallfirebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yogdroidtech.mallfirebase.model.Products;

import java.util.ArrayList;
import java.util.List;

public class MainActViewModel extends ViewModel {
    private MutableLiveData<List<Products>> cartProductList;
    private Boolean isRefresh = false;
    public MutableLiveData<List<Products>> getCartProducts() {
        if (cartProductList == null || isRefresh) {
            cartProductList = new MutableLiveData<List<Products>>();
            loadCartProducts();
        }
        return cartProductList;
    }

    public void setRefresh(Boolean refresh) {
        isRefresh = refresh;
    }

    private void loadCartProducts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Products> products = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.get("imgUrl"));
                                List<String> imgUrlList =(List<String>) document.get("imgUrl");
                                String productName = (String)document.get("productName");
                                String category = (String)document.get("category");
                                String subCategory = (String) document.get("subCategory");
                                String unit =(String) document.get("unit");
                                Boolean isWishList = (Boolean)document.get("isWishList");
                                String id = (String)document.get("id");

                                Long quantity = (Long)document.get("quantity");
                                int quantInt = quantity.intValue();

                                Long maxPrice = (Long)document.get("markPrice");
                                int maxPriceInt = maxPrice.intValue();

                                Long sellPrice = (Long)document.get("sellPrice");
                                int sellPriceInt = sellPrice.intValue();

                                Products product = new Products(productName,category,subCategory,id,maxPriceInt,sellPriceInt,isWishList,unit,quantInt);
                                products.add(product);
                            }
                            cartProductList.setValue(products);
                        }
                        else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
