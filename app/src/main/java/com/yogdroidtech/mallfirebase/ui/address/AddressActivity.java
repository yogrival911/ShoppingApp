package com.yogdroidtech.mallfirebase.ui.address;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yogdroidtech.mallfirebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends AppCompatActivity {
    @BindView(R.id.actionTitle)
    TextView actionTitle;
    @BindView(R.id.imageView15)
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        ButterKnife.bind(this);

        actionTitle.setText("Addresses");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}