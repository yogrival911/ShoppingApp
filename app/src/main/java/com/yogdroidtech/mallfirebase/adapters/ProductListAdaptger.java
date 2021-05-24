package com.yogdroidtech.mallfirebase.adapters;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yogdroidtech.mallfirebase.DeleteClickListener;
import com.yogdroidtech.mallfirebase.ProductSelectListener;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.model.Products;
import com.yogdroidtech.mallfirebase.ui.productdetatail.ProductDetailActivity;

import java.util.List;

public class ProductListAdaptger extends RecyclerView.Adapter<ProductListAdaptger.ProductsViewHolder> {
    private List<Products> productsList;
    private Boolean isDelete = false;
    private DeleteClickListener deleteClickListener;
    private ProductSelectListener productSelectListener;

    public ProductListAdaptger(List<Products> productsList, ProductSelectListener productSelectListener) {
        this.productsList = productsList;
        this.productSelectListener = productSelectListener;
    }

    public ProductListAdaptger(List<Products> productsList, Boolean isDelete, DeleteClickListener deleteClickListener) {
        this.productsList = productsList;
        this.isDelete = isDelete;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.mrp.setPaintFlags( Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(holder.itemView.getContext()).load(productsList.get(position).getImgUrl().get(0)).into(holder.ivProduct);
        holder.sellPrice.setText("\u20B9"+productsList.get(position).getSellPrice());
        holder.mrp.setText("\u20B9"+productsList.get(position).getMarkPrice());
        holder.name.setText(productsList.get(position).getProductName());
        if(isDelete) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteClickListener.onClick(productsList.get(position).getId(), position);
                }
            });

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDelete){
                    Intent intent = new Intent(holder.itemView.getContext(),ProductDetailActivity.class);
                    intent.putExtra("productDetail", productsList.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
                else {
                    productSelectListener.onClick(productsList.get(position));

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct, delete;
        TextView name,sellPrice, mrp;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.imageView4);
            delete = itemView.findViewById(R.id.imageView7);
            name = itemView.findViewById(R.id.textView4);
            sellPrice = itemView.findViewById(R.id.textView7);
            mrp = itemView.findViewById(R.id.textView19);
        }
    }
    public void filterList(List<Products> products) {
        // below line is to add our filtered
        // list in our course array list.
        productsList = products;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}
