package com.hieunghia.dmt.appnghenhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity;
import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.Service.APIService;
import com.hieunghia.dmt.appnghenhac.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DansachbaihatAdapter extends RecyclerView.Adapter<DansachbaihatAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> mangbaihat;
    public DansachbaihatAdapter(Context context, ArrayList<BaiHat> mangbaihat)
    {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_bai_hat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = mangbaihat.get(position);
        holder.txtcasi.setText(baiHat.getCaSi());
        holder.txttenbaihat.setText(baiHat.getTenBaiHat());
        holder.txtindex.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    // class này cung cấp những cái biến để tương tác và gắn dữ liệu lại
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtindex, txttenbaihat,txtcasi;
        ImageView imgluotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtcasi = itemView.findViewById(R.id.textviewtencasi);
            txtindex = itemView.findViewById(R.id.textviewdanhsachindex);
            txttenbaihat = itemView.findViewById(R.id.textviewdanhsachtenbaihat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc", mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

}
