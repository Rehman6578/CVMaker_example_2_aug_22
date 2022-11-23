package com.example.resume.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.resume.CVMaker;
import com.example.resume.R;
import com.example.resume.models.Achievements;
import com.example.resume.models.Education;
import com.example.resume.models.Experience;
import com.example.resume.models.Hobbies;
import com.example.resume.models.Languages;
import com.example.resume.models.Objective;
import com.example.resume.models.PersonalInformation;
import com.example.resume.models.Project;
import com.example.resume.models.Reference;
import com.example.resume.models.Skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

import static com.example.resume.activities.Constants.KEY_FILE_LOCATION;


public class CVDataListFragment extends Fragment implements View.OnClickListener {

    private OnGenerateCVListener mListener;

    private View root;
    private ListView optionsListView;
    private Button buttonGenerateCV;

    private List<String> selectedFieldDataList;
    private List<Boolean> selectedFieldList;
    private List<Boolean> addedFieldList;
    CheckBox ch_1, ch_2, ch_3, ch_4, ch_5, ch_6, ch_7, ch_8, ch_9, ch_10,ch_11,ch_12;


    @Inject
    SharedPreferences sharedPreferences;

    public CVDataListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_cvdata_list_test, container, false);

        ((CVMaker) getActivity().getApplication()).getAppComponent().inject(this);

        // init();
        init_test();
        setDataForAdapter();
        //  setAdapter();
        setListeners();

        checkbox_set_check();
        check_box_text();
        init_check_listner();
        return root;
    }

    private void init_check_listner() {

        ch_1.setOnClickListener(this);
        ch_2.setOnClickListener(this);
        ch_3.setOnClickListener(this);
        ch_4.setOnClickListener(this);
        ch_5.setOnClickListener(this);
        ch_6.setOnClickListener(this);
        ch_7.setOnClickListener(this);
        ch_8.setOnClickListener(this);
        ch_9.setOnClickListener(this);
        ch_10.setOnClickListener(this);
        ch_11.setOnClickListener(this);
        ch_12.setOnClickListener(this);

    }

    private void check_box_text() {
        //  holder.checkBoxOption.setText(selectedFieldDataList.get(position));
        ch_1.setText(selectedFieldDataList.get(0));
        ch_2.setText(selectedFieldDataList.get(1));
        ch_3.setText(selectedFieldDataList.get(2));
        ch_4.setText(selectedFieldDataList.get(3));
        ch_5.setText(selectedFieldDataList.get(4));
        ch_6.setText(selectedFieldDataList.get(5));
        ch_7.setText(selectedFieldDataList.get(6));
        ch_8.setText(selectedFieldDataList.get(7));
        ch_9.setText(selectedFieldDataList.get(8));
        ch_10.setText(selectedFieldDataList.get(9));
        ch_11.setText(selectedFieldDataList.get(10));
        ch_12.setText(selectedFieldDataList.get(11));

    }

    private void init() {
        optionsListView = (ListView) root.findViewById(R.id.list_view_cv_option);
        buttonGenerateCV = (Button) root.findViewById(R.id.button_generate_cv);
    }


    private void init_test() {

        buttonGenerateCV = (Button) root.findViewById(R.id.button_generate_cv);
        ch_1 = (CheckBox) root.findViewById(R.id.ch_1);
        ch_2 = (CheckBox) root.findViewById(R.id.ch_2);
        ch_3 = (CheckBox) root.findViewById(R.id.ch_3);
        ch_4 = (CheckBox) root.findViewById(R.id.ch_4);
        ch_5 = (CheckBox) root.findViewById(R.id.ch_5);
        ch_6 = (CheckBox) root.findViewById(R.id.ch_6);
        ch_7 = (CheckBox) root.findViewById(R.id.ch_7);
        ch_8 = (CheckBox) root.findViewById(R.id.ch_8);
        ch_9 = (CheckBox) root.findViewById(R.id.ch_9);
        ch_10 = (CheckBox) root.findViewById(R.id.ch_10);
        ch_11 = (CheckBox) root.findViewById(R.id.ch_11);
        ch_12 = (CheckBox) root.findViewById(R.id.ch_12);
    }

    private void checkbox_set_check() {

        if (!selectedFieldList.get(0)) {
            ch_1.setEnabled(false);
        }
        if (!selectedFieldList.get(1)) {
            ch_2.setEnabled(false);
        }
        if (!selectedFieldList.get(2)) {
            ch_3.setEnabled(false);
        }
        if (!selectedFieldList.get(3)) {
            ch_4.setEnabled(false);
        }
        if (!selectedFieldList.get(4)) {
            ch_5.setEnabled(false);
        }
        if (!selectedFieldList.get(5)) {
            ch_6.setEnabled(false);
        }
        if (!selectedFieldList.get(6)) {
            ch_7.setEnabled(false);
        }
        if (!selectedFieldList.get(7)) {
            ch_8.setEnabled(false);
        }
        if (!selectedFieldList.get(8)) {
            ch_9.setEnabled(false);
        }
        if (!selectedFieldList.get(9)) {
            ch_10.setEnabled(false);
        }
        if (!selectedFieldList.get(10)) {
            ch_11.setEnabled(false);
        }

        ch_1.setChecked(selectedFieldList.get(0));
        ch_2.setChecked(selectedFieldList.get(1));
        ch_3.setChecked(selectedFieldList.get(2));
        ch_4.setChecked(selectedFieldList.get(3));
        ch_5.setChecked(selectedFieldList.get(4));
        ch_6.setChecked(selectedFieldList.get(5));
        ch_7.setChecked(selectedFieldList.get(6));
        ch_8.setChecked(selectedFieldList.get(7));
        ch_9.setChecked(selectedFieldList.get(8));
        ch_10.setChecked(selectedFieldList.get(9));
        ch_11.setChecked(selectedFieldList.get(10));

    }

    private void setDataForAdapter() {
        selectedFieldDataList = new ArrayList<>(
                Arrays.asList(getResources().getStringArray(R.array.info_category_array))
        );
        getSavedInfo();
        selectedFieldList = new ArrayList<>(addedFieldList);
//        for (int index = 0; index < selectedFieldDataList.size(); index++) {
//            selectedFieldList.add(addedFieldList.get(index));
//        }
    }

    protected final void getSavedInfo() {
        addedFieldList = new ArrayList<>(selectedFieldDataList.size());
        Realm realm = Realm.getDefaultInstance();
        PersonalInformation personalInfo = realm.where(PersonalInformation.class).findFirst();
        addedFieldList.add(personalInfo != null);
        Objective objective = realm.where(Objective.class).findFirst();
        addedFieldList.add(objective != null);
        List<Education> educationList = realm.where(Education.class).findAll();
        addedFieldList.add(educationList.size() > 0);
        Skills skills = realm.where(Skills.class).findFirst();
        addedFieldList.add(skills != null);
        List<Experience> experienceList = realm.where(Experience.class).findAll();
        addedFieldList.add(experienceList.size() > 0);
        Hobbies hobbies = realm.where(Hobbies.class).findFirst();
        addedFieldList.add(hobbies != null);
        Languages languages = realm.where(Languages.class).findFirst();
        addedFieldList.add(languages != null);
        Achievements achievements = realm.where(Achievements.class).findFirst();
        addedFieldList.add(achievements != null);
        List<Project> projectList = realm.where(Project.class).findAll();
        addedFieldList.add(projectList.size() > 0);
        List<Reference> referenceList = realm.where(Reference.class).findAll();
        addedFieldList.add(referenceList.size() > 0);
        addedFieldList.add(sharedPreferences.getString(KEY_FILE_LOCATION, null) != null);
    }

//    private void setAdapter() {
//        CVDataListAdapter cvDataListAdapter = new CVDataListAdapter(
//                getContext(), R.layout.cv_option, selectedFieldDataList
//        );
//        optionsListView.setAdapter(cvDataListAdapter);
//    }

    private void setListeners() {
        buttonGenerateCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onGenerateCVClicked(selectedFieldList);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGenerateCVListener) {
            mListener = (OnGenerateCVListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGenerateCVListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ch_1:
                selectedFieldList.set(0, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_2:
                selectedFieldList.set(1, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_3:
                selectedFieldList.set(2, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_4:
                selectedFieldList.set(3, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_5:
                selectedFieldList.set(4, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_6:
                selectedFieldList.set(5, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_7:
                selectedFieldList.set(6, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_8:
                selectedFieldList.set(7, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_9:
                selectedFieldList.set(8, ((CheckBox) v).isChecked());
                break;

            case R.id.ch_10:
                selectedFieldList.set(9, ((CheckBox) v).isChecked());
                break;
            case R.id.ch_11:
                selectedFieldList.set(10, ((CheckBox) v).isChecked());
                break;

        }

    }

    private class CVDataListAdapter extends ArrayAdapter<String> {

        private String TAG = "CVDataListAdapter";

        CVDataListAdapter(Context context, int textViewResourceId,
                          List<String> cvDataListAdapter) {
            super(context, textViewResourceId, cvDataListAdapter);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        CVDataListFragment.this.getContext()
                ).inflate(
                        R.layout.cv_option, parent, false
                );
            }
            final ViewHolder holder = new ViewHolder(convertView, position);
            holder.checkBoxOption.setText(selectedFieldDataList.get(position));
            return convertView;
        }

        private class ViewHolder {
            View root;
            CheckBox checkBoxOption;

            ViewHolder(View root, final int position) {
                this.root = root;
                root.setTag(this);

                checkBoxOption = (CheckBox) root.findViewById(R.id.check_box_option);
                checkBoxOption.setChecked(selectedFieldList.get(position));
                if (!selectedFieldList.get(position)) {
                    //  getSavedInfo();
                    Toast.makeText(getContext(), "check" + position, Toast.LENGTH_SHORT).show();
                    checkBoxOption.setEnabled(false);
                }
                checkBoxOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //getSavedInfo();
                        selectedFieldList.set(position, ((CheckBox) v).isChecked());
                        Toast.makeText(getContext(), "null" + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
