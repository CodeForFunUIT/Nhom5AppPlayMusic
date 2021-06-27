package com.hieunghia.dmt.appnghenhac.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hieunghia.dmt.appnghenhac.Adapter.BannerAdapter;
import com.hieunghia.dmt.appnghenhac.Model.QuangCao;
import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.Service.APIService;
import com.hieunghia.dmt.appnghenhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        AnhXa();
        GetData();
        return view;
    }

    private void AnhXa() {
        viewPager = view.findViewById(R.id.viewpaper);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }

    private void GetData() {
        DataService dataService = APIService.getService(); //

        Call<List<QuangCao>> callback = dataService.GetDataBanner();
        callback.enqueue(new Callback<List<QuangCao>>() { // sự kiện lắng nghe
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) { //lắng nghe có kết quả trả về
                ArrayList<QuangCao> banners = (ArrayList<QuangCao>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(), banners);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if (currentItem >= 10)
                        {
                            currentItem = 0;
                        }
                        viewPager.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable,5000 ); // sau 5s nó sẽ tự động lướt qua
                    }
                };
                handler.postDelayed(runnable, 5000);
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) { // thất bại

            }
        });
    }
}
