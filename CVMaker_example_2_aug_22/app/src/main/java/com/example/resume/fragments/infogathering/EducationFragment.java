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
import com.example.resume.models.Education;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateCVListener} interface
 * to handle interaction events.
 */
public class EducationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private List<Education> educationList;

    private OnUpdateCVListener mListener;

    private View root;
    private EditText eTTitle;
    private EditText eTCollegeName;
    private EditText eTStartingYear;
    private EditText eTCompletionYear;
    private EditText eTTotalMarks;
    private EditText eTObtainedMarks;
    private EditText eTHeading;
    private FloatingActionButton addMore;

    private View.OnClickListener addMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addEducation();
        }
    };

    public EducationFragment() {
        // Required empty public
        educationList = new ArrayList<>(
                Realm.getDefaultInstance().where(Education.class).findAll()
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
        root = inflater.inflate(R.layout.fragment_education, container, false);
        init();
        showData();
        return root;
    }

    private void init() {
        eTHeading = (EditText) root.findViewById(R.id.text_view_title);
        eTTitle = (EditText) root.findViewById(R.id.edit_text_degree_title);
        eTCollegeName = (EditText) root.findViewById(R.id.edit_text_degree_institute);
        eTStartingYear = (EditText) root.findViewById(R.id.edit_text_degree_start_year);
        eTCompletionYear = (EditText) root.findViewById(R.id.edit_text_degree_completing_year);
        eTTotalMarks = (EditText) root.findViewById(R.id.edit_text_degree_total_marks);
        eTObtainedMarks = (EditText) root.findViewById(R.id.edit_text_degree_obt_marks);
        addMore = (FloatingActionButton) root.findViewById(R.id.add_more);
        addMore.setOnClickListener(addMoreClickListener);
    }

    private void showData() {
        if (educationList.size() <= 0)
            return;

        eTHeading.setText(educationList.get(0).getHeading());
        eTTitle.setText(educationList.get(0).getDegreeTitle());
        eTCollegeName.setText(educationList.get(0).getCollegeName());
        eTStartingYear.setText(educationList.get(0).getStartingYear());
        eTCompletionYear.setText(educationList.get(0).getCompletingYear());
        eTTotalMarks.setText(educationList.get(0).getTotalMarks());
        eTObtainedMarks.setText(educationList.get(0).getObtainedMarks());
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
        addEducation();
        mListener = null;
    }

    private void addEducation() {
        if (eTTitle.getText().toString().equals("")) {
            CommonMethods.showAlertDialog(getContext(), "Fill in the fields");
            return;
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Education education = new Education();
        education.setId(eTTitle.getText().toString().trim());
        education.setHeading(eTHeading.getText().toString().trim());
        education.setDegreeTitle(eTTitle.getText().toString().trim());
        education.setCollegeName(eTCollegeName.getText().toString().trim());
        education.setStartingYear(eTStartingYear.getText().toString().trim());
        education.setCompletingYear(eTCompletionYear.getText().toString().trim());
        education.setTotalMarks(eTTotalMarks.getText().toString().trim());
        education.setObtainedMarks(eTObtainedMarks.getText().toString().trim());




        realm.copyToRealmOrUpdate(education);
        realm.commitTransaction();

        clearFields();
    }

    private void clearFields() {
        eTTitle.getText().clear();
        eTCollegeName.getText().clear();
        eTStartingYear.getText().clear();
        eTCompletionYear.getText().clear();
        eTTotalMarks.getText().clear();
        eTObtainedMarks.getText().clear();
    }
}
