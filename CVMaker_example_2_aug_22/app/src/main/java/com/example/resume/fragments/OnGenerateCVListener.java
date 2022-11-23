package com.example.resume.fragments;

import java.util.List;

/**
 * Project Name : CVMaker
 * Created by   : Ummer Siddique
 * Created on   : August 01, 2017
 * Created at   : 12:15 PM
 */

public interface OnGenerateCVListener {



    void onNextClicked(int formatNumber);

    void onGenerateCVClicked(List<Boolean> selectedFieldList);
}
