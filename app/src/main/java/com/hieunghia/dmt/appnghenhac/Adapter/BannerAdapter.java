package com.hieunghia.dmt.appnghenhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.hieunghia.dmt.appnghenhac.Activity.DanhsachbaihatActivity;
import com.hieunghia.dmt.appnghenhac.Activity.PlayNhacActivity;
import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.Model.QuangCao;
import com.hieunghia.dmt.appnghenhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    Context context;
    ArrayList<QuangCao> arrayListbanner;
    BaiHat baiHat;

    public BannerAdapter(Context context, ArrayList<QuangCao> arrayListbanner){
        this.context = context;
        this.arrayListbanner = arrayListbanner;
        baiHat = new BaiHat();
    }

    @Override
    public int getCount() { // mảng có bao nhiêu tấm hình thì vẽ bấy nhiêu
        return arrayListbanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_banner, null);

        ImageView imgbackgroundbanner = view.findViewById(R.id.imageviewbackgroundbanner);
        ImageView imgsongbanner = view.findViewById(R.id.imageviewbanner);
        TextView txttitlesongbanner = view.findViewById(R.id.textviewtitlebanner);
        TextView txtnoidung = view.findViewById(R.id.textviewnoidung);

        Picasso.with(context).load(arrayListbanner.get(position).getHinhAnh()).into(imgbackgroundbanner); // gắn hình ảnh vào thư viện picasso (phải add vào)
        Picasso.with(context).load(arrayListbanner.get(position).getHinhAnh()).into(imgsongbanner);
        txttitlesongbanner.setText(arrayListbanner.get(position).getTenBaiHat());
        txtnoidung.setText(arrayListbanner.get(position).getNoiDung());

        baiHat.setLinkBaiHat(arrayListbanner.get(position).getLinkBaiHat());
        baiHat.setCaSi(arrayListbanner.get(position).getTenCaSi());
        baiHat.setHinhBaiHat(arrayListbanner.get(position).getHinhBaiHat());
        baiHat.setTenBaiHat(arrayListbanner.get(position).getTenBaiHat());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayNhacActivity.class);
                intent.putExtra("cakhuc",baiHat);
                context.startActivity(intent); // ;lệnh chuyển màn hình
            }
        });
        container.addView(view);
        return view;
    }

    // chuyển đến banner cuối cùng mà không finish sẽ bị lỗi

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
