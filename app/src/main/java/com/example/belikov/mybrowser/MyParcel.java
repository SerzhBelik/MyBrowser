package com.example.belikov.mybrowser;

import android.os.Parcelable;
import java.util.Set;

class MyParcel implements Parcelable {
    private String[] bookmarksArr;

    public String[] getBookmarksArr() {
        return bookmarksArr;
    }

    protected MyParcel(android.os.Parcel in) {

        bookmarksArr = in.createStringArray();
    }

    public MyParcel (String[] bookmarksArr){
        this.bookmarksArr = bookmarksArr;
    }

    public static final Creator<MyParcel> CREATOR = new Creator<MyParcel>() {
        @Override
        public MyParcel createFromParcel(android.os.Parcel in) {
            return new MyParcel(in);
        }

        @Override
        public MyParcel[] newArray(int size) {
            return new MyParcel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeStringArray(bookmarksArr);
    }



}
