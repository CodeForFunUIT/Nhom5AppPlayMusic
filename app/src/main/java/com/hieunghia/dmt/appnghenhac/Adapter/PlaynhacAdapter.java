package com.hieunghia.dmt.appnghenhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.R;

import java.util.ArrayList;

public class PlaynhacAdapter extends RecyclerView.Adapter<PlaynhacAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> mangbaihat;
    public static int  iposition = 0;
    public static boolean isChange = false;
    public PlaynhacAdapter(Context context, ArrayList<BaiHat> mangbaihat)
    {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_play_bai_hat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = mangbaihat.get(position);
        holder.txtcasi.setText(baiHat.getCaSi());
        holder.txtindex.setText(position + 1 +"");
        holder.txttenbaihat.setText(baiHat.getTenBaiHat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("changepos",position);

                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtindex, txttenbaihat, txtcasi;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            txtcasi = itemView.findViewById(R.id.textviewplaynhactencasi);
            txttenbaihat = itemView.findViewById(R.id.textviewplaynhactenbaihat);
            txtindex = itemView.findViewById(R.id.textviewplaynhacindex);
        }
    }
}
