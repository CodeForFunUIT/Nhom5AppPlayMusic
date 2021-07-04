package com.hieunghia.dmt.appnghenhac.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.hieunghia.dmt.appnghenhac.Adapter.DanhsachtheloaitheochudeAdapter;
import com.hieunghia.dmt.appnghenhac.Model.ChuDe;
import com.hieunghia.dmt.appnghenhac.Model.TheLoai;
import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.Service.APIService;
import com.hieunghia.dmt.appnghenhac.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachtheloaitheochudeActivity extends AppCompatActivity {

    ChuDe chuDe;
    RecyclerView recyclerViewtheloaithechude;
    Toolbar toolbartheloaitheochude;
    DanhsachtheloaitheochudeAdapter danhsachtheloaitheochudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtheloaitheochude);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GetIntent();
        init();
        GetDaTa();
    }

    private void GetDaTa() {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.GetTheloaitheochude(chuDe.getIDChuDe());
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> mangtheloai = (ArrayList<TheLoai>) response.body();
                danhsachtheloaitheochudeAdapter = new DanhsachtheloaitheochudeAdapter(DanhsachtheloaitheochudeActivity.this,mangtheloai);
                recyclerViewtheloaithechude.setLayoutManager(new GridLayoutManager(DanhsachtheloaitheochudeActivity.this, 2));
                recyclerViewtheloaithechude.setAdapter(danhsachtheloaitheochudeAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }


    private void init() {
        recyclerViewtheloaithechude = findViewById(R.id.recyclerviewtheloaitheochude);
        toolbartheloaitheochude = findViewById(R.id.toobartheloaithechude);
        // calling the action bar
        setSupportActionBar(toolbartheloaitheochude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartheloaitheochude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude"))
        {
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
        }
    }
}