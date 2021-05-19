package com.yogdroidtech.mallfirebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.SubCatListener;

import java.util.List;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.SubViewHolder> {
    private List<String> subCatList;
    private SubCatListener subCatListener;

    public SubCatAdapter(List<String> subCatList, SubCatListener subCatListener) {
        this.subCatList = subCatList;
        this.subCatListener = subCatListener;
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcat_item_layout, parent, false);

        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
        holder.subCatName.setText(subCatList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCatListener.onSubCatSelected(subCatList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCatList.size();
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {
        TextView subCatName;
        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            subCatName = itemView.findViewById(R.id.textView6);
        }
    }
}
