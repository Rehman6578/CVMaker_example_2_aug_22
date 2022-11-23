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
 * Created at   : 2:51 PM
 */
@RealmClass
public class Skills implements Parcelable, RealmModel {

    public static final Creator<Skills> CREATOR = new Creator<Skills>() {
        @Override
        public Skills createFromParcel(Parcel in) {
            return new Skills(in);
        }

        @Override
        public Skills[] newArray(int size) {
            return new Skills[size];
        }
    };
    private static final String SKILLS = "Skills";

    @PrimaryKey
    private long id;
    private String skills;
    private String heading;

    public Skills() {
        heading = SKILLS;
    }

    private Skills(Parcel in) {
        id = in.readLong();
        skills = in.readString();
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(skills);
    }
}
