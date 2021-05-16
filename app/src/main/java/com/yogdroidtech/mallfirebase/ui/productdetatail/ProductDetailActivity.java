package com.yogdroidtech.mallfirebase.ui.productdetatail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.SliderView;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.ProductSliderAdapter;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {
private Products productDetail;
private ProductSliderAdapter productSliderAdapter;
private FirebaseAuth firebaseAuth;
private WishlistViewModel wishlistViewModel;
private Boolean isRefresh = false;

@BindView(R.id.slider)
SliderView sliderView;
@BindView(R.id.button3)
Button addToCart;
@BindView(R.id.imageView6)
ImageView addToWish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);

        wishlistViewModel = wishlistViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);

        productDetail = (Products) getIntent().getSerializableExtra("productDetail");
        Log.i("yog", productDetail.toString());
         firebaseAuth = FirebaseAuth.getInstance();

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartCall();
            }
        });
        addToWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToWishCall();
            }
        });

        productSliderAdapter = new ProductSliderAdapter(productDetail.getImgUrl());
        sliderView.setSliderAdapter(productSliderAdapter);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    private void addToWishCall() {
        productDetail.setWishList(true);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("wishlist").document(String.valueOf(productDetail.getId())).set(productDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                isRefresh = true;
//                Fragment currentFragment = getFragmentManager().findFragmentByTag("3");
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.detach(currentFragment);
//                fragmentTransaction.attach(currentFragment);
//                fragmentTransaction.commit();
//                wishlistViewModel.refreshProducts();
            }
        });

    }


    private void addToCartCall() {
        productDetail.setQuantity(2);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("cart").document(String.valueOf(productDetail.getId())).set(productDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("gg", "g");
                isRefresh = true;

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("gg", "g");
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent();
        intent.putExtra("isRefresh",isRefresh);
        setResult(111,intent);
        super.onBackPressed();
        finish();
    }
}