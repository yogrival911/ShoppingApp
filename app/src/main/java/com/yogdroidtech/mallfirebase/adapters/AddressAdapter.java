package com.yogdroidtech.mallfirebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yogdroidtech.mallfirebase.AddressSelectListener;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.model.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddViewHolder> {
    List<Address> addressList;
    AddressSelectListener addressSelectListener;

    public AddressAdapter(List<Address> addressList, AddressSelectListener addressSelectListener) {
        this.addressList = addressList;
        this.addressSelectListener = addressSelectListener;
    }

    @NonNull
    @Override
    public AddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);
        return new AddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddViewHolder holder, int position) {
        holder.tvName.setText(addressList.get(position).getFirstName() + addressList.get(position).getLastName());

        String address = addressList.get(position).getHouse()
                + ", " + addressList.get(position).getStreet()+"\n"
                + ", " + addressList.get(position).getCity()
                + ", " + addressList.get(position).getCity() + "\n"
                + ", " + addressList.get(position).getState()
                + ", " + addressList.get(position).getPin();

        holder.tvAddress.setText(address);
        holder.tvLand.setText(addressList.get(position).getLandmark()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressSelectListener.onAddressSelect(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class AddViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout removeAddress;
        TextView tvName,tvAddress,tvLand;
        public AddViewHolder(@NonNull View itemView) {
            super(itemView);
            removeAddress = itemView.findViewById(R.id.deleteAddress);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvLand = itemView.findViewById(R.id.tvLand);
        }
    }
}
