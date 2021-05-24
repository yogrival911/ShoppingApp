package com.yogdroidtech.mallfirebase.ui.offer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yogdroidtech.mallfirebase.ProductSelectListener;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.OfferAdapter;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistViewModel;

import java.util.List;


public class OfferFragment extends Fragment implements ProductSelectListener {
RecyclerView rvOffer;
GridLayoutManager gridLayoutManager;
OfferViewModel offerViewModel;
OfferAdapter offerAdapter;

    public OfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_offer, container, false);
        rvOffer = view.findViewById(R.id.rvOffer);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rvOffer.setLayoutManager(gridLayoutManager);

        offerViewModel = new ViewModelProvider(getActivity()).get(OfferViewModel.class);

        offerViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                offerAdapter = new OfferAdapter(products,OfferFragment.this::onClick);
                rvOffer.setAdapter(offerAdapter);
            }
        });
        return view;
    }

    @Override
    public void onClick(Products product) {

    }
}