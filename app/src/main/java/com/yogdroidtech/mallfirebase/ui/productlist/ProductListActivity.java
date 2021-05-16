package com.yogdroidtech.mallfirebase.ui.productlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.ProductListAdaptger;
import com.yogdroidtech.mallfirebase.adapters.SubCatAdapter;
import com.yogdroidtech.mallfirebase.model.Category;
import com.yogdroidtech.mallfirebase.model.Products;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {
private LinearLayoutManager linearLayoutManager;
private GridLayoutManager gridLayoutManager;
private SubCatAdapter subCatAdapter;
private Category category;
private List<String> subCatList;
private List<Products> productsList;
private ProductListAdaptger productListAdaptger;

    @BindView(R.id.rvSubCat)
    RecyclerView rvSubCat;
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);

        category = (Category) getIntent().getSerializableExtra("category");
        subCatList = category.getSubCategories();

        linearLayoutManager = new LinearLayoutManager(this );
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvSubCat.setLayoutManager(linearLayoutManager);
        subCatAdapter =  new SubCatAdapter(subCatList);
        rvSubCat.setAdapter(subCatAdapter);

        gridLayoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(gridLayoutManager);

       getProducts();

    }

    private void getProducts() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        firestore.collection("products").
                whereEqualTo("subCategory", "mobile").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Products> products = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
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
                    productsList = products;
                    Log.i("y", productsList.toString());
                    setProducts(productsList);

                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void setProducts(List<Products> productsList) {
        productListAdaptger = new ProductListAdaptger(productsList);
        rvProducts.setAdapter(productListAdaptger);
    }
}