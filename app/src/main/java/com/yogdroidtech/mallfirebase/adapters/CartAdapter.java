package com.yogdroidtech.mallfirebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yogdroidtech.mallfirebase.CartRemoveListner;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.model.Products;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Products> productsList;
    private CartRemoveListner cartRemoveListner;

    public CartAdapter(List<Products> productsList, CartRemoveListner cartRemoveListner) {
        this.productsList = productsList;
        this.cartRemoveListner = cartRemoveListner;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.tvQuanity.setText(productsList.get(position).getQuantity()+"");
        holder.sellPrice.setText(productsList.get(position).getSellPrice()+"");
        Glide.with(holder.itemView.getContext()).load(productsList.get(position).getImgUrl().get(0)).into(holder.proImage);
        holder.removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartRemoveListner.onCartRemove(productsList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuanity,sellPrice;
        ImageView removeCart,proImage;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuanity = itemView.findViewById(R.id.tvQuanity);
            sellPrice = itemView.findViewById(R.id.sellPrice);
            removeCart = itemView.findViewById(R.id.removeCart);
            proImage = itemView.findViewById(R.id.proImage);
        }
    }
}
