package com.example.resume.activities;

import android.content.Context;

/**
 * Project Name : CVMaker
 * Created by   : Ummer Siddique
 * Created on   : August 07, 2017
 * Created at   : 11:01 AM
 */

public class Constants {

    public static final String EXTENSION = ".pdf";
    public static final String SEPARATOR = "/";
    public static final String FOLDER_NAME = "My CV";
    private static final String FOLDER_NAME2 = "profile_pic_cv";
//    public static final String FOLDER_LOCATION2 =
//            Environment.getExternalStorageDirectory() + SEPARATOR + FOLDER_NAME2;
//
//    public static final String FOLDER_LOCATION =
//            Environment.getExternalStorageDirectory() + SEPARATOR + FOLDER_NAME;

    public static final String FILE_NAME = "Settings";
    public static final String KEY_FILE_LOCATION = "File_Location";

    public static final int PICK_IMAGE = 200;


    public static String FOLDER_LOCATION(Context context){
       return context.getExternalFilesDir(null).getAbsolutePath()+SEPARATOR + FOLDER_NAME;
    }

    public static String FOLDER_LOCATION2(Context context){
        return context.getExternalFilesDir(null).getAbsolutePath()+SEPARATOR + FOLDER_NAME2;
    }
}
