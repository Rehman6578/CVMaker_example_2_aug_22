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
 * Created at   : 3:00 PM
 */
@RealmClass
public class Reference implements Parcelable, RealmModel {

    public static final Creator<Reference> CREATOR = new Creator<Reference>() {
        @Override
        public Reference createFromParcel(Parcel in) {
            return new Reference(in);
        }

        @Override
        public Reference[] newArray(int size) {
            return new Reference[size];
        }
    };
    private static final String REFERENCES = "References";

    @PrimaryKey
    private String id;

    private String heading;
    private String name;
    private String organization;
    private String contactNumber;
    private String city;

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

    public Reference() {
        heading = REFERENCES;

    }

    private Reference(Parcel in) {
        name = in.readString();
        organization = in.readString();
        contactNumber = in.readString();
        city = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(organization);
        dest.writeString(contactNumber);
        dest.writeString(city);
    }

    @Override
    public String toString() {
        return "Reference{" +
                "name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
