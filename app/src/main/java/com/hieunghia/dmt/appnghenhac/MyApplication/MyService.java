//package com.hieunghia.dmt.appnghenhac.MyApplication;
//
//import android.annotation.SuppressLint;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.media.MediaPlayer;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.StrictMode;
//import android.support.v4.media.session.MediaSessionCompat;
//import android.view.View;
//import android.widget.RemoteViews;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//import com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity;
//import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
//import com.hieunghia.dmt.appnghenhac.R;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Random;
//
//import static com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity.fragment_dia_nhac;
//
//import static com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity.isAudio;
//
//import static com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity.mangbaihat;
//
//import static com.hieunghia.dmt.appnghenhac.MyApplication.Notification.CHANNEL_ID;
//
//public class MyService extends Service {
//
//    public static MediaPlayer mediaPlayer;
//    public static boolean isPlaying = true;
//    public static String nameActionBar;
//    public static boolean isFromService = false;
//    public static int position = 0;
//
//    final public static int ACTION_PREVIOUS = 1;
//    final public static int ACTION_PLAY = 2;
//    final public static int ACTION_NEXT = 3;
//    final public static int ACTION_PAUSE = 4;
//    final public static int ACTION_RESUME = 5;
//
//    final public static int ACTION_CLEAR = 6;
//    final public static int ACTION_CHANGE_NAME_ACTIONBAR = 7;
//    final public static int ACTION_NOTIFICATION_IS_PLAYING = 7;
////    final public static int ACTION_START_PLAY = 7;
////    final public static int ACTION_PLAY_FALIED = 8;
//
//    public static boolean stop = false;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); // kiểm tra tín hiệu mạng
//        StrictMode.setThreadPolicy(policy);
//        mediaPlayer = new MediaPlayer();
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        playNhac();
//        controlNotification();
//
//        int actionMusic = intent.getIntExtra("action_music_service",0);
//        handleActionMusic(actionMusic);
//
//        return START_NOT_STICKY;
//    }
//
////    private void playNhac() {
////        if (isFirstPlaying){
////            isFirstPlaying = false;
////            if (mangbaihat.size() > 0)
////            {
////                new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
////                imgPlay.setImageResource(R.drawable.iconpause);
////            }
////        }
////
////    }
//
//    private void handleActionMusic(int action) {
//        switch (action){
//            case ACTION_PAUSE:
//                pauseMusic();
//                break;
//            case ACTION_RESUME:
//                resumeMusic();
//                break;
//            case ACTION_NEXT:
//                nextMusic();
//                sendActionToPlayNhacActivity(ACTION_CHANGE_NAME_ACTIONBAR);
//                break;
//            case ACTION_PREVIOUS:
//                previousMusic();
//                sendActionToPlayNhacActivity(ACTION_CHANGE_NAME_ACTIONBAR);
//                break;
//            case ACTION_CLEAR:
//                stopSelf();
//                break;
//        }
//    }
//
////    private void previousMusic() {
////        isPlaying = true;
////        if (mangbaihat.size() > 0){
////            if (mediaPlayer.isPlaying() || mediaPlayer != null)
////            {
////                mediaPlayer.stop();
////                mediaPlayer.release();
////                mediaPlayer = null;
////            }
////            if (position < (mangbaihat.size())){
////                imgPlay.setImageResource(R.drawable.iconpause);
////                position--;
////                if (position < 0){
////                    position = mangbaihat.size() - 1;
////                }
////                if (repeat){
////                    position += 1;
////                }
////                if (checkrandom) {
////                    Random random = new Random();
////                    int index = random.nextInt(mangbaihat.size());
////                    if (index == position){
////                        position = index - 1;
////                    }
////                    position = index;
////                }
////                new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
////                nameActionBar = mangbaihat.get(position).getTenBaiHat();
////                fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat(), isAudio);
////            }
////        }
////        controlNotification();
////        playNhac();
//////        imgPre.setClickable(false);
//////        imgNext.setClickable(false);
//////        Handler handler1 = new Handler();
//////        handler1.postDelayed(new Runnable() {
//////            @Override
//////            public void run() {
//////                imgPre.setClickable(true);
//////                imgNext.setClickable(true);
//////            }
//////        }, 5000);
////    }
//
//    private void nextMusic() {
//        isPlaying = true;
//        if (mangbaihat.size() > 0){
//            if (mediaPlayer.isPlaying() || mediaPlayer != null)
//            {
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                mediaPlayer = null;
//            }
//            if (position < (mangbaihat.size())){
//                position++;
//                if (repeat){
//                    if (position == 0){
//                        position = mangbaihat.size();
//                    }
//                    position -= 1;
//                }
//                if (checkrandom) {
//                    Random random = new Random();
//                    int index = random.nextInt(mangbaihat.size());
//                    if (index == position){
//                        position = index - 1;
//                    }
//                    position = index;
//                }
//                if (position > mangbaihat.size() - 1){
//                    position = 0;
//                }
//                new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
//                fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat(), isAudio);
//                nameActionBar = mangbaihat.get(position).getTenBaiHat();
//            }
//        }
//        controlNotification();
//        playNhac();
////        imgPre.setClickable(false);
////        imgNext.setClickable(false);
////        Handler handler1 = new Handler();
////        handler1.postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                imgPre.setClickable(true);
////                imgNext.setClickable(true);
////            }
////        }, 5000);
//
//    }
//
//    private void resumeMusic() {
//        if (!mediaPlayer.isPlaying()){
//            isPlaying = true;
//            mediaPlayer.start();
//            imgPlay.setImageResource(R.drawable.iconpause);
//            controlNotification();
//        }
//    }
//
//    private void pauseMusic() {
//        if(mediaPlayer.isPlaying()){
//            isPlaying = false;
//            mediaPlayer.pause();
//            imgPlay.setImageResource(R.drawable.iconplay);
//            controlNotification();
//        }
//    }
//
//    private void controlNotification() {
//        if (mangbaihat != null) {
//            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_music)
//                    .setSubText(mangbaihat.get(position).getTenBaiHat())
//                    .setContentTitle(mangbaihat.get(position).getTenBaiHat())
//                    .setContentText(mangbaihat.get(position).getCaSi())
//                    .setPriority(NotificationCompat.PRIORITY_MAX)
//                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                            .setShowActionsInCompactView(1, 3)
//                            .setMediaSession(mediaSessionCompat.getSessionToken()));
//            if (!isAudio)
//                notificationBuilder.setLargeIcon(converURLtoBitMap(mangbaihat.get(position).getHinhBaiHat()));
//            else
//                notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_music));
//
//            if (isPlaying)
//                notificationBuilder
//                        .addAction(R.drawable.ic_previous, "Previoust", getPendingIntent(this, ACTION_PREVIOUS))
//                        .addAction(R.drawable.ic_pauses, "Play", getPendingIntent(this, ACTION_PAUSE))
//                        .addAction(R.drawable.ic_btn_next, "Next", getPendingIntent(this, ACTION_NEXT))
//                        .addAction(R.drawable.ic_clear, "Cancel", getPendingIntent(this, ACTION_CLEAR));
//            else
//                notificationBuilder
//                        .addAction(R.drawable.ic_previous, "Previoust", getPendingIntent(this, ACTION_PREVIOUS))
//                        .addAction(R.drawable.ic_btn_play, "Play", getPendingIntent(this, ACTION_RESUME))
//                        .addAction(R.drawable.ic_btn_next, "Next", getPendingIntent(this, ACTION_NEXT))
//                        .addAction(R.drawable.ic_clear, "Cancel", getPendingIntent(this, ACTION_CLEAR));
//
//            android.app.Notification notification = notificationBuilder.build();
//            startForeground(1, notification);
//        }
//    }
//
//
//    private PendingIntent getPendingIntent(Context context, int action) {
//        Intent intent = new Intent(this, MyReceiver.class);
//        intent.putExtra("action_music", action);
//        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//    public Bitmap converURLtoBitMap(String string) {
//        try {
//            URL url = new URL(string);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            return null;
//        }
//
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mediaPlayer.seekTo(0);
//        mediaPlayer.stop();
//        mediaPlayer.prepareAsync();
//
//        imgPlay.setImageResource(R.drawable.iconplay);
//    }
//
//    private void sendActionToPlayNhacActivity(int action){
//
//        Intent intent = new Intent("SendActionToActivity");
//        Bundle bundle = new Bundle();
//        bundle.putInt("action_music",action);
//        bundle.putString("action_name",nameActionBar);
//        intent.putExtras(bundle);
//
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    class PlayMp3 extends AsyncTask<String,Void,String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            return strings[0];
//        }
//
//        @Override
//        protected void onPostExecute(String baihat) {
//            super.onPostExecute(baihat);
//            try {
//                mediaPlayer = new MediaPlayer();
////            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mediaPlayer.stop();
//                        mediaPlayer.reset();
//                    }
//                });
//                mediaPlayer.setDataSource(baihat);
//                mediaPlayer.prepare();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mediaPlayer.start();
//
//        }
//    }
//}
