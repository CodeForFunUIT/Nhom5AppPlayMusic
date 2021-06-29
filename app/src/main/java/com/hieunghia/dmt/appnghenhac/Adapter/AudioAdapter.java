package com.hieunghia.dmt.appnghenhac.Adapter;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity;
import com.hieunghia.dmt.appnghenhac.Model.Audio;
import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    Context context;
    ArrayList<Audio> audios;
    ArrayList<BaiHat> baiHats;
    public AudioAdapter(Context context, ArrayList<Audio> audios) {
        this.context = context;
        this.audios = audios;
        baiHats = new ArrayList<>();
        if(audios.size() > 0){
            for(int i = 0; i < audios.size(); i++)
                baiHats.add(audios.get(i).converToBaiHat());
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_audio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(audios.get(position).getAudioTitle());
        holder.artist.setText(audios.get(position).getAudioArtist());

    }


    @Override
    public int getItemCount() {
        return audios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, artist;
        MaterialButton btnMenuAudio;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.titleaudio);
            artist = (TextView) itemView.findViewById(R.id.artistaudio);
            btnMenuAudio = itemView.findViewById(R.id.btnmenuaudio);
            imageView = itemView.findViewById(R.id.imageaudio);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (checkFile(new File(audios.get(getPosition()).getAudioPath()))){
                        Intent intent = new Intent(context, PlayNhacActivity.class);
                        intent.putExtra("PositionAudio",getPosition());
                        intent.putExtra("cakhucAudio", baiHats);
                        intent.putExtra("audioTrue",true);
                        context.startActivity(intent);
                    }else {
                        Snackbar.make(v, "file không tồn tại!", Snackbar.LENGTH_LONG)
                                .show();
                    }

                }
            });
            btnMenuAudio.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(context, v);
                    menu.getMenuInflater().inflate(R.menu.menu_audio, menu.getMenu());
                    menu.show();
                    menu.setOnMenuItemClickListener(item -> {
                        switch (item.getItemId()){
                            case R.id.deleteaudio:
                                deleteAudio(getPosition(),v);
                                break;
                        }
                        return true;
                    });
                }
            });
        }
        private void deleteAudio(int position, View view) {
            Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    Long.parseLong(audios.get(position).getAudioid())); // delete audio
            File file = new File(audios.get(position).getAudioPath());
            Log.d("BBB",audios.get(position).getAudioPath());
            if(checkFile(file)){
                boolean deleted = file.delete();
                if (deleted) {
                    context.getContentResolver().delete(uri,null,null);
                    audios.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, audios.size());
                    Snackbar.make(view, "Đã xóa!", Snackbar.LENGTH_LONG)
                            .show();
                }else {
                    Snackbar.make(view, "Không thể xóa!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }else {
                Snackbar.make(view, "file không tồn tại!", Snackbar.LENGTH_LONG)
                        .show();
            }

        }

        public boolean checkFile(File file){
            if (file.exists()){
                return true;
            }
            return false;
        }
    }
}
