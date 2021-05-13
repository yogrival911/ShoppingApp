package com.yogdroidtech.mallfirebase.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.yogdroidtech.mallfirebase.BannerSliderAdapter;
import com.yogdroidtech.mallfirebase.MainActivity;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.UploadActivity;
import com.yogdroidtech.mallfirebase.model.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    private String imgUrl = "https://img.freepik.com/free-psd/digital-marketing-facebook-banner-template_237398-233.jpg?size=626&ext=jpg";
    private String imgUrl2 = "https://img.freepik.com/free-psd/digital-marketing-social-network-cover-web-banner-template_237398-271.jpg?size=626&ext=jpg";
    private String imgUrl3 = "https://img.freepik.com/free-psd/digital-marketing-facebook-banner-template_237398-233.jpg?size=626&ext=jpg";
    private List<Banner> bannerList = new ArrayList<>();
    private BannerSliderAdapter bannerSliderAdapter;
    private SliderView sliderView;
    private Button upload;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = view.findViewById(R.id.slider);
        upload = view.findViewById(R.id.button4);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UploadActivity.class));
            }
        });
        makeList();

        bannerSliderAdapter = new BannerSliderAdapter(bannerList);
        sliderView.setSliderAdapter(bannerSliderAdapter);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        return view;
    }

    private void makeList() {
        bannerList.add(new Banner(imgUrl));
        bannerList.add(new Banner(imgUrl2));
        bannerList.add(new Banner(imgUrl3));
    }
}