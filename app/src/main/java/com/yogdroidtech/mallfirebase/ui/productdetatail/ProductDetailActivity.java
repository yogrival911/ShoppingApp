package com.yogdroidtech.mallfirebase.ui.productdetatail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.SliderView;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.ProductSliderAdapter;
import com.yogdroidtech.mallfirebase.model.Products;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {
private Products productDetail;
private ProductSliderAdapter productSliderAdapter;
private FirebaseAuth firebaseAuth;

@BindView(R.id.slider)
SliderView sliderView;
@BindView(R.id.button3)
Button addToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);

        productDetail = (Products) getIntent().getSerializableExtra("productDetail");
        Log.i("yog", productDetail.toString());
         firebaseAuth = FirebaseAuth.getInstance();

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartCall();
            }
        });

        productSliderAdapter = new ProductSliderAdapter(productDetail.getImgUrlList());
        sliderView.setSliderAdapter(productSliderAdapter);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    private void addToCartCall() {
        productDetail.setQuantity(4);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("wishlist").document(String.valueOf(productDetail.getId())).set(productDetail);
    }
}