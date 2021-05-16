package com.yogdroidtech.mallfirebase.ui.wishlist;

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
import com.yogdroidtech.mallfirebase.model.Banner;
import com.yogdroidtech.mallfirebase.model.Products;

import java.util.ArrayList;
import java.util.List;

public class WishlistViewModel extends ViewModel {
    private MutableLiveData<List<Products>> productList;
    public LiveData<List<Products>> getProducts() {
        if (productList == null) {
            productList = new MutableLiveData<List<Products>>();
            loadProducts();
        }
        return productList;
    }

    public void refreshProducts(){
        loadProducts();
    }

    private void loadProducts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("wishlist").get()
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
                                Long id = (Long)document.get("id");
                                int idInt = id.intValue();

                                Long maxPrice = (Long)document.get("markPrice");
                                int maxPriceInt = maxPrice.intValue();

                                Long sellPrice = (Long)document.get("sellPrice");
                                int sellPriceInt = sellPrice.intValue();

                                Products product = new Products(productName,category,subCategory,idInt,maxPriceInt,sellPriceInt,isWishList,unit);
                                product.setImgUrl(imgUrlList);
                                products.add(product);
                            }
                            productList.setValue(products);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
