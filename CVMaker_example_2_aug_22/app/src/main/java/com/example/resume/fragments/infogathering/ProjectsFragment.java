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
import com.example.resume.models.Project;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateCVListener} interface
 * to handle interaction events.
 */
public class ProjectsFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private List<Project> projectList;

    private OnUpdateCVListener mListener;

    private View root;
    private EditText eTHeading;
    private EditText eTProjectName;
    private EditText eTProjectDescription;
    private FloatingActionButton addMore;

    private View.OnClickListener addMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addProject();
        }
    };

    public ProjectsFragment() {
        // Required empty public constructor
        projectList = new ArrayList<>(
                Realm.getDefaultInstance().where(Project.class).findAll()
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
        root = inflater.inflate(R.layout.fragment_projects, container, false);
        init();
        showData();
        return root;
    }

    private void init() {
        eTHeading = (EditText) root.findViewById(R.id.text_view_title);
        eTProjectName = (EditText) root.findViewById(R.id.edit_text_project);
        eTProjectDescription = (EditText) root.findViewById(R.id.edit_text_project_des);
        addMore = (FloatingActionButton) root.findViewById(R.id.add_more);
        addMore.setOnClickListener(addMoreClickListener);
    }

    private void showData() {
        if (projectList.size() <= 0)
            return;
        Project project = projectList.get(0);
        eTHeading.setText(project.getHeading());
        eTProjectName.setText(project.getName());
        eTProjectDescription.setText(project.getDescription());

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
        addProject();
        mListener = null;
    }

    private void addProject() {
        if (eTProjectName.getText().toString().equals("")) {
            CommonMethods.showAlertDialog(getContext(), "Fill in the fields");
            return;
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Project project = new Project();
        project.setId(eTProjectName.getText().toString().trim());
        project.setHeading(eTHeading.getText().toString().trim());
        project.setName(eTProjectName.getText().toString().trim());
        project.setDescription(eTProjectDescription.getText().toString().trim());
        projectList.add(project);

        realm.copyToRealmOrUpdate(project);
        realm.commitTransaction();

        clearFields();
    }

    private void clearFields() {
        eTProjectName.getText().clear();
        eTProjectDescription.getText().clear();
    }
}
