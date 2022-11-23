package com.example.resume.fragments.infogathering;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.resume.R;
import com.example.resume.activities.CommonMethods;
import com.example.resume.models.Hobbies;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateCVListener} interface
 * to handle interaction events.
 */
public class HobbiesFragment extends Fragment {

    private Hobbies hobbies;

    private OnUpdateCVListener mListener;

    private View root;
    private EditText eTHobbies;
    private EditText eTHeading;

    public HobbiesFragment() {
        // Required empty public constructor
        hobbies = Realm.getDefaultInstance().where(Hobbies.class).findFirst();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_hobbies, container, false);
        init();
        showData();
        return root;
    }

    private void init() {
        eTHeading = (EditText) root.findViewById(R.id.text_view_title);
        eTHobbies = (EditText) root.findViewById(R.id.edit_text_hobbies);
    }

    private void showData() {
        if (hobbies == null)
            hobbies = new Hobbies();
        eTHeading.setText(hobbies.getHeading());
        eTHobbies.setText(hobbies.getHobbies());
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
        if (eTHobbies.getText().toString().equals("")) {
            CommonMethods.showAlertDialog(getContext(), "Fill in the fields");
            return;
        }
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                hobbies.setHeading(eTHeading.getText().toString().trim());
                hobbies.setHobbies(eTHobbies.getText().toString().trim());
                realm.copyToRealmOrUpdate(hobbies);
            }
        });
    }
}
