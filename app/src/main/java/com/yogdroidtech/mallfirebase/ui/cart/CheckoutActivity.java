package com.yogdroidtech.mallfirebase.ui.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yogdroidtech.mallfirebase.CartRemoveListner;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.AddressAdapter;
import com.yogdroidtech.mallfirebase.adapters.CartAdapter;
import com.yogdroidtech.mallfirebase.model.Address;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.address.AddressActivity;
import com.yogdroidtech.mallfirebase.ui.address.NewAddressActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity implements CartRemoveListner {
private List<Products> cartList;
private List<Address> addressList = new ArrayList<>();
private CartAdapter cartAdapter;
@BindView(R.id.textView24)
TextView selectAdd;
@BindView(R.id.tvName)
TextView tvName;
@BindView(R.id.tvAddress)
TextView tvAddress;
@BindView(R.id.tvLand)
TextView tvLand;
@BindView(R.id.rvCart)
    RecyclerView rvCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ButterKnife.bind(this);
        cartList = (List<Products>) getIntent().getSerializableExtra("cartList");
        Log.i("g", cartList.toString());
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartList,this::onCartRemove);
        rvCart.setAdapter(cartAdapter);
        rvCart.setNestedScrollingEnabled(false);
        getAddresses();

        selectAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, AddressActivity.class);
                intent.putExtra("addressList",(Serializable) addressList);
                startActivityForResult(intent, 123);
            }
        });
    }
    private void getAddresses() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("address").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.get("imgUrl"));
                                Address address = document.toObject(Address.class);
                                addressList.add(address);
                            }

                           if(addressList.size()!=0){
                               tvName.setText(addressList.get(0).getFirstName() + addressList.get(0).getLastName());
                               String address = addressList.get(0).getHouse()
                                       + ", " + addressList.get(0).getStreet()+"\n"
                                       + ", " + addressList.get(0).getCity()
                                       + ", " + addressList.get(0).getCity() + "\n"
                                       + ", " + addressList.get(0).getState()
                                       + ", " + addressList.get(0).getPin();
                               tvAddress.setText(address);
                               tvLand.setText(addressList.get(0).getLandmark()+"");
                           }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onCartRemove(Products product, int position) {

    }
}