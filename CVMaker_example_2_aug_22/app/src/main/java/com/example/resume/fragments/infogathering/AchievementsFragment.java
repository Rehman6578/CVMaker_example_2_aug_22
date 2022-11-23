package com.example.resume.fragments.infogathering;

import android.content.Context;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.resume.R;
import com.example.resume.activities.CommonMethods;
import com.example.resume.models.Achievements;

import java.util.GregorianCalendar;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUpdateCVListener} interface
 * to handle interaction events.
 */
public class AchievementsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Achievements achievements;

    private OnUpdateCVListener mListener;

    private View root;
    private EditText eTAchievements;
    private EditText eTHeading;

    public AchievementsFragment() {
        // Required empty public constructor
        achievements = Realm.getDefaultInstance().where(Achievements.class).findFirst();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Time time = new Time();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.getTime();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_achievements, container, false);
        init();
        showData();
        return root;
    }

    private void init() {
        eTHeading = (EditText) root.findViewById(R.id.text_view_title);
        eTAchievements = (EditText) root.findViewById(R.id.edit_text_achievements);
    }

    private void showData() {
        if (achievements == null)
            achievements = new Achievements();
        eTHeading.setText(achievements.getHeading());
        eTAchievements.setText(achievements.getAchievements());
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
        if (eTAchievements.getText().toString().equals("")) {
            CommonMethods.showAlertDialog(getContext(), "Fill in the fields");
            return;
        }
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                achievements.setHeading(eTHeading.getText().toString().trim());
                achievements.setAchievements(eTAchievements.getText().toString().trim());
                realm.copyToRealmOrUpdate(achievements);
            }
        });
    }
}
