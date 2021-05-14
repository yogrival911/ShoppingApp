package com.yogdroidtech.mallfirebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.yogdroidtech.mallfirebase.R;

import java.util.List;

public class ProductSliderAdapter extends SliderViewAdapter<ProductSliderAdapter.ProDuctViewHolder> {
    private List<String> productUrlList;

    public ProductSliderAdapter(List<String> productUrlList) {
        this.productUrlList = productUrlList;
    }

    @Override
    public ProductSliderAdapter.ProDuctViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_layout, parent, false);

        return new ProDuctViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductSliderAdapter.ProDuctViewHolder viewHolder, int position) {
        Glide.with(viewHolder.itemView.getContext()).load(productUrlList.get(position)).into(viewHolder.ivBanner);
    }

    @Override
    public int getCount() {
        return productUrlList.size();
    }

    public class ProDuctViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView ivBanner;
        public ProDuctViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.imageView2);
        }
    }
}