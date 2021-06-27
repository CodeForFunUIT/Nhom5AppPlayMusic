package com.hieunghia.dmt.appnghenhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity;
import com.hieunghia.dmt.appnghenhac.Model.Audio;
import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.R;

import org.jetbrains.annotations.NotNull;

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

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            artist = (TextView) itemView.findViewById(R.id.artist);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("PositionAudio",getPosition());
                    intent.putExtra("cakhucAudio", baiHats);
                    intent.putExtra("audioTrue",true);
                    context.startActivity(intent);
                }
            });
        }
    }

}
