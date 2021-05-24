package com.yogdroidtech.mallfirebase.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yogdroidtech.mallfirebase.R;
import com.yogdroidtech.mallfirebase.SubCatListener;

import java.util.List;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.SubViewHolder> {
    private List<String> subCatList;
    private SubCatListener subCatListener;
    int checkedPosition = 0;

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
        if (checkedPosition == -1) {
            holder.conLa.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.subcat_disable));
            holder.subCatName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black));

        } else {
            if (checkedPosition == holder.getAdapterPosition()) {
                holder.conLa.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.subcat_back));
                holder.subCatName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));

            } else {
                holder.conLa.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.subcat_disable));
                holder.subCatName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black));

            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCatListener.onSubCatSelected(subCatList.get(position));
                holder.conLa.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.subcat_back));
                holder.subCatName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));

                if (checkedPosition != holder.getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = holder.getAdapterPosition();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCatList.size();
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {
        TextView subCatName;
        ConstraintLayout conLa;
        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            subCatName = itemView.findViewById(R.id.textView6);
            conLa = itemView.findViewById(R.id.conLa);
        }
    }
}
