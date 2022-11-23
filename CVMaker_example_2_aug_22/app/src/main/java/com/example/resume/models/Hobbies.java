package com.example.resume.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Project Name : CVMaker
 * Created by   : Ummer Siddique
 * Created on   : July 28, 2017
 * Created at   : 3:01 PM
 */

@RealmClass
public class Hobbies implements Parcelable, RealmModel {

    public static final Creator<Hobbies> CREATOR = new Creator<Hobbies>() {
        @Override
        public Hobbies createFromParcel(Parcel in) {
            return new Hobbies(in);
        }

        @Override
        public Hobbies[] newArray(int size) {
            return new Hobbies[size];
        }
    };

    private static final String HOBBIES = "Hobbies";

    @PrimaryKey
    private long id;
    private String hobbies;
    private String heading;

    private Hobbies(Parcel in) {
        id = in.readLong();
        hobbies = in.readString();
    }

    public Hobbies() {
        heading = HOBBIES;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(hobbies);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
}
