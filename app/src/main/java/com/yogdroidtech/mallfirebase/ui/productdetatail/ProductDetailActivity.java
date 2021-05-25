package com.yogdroidtech.mallfirebase.ui.productdetatail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.SliderView;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.ProductSliderAdapter;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {
private Products productDetail;
private ProductSliderAdapter productSliderAdapter;
private FirebaseAuth firebaseAuth;
private WishlistViewModel wishlistViewModel;
private Boolean isRefresh = false; // wishlist refresh flag
private Boolean isCartRefresh = false; //cartRefresh flag
    private List<Products> wishListProducts;
    @BindView(R.id.slider)
SliderView sliderView;
@BindView(R.id.textView17)
TextView addToCart;
@BindView(R.id.imageView6)
ImageView addToWish;
@BindView(R.id.name)
TextView name;
@BindView(R.id.textView11)
TextView sellPrice;
@BindView(R.id.textView13)
TextView mrp;
@BindView(R.id.textView14)
TextView discount;
@BindView(R.id.textView16)
TextView description;
@BindView(R.id.cartCount)
TextView cartCount;
@BindView(R.id.addCartLayout)
LinearLayout addCartLayout;
@BindView(R.id.actionTitle)
TextView actionTitle;
@BindView(R.id.imageView15)
ImageView backButton;
@BindView(R.id.imageView17)
ImageView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);

        actionTitle.setText("Product Detail");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        productDetail = (Products) getIntent().getSerializableExtra("productDetail");
        Log.i("yog", productDetail.toString());
        setView();
        if (productDetail.getQuantity()!=0){
            cartCount.setText(""+productDetail.getQuantity());
            addToCart.setText("Added");
        }
         firebaseAuth = FirebaseAuth.getInstance();

        addCartLayout.setOnClickListener(new View.OnClickListener() {
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
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDynamicLink(productDetail.getId());
            }
        });

        productSliderAdapter = new ProductSliderAdapter(productDetail.getImgUrl());
        sliderView.setSliderAdapter(productSliderAdapter);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    private void createDynamicLink(String productID) {

        String link = "https://www.google.com/productID="+productID;
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(link))
                .setDomainUriPrefix("https://yogdroidtech.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(dynamicLinkUri)
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.i("y",shortLink.toString());
                            shareProduct(shortLink.toString());
                        } else {
                            Log.i("t", "error");
                            // Error
                            // ...
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("t", e.getMessage());

                    }
                });
    }

    private void setView() {
        int disc = productDetail.getMarkPrice() - productDetail.getSellPrice();
        name.setText(productDetail.getProductName());
        sellPrice.setText("\u20B9"+productDetail.getSellPrice());
        mrp.setText("\u20B9"+productDetail.getMarkPrice());
        discount.setText(disc+"%");

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
        int updatedCartQuant = productDetail.getQuantity()+1;
        productDetail.setQuantity(updatedCartQuant);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("cart").document(String.valueOf(productDetail.getId())).set(productDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("gg", "g");
                cartCount.setText(updatedCartQuant+"");
                addToCart.setText("Added");
                isCartRefresh = true;

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
        intent.putExtra("isCartRefresh",isCartRefresh);
        setResult(111,intent);
        super.onBackPressed();
        finish();
    }

    public void shareProduct(String link) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share subject");
            String shareMessage = "\n" + link + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}