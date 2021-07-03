package com.hieunghia.dmt.appnghenhac.Model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("TaiKhoan")
    @Expose
    private String taiKhoan;
    @SerializedName("MatKhau")
    @Expose
    private String matKhau;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("SoDienThoai")
    @Expose
    private String soDienThoai;

    @SerializedName("HinhAnh")
    @Expose
    private String hinhanh;

    protected User(Parcel in) {
        id = in.readString();
        taiKhoan = in.readString();
        matKhau = in.readString();
        email = in.readString();
        soDienThoai = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaiKhoan() { return taiKhoan; }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHinhanh() { return hinhanh; }

    public void setHinhanh(String hinhanh) { this.hinhanh = hinhanh; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taiKhoan);
        dest.writeString(matKhau);
        dest.writeString(email);
        dest.writeString(soDienThoai);
    }
}
