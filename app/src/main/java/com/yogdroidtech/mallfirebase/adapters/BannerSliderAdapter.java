package com.yogdroidtech.mallfirebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.model.Banner;

import java.util.List;

public class BannerSliderAdapter extends SliderViewAdapter<BannerSliderAdapter.SliderViewHolder> {
    private List<Banner> bannerList;

    public BannerSliderAdapter(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {


        Glide.with(viewHolder.itemView.getContext()).load(bannerList.get(position).getImgUrl()).into(viewHolder.ivBanner);

    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView ivBanner;
        public SliderViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.imageView2);
        }
    }
}
