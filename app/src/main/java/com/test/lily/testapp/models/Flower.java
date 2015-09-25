package com.test.lily.testapp.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lily on 30/07/2015.
 */
public class Flower implements Parcelable {

    public static final String PRODUCT_ID = "productId";
    public static final String CATEGORY = "category";
    public static final String NAME = "name";
    public static final String INSTRUCTIONS = "instructions";
    public static final String PRICE = "price";
    public static final String PHOTO = "photo";

    private int productId;
    private String category;
    private String name;
    private String instructions;
    private double price;
    private String photo;
    private Bitmap bitmap;

    public Flower() {

    }

    public Flower(Parcel in) {
        productId = in.readInt();
        category = in.readString();
        name = in.readString();
        instructions = in.readString();
        price = in.readDouble();
        photo = in.readString();
        bitmap = in.readParcelable(getClass().getClassLoader());
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String toString() {
        return this.getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(productId);
        dest.writeString(category);
        dest.writeString(name);
        dest.writeString(instructions);
        dest.writeDouble(price);
        dest.writeString(photo);
        dest.writeParcelable(bitmap, flags);
    }

    public static final Parcelable.Creator<Flower> CREATOR = new Parcelable.Creator<Flower>() {

        @Override
        public Flower createFromParcel(Parcel source) {
            return new Flower(source);
        }

        @Override
        public Flower[] newArray(int size) {
            return new Flower[size];
        }
    };
}
