package com.example.resume.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import com.itextpdf.text.BaseColor;
import com.example.resume.R;
import com.example.resume.fragments.CVDataListFragment;
import com.example.resume.fragments.CVFormatsListFragment;
import com.example.resume.fragments.OnGenerateCVListener;
import com.example.resume.fragments.infocategories.InfoCategoryContent;
import com.example.resume.fragments.infocategories.InfoCategoryFragment;
import com.example.resume.fragments.infogathering.AchievementsFragment;
import com.example.resume.fragments.infogathering.AddImageFragment;
import com.example.resume.fragments.infogathering.EducationFragment;
import com.example.resume.fragments.infogathering.ExperienceFragment;
import com.example.resume.fragments.infogathering.HobbiesFragment;
import com.example.resume.fragments.infogathering.InfoFragmentsAdapter;
import com.example.resume.fragments.infogathering.LanguagesFragment;
import com.example.resume.fragments.infogathering.ObjectiveFragment;
import com.example.resume.fragments.infogathering.OnUpdateCVListener;
import com.example.resume.fragments.infogathering.PersonalInfoFragment;
import com.example.resume.fragments.infogathering.ProjectsFragment;
import com.example.resume.fragments.infogathering.ReferencesFragment;
import com.example.resume.fragments.infogathering.SkillsFragment;
import com.example.resume.pdfscanner.HelperPDF;
import com.example.resume.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

import static com.example.resume.pdfscanner.HelperPDF.CVGenerated;

public class CreateCVActivity extends AppCompatActivity implements
        InfoCategoryFragment.OnListFragmentInteractionListener,
        View.OnClickListener, OnUpdateCVListener, OnGenerateCVListener, AddImageFragment.OnCropImageListener {

    private static final String TAG = "CreateCVActivity";


//    InterstitialAd mInterstitialAd;


    HelperPDF helperPDF;
    private List<Fragment> fragmentList;
    private Fragment fragment;
    private String[] info_category_array;
    private Button next;
    private Button skip;
    private Button previous;
    private Realm realm;
    private int formatNumber;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case CVGenerated:
                    CommonMethods.openCV(CreateCVActivity.this, (File) msg.obj);
                    Toast.makeText(
                            CreateCVActivity.this, R.string.cv_generated, Toast.LENGTH_SHORT
                    ).show();
                    break;
            }
            return true;
        }
    });

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cv);

        Context context = this;


//        requestNewInterstitial();
        init();
        setOnClickListener();
        addStartFragment();
    }



    @Override
    public void onAttach(Fragment fragment) {

    }

    private void init() {

        initFragmentList();
        next = (Button) findViewById(R.id.next);
        skip = (Button) findViewById(R.id.skip);
        previous = (Button) findViewById(R.id.previous);
        LinearLayout abc = (LinearLayout) findViewById(R.id.control_buttons);
        info_category_array = getResources().getStringArray(R.array.info_category_array);
        realm = Realm.getDefaultInstance();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.fragment_pager);

        mPagerAdapter = new InfoFragmentsAdapter(getSupportFragmentManager(), this, fragmentList, abc);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new InfoFragmentsAdapter.DepthPageTransformer());
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new PersonalInfoFragment());
        fragmentList.add(new ObjectiveFragment());
        fragmentList.add(new EducationFragment());
        fragmentList.add(new SkillsFragment());
        fragmentList.add(new ExperienceFragment());
        fragmentList.add(new HobbiesFragment());
        fragmentList.add(new LanguagesFragment());
        fragmentList.add(new AchievementsFragment());
        fragmentList.add(new ProjectsFragment());
        fragmentList.add(new ReferencesFragment());
        fragmentList.add(new AddImageFragment());
        fragmentList.add(new CVFormatsListFragment());
        fragmentList.add(new CVDataListFragment());
    }

    private void setOnClickListener() {
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    private void addStartFragment() {
        if (fragment == null)
            fragment = new InfoCategoryFragment();
        getSupportFragmentManager().beginTransaction().add(
                R.id.create_cv_fragment, fragment
        ).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            popBackStack();
        }
    }

    @Override
    public void onSelectFormatClick() {
        addFormatsFragment();
    }

    @Override
    public void onDetach(Fragment fragment) {

    }

    private void popBackStack() {
        getSupportFragmentManager().popBackStackImmediate();
    }

    public void addControlButtons() {
        findViewById(R.id.control_buttons).setVisibility(View.VISIBLE);
    }

    public void removeControlButtons() {
        findViewById(R.id.control_buttons).setVisibility(View.GONE);
    }

    @Override
    public void onListItemClick(InfoCategoryContent.InfoCategoryItem item) {
        try {
            mPager.setCurrentItem(Integer.parseInt(item.getId()));
//            if (item.toString().equals(info_category_array[10])) {
//                startActivity(new Intent(this, CropImageActivity.class));
//            } else {
//                getSupportFragmentManager().beginTransaction().remove(getCurrentFragment()).commit();
//            }
        } catch (Exception ex) {
            Logger.i(TAG, "index out of bound " + ex);
        }
    }

    private void addFormatsFragment() {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.create_cv_fragment, new CVFormatsListFragment()
        ).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (fragmentList.size() + 1 == mPager.getCurrentItem()) {
                    addFormatsFragment();
                }
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                break;
            case R.id.skip:
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                break;
            case R.id.previous:
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                break;
        }
    }

    @Override
    public void onNextClicked(int formatNumber) {
        this.formatNumber = formatNumber;
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @Override
    public void onGenerateCVClicked(List<Boolean> selectedFieldList) {

        int total = 0;
        for (boolean selectedField : selectedFieldList) {
            if (selectedField)
                total++;
        }
        if (total < 4) {
            Toast.makeText(this, "Enter information first", Toast.LENGTH_SHORT).show();
            return;
        }

        helperPDF = new HelperPDF(this, this.formatNumber, selectedFieldList, handler);

        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(getResources().getColor(R.color.colorPrimary))
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        // edited to support big numbers bigger than 0x80000000
                        int color = (int) Long.parseLong(Integer.toHexString(selectedColor), 16);
                        final int r = (color >> 16) & 0xFF;
                        final int g = (color >> 8) & 0xFF;
                        final int b = (color) & 0xFF;


//                        if (mInterstitialAd.isLoaded()) {
//                            mInterstitialAd.show();
//                            mInterstitialAd.setAdListener(new AdListener() {
//                                @Override
//                                public void onAdClosed() {
//                                    super.onAdClosed();
//                                    createPdf(new BaseColor(r, g, b));
//                                }
//                            });
//
//                        } else {
//                            createPdf(new BaseColor(r, g, b));
//                        }


                    }
                })
                .build()
                .show();

    }


    @Override
    public void onCropImage() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    private void createPdf(BaseColor color) {
        helperPDF.setColor(color);
        helperPDF.start();
        finish();
    }
}
