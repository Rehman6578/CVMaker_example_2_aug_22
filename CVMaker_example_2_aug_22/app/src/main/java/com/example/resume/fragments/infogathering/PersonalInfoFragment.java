package com.example.resume.fragments.infogathering;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.resume.R;
import com.example.resume.activities.CommonMethods;
import com.example.resume.models.PersonalInformation;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateCVListener} interface
 * to handle interaction events.
 */
public class PersonalInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private PersonalInformation personalInfo;

    private OnUpdateCVListener mListener;

    private View root;
    private EditText eTHeading;
    private EditText eTName;
    private EditText eTFName;
    private EditText eTContactNumber;
    private EditText eTEmailID;
    private EditText eTAddress;
    private EditText eTNicNumber;
    private EditText eTDateOfBirth;
    private EditText eTNationality;

    public PersonalInfoFragment() {
        // Required empty public constructor
        personalInfo = Realm.getDefaultInstance().where(PersonalInformation.class).findFirst();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personal_info, container, false);
//        View view = getActivity().getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            Toast.makeText(getActivity(), "hide keyboard", Toast.LENGTH_SHORT).show();
//       }


        init();
        showData();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //Toast.makeText(getActivity(), "hide keyboard", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        eTHeading = (EditText) root.findViewById(R.id.text_view_title);
        eTName = (EditText) root.findViewById(R.id.edit_text_name);
        eTFName = (EditText) root.findViewById(R.id.edit_text_fname);
        eTContactNumber = (EditText) root.findViewById(R.id.edit_text_contact_number);
        eTEmailID = (EditText) root.findViewById(R.id.edit_text_email);
        eTAddress = (EditText) root.findViewById(R.id.edit_text_address);
        eTNicNumber = (EditText) root.findViewById(R.id.edit_text_cnic);
        eTDateOfBirth = (EditText) root.findViewById(R.id.edit_text_dob);
        eTNationality = (EditText) root.findViewById(R.id.edit_text_nationality);
    }

    private void showData() {
        if (personalInfo == null) {
            personalInfo = new PersonalInformation();
        }
        eTHeading.setText(personalInfo.getHeading());
        eTName.setText(personalInfo.getName());
        eTFName.setText(personalInfo.getfName());
        eTContactNumber.setText(personalInfo.getPhoneNumber());
        eTEmailID.setText(personalInfo.getEmail());
        eTAddress.setText(personalInfo.getAddress());
        eTNicNumber.setText(personalInfo.getCNIC());
        eTDateOfBirth.setText(personalInfo.getDateOfBirth());
        eTNationality.setText(personalInfo.getNationality());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUpdateCVListener) {
            mListener = (OnUpdateCVListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUpdateCVListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        saveData();
        mListener = null;
    }


    private void saveData() {
        if (eTName.getText().toString().equals("")) {
            CommonMethods.showAlertDialog(getContext(), "Fill in the fields");
            return;
        }


        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                personalInfo.setHeading(eTHeading.getText().toString().trim());
                personalInfo.setName(eTName.getText().toString().trim());
                personalInfo.setfName(eTFName.getText().toString().trim());
                personalInfo.setAddress(eTAddress.getText().toString().trim());
                personalInfo.setPhoneNumber(eTContactNumber.getText().toString().trim());
                personalInfo.setEmail(eTEmailID.getText().toString().trim());
                personalInfo.setDateOfBirth(eTDateOfBirth.getText().toString().trim());
                personalInfo.setCNIC(eTNicNumber.getText().toString().trim());
                personalInfo.setNationality(eTNationality.getText().toString().trim());
                realm.copyToRealmOrUpdate(personalInfo);
            }
        });
    }
}
