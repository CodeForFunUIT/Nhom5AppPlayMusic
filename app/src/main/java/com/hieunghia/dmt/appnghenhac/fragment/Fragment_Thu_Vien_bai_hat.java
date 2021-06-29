package com.hieunghia.dmt.appnghenhac.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity;
import com.hieunghia.dmt.appnghenhac.Adapter.AudioAdapter;
import com.hieunghia.dmt.appnghenhac.Adapter.PlaynhacAdapter;
import com.hieunghia.dmt.appnghenhac.Model.Audio;
import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Thu_Vien_bai_hat extends Fragment {
    public static final int READ_EXTERNAL_STORAGE = 1;
    RecyclerView recyclerView;
    AudioAdapter adapter;
    ArrayList<Audio> audios;
    ArrayList<BaiHat> baiHats;
    public Fragment_Thu_Vien_bai_hat() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu_vien_bai_hat, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        audios = new ArrayList<>();
        baiHats = new ArrayList<>();
        checkPermission();
        getAudioFiles();

        return view;
    }
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,},1);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == READ_EXTERNAL_STORAGE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
    }


    public void getAudioFiles() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projecttion = {
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ALBUM


        };
        Cursor cursor = contentResolver.query(uri, projecttion, null, null, null);

        if(cursor!=null ){
            while(cursor.moveToNext()){
                if(!cursor.getString(0).equals(null)){
                    audios.add((new Audio(cursor.getString(0)

                                          ,cursor.getString(2)

                                          ,cursor.getString(3)

                                          ,cursor.getString(4)

                                          ,cursor.getString(5)

                                          ,uri.parse(cursor.getString(1)
                    ))));
                }
            }
            cursor.close();
        }
        removeFileUnknown(4);

        adapter = new AudioAdapter(getActivity(), audios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void removeFileUnknown(int number){
        for (int j = 0; j < number; j++){
            for (int i = 0; i < audios.size(); i++){
                File file = new File(audios.get(i).getAudioPath());
                if (!file.exists()){
                    audios.remove(i);
                }
            }
        }
    }
}


