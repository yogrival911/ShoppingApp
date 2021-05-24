package com.yogdroidtech.mallfirebase.ui.search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.datatransport.runtime.dagger.BindsOptionalOf;
import com.yogdroidtech.mallfirebase.ProductSelectListener;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.ProductListAdaptger;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.home.HomeViewModel;
import com.yogdroidtech.mallfirebase.ui.productdetatail.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements ProductSelectListener {
private SearchViewModel searchViewModel;
private List<Products> productsList =new ArrayList<>();
private ProductListAdaptger productListAdaptger;
private Boolean isRefresh = false;
private Boolean isCartRefresh = false;
@BindView(R.id.searchPro)
SearchView searchView;
@BindView(R.id.rvSearch)
RecyclerView rvSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvSearch.setLayoutManager(gridLayoutManager);


        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getProducts().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                productsList = products;
                List<Products> emptyList = new ArrayList<>();
                productListAdaptger = new ProductListAdaptger(emptyList, SearchActivity.this::onClick);
                rvSearch.setAdapter(productListAdaptger);

            }
        });
        searchView.requestFocus();
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showInputMethod(view.findFocus());
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        List<Products> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Products item : productsList ) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getProductName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            productListAdaptger.filterList(filteredlist);
        }
    }

    @Override
    public void onClick(Products product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productDetail", product);
        startActivityForResult(intent,999);
//        searchView.clearFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999){
            if(resultCode == 111){
//                Log.i("ll", data.getData().toString());
                isRefresh = data.getBooleanExtra("isRefresh", false);
                isCartRefresh = data.getBooleanExtra("isCartRefresh", false);
                Log.i("t", isRefresh.toString());
            }
            else{
                Log.i("k", data.getData().toString());
            }
        }
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
    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

}