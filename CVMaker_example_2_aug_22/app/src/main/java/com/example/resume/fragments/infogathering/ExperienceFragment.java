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
import com.example.resume.models.Experience;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateCVListener} interface
 * to handle interaction events.
 */
public class ExperienceFragment extends Fragment {

    private static final String TAG = "ExperienceFragment";

    // TODO: Rename and change types of parameters
    private List<Experience> experienceList;
    private OnUpdateCVListener mListener;
    private View root;
    private EditText eTCompanyName;
    private EditText eTJobTitle;
    private EditText eTJobDescription;
    private EditText eTCity;
    private EditText eTCountry;
    private EditText eTStartDate;
    private EditText eTEndDate;
    private EditText eTHeading;
    private FloatingActionButton addMore;

    private View.OnClickListener addMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addExperience();
        }
    };

    public ExperienceFragment() {
        // Required empty public constructor
        experienceList = new ArrayList<>(
                Realm.getDefaultInstance().where(Experience.class).findAll()
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
        root = inflater.inflate(R.layout.fragment_experience, container, false);
        init();
        showData();
        return root;
    }

    private void init() {
        eTHeading = (EditText) root.findViewById(R.id.text_view_title);
        eTCompanyName = (EditText) root.findViewById(R.id.edit_text_company_name);
        eTJobTitle = (EditText) root.findViewById(R.id.edit_text_job_title);
        eTJobDescription = (EditText) root.findViewById(R.id.edit_text_job_des);
        eTCity = (EditText) root.findViewById(R.id.edit_text_city);
        eTCountry = (EditText) root.findViewById(R.id.edit_text_country);
        eTStartDate = (EditText) root.findViewById(R.id.edit_text_start_date);
        eTEndDate = (EditText) root.findViewById(R.id.edit_text_end_date);
        addMore = (FloatingActionButton) root.findViewById(R.id.add_more);
        addMore.setOnClickListener(addMoreClickListener);
    }

    private void showData() {
        if (experienceList.size() <= 0)
            return;
        Experience experience = experienceList.get(0);
        eTHeading.setText(experience.getHeading());
        eTCompanyName.setText(experience.getCompanyName());
        eTJobTitle.setText(experience.getJobTitle());
        eTJobDescription.setText(experience.getJobDescription());
        eTCity.setText(experience.getCity());
        eTCountry.setText(experience.getCountry());
        eTStartDate.setText(experience.getStartDate());
        eTEndDate.setText(experience.getEndDate());
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
        addExperience();
        mListener = null;
    }

    private void addExperience() {
        if (eTCompanyName.getText().toString().equals("")) {
            CommonMethods.showAlertDialog(getContext(), "Fill in the fields");
            return;
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Experience experience = new Experience();
        experience.setId(eTCompanyName.getText().toString().trim());
        experience.setHeading(eTHeading.getText().toString().trim());
        experience.setCompanyName(eTCompanyName.getText().toString().trim());
        experience.setJobTitle(eTJobTitle.getText().toString().trim());
        experience.setJobDescription(eTJobDescription.getText().toString().trim());
        experience.setCity(eTCity.getText().toString().trim());
        experience.setCountry(eTCountry.getText().toString().trim());
        experience.setStartDate(eTStartDate.getText().toString().trim());
        experience.setEndDate(eTEndDate.getText().toString().trim());

        realm.copyToRealmOrUpdate(experience);
        realm.commitTransaction();

        clearFields();
    }

    private void clearFields() {
        eTCompanyName.getText().clear();
        eTJobTitle.getText().clear();
        eTJobDescription.getText().clear();
        eTCity.getText().clear();
        eTCountry.getText().clear();
        eTStartDate.getText().clear();
        eTEndDate.getText().clear();
    }
}
