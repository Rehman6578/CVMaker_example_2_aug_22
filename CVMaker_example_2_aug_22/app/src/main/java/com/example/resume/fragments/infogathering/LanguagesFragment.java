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
import com.example.resume.models.Languages;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateCVListener} interface
 * to handle interaction events.
 */
public class LanguagesFragment extends Fragment {

    private Languages languages;

    private OnUpdateCVListener mListener;

    private View root;
    private EditText eTLanguages;
    private EditText eTHeading;

    public LanguagesFragment() {
        // Required empty public constructor
        languages = Realm.getDefaultInstance().where(Languages.class).findFirst();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_languages, container, false);
        init();
        showData();
        return root;
    }

    private void init() {
        eTHeading = (EditText) root.findViewById(R.id.text_view_title);
        eTLanguages = (EditText) root.findViewById(R.id.edit_text_languages);
    }

    private void showData() {
        if(languages== null)
            languages = new Languages();
        eTHeading.setText(languages.getHeading());
        eTLanguages.setText(languages.getLanguages());
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
        if (eTLanguages.getText().toString().equals("")) {
            CommonMethods.showAlertDialog(getContext(), "Fill in the fields");
            return;
        }
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                languages.setHeading(eTHeading.getText().toString().trim());
                languages.setLanguages(eTLanguages.getText().toString().trim());
                realm.copyToRealmOrUpdate(languages);
            }
        });
    }
}
