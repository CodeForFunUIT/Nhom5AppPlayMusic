package com.hieunghia.dmt.appnghenhac.Service;

import android.provider.MediaStore;

import com.hieunghia.dmt.appnghenhac.Model.Album;
import com.hieunghia.dmt.appnghenhac.Model.BaiHat;
import com.hieunghia.dmt.appnghenhac.Model.ChuDe;
import com.hieunghia.dmt.appnghenhac.Model.PlayList;
import com.hieunghia.dmt.appnghenhac.Model.QuangCao;
import com.hieunghia.dmt.appnghenhac.Model.TheLoai;
import com.hieunghia.dmt.appnghenhac.Model.TheLoaiHomNay;
import com.hieunghia.dmt.appnghenhac.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @GET("songbanner.php")
    Call<List<QuangCao>> GetDataBanner();

    @GET("playlistforcurent.php")
    Call<List<PlayList>> GetPlayListCurrentDay();

    @GET("chudevatheloai.php")
    Call<TheLoaiHomNay> GetCateGoryMusic();

    @GET("album.php")
    Call<List<Album>> GetAlbumHot();

    @GET("baihatduocyeuthich.php")
    Call<List<BaiHat>> GetBaiHatHot();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")                                                                  // vi` ở phần service đặt tương tác vs service là POST nên client phải trùng vs phương thức.
    Call<List<BaiHat>> GetDanhSachBaiHatTheoQuangCao(@Field("idquangcao") String idquangcao);   // gửi dữ liệu lên và trả dữ liệu về.

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaitheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("danhsachcacplaylist.php")
    Call<List<PlayList>> GetDanhsachcacplaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaihattheotheloai(@Field("idtheloai") String idtheloai);

    @GET("tatcachude.php")
    Call<List<ChuDe>> GetAllChuDe();

    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> GetTheloaitheochude(@Field("idchude") String idchude);

    @GET("tatcaalbum.php")
    Call<List<Album>> GetAllAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaihattheoalbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich, @Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("InsertUserAccount.php")
    Call<String> InsertData(@Field("taikhoan") String taikhoan,
                            @Field("matkhau") String matkhau,
                            @Field("email") String email,
                            @Field("sodienthoai") String sodienthoai);
    @FormUrlEncoded
    @POST("userLogin.php")
    Call<List<User>> GetLoginData(@Field("taikhoan") String taikhoan,
                               @Field("matkhau") String matkhau);

    @FormUrlEncoded
    @POST("ForgetPassWord.php")
    Call<String> GetResetPassWord(@Field("email") String email);

    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<BaiHat>> GetSearchBaiHat(@Field("tukhoa") String tukhoa);


}
