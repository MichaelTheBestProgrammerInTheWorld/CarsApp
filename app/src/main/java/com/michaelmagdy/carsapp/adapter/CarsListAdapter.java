package com.michaelmagdy.carsapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.michaelmagdy.carsapp.R;
import com.michaelmagdy.carsapp.webservice.DataItem;

import java.text.DecimalFormat;
import java.util.List;

public class CarsListAdapter extends RecyclerView.Adapter<CarsListAdapter.CarsListViewHolder> {


    private List<DataItem> dataList;
    private Context context;

    private View view;

    public CarsListAdapter(Context context, List<DataItem> dataList) {
        this.dataList = dataList;
        this.context = context;

    }

    @NonNull
    @Override
    public CarsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        final CarsListViewHolder holder = new CarsListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarsListViewHolder holder, int position) {


        holder.brand.setText(dataList.get(position).getBrand());
        holder.constructionY.setText(dataList.get(position).getConstructionYear());
        Glide.with(context).load(dataList.get(position).getImageUrl()).into(holder.image);
        if (dataList.get(position).isIsUsed()){
            holder.newOrUsed.setText("Used");
        } else {
            holder.newOrUsed.setText("New");
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class CarsListViewHolder extends RecyclerView.ViewHolder {

        TextView brand, constructionY, newOrUsed;
        ImageView image;

        public CarsListViewHolder(View itemView) {
            super(itemView);

            brand = itemView.findViewById(R.id.txt_brand);
            constructionY =  itemView.findViewById(R.id.txt_construction_year_);
            image =  itemView.findViewById(R.id.image);
            newOrUsed = itemView.findViewById(R.id.txt_new);

        }

    }
}
