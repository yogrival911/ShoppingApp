package com.yogdroidtech.mallfirebase.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderView;
import com.yogdroidtech.mallfirebase.adapters.BannerSliderAdapter;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.UploadActivity;
import com.yogdroidtech.mallfirebase.adapters.ProductListAdaptger;
import com.yogdroidtech.mallfirebase.model.Banner;
import com.yogdroidtech.mallfirebase.model.Products;

import java.util.List;


public class HomeFragment extends Fragment {
    private String imgUrl = "https://img.freepik.com/free-psd/digital-marketing-facebook-banner-template_237398-233.jpg?size=626&ext=jpg";
    private String imgUrl2 = "https://img.freepik.com/free-psd/digital-marketing-social-network-cover-web-banner-template_237398-271.jpg?size=626&ext=jpg";
    private String imgUrl3 = "https://img.freepik.com/free-psd/digital-marketing-facebook-banner-template_237398-233.jpg?size=626&ext=jpg";
    private List<Banner> bannerList;
    private List<Products> productsList;
    private BannerSliderAdapter bannerSliderAdapter;
    private SliderView sliderView;
    private Button upload;
    private RecyclerView rvNewArrival;
    private HomeViewModel homeViewModel;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ProductListAdaptger productListAdaptger;
    private GridLayoutManager gridLayoutManager;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        sliderView = view.findViewById(R.id.slider);
        upload = view.findViewById(R.id.button4);
        rvNewArrival = view.findViewById(R.id.rvNewArrival);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UploadActivity.class));
            }
        });
        makeList();

        return view;
    }

    private void makeList() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        homeViewModel.getBanners().observe(getActivity(), new Observer<List<Banner>>() {
            @Override
            public void onChanged(List<Banner> banners) {

                bannerList = banners;
                bannerSliderAdapter = new BannerSliderAdapter(bannerList);
                sliderView.setSliderAdapter(bannerSliderAdapter);
                sliderView.setScrollTimeInSec(2);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
                getNewArrival();
            }
        });
    }

    private void getNewArrival() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        homeViewModel.getProducts().observe(getActivity(), new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                productListAdaptger = new ProductListAdaptger(products);
                gridLayoutManager = new GridLayoutManager(getContext(), 2);
                rvNewArrival.setLayoutManager(gridLayoutManager);
                rvNewArrival.setAdapter(productListAdaptger);
            }
        });
    }
}