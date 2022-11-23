package com.example.resume.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Project Name : CVMaker
 * Created by   : Ummer Siddique
 * Created on   : July 25, 2017
 * Created at   : 11:44 AM
 */
@RealmClass
public class PersonalInformation implements Parcelable, RealmModel {
    public static final Creator<PersonalInformation> CREATOR = new Creator<PersonalInformation>() {
        @Override
        public PersonalInformation createFromParcel(Parcel in) {
            return new PersonalInformation(in);
        }

        @Override
        public PersonalInformation[] newArray(int size) {
            return new PersonalInformation[size];
        }
    };
    private static final String PERSONAL_INFORMATION = "Personal Information";

    @PrimaryKey
    private long id;
    private String heading;
    private String name;
    private String fName;
    private String address;
    private String phoneNumber;
    private String email;
    private String dateOfBirth;
    private String CNIC;
    private String Nationality;

    public PersonalInformation() {
        heading = PERSONAL_INFORMATION;
    }

    private PersonalInformation(Parcel in) {
        name = in.readString();
        fName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        dateOfBirth = in.readString();
        CNIC = in.readString();
        Nationality = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(fName);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(dateOfBirth);
        dest.writeString(CNIC);
        dest.writeString(Nationality);
    }

    @Override
    public String toString() {
        return "PersonalInformation{" +
                "id=" + id +
                ", heading='" + heading + '\'' +
                ", name='" + name + '\'' +
                ", fName='" + fName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", CNIC='" + CNIC + '\'' +
                ", Nationality='" + Nationality + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalInformation that = (PersonalInformation) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getfName() != null ? getfName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getDateOfBirth() != null ? getDateOfBirth().hashCode() : 0);
        result = 31 * result + (getCNIC() != null ? getCNIC().hashCode() : 0);
        result = 31 * result + (getNationality() != null ? getNationality().hashCode() : 0);
        return result;
    }
}
