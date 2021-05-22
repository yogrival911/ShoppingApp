package com.yogdroidtech.mallfirebase.ui.wishlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yogdroidtech.mallfirebase.DeleteClickListener;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.ProductListAdaptger;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.home.HomeViewModel;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class WishlistFragment extends Fragment implements DeleteClickListener {
private WishlistViewModel wishlistViewModel;
private ProductListAdaptger productListAdaptger;
private GridLayoutManager gridLayoutManager;
private RecyclerView recyclerView;
private List<Products> productsList = new ArrayList<>();
private Boolean isWishRefresh = false;
    public WishlistFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Bundle bundle = getArguments();
        isWishRefresh = bundle.getBoolean("isRefresh", false);
        wishlistViewModel = new ViewModelProvider(getActivity()).get(WishlistViewModel.class);
        if(true){
            wishlistViewModel.setRefresh(true);
        }
        else {
            wishlistViewModel.setRefresh(false);
        }
        wishlistViewModel.getProducts().observe(getActivity(), new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                Log.i("y", products.toString());
                productsList = products;
                productListAdaptger = new ProductListAdaptger(productsList, true, WishlistFragment.this::onClick);
                recyclerView.setAdapter(productListAdaptger);
            }
        });

        return view;
    }

    @Override
    public void onClick(String id, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("wishlist").document(String.valueOf(id)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Removed", Toast.LENGTH_SHORT).show();
                productsList.remove(position);
                productListAdaptger.notifyDataSetChanged();

            }
        });
    }
}