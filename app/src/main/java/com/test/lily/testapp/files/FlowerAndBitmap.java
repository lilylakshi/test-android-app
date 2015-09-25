package com.test.lily.testapp.files;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.test.lily.testapp.models.Flower;

/**
 * Created by Lily on 02/08/2015.
 */
public class FlowerAndBitmap implements Parcelable {

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Flower flower;
    private Bitmap bitmap;

    public FlowerAndBitmap(Flower flower, Bitmap bitmap) {
        this.flower = flower;
        this.bitmap = bitmap;
    }

    public FlowerAndBitmap(Parcel in) {
        flower = in.readParcelable(getClass().getClassLoader());
        bitmap = in.readParcelable(getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(flower, flags);
        dest.writeParcelable(bitmap, flags);
    }

    public static final Parcelable.Creator<FlowerAndBitmap> CREATOR = new Creator<FlowerAndBitmap>() {
        @Override
        public FlowerAndBitmap createFromParcel(Parcel source) {
            return new FlowerAndBitmap(source);
        }

        @Override
        public FlowerAndBitmap[] newArray(int size) {
            return new FlowerAndBitmap[size];
        }
    };
}
