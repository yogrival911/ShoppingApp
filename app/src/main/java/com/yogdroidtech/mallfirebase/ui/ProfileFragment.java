package com.yogdroidtech.mallfirebase.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.ui.address.AddressActivity;
import com.yogdroidtech.mallfirebase.ui.cart.CartActivity;


public class ProfileFragment extends Fragment implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
private TextView userName;
private ConstraintLayout address,cart,wishlist,orderHistory,wallet;
private BottomNavigationView bottomNavigationView;
    //
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.textView10);
        address = view.findViewById(R.id.address);
        cart = view.findViewById(R.id.cart);
        wishlist = view.findViewById(R.id.wishListpro);
        orderHistory = view.findViewById(R.id.orderHistory);
        wallet = view.findViewById(R.id.wallet);
        bottomNavigationView =(BottomNavigationView) getActivity().findViewById(R.id.bottomNavigationView);

        address.setOnClickListener(this::onClick);
        cart.setOnClickListener(this::onClick);
        wishlist.setOnClickListener(this::onClick);

        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.address:
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.cart:
                startActivity(new Intent(getContext(), CartActivity.class));
                break;
            case R.id.wishListpro:
                bottomNavigationView.setSelectedItemId(R.id.wishList);
            break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNavigationView.setSelectedItemId(item.getItemId());
        return true;
    }
}