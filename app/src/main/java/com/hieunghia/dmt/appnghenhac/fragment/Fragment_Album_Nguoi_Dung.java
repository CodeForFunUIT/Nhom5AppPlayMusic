package com.hieunghia.dmt.appnghenhac.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hieunghia.dmt.appnghenhac.R;

public class Fragment_Album_Nguoi_Dung extends Fragment {

    View view;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    public Fragment_Album_Nguoi_Dung() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album_nguoi_dung, container, false);
        anhxa();
        return view;
    }

    private void anhxa() {
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerviewalbumuser);
    }
}