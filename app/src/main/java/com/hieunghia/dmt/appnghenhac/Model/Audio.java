package com.hieunghia.dmt.appnghenhac.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.hieunghia.dmt.appnghenhac.R;

import java.util.Collections;

public class Audio implements Parcelable  {
    String audioTitle;
    String audioArtist;
    Uri audioUri;

    public Audio(String audioTitle, String audioArtist, Uri audioUri) {
        this.audioTitle = audioTitle;
        this.audioArtist = audioArtist;
        this.audioUri = audioUri;
    }

    protected Audio(Parcel in) {
        audioTitle = in.readString();
        audioArtist = in.readString();
        audioUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Audio> CREATOR = new Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel in) {
            return new Audio(in);
        }

        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }


    public String getAudioArtist() {
        return audioArtist;
    }

    public void setAudioArtist(String audioArtist) {
        this.audioArtist = audioArtist;
    }

    public Uri getAudioUri() {
        return audioUri;
    }

    public void setAudioUri(Uri audioUri) {
        this.audioUri = audioUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(audioTitle);
        dest.writeString(audioArtist);
        dest.writeParcelable(audioUri, flags);
    }

    public BaiHat converToBaiHat() {
        BaiHat baiHat = new BaiHat();

        baiHat.setCaSi(audioArtist);
        baiHat.setLinkBaiHat(audioUri.toString());
        baiHat.setTenBaiHat(audioTitle);
        baiHat.setHinhBaiHat("R.drawable.img_disknhac");

        return baiHat;
    }
}
