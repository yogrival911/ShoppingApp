package com.yogdroidtech.mallfirebase.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yogdroidtech.mallfirebase.CategorySelectListner;
import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.model.Category;
import com.yogdroidtech.mallfirebase.ui.productlist.ProductListActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatViewHolder> {
    private List<Category> categoryList;
    private CategorySelectListner categorySelectListner;

    public CategoryAdapter(List<Category> categoryList, CategorySelectListner categorySelectListner) {
        this.categoryList = categoryList;
        this.categorySelectListner = categorySelectListner;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CatViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(categoryList.get(position).getCatImgUrl()).into(holder.imageView);
        holder.categoryName.setText(categoryList.get(position).getCategoryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categorySelectListner.onCategorySelect(categoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView categoryName;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView5);
            categoryName = itemView.findViewById(R.id.textView5);
        }
    }
}
