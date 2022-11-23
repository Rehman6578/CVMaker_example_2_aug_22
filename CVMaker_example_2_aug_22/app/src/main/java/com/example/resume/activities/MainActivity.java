package com.example.resume.activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.resume.Permissions;
import com.example.resume.R;


import java.io.File;
import java.util.List;




public class MainActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    private static final String TAG = "MainActivity";

    private Boolean isBack = false;
    Context context;


    SharedPreferences sharepreferencess;


    FrameLayout frameLayout;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activities.MainActivity);
        super.onCreate(savedInstanceState);
        context = this;


            setContentView(R.layout.activity_main);
            context = this;



            Log.e("file", MainActivity.this.getExternalFilesDir(null).getAbsolutePath());
            Log.e("file", getExternalFilesDir(null).getAbsolutePath());

            File dir = new File(Constants.FOLDER_LOCATION(context));
            if (!dir.exists()) {
                dir.mkdir();
                // Toast.makeText(this, "done making directory", Toast.LENGTH_SHORT).show();
            }

            File dir2 = new File(Constants.FOLDER_LOCATION2(context));
            if (!dir2.exists()) {
                dir2.mkdir();
                // Toast.makeText(this, "done making directory", Toast.LENGTH_SHORT).show();
            }


            init();



//        }
    }






    @Override
    public void onBackPressed() {
        dialog_back();
    }

    public void dialog_back() {

        final Dialog dialog = new Dialog(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert);

        dialog.setContentView(R.layout.custom_dailog);

            frameLayout.setVisibility(View.GONE);



        Button dialogButton = (Button) dialog.findViewById(R.id.btn_yes);
        Button dialogButton_no = (Button) dialog.findViewById(R.id.btn_no);
        Button dialogButton_rate = (Button) dialog.findViewById(R.id.btn_ratee);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        dialogButton_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButton_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMarket();
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public void openMarket() {
        Uri uri = Uri.parse("market://details?id=" + MainActivity.this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            MainActivity.this.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
        }
    }

    public final static int REQUEST_CODE = 200;

    private void init() {
        findViewById(R.id.button_cv_tips).setOnClickListener(this);
        findViewById(R.id.button_open_share_cv).setOnClickListener(this);
        findViewById(R.id.button_create_cv).setOnClickListener(this);
        findViewById(R.id.button_cv_example).setOnClickListener(this);
        findViewById(R.id.button_more_apps).setOnClickListener(this);
        findViewById(R.id.button_rate_us).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cv_tips:
//


                    openGuideActivity();


                break;
            case R.id.button_open_share_cv:

                SharedPreferences shareprefs = context.getSharedPreferences("rate_us", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shareprefs.edit();
                editor.putInt("int_rate", 1);
                editor.apply();



                    if (Build.VERSION.SDK_INT >= 23) {
                        if (Permissions.hasStoragePermissions(context)) openCVListActivity();
                        else Permissions.getStoragePermissions(context);

                    } else {
                        openCVListActivity();
                    }

                break;
            case R.id.button_create_cv:

                SharedPreferences shareprefss = context.getSharedPreferences("rate_us", Context.MODE_PRIVATE);
                SharedPreferences.Editor editors = shareprefss.edit();
                editors.putInt("int_rate", 2);
                editors.apply();

//
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        if (Permissions.hasStoragePermissions(context)) openCreateCVActivity();
//                        else Permissions.getStoragePermissions(context);
//
//                    } else {
//                        openCreateCVActivity();
//                    }
//                }
//

                break;
            case R.id.button_cv_example:


//
//                    openCVExampleActivity();
//
                break;
            case R.id.button_more_apps:
                openMoreApps();
                break;
            case R.id.button_rate_us:
                openRateUs();
                break;


        }
    }

    private void openRateUs() {
        Uri uri = Uri.parse("market://details?id=" +);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" )));
        }
    }

    private void openMoreApps() {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("market://search?q="));
        startActivity(
                new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("market://search?q=pub:"))
        );
    }

    private void openCVExampleActivity() {
        startActivity(new Intent(this, CvExampleActivity.class));
    }

    private void openCVListActivity() {
        startActivity(new Intent(this, OpenShareActivity.class));
    }

    private void openCreateCVActivity() {
        startActivity(new Intent(this, CreateCVActivity.class));
    }

    private void openGuideActivity() {
        startActivity(new Intent(this, GuideActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.help: {

                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
                break;
            }

            case R.id.privacy: {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://-policy-wa.html"));
                startActivity(browserIntent);
                break;

            }

//                return true;
            case R.id.feedbck: {
                Toast.makeText(context, "Send Us Your Complaint or suggestion", Toast.LENGTH_SHORT).show();
                String mailto = "mailto:ssddcom" +
                        "?cc=" + "" +
                        "&subject=" + Uri.encode("CV Maker Feedback") +
                        "&body=" + Uri.encode(" Kindly write your suggestions here");
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                // intent.setType("text/plain");
                intent.setDataAndType(Uri.parse(mailto), "text/plain");
                final PackageManager pm = context.getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") ||
                            info.activityInfo.name.toLowerCase().contains("gmail"))
                        best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                try {
                    context.startActivity(intent);
                    Log.e("abd", "here1");
                } catch (ActivityNotFoundException e) {

                    try {
//                        Intent emailIntentt = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                                "mailto", "sidekeeperapps@gmail.com", null));
                        Intent emailIntentt = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                                mailto));
                        //emailIntentt.putExtra(Intent.EXTRA_SUBJECT, "CV Maker FeedBack");
                        // emailIntentt.putExtra(Intent.EXTRA_TEXT, "Write your suggestions here");
                        startActivity(Intent.createChooser(emailIntentt, "Send email..."));


                    } catch (ActivityNotFoundException ee) {
                        //TODO: Handle case where no email app is available
                        Log.e("abd", "here3");


                    }
                }


            }

//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }



    @Override
    protected void onResume() {
        super.onResume();
        // adds.createAd();
        // mInterstitalAd = adds.getAd();
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

    }


}
