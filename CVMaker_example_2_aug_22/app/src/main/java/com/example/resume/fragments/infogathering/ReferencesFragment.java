package com.example.resume.fragments.infogathering;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.resume.R;
import com.example.resume.activities.CommonMethods;
import com.example.resume.models.Reference;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateCVListener} interface
 * to handle interaction events.
 */
public class ReferencesFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private List<Reference> referenceList;

    private OnUpdateCVListener mListener;

    private View root;
    private EditText eTHeading;
    private EditText eTName;
    private EditText eTOrganization;
    private EditText eTContactNumber;
    private EditText eTCity;
    private FloatingActionButton addMore;

    private View.OnClickListener addMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addReference();
        }
    };

    public ReferencesFragment() {
        // Required empty public constructor
        referenceList = new ArrayList<>(
                Realm.getDefaultInstance().where(Reference.class).findAll()
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_references, container, false);
        init();
        showData();
        return root;
    }

    private void init() {
        eTHeading = (EditText) root.findViewById(R.id.text_view_title);
        eTName = (EditText) root.findViewById(R.id.edit_text_name);
        eTOrganization = (EditText) root.findViewById(R.id.edit_text_organization);
        eTContactNumber = (EditText) root.findViewById(R.id.edit_text_phone_number);
        eTCity = (EditText) root.findViewById(R.id.edit_text_city);
        addMore = (FloatingActionButton) root.findViewById(R.id.add_more);
        addMore.setOnClickListener(addMoreClickListener);
    }

    private void showData() {
        if (referenceList.size() <= 0)
            return;
        Reference reference = referenceList.get(0);
        eTHeading.setText(reference.getHeading());
        eTName.setText(reference.getName());
        eTOrganization.setText(reference.getOrganization());
        eTContactNumber.setText(reference.getContactNumber());
        eTCity.setText(reference.getCity());
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
        addReference();
        mListener = null;
    }

    private void addReference() {
        if (eTName.getText().toString().equals("")) {
            CommonMethods.showAlertDialog(getContext(), "Fill in the fields");
            return;
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Reference reference = new Reference();
        reference.setId(eTName.getText().toString().trim());
        reference.setHeading(eTHeading.getText().toString().trim());
        reference.setName(eTName.getText().toString().trim());
        reference.setOrganization(eTOrganization.getText().toString().trim());
        reference.setContactNumber(eTContactNumber.getText().toString().trim());
        reference.setCity(eTCity.getText().toString().trim());

        realm.copyToRealmOrUpdate(reference);
        realm.commitTransaction();

        clearFields();
    }

    private void clearFields() {
        eTName.getText().clear();
        eTOrganization.getText().clear();
        eTContactNumber.getText().clear();
        eTCity.getText().clear();
    }
}
