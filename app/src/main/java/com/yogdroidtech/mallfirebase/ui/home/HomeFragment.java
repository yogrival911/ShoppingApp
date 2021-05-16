package com.yogdroidtech.mallfirebase.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.CircularPropagation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.yogdroidtech.mallfirebase.CircleProgressBarCustom;
import com.yogdroidtech.mallfirebase.adapters.BannerSliderAdapter;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.UploadActivity;
import com.yogdroidtech.mallfirebase.adapters.CategoryAdapter;
import com.yogdroidtech.mallfirebase.adapters.ProductListAdaptger;
import com.yogdroidtech.mallfirebase.model.Banner;
import com.yogdroidtech.mallfirebase.model.Category;
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
    private RecyclerView rvNewArrival,rvCategory;
    private HomeViewModel homeViewModel;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ProductListAdaptger productListAdaptger;
    private CategoryAdapter categoryAdapter;
    private GridLayoutManager gridLayoutManager,gridLayoutManager2;
    private LinearLayoutManager linearLayoutManager;
    private static int RC_SIGN_IN= 123;
    private CircleProgressBarCustom progressBarCustom;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        sliderView = view.findViewById(R.id.slider);
        rvNewArrival = view.findViewById(R.id.rvNewArrival);
        rvCategory = view.findViewById(R.id.rvCategory);
        progressBarCustom = view.findViewById(R.id.circularProgressBar);

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager2 = new GridLayoutManager(getContext(), 2);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvCategory.setLayoutManager(gridLayoutManager);

//        rvCategory.setNestedScrollingEnabled(false);
        rvNewArrival.setNestedScrollingEnabled(false);
//
//        rvCategory.setHasFixedSize(false);
//        rvCategory.setHasFixedSize(false);

        rvNewArrival.setLayoutManager(gridLayoutManager2);

        makeList();

        return view;
    }

    private void makeList() {
        progressBarCustom.setVisibility(View.VISIBLE);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        homeViewModel.getBanners().observe(getActivity(), new Observer<List<Banner>>() {
            @Override
            public void onChanged(List<Banner> banners) {

                bannerList = banners;
                bannerSliderAdapter = new BannerSliderAdapter(bannerList);
                sliderView.setSliderAdapter(bannerSliderAdapter);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                sliderView.setIndicatorSelectedColor(getResources().getColor(R.color.purple_500));
                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                sliderView.setScrollTimeInSec(5); //set scroll delay in seconds :
                sliderView.startAutoCycle();
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
                rvNewArrival.setAdapter(productListAdaptger);
                rvCategory.setAdapter(productListAdaptger);
                getCategories();
            }
        });
    }

    private void getCategories() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        homeViewModel.getCategories().observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                Log.i("u", categories.toString());
                categoryAdapter = new CategoryAdapter(categories);
                rvCategory.setAdapter(categoryAdapter);
                progressBarCustom.clearAnimation();
                progressBarCustom.setVisibility(View.GONE);

            }
        });
    }

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}