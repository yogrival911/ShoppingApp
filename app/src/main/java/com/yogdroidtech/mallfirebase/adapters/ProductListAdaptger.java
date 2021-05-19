package com.yogdroidtech.mallfirebase.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        Glide.with(holder.itemView.getContext()).load(productsList.get(position).getImgUrl().get(0)).into(holder.ivProduct);

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
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.imageView4);
            delete = itemView.findViewById(R.id.imageView7);
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
