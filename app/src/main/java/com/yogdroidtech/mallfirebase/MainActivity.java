package com.yogdroidtech.mallfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.ProfileFragment;
import com.yogdroidtech.mallfirebase.ui.cart.CartActivity;
import com.yogdroidtech.mallfirebase.ui.home.HomeFragment;
import com.yogdroidtech.mallfirebase.ui.home.HomeViewModel;
import com.yogdroidtech.mallfirebase.ui.offer.OfferFragment;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistFragment;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    Fragment fragment1 = new HomeFragment();
    Fragment fragment2 = new WishlistFragment();
    Fragment fragment3 = new OfferFragment();
    Fragment fragment4 = new ProfileFragment();
    FragmentManager fm = getSupportFragmentManager();
    private WishlistViewModel wishlistViewModel;
    private MainActViewModel mainActViewModel;
    private static int RC_SIGN_IN= 123;
    private Boolean isRefresh = false;          //wishlist refresh
    private Boolean isCartRefresh = false;      //cart refresh
    Fragment active = fragment1;
    private TextView textCartItemCount;
    private int mCartItemCount;
    private List<Products> cartListProducts;
    private List<Products> wishListProducts;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.frame_container)
    FrameLayout frame_container;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mainActViewModel = new ViewModelProvider(this).get(MainActViewModel.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", false);
        fragment2.setArguments(bundle);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.purple_500));
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.cart:
                        startActivity(new Intent(MainActivity.this, CartActivity.class));
                        return true;
                }
                return false;
            }
        });

       if(FirebaseAuth.getInstance().getCurrentUser()==null){

           // Choose authentication providers
           List<AuthUI.IdpConfig> providers = Arrays.asList(
                   new AuthUI.IdpConfig.EmailBuilder().build(),
                   new AuthUI.IdpConfig.GoogleBuilder().build(),
                   new AuthUI.IdpConfig.FacebookBuilder().build());

// Create and launch sign-in intent
           startActivityForResult(
                   AuthUI.getInstance()
                           .createSignInIntentBuilder()
                           .setAvailableProviders(providers)
                           .build(),
                   RC_SIGN_IN);
       }
       else{
           setUpFragments();
           loadCartProducts();

       }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;

                    case R.id.wishList:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.offer:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;

                    case R.id.profile:
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;
                        return true;
                }
                return false;
            }
        });
    }

    private void setUpFragments() {
        fm.beginTransaction().add(R.id.frame_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.frame_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.frame_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.frame_container,fragment1, "1").commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.i("yogesh", user.getDisplayName());
                setUpFragments();

                // ...
            } else {
                Log.i("yogesh", "failed");

                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
        if(requestCode == 9){
            if(resultCode == 111){
//                Log.i("ll", data.getData().toString());
                isRefresh = data.getBooleanExtra("isRefresh", false);
                isCartRefresh = data.getBooleanExtra("isCartRefresh", false);
                Log.i("t", isRefresh.toString());
                if(isRefresh){
                    //final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment frg = getSupportFragmentManager().findFragmentByTag("2");
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isRefresh", true);
                    frg.setArguments(bundle);
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();
                }
                if(isCartRefresh){
                    loadCartProducts();
                    Fragment frg = getSupportFragmentManager().findFragmentByTag("1");
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();
                }
            }
            else{
                Log.i("k", data.getData().toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
                startActivityForResult(new Intent(MainActivity.this, CartActivity.class),9);
        }

        return true;

    }

    public void setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    private void loadCartProducts() {

        mainActViewModel.setRefresh(true);
        mainActViewModel.getCartProducts().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                cartListProducts = products;
                mCartItemCount = products.size();
                setupBadge();
            }
        });

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        // Create a new user with a first and last name
//        db.collection("users")
//                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("cart").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            List<Products> products = new ArrayList<>();
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("TAG", document.getId() + " => " + document.get("imgUrl"));
//                                List<String> imgUrlList =(List<String>) document.get("imgUrl");
//                                String productName = (String)document.get("productName");
//                                String category = (String)document.get("category");
//                                String subCategory = (String) document.get("subCategory");
//                                String unit =(String) document.get("unit");
//                                Boolean isWishList = (Boolean)document.get("isWishList");
//                                String id = (String)document.get("id");
//
//                                Long quantity = (Long)document.get("quantity");
//                                int quantInt = quantity.intValue();
//
//                                Long maxPrice = (Long)document.get("markPrice");
//                                int maxPriceInt = maxPrice.intValue();
//
//                                Long sellPrice = (Long)document.get("sellPrice");
//                                int sellPriceInt = sellPrice.intValue();
//
//                                Products product = new Products(productName,category,subCategory,id,maxPriceInt,sellPriceInt,isWishList,unit,quantInt);
//                                products.add(product);
//                            }
//                            mCartItemCount = products.size();
//                            setupBadge();
//
//                        } else {
//                            Log.d("TAG", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
    }

}