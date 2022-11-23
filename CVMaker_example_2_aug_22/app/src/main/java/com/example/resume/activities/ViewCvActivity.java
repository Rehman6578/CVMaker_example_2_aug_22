package com.example.resume.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.example.resume.R;

import java.io.File;
import java.util.List;

import static com.example.resume.activities.CommonMethods.FILE_LOCATION;

public class ViewCvActivity extends Activity {

    private PDFView pdfView;
    private File file;
    Context context;
    SharedPreferences shareprefs;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_view_cv);
        context = this;

        // if(abc)

        shareprefs = context.getSharedPreferences("rate_uss", Context.MODE_PRIVATE);
        i = shareprefs.getInt("int_rate1", 2);
        if (i == 1) {
            i = i + 1;
        } else {
            Handler rate = new Handler();
            rate.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (shareprefs.getInt("int_rate1", 2) % 3 == 0) {
                        dialog_rate();
                        //  Log.e("file",shareprefs.getInt("int_rate1", 2)+"");
                        i = i + 1;
                        SharedPreferences.Editor editor = shareprefs.edit();
                        editor.putInt("int_rate1", i);
                        editor.apply();
                    } else {
                        i = i + 1;
                        SharedPreferences.Editor editor = shareprefs.edit();
                        editor.putInt("int_rate1", i);
                        editor.apply();
                        //  Log.e("file2",shareprefs.getInt("int_rate1", 2)+"");

                    }
                }
            }, 2000);
        }
        this.file = new File(getIntent().getStringExtra(FILE_LOCATION));
        if (!this.file.exists())
            this.finish();

        AdView adView = (AdView) findViewById(R.id.adView_pdf_viewer);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        this.init();
        this.loadCv();
    }

    private void init() {
        this.pdfView = (PDFView) this.findViewById(R.id.pdfView);
    }

    private void loadCv() {
        this.pdfView.fromFile(this.file).enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen
                //.onDraw(onDrawListener)
                // allows to draw something on all pages, separately for every page. Called only for visible pages
                //.onDrawAll(onDrawListener)
                //.onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
                //.onPageChange(onPageChangeListener)
                //.onPageScroll(onPageScrollListener)
                //.onError(onErrorListener)
                //.onRender(onRenderListener) // called after document is rendered for the first time
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                //.spacing(0)
                .load();
        this.pdfView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray
        ));
        //this.pdfView.page
    }


    public void dialog_rate() {
        if (!isFinishing()) {
            final Dialog mDialog = new Dialog(context, R.style.actionSheetTheme);
            mDialog.setContentView(R.layout.dialog_rating);
            mDialog.setCancelable(false);
            mDialog.show();

            mDialog.setOnKeyListener(new Dialog.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode,
                                     KeyEvent event) {
                    // TODO Auto-generated method stub
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mDialog.dismiss();
                    }
                    return true;
                }
            });


            TextView buttonOk = mDialog.findViewById(R.id.btnNegative);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // listener.onOnClicked();
                    // dismissDialog();
                    mDialog.dismiss();

                }
            });
            TextView buttonCancel = mDialog.findViewById(R.id.btnPositive);
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // listener.onCancelClicked();
                    //dismissDialog();
                    mDialog.dismiss();
                }
            });

            RatingBar rt_bar = mDialog.findViewById(R.id.ratingBar2);
            rt_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    //Log.d(TAG, "Rating changed : " + v);
                    if (v > 3) {

                        Toast.makeText(context, "Kindly appreciate and rate in Playstore", Toast.LENGTH_SHORT).show();

//                            sharedPref = context.getSharedPreferences("app_intro", Context.MODE_PRIVATE);
//                            sharedPref.edit().putInt("INT_APP_RATE", 1).apply();

                        //openMarket();


                        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                        }


                    } else {
                        Toast.makeText(context, "Send Us Your Complaint or suggestion", Toast.LENGTH_SHORT).show();
                        String mailto = "mailto:si.com" +
                                "?cc=" + "" +
                                "&subject=" + Uri.encode("CV Maker Feedback") +
                                "&body=" + Uri.encode(" Kindly write your suggestions here");

//                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//                    emailIntent.setData(Uri.parse(mailto));
//
//                    try {
//                        contextt.startActivity(emailIntent);
//                    } catch (ActivityNotFoundException e) {
//                        //TODO: Handle case where no email app is available
//
//                    }
//                    sharedPref = contextt.getSharedPreferences("app_intro", Context.MODE_PRIVATE);
//                    sharedPref.edit().putInt("INT_APP_RATE", 1).apply();


                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        // intent.setType("text/plain");
                        intent.setDataAndType(Uri.parse(mailto), "message/rfc822");
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
                                Intent emailIntentt = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                                        mailto));
//                                emailIntentt.putExtra(Intent.EXTRA_SUBJECT, "CV Maker FeedBack");
//                                emailIntentt.putExtra(Intent.EXTRA_TEXT, "Write your suggestions here");
                                startActivity(Intent.createChooser(emailIntentt, "Send email..."));


                            } catch (ActivityNotFoundException ee) {
                                //TODO: Handle case where no email app is available
                                Log.e("abd", "here3");


                            }
                        }


                    }
                }
            });
        }


    }


}
