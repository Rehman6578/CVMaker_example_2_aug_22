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
 * Created at   : 11:57 AM
 */
@RealmClass
public class Objective implements Parcelable, RealmModel {
    public static final Creator<Objective> CREATOR = new Creator<Objective>() {
        @Override
        public Objective createFromParcel(Parcel in) {
            return new Objective(in);
        }

        @Override
        public Objective[] newArray(int size) {
            return new Objective[size];
        }
    };
    private static final String OBJECTIVE = "Objective";
    @PrimaryKey
    private long id;
    private String objectiveStatement;
    private String heading;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Objective() {
            heading = OBJECTIVE;
    }

    private Objective(Parcel in) {
        id = in.readLong();
        objectiveStatement = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObjectiveStatement() {
        return objectiveStatement;
    }

    public void setObjectiveStatement(String objectiveStatement) {
        this.objectiveStatement = objectiveStatement;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(objectiveStatement);
    }
}
