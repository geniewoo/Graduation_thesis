package com.sungwoo.boostcamp.photoalbumfilter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by psw10 on 2017-10-18.
 */

public class ImageInfoModel implements Parcelable {
    private String data;

    public ImageInfoModel(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    protected ImageInfoModel(Parcel in) {
        data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ImageInfoModel> CREATOR = new Parcelable.Creator<ImageInfoModel>() {
        @Override
        public ImageInfoModel createFromParcel(Parcel in) {
            return new ImageInfoModel(in);
        }

        @Override
        public ImageInfoModel[] newArray(int size) {
            return new ImageInfoModel[size];
        }
    };
}