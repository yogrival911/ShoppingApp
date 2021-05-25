package com.yogdroidtech.mallfirebase.ui.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yogdroidtech.mallfirebase.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewAddressActivity extends AppCompatActivity {
    private List<String> stateList =new ArrayList<>();
    private List<String> cityList =new ArrayList<>();

    @BindView(R.id.etFirstName)
EditText etFirstName;
@BindView(R.id.etLastName)
EditText etLastName;
@BindView(R.id.etHouse)
EditText etHouse;
@BindView(R.id.etStreet)
EditText etStreet;
@BindView(R.id.spinner45)
Spinner stateDrop;
@BindView(R.id.spinner49)
Spinner cityDrop;
@BindView(R.id.etPin)
EditText etPin;
@BindView(R.id.etLandmark)
EditText etLandmark;
@BindView(R.id.etPhone)
EditText etPhone;
@BindView(R.id.button8)
Button saveAddress;
@BindView(R.id.actionTitle)
TextView actionTitle;
@BindView(R.id.imageView15)
ImageView backButton;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);

        ButterKnife.bind(this);
        setDropDown();
        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String house = etHouse.getText().toString();
                String street = etStreet.getText().toString();
                String pin = etPin.getText().toString();
                String landmark = etLandmark.getText().toString();
                String phone = etPhone.getText().toString();
                String state = stateDrop.getSelectedItem().toString();
                String city = cityDrop.getSelectedItem().toString();

                if(isDataValid()){
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("firstName", firstName);
                    docData.put("lastName",lastName);
                    docData.put("house", house);
                    docData.put("street", street);
                    docData.put("pin", pin);
                    docData.put("landmark", landmark);
                    docData.put("phone", phone);
                    docData.put("state", state);
                    docData.put("city", city);
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("address").document().set(docData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(NewAddressActivity.this, "Address Saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    actionTitle.setText("New Address");
    backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    });
    }

    private void setDropDown() {
        stateList.add("Punjab");
        stateList.add("Haryana");
        stateList.add("Delhi");
        stateList.add("Rajasthan");

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        stateDrop.setAdapter(stateAdapter);

        cityList.add("Gurugram");
        cityList.add("Delhi");
        cityList.add("Bathinda");
        cityList.add("Patiala");
        cityList.add("Chandigarh");
        cityList.add("Jalandhar");
        cityList.add("Ludhiana");

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        cityDrop.setAdapter(cityAdapter);
    }

    private boolean isDataValid() {
        if (etFirstName.length() < 4) {
            etFirstName.setError("Atleast 4 characters required");
            return false;
        }

        if (etLastName.length() < 4) {
            etLastName.setError("Atleast 4 characters required");
            return false;
        }

        if (etHouse.length() == 0) {
            etHouse.setError("House no. is required");
            return false;
        }

        if (etStreet.length() == 0) {
            etStreet.setError("Street is required");
            return false;
        }

        if (etStreet.length() == 0) {
            etStreet.setError("Street is required");
            return false;
        }

        if (etPin.length() < 6) {
            etPin.setError("Enter 6 digit pin code");
            return false;
        }

        if (etLandmark.length() == 0) {
            etLandmark.setError("Landmark is required");
            return false;
        }

        if (etPhone.length() < 10) {
            etPhone.setError("Enter 10 digit mobile number");
            return false;
        }

        return true;
    }

}