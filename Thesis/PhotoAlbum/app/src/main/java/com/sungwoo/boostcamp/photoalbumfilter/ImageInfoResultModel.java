package com.sungwoo.boostcamp.photoalbumfilter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by psw10 on 2017-10-20.
 */

public class ImageInfoResultModel implements Parcelable {

    private String data;
    private Boolean isSubjectOk;
    private Boolean isObjectOk;
    public Boolean isChecked;

    public ImageInfoResultModel(String data, Boolean isSubjectOk, Boolean isObjectOk) {
        this.data = data;
        this.isSubjectOk = isSubjectOk;
        this.isObjectOk = isObjectOk;
        isChecked = false;
    }

    public String getData() {
        return data;
    }

    public Boolean getIsSubjectOk() {
        return isSubjectOk;
    }

    public Boolean getIsObjectOk() {
        return isObjectOk;
    }

    protected ImageInfoResultModel(Parcel in) {
        data = in.readString();
        byte subjectBoolVal = in.readByte();
        isSubjectOk = subjectBoolVal == 0x02 ? null : subjectBoolVal != 0x00;
        byte objectBoolVal = in.readByte();
        isObjectOk = objectBoolVal == 0x02 ? null : objectBoolVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        if (isSubjectOk == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isSubjectOk ? 0x01 : 0x00));
        }
        if (isObjectOk == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isObjectOk ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ImageInfoResultModel> CREATOR = new Parcelable.Creator<ImageInfoResultModel>() {
        @Override
        public ImageInfoResultModel createFromParcel(Parcel in) {
            return new ImageInfoResultModel(in);
        }

        @Override
        public ImageInfoResultModel[] newArray(int size) {
            return new ImageInfoResultModel[size];
        }
    };
}
