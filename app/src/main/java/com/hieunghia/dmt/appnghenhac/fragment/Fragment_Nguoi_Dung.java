package com.hieunghia.dmt.appnghenhac.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.hieunghia.dmt.appnghenhac.Adapter.ViewPagerAdapter;
import com.hieunghia.dmt.appnghenhac.R;

import java.util.ArrayList;

public class Fragment_Nguoi_Dung extends Fragment {
    View view;

    public ViewPager viewPager;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter;

    public Fragment_Nguoi_Dung() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nguoi_dung, container,false);
        anhxa();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    public void init(){
        ArrayList<Fragment> arrayList = new ArrayList<>();

        arrayList.add(new Fragment_Thu_Vien_bai_hat());
        arrayList.add(new Fragment_Album_Nguoi_Dung());
        ArrayList<String> title = new ArrayList<>();
        title.add("Trên Thiết Bị");
        title.add("Album");
        assert getFragmentManager() != null;
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.setList(arrayList);
        adapter.setTitle(title);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void anhxa(){
        viewPager =  view.findViewById(R.id.userViewPaper);
        tabLayout =  view.findViewById(R.id.userTabLayout);
    }
}