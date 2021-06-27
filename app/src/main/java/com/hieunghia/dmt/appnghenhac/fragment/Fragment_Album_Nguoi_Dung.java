package com.hieunghia.dmt.appnghenhac.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hieunghia.dmt.appnghenhac.R;

public class Fragment_Album_Nguoi_Dung extends Fragment {

    View view;
    public Fragment_Album_Nguoi_Dung() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album_nguoi_dung, container, false);
        return view;
    }
}