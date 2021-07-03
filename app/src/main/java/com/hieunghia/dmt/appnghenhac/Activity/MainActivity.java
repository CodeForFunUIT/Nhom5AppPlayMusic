package com.hieunghia.dmt.appnghenhac.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.tabs.TabLayout;
import com.hieunghia.dmt.appnghenhac.Adapter.MainViewPaperAdapter;
import com.hieunghia.dmt.appnghenhac.Model.User;
import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Ho_So;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Nguoi_Dung;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Thu_Vien_bai_hat;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Tim_Kiem;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Trang_Chu;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.hieunghia.dmt.appnghenhac.Activity.LoginActivity.isAvatarNull;
import static com.hieunghia.dmt.appnghenhac.Activity.LoginActivity.isLogByFaceBook;


public class MainActivity extends AppCompatActivity {

    private long backtime;
    TabLayout tabLayout;
    ViewPager viewPager;
    String getName,getPhone,getEmail,getPersonImage = null;
    ImageView imageView;
    Uri personPhoto;
    LoginActivity loginActivity;
    private static final int READ_EXtERNAL_STORAGE = 1;
    public static int progress;
    public static ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        anhxa();
        init();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null){
            getName = acct.getDisplayName();
            getEmail = acct.getEmail();
            personPhoto = acct.getPhotoUrl();
            getPhone = "Unknow";
        }
//        else if(true){
//            getName = loginActivity.getFaceBookName();
//            getEmail = loginActivity.getFaceBookEmail();
//            personPhoto = loginActivity.getImgProfile();
//            getPhone = "Unknow";
//        }
        else {
            getName = getIntent().getStringExtra("UserName");
            getEmail = getIntent().getStringExtra("UserEmail");
            getPhone = getIntent().getStringExtra("UserPhone");
            if (!isAvatarNull){
                getPersonImage = getIntent().getStringExtra("UserImage");
            }
        }
    }

    private void init() {
        MainViewPaperAdapter mainViewPaperAdapter = new MainViewPaperAdapter(getSupportFragmentManager());
        mainViewPaperAdapter.addFragment(new Fragment_Nguoi_Dung(),"Thiết Bị");
        mainViewPaperAdapter.addFragment(new Fragment_Trang_Chu(), "Trang chủ");
        mainViewPaperAdapter.addFragment(new Fragment_Tim_Kiem(), "Tìm Kiếm");
        mainViewPaperAdapter.addFragment(new Fragment_Ho_So(MainActivity.this), "Hồ Sơ");
        viewPager.setAdapter(mainViewPaperAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_phone);
        tabLayout.getTabAt(1).setIcon(R.drawable.icontrangchu2);
        tabLayout.getTabAt(2).setIcon(R.drawable.icsearch);
        tabLayout.getTabAt(3).setIcon(R.drawable.iconhoso2);

    }

    private void anhxa() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPaper);
    }

    @Override
    public void onBackPressed() {
        if (backtime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Nhấn Lần Nữa Để Thoát", Toast.LENGTH_SHORT).show();
        }
        backtime = System.currentTimeMillis();
    }
    public static void LoadingComplete() {
        progress++;
        if(progress >= 3)
            progressBar.setVisibility(View.INVISIBLE);
    }

    public String getGetName() {
        return getName;
    }
    public String getGetPhone() {
        return getPhone;
    }
    public String getGetEmail() {
        return getEmail;
    }
    public String getGetPersonImage() { return getPersonImage; }
    public Uri getPersonPhoto() { return personPhoto; }

}