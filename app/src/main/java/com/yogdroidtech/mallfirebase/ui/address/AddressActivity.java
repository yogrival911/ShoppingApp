package com.yogdroidtech.mallfirebase.ui.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yogdroidtech.mallfirebase.AddressSelectListener;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.adapters.AddressAdapter;
import com.yogdroidtech.mallfirebase.model.Address;
import com.yogdroidtech.mallfirebase.model.Products;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends AppCompatActivity implements AddressSelectListener {
    List<Address> addressList;
    private AddressAdapter addressAdapter;
    @BindView(R.id.actionTitle)
    TextView actionTitle;
    @BindView(R.id.imageView15)
    ImageView backButton;
    @BindView(R.id.addNewAddress)
    Button addNewAddress;
    @BindView(R.id.recyclerAddress)
    RecyclerView recyclerAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        ButterKnife.bind(this);

        addressList = (List<Address>) getIntent().getSerializableExtra("addressList");
        recyclerAddress.setLayoutManager(new LinearLayoutManager(this));
        addressAdapter = new AddressAdapter(addressList, this::onAddressSelect);
        recyclerAddress.setAdapter(addressAdapter);
        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, NewAddressActivity.class);
                startActivity(intent);
            }
        });
        actionTitle.setText("Addresses");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onAddressSelect(int position) {

    }
}