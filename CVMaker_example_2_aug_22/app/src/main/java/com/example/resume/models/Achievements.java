package com.example.resume.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;



@RealmClass
public class Achievements implements Parcelable, RealmModel {

    public static final Creator<Achievements> CREATOR = new Creator<Achievements>() {
        @Override
        public Achievements createFromParcel(Parcel in) {
            return new Achievements(in);
        }

        @Override
        public Achievements[] newArray(int size) {
            return new Achievements[size];
        }
    };

    public static final String ACHIEVEMENTS = "Achievements";

    @PrimaryKey
    private long id;
    private String achievements;
    private String heading;

    public Achievements() {
        heading = ACHIEVEMENTS;
    }

    private Achievements(Parcel in) {
        id = in.readLong();
        achievements = in.readString();
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

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(achievements);
    }
}
