package com.yogdroidtech.mallfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yogdroidtech.mallfirebase.ui.home.HomeFragment;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    Fragment fragment1 = new HomeFragment();
    Fragment fragment2 = new WishlistFragment();
    Fragment fragment3 = new WishlistFragment();
    FragmentManager fm = getSupportFragmentManager();
    private static int RC_SIGN_IN= 123;
    private Boolean isRefresh = false;
    Fragment active = fragment1;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.frame_container)
    FrameLayout frame_container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

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
           if (savedInstanceState == null) {
               setUpFragments();
           }

       }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;

                    case R.id.wishlist:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;

                    case R.id.profile:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;
                }
                return false;
            }
        });
    }

    private void setUpFragments() {
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
                Log.i("t", isRefresh.toString());
                if(isRefresh){
                    //final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment frg = getSupportFragmentManager().findFragmentByTag("2");
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

}