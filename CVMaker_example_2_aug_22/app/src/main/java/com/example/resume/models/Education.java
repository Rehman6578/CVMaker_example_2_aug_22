package com.example.resume.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Project Name : CVMaker
 * Created by   : Ummer Siddique
 * Created on   : July 26, 2017
 * Created at   : 2:46 PM
 */

@RealmClass
public class Education implements Parcelable, RealmModel {

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };
    public static final String EDUCATION = "Education";

    @PrimaryKey
    private String id;
    private String heading;
    private String degreeTitle;
    private String collegeName;
    private String startingYear;
    private String completingYear;
    private String totalMarks;
    private String obtainedMarks;

    public Education() {
        heading = EDUCATION;
    }

    private Education(Parcel in) {
        degreeTitle = in.readString();
        collegeName = in.readString();
        startingYear = in.readString();
        completingYear = in.readString();
        totalMarks = in.readString();
        obtainedMarks = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDegreeTitle() {
        return degreeTitle;
    }

    public void setDegreeTitle(String degreeTitle) {
        this.degreeTitle = degreeTitle;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getStartingYear() {
        return startingYear;
    }

    public void setStartingYear(String startingYear) {
        this.startingYear = startingYear;
    }

    public String getCompletingYear() {
        return completingYear;
    }

    public void setCompletingYear(String completingYear) {
        this.completingYear = completingYear;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(String obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(degreeTitle);
        dest.writeString(collegeName);
        dest.writeString(startingYear);
        dest.writeString(completingYear);
        dest.writeString(totalMarks);
        dest.writeString(obtainedMarks);
    }

    @Override
    public String toString() {
        return "Education{" +
                "id='" + id + '\'' +
                ", heading='" + heading + '\'' +
                ", degreeTitle='" + degreeTitle + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", startingYear='" + startingYear + '\'' +
                ", completingYear='" + completingYear + '\'' +
                ", totalMarks='" + totalMarks + '\'' +
                ", obtainedMarks='" + obtainedMarks + '\'' +
                '}';
    }
}
