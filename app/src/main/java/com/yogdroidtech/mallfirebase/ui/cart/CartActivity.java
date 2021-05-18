package com.yogdroidtech.mallfirebase.ui.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yogdroidtech.mallfirebase.CartRemoveListner;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.CartAdapter;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Integer.parseInt;

public class CartActivity extends AppCompatActivity implements CartRemoveListner {
private CartAdapter cartAdapter;
private CartViewModel cartViewModel;
private LinearLayoutManager linearLayoutManager;
private List<Products> productsList = new ArrayList<>();
private Boolean isCartRefresh = false;
private int grandTotal = 0;
@BindView(R.id.rvCart)
RecyclerView rvCart;
@BindView(R.id.textView3)
TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(linearLayoutManager);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getProducts().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                productsList = products;
                cartAdapter = new CartAdapter(products, CartActivity.this::onCartRemove);
                rvCart.setAdapter(cartAdapter);
                for(int i=0; i<products.size();i++){
                    int quantity = products.get(i).getQuantity();
                    int sellPrice = products.get(i).getSellPrice();
                    grandTotal = grandTotal + quantity * sellPrice;
                }
                tvTotal.setText(""+ grandTotal);
            }
        });
    }

    @Override
    public void onCartRemove(Products product, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("cart").document(product.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                isCartRefresh = true;
                productsList.remove(position);
                cartAdapter.notifyDataSetChanged();
                int quantity = product.getQuantity();
                int sellPrice = product.getSellPrice();
                int amountReduced = quantity*sellPrice;
                grandTotal = grandTotal - amountReduced;
                tvTotal.setText(""+ grandTotal);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("isCartRefresh",isCartRefresh);
        setResult(111,intent);
        super.onBackPressed();
        finish();
    }
}