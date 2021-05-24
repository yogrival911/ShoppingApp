package com.yogdroidtech.mallfirebase.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yogdroidtech.mallfirebase.ProductSelectListener;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.model.Products;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {
    private List<Products> productsList;
    private ProductSelectListener productSelectListener;

    public OfferAdapter(List<Products> productsList, ProductSelectListener productSelectListener) {
        this.productsList = productsList;
        this.productSelectListener = productSelectListener;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        holder.discount.setVisibility(View.VISIBLE);
        holder.mrp.setPaintFlags( Paint.STRIKE_THRU_TEXT_FLAG);
        holder.sellPrice.setText("\u20B9"+productsList.get(position).getSellPrice());
        holder.mrp.setText("\u20B9"+productsList.get(position).getMarkPrice());
        holder.name.setText(productsList.get(position).getProductName());
        Glide.with(holder.itemView.getContext()).load(productsList.get(position).getImgUrl().get(0)).into(holder.ivProduct);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct, delete;
        TextView name,sellPrice, mrp;
        ConstraintLayout discount;
        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.imageView4);
            delete = itemView.findViewById(R.id.imageView7);
            name = itemView.findViewById(R.id.textView4);
            sellPrice = itemView.findViewById(R.id.textView7);
            mrp = itemView.findViewById(R.id.textView19);
            discount = itemView.findViewById(R.id.discount);
        }
    }
}
