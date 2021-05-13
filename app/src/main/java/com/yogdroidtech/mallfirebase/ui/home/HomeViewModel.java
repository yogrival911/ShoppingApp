package com.yogdroidtech.mallfirebase.ui.home;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.yogdroidtech.mallfirebase.UploadActivity;
import com.yogdroidtech.mallfirebase.model.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Banner>> bannerList;
    public LiveData<List<Banner>> getBanners() {
        if (bannerList == null) {
            bannerList = new MutableLiveData<List<Banner>>();
            loadBanners();
        }
        return bannerList;
    }

    private void loadBanners() {
        // Do an asynchronous operation to fetch users.

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        db.collection("banners")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Banner> banners = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Banner banner = document.toObject(Banner.class);
                                banners.add(banner);
                            }
                            bannerList.setValue(banners);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}