package com.yogdroidtech.mallfirebase.ui.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.CartAdapter;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistViewModel;

import java.util.List;

import butterknife.BindView;

public class CartActivity extends AppCompatActivity {
private CartAdapter cartAdapter;
private CartViewModel cartViewModel;
private LinearLayoutManager linearLayoutManager;
@BindView(R.id.rvCart)
RecyclerView rvCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        linearLayoutManager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(linearLayoutManager);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getProducts().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                cartAdapter = new CartAdapter(products);
                rvCart.setAdapter(cartAdapter);
            }
        });
    }
}