package com.hieunghia.dmt.appnghenhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hieunghia.dmt.appnghenhac.Activity.DanhsachbaihatActivity;
import com.hieunghia.dmt.appnghenhac.Model.Album;
import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllAlbumAdapter extends RecyclerView.Adapter<AllAlbumAdapter.ViewHoldrer> {
    Context context;
    ArrayList<Album> albumArrayList;
    public AllAlbumAdapter(Context context, ArrayList<Album> albumArrayList)
    {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }
    @NonNull
    @Override
    public ViewHoldrer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_all_album,parent,false);

        return new ViewHoldrer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldrer holder, int position) {
        Album album = albumArrayList.get(position);
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.imgallalbum);
        holder.txttenallalbum.setText(album.getTenAlbum());
        holder.txtcasialbum1.setText(album.getTenCaSiAlbum());
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public class ViewHoldrer extends RecyclerView.ViewHolder{
        ImageView imgallalbum;
        TextView txttenallalbum,txtcasialbum1;
        public ViewHoldrer(@NonNull View itemView) {
            super(itemView);
            imgallalbum = itemView.findViewById(R.id.imageviewallalbum);
            txttenallalbum = itemView.findViewById(R.id.textviewtenallalbum);
            txtcasialbum1 = itemView.findViewById((R.id.textviewtencasialbum1));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("album",albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
