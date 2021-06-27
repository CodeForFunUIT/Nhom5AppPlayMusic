package com.hieunghia.dmt.appnghenhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hieunghia.dmt.appnghenhac.Adapter.PlaynhacAdapter;
import com.hieunghia.dmt.appnghenhac.Adapter.ViewPagerPlaylistnhac;
import com.hieunghia.dmt.appnghenhac.Model.Audio;
import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Dia_Nhac;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Play_Danh_Sach_Cac_Bai_Hat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



public class PlayNhacActivity extends AppCompatActivity {

    //TextView txtCasi, txttenbaihat, txtthoigianbatdau, txtthoigianketthuc;
    //ImageView imgbtnback;
    //ImageButton imgPlay, imgPre, imgNext, imgrepeat;
    //SeekBar seekbartime;

    Toolbar toolbarplaynhac;
    TextView txtTimeSong, txtTotaltimesong;
    //ImageView imgbtnback;
    ImageButton imgPlay, imgPre, imgNext, imgrepeat, imgrand;
    SeekBar seekbartime;
    ViewPager viewPagerplaynhac;
    RecyclerView recyclerView;
    public static Fragment_Dia_Nhac fragment_dia_nhac;
    public Fragment_Play_Danh_Sach_Cac_Bai_Hat fragment_play_danh_sach_cac_bai_hat;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    public static ViewPagerPlaylistnhac adapternhac;
    public static MediaPlayer mediaPlayer;
    public static boolean isAudio = false;

    boolean repeat = false;
    boolean checkrandom = false;
    boolean nextsong = false;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); // kiểm tra tín hiệu mạng
        StrictMode.setThreadPolicy(policy);
        GetDataInTent();
        init();
        evenClick();

    }

    private void evenClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapternhac.getItem(1) != null) {
                    if (mangbaihat.size() > 0){
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat(), isAudio);
                        // trường hợp khi run lên mà load bài hát quá lâu ko lấy đc bài hát thì hàm này sẽ đợi đến khi load đc
                        handler.removeCallbacks(this);
                    }else  {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay);
                }else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause);
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false) {
                    if (checkrandom == true){
                        checkrandom = false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgrand.setImageResource(R.drawable.iconsuffle);

                    }
                    imgrepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                }else {
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });
        imgrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkrandom == false) {
                    if (repeat == true){
                        repeat = false;
                        imgrand.setImageResource(R.drawable.iconsuffle);
                        imgrepeat.setImageResource(R.drawable.iconrepeat);

                    }
                    imgrand.setImageResource(R.drawable.iconshuffled);
                    checkrandom = true;
                }else {
                    imgrand.setImageResource(R.drawable.iconsuffle);
                    checkrandom = false;
                }
            }
        });
        seekbartime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0){
                    if (mediaPlayer.isPlaying() || mediaPlayer != null)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (mangbaihat.size())){
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true){
                            if (position == 0){
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > mangbaihat.size() - 1){
                            position = 0;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat(), isAudio);
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());

                    }
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0){
                    if (mediaPlayer.isPlaying() || mediaPlayer != null)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (mangbaihat.size())){
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position--;

                        if (position < 0){
                            position  = mangbaihat.size() - 1;
                        }
                        if (repeat == true){
                            position += 1;
                        }
                        if (checkrandom) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position){
                                position = index - 1;
                            }
                            position = index;
                        }

                        new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat(), isAudio);
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        UpdateSong();
                    }
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });

//        if (PlaynhacAdapter.ChangeSongTo(isChangeSong,positionListSong)){
//            position = positionListSong;
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
//            imgPlay.setImageResource(R.drawable.iconpause);
//            new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
//            fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat(), isAudio);
//            getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
//            UpdateSong();
//        }
    }

    private void GetDataInTent() {
        Intent intent = getIntent();
        mangbaihat.clear();
        if (intent != null)
        {
            if (intent.hasExtra("audioTrue")){
                isAudio = intent.getBooleanExtra("audioTrue",false);
            }
            if (intent.hasExtra("PositionAudio")){
                position = intent.getIntExtra("PositionAudio",0);
            }
            if (intent.hasExtra("cakhucAudio")){
                mangbaihat = intent.getParcelableArrayListExtra("cakhucAudio");
            }
            if (intent.hasExtra("cakhuc")){
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baiHat);
            }
            if (intent.hasExtra("cacbaihat"))   // điều kiện này sẽ nhận dữ liệu từ floatactionbutton và nhận tất cả bài hát trong đó.
            {
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat = baiHatArrayList;
            }
        }
    }



    private void init() {
//        recyclerView = findViewById(R.id.)
        toolbarplaynhac = findViewById(R.id.toobarplaynhac);
        txtTimeSong = findViewById(R.id.textviewtimesong);
        txtTotaltimesong = findViewById(R.id.textviewtotaltimesong);
        seekbartime = findViewById(R.id.seekbarsong);
        imgPlay = findViewById(R.id.imagebuttonplay);
        imgrepeat = findViewById(R.id.imagebuttonrepeat);
        imgNext = findViewById(R.id.imagebuttonnext);
        imgrand = findViewById(R.id.imagebuttonsuffle);
        imgPre = findViewById(R.id.imagebuttonpreview);
        viewPagerplaynhac = findViewById(R.id.viewpaperplaynhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangbaihat.clear();
                isAudio = false;
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danh_sach_cac_bai_hat = new Fragment_Play_Danh_Sach_Cac_Bai_Hat();
        adapternhac = new ViewPagerPlaylistnhac(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_play_danh_sach_cac_bai_hat);
        adapternhac.AddFragment(fragment_dia_nhac);
        viewPagerplaynhac.setAdapter(adapternhac);
        fragment_dia_nhac = (Fragment_Dia_Nhac) adapternhac.getItem(1);
        if (mangbaihat.size() > 0)
        {
            getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
            new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause);
        }
//        if (mangAudio.size() > 0){
//            getSupportActionBar().setTitle(mangAudio.get(position).getAudioTitle());
//            new PlayMp3().execute(mangAudio.get(position).getAudioUri().toString());
//            imgPlay.setImageResource(R.drawable.iconpause);
//        }
    }

    class PlayMp3 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateSong();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekbartime.setMax(mediaPlayer.getDuration());
    }
    private void UpdateSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null)
                {
                    seekbartime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            nextsong = true;
                            try {
                                Thread.sleep(1000);

                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (nextsong == true){
                    if (position < (mangbaihat.size())){
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position--;

                        if (position < 0){
                            position  = mangbaihat.size() - 1;
                        }
                        if (repeat == true){
                            position += 1;
                        }
                        if (checkrandom) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position){
                                position = index - 1;
                            }
                            position = index;
                        }

                        new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat(), isAudio);
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());

                    }
                    imgPre.setClickable(false);
                    imgNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgPre.setClickable(true);
                            imgNext.setClickable(true);
                        }
                    }, 5000);
                    nextsong = false;
                    handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}