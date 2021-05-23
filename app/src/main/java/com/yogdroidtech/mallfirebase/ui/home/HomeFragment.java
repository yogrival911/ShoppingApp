package com.yogdroidtech.mallfirebase.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
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
import com.yogdroidtech.mallfirebase.CategorySelectListner;
import com.yogdroidtech.mallfirebase.CircleProgressBarCustom;
import com.yogdroidtech.mallfirebase.MainActViewModel;
import com.yogdroidtech.mallfirebase.ProductSelectListener;
import com.yogdroidtech.mallfirebase.adapters.BannerSliderAdapter;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.UploadActivity;
import com.yogdroidtech.mallfirebase.adapters.CategoryAdapter;
import com.yogdroidtech.mallfirebase.adapters.ProductListAdaptger;
import com.yogdroidtech.mallfirebase.model.Banner;
import com.yogdroidtech.mallfirebase.model.Category;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.productdetatail.ProductDetailActivity;
import com.yogdroidtech.mallfirebase.ui.productlist.ProductListActivity;
import com.yogdroidtech.mallfirebase.ui.search.SearchActivity;
import com.yogdroidtech.mallfirebase.ui.wishlist.WishlistViewModel;

import java.io.Serializable;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment implements ProductSelectListener , CategorySelectListner {
    private List<Banner> bannerList;
    private List<Products> wishListProducts;
    private BannerSliderAdapter bannerSliderAdapter;
    private SliderView sliderView;
    private WishlistViewModel wishlistViewModel;
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
    private NestedScrollView scrollView;
    private ConstraintLayout searchLayout;
    private List<Products> cartProducts;
    private MainActViewModel mainActViewModel;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainActViewModel = new ViewModelProvider(requireActivity()).get(MainActViewModel.class);
        mainActViewModel.setRefresh(false);
        mainActViewModel.getCartProducts().observe(requireActivity(), new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                cartProducts = products;
                Log.i("c", cartProducts.toString());
            }
        });
//        cartProducts =(List<Products>) getArguments().getSerializable("cartList");
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        wishlistViewModel = new ViewModelProvider(getActivity()).get(WishlistViewModel.class);
        wishlistViewModel.setRefresh(false);
        wishlistViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                wishListProducts = products;
                Log.i("y", wishListProducts.toString());
            }
        });

        sliderView = view.findViewById(R.id.slider);
        rvNewArrival = view.findViewById(R.id.rvNewArrival);
        rvCategory = view.findViewById(R.id.rvCategory);
        progressBarCustom = view.findViewById(R.id.circularProgressBar);
        scrollView = view.findViewById(R.id.scroll);
        searchLayout = view.findViewById(R.id.searchLayout);
//        scrollView.setVisibility(View.GONE);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
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
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivityForResult(new Intent(getActivity(), SearchActivity.class),9);
            }
        });

        return view;
    }

    private void makeList() {
        progressBarCustom.setVisibility(View.VISIBLE);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        homeViewModel.getBanners().observe(getViewLifecycleOwner(), new Observer<List<Banner>>() {
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
                productListAdaptger = new ProductListAdaptger(products, HomeFragment.this::onClick);
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
                categoryAdapter = new CategoryAdapter(categories, HomeFragment.this::onCategorySelect);
                rvCategory.setAdapter(categoryAdapter);
                progressBarCustom.clearAnimation();
                progressBarCustom.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onClick(Products product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
//        intent.putExtra("cart_products", (Serializable) cartProducts);
//        intent.putExtra("wish_list",(Serializable)wishListProducts);
        for(int i=0;i<wishListProducts.size();i++){
            if(wishListProducts.get(i).getId().equals(product.getId())){
                product.setWishList(true);
            }
        }
        for(int j=0;j<cartProducts.size();j++){
            if(cartProducts.get(j).getId().equals(product.getId())){
                product.setQuantity(cartProducts.get(j).getQuantity());
            }
        }
        intent.putExtra("productDetail", product);
        getActivity().startActivityForResult(intent,9);
    }

    @Override
    public void onCategorySelect(Category category) {
        Intent intent = new Intent(getActivity(), ProductListActivity.class);
        intent.putExtra("category", category);
        getActivity().startActivityForResult(intent,9);
    }
}