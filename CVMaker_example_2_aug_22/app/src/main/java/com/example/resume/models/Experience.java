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
 * Created at   : 2:51 PM
 */

@RealmClass
public class Experience implements Parcelable, RealmModel {
    public static final Creator<Experience> CREATOR = new Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };
    private static final String EXPERIENCE = "Experience";
    @PrimaryKey
    private String id;
    private String heading;
    private String companyName;
    private String jobTitle;
    private String jobDescription;
    private String city;
    private String country;
    private String startDate;
    private String endDate;
    public Experience() {
        heading = EXPERIENCE;
    }
    private Experience(Parcel in) {
        companyName = in.readString();
        jobTitle = in.readString();
        jobDescription = in.readString();
        city = in.readString();
        country = in.readString();
        startDate = in.readString();
        endDate = in.readString();
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {

        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyName);
        dest.writeString(jobTitle);
        dest.writeString(jobDescription);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(startDate);
        dest.writeString(endDate);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", heading='" + heading + '\'' +
                ", companyName='" + companyName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
