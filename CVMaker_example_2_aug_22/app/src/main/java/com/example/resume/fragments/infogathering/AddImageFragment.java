package com.example.resume.fragments.infogathering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.isseiaoki.simplecropview.util.Utils;
import com.example.resume.CVMaker;
import com.example.resume.R;
import com.example.resume.image_picker_lib.Adapter;
import com.example.resume.image_picker_lib.MediaLoader;
import com.example.resume.utils.ImageZoom;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import static android.graphics.Bitmap.createBitmap;
import static com.example.resume.activities.CommonMethods.transform;
import static com.example.resume.activities.Constants.FOLDER_LOCATION2;
import static com.example.resume.activities.Constants.KEY_FILE_LOCATION;
import static com.example.resume.activities.Constants.PICK_IMAGE;
import static com.example.resume.activities.Constants.SEPARATOR;


public class AddImageFragment extends Fragment implements View.OnClickListener
        , View.OnTouchListener {

    private static final String TAG = AddImageFragment.class.getName();
    private static  String FILE_LOCATION2;
    @Inject
    SharedPreferences sharedPreferences;
    private OnCropImageListener mListener;
    private RelativeLayout cropView;
    private ImageView imageView;

    private ExecutorService mExecutor;
    private View view;
    private Activity context;

//    InterstitialAd mInterstitialAd;



    //image picker
    private TextView mTvMessage;

    private ImageView image_view;

    private Adapter mAdapter;
    private ArrayList<AlbumFile> mAlbumFiles;
    private Uri obtainedUri;


    public AddImageFragment() {
        // Required empty public constructor
    }

    private int calcImageSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = context.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return Math.min(Math.max(metrics.widthPixels, metrics.heightPixels), 2048);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_image, container, false);
        Context context = getActivity();
        FILE_LOCATION2 = FOLDER_LOCATION2(context) + SEPARATOR + "profile.png";
        Album.initialize(AlbumConfig.newBuilder(getActivity())
                .setAlbumLoader(new MediaLoader())
                .build());

//       requestNewInterstitial();
        init();
        return view;
    }


//    private void requestNewInterstitial() {
//        mInterstitialAd = new InterstitialAd(getActivity());
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//(getString(R.string.test_interstitial));// for defing add id in strings
//        AdRequest adRequest = new AdRequest.Builder()
//                //.addTestDevice("71755D0EC2C44A36020F3AFEDFEA5851")
//                .build();
//
//        mInterstitialAd.loadAd(adRequest);
//    }



    private void init() {
        // assign singleton instances to fields
        ((CVMaker) context.getApplication()).getAppComponent().inject(this);

        mExecutor = Executors.newSingleThreadExecutor();
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setOnTouchListener(this);


        cropView = (RelativeLayout) view.findViewById(R.id.cropView);

        view.findViewById(R.id.button_save).setOnClickListener(this);
        view.findViewById(R.id.change_image).setOnClickListener(this);
        //CommonMethods.checkAppDir();
        File saveFile = new File(FILE_LOCATION2);
        if (saveFile.exists()) {
            mExecutor.submit(new LoadScaledImageTask(
                    context, Uri.fromFile(saveFile),
                    imageView, calcImageSize())
            );
            view.findViewById(R.id.change_image).setVisibility(View.VISIBLE);
        }
        //else
        //CommonMethods.openGallery(this, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    mExecutor.submit(new LoadScaledImageTask(
                            context, data.getData(),
                            imageView, calcImageSize())
                    );
                }
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCropImageListener) {
            mListener = (OnCropImageListener) context;
            this.context = (Activity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCropImageListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                saveScreenShot(getScreenShot(cropView));
                break;
            case R.id.change_image:



//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                    mInterstitialAd.setAdListener(new AdListener() {
//                        @Override
//                        public void onAdClosed() {
//                            super.onAdClosed();
//
//                            //  CommonMethods.openGallery(getActivity(), PICK_IMAGE);
//                            selectImage();
//                        }
//                    });
//                } else {
//                    selectImage();
//                    // CommonMethods.openGallery(this, PICK_IMAGE);
//                }


                break;
        }
    }


    private void selectImage() {
        Album.image(this)
                .singleChoice()
                .camera(true)
                .columnCount(2)
                .widget(
                        Widget.newDarkBuilder(getActivity())
                                .title("Tap On Image To Select")
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        if (mAlbumFiles.size() > 0) {
                            obtainedUri = Uri.fromFile(new File(result.get(0).getPath()));
                            // doSomethingWithUri();
//                            Toast.makeText(getActivity(), "result " + Uri.fromFile(new File(mAlbumFiles.get(0).getPath())),
//                                    Toast.LENGTH_LONG).show();
                            mExecutor.submit(new LoadScaledImageTask(
                                    context, obtainedUri,
                                    imageView, calcImageSize())
                            );
                        }
                        //  mAdapter.notifyDataSetChanged(mAlbumFiles);
//                        mTvMessage.setVisibility(result.size() > 0 ? View.VISIBLE : View.GONE);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }


    private void saveScreenShot(Bitmap screenShot) {
        File file = new File(FILE_LOCATION2);
        try {
            FileOutputStream out = new FileOutputStream(file);
            screenShot.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            saveFilePath(Uri.fromFile(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Image is saved", Toast.LENGTH_SHORT).show();
    }

    private Bitmap getScreenShot(RelativeLayout mainLayout) {
        mainLayout.setDrawingCacheEnabled(true);
        return transform(createBitmap(mainLayout.getDrawingCache()));
    }

    private void saveFilePath(Uri fileUri) {
        sharedPreferences.edit().putString(KEY_FILE_LOCATION, fileUri.toString()).apply();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return ImageZoom.onTouch((ImageView) v, event);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExecutor != null)
            mExecutor.shutdown();
        mListener = null;
    }

    public interface OnCropImageListener {
        void onCropImage();
    }

    private static class LoadScaledImageTask implements Runnable {
        Context context;
        Uri uri;
        ImageView imageView;
        int width;
        private Handler mHandler = new Handler(Looper.getMainLooper());

        LoadScaledImageTask(Context context, Uri uri, ImageView imageView, int width) {
            this.context = context;
            this.uri = uri;
            this.imageView = imageView;
            this.width = width;
        }

        @Override
        public void run() {
            final int exifRotation = Utils.getExifOrientation(context, uri);
            int maxSize = Utils.getMaxSize();
            final int requestSize = Math.min(width, maxSize);
            try {
                //final Bitmap sampledBitmap = Utils.decodeSampledBitmapFromUri(context, uri, requestSize);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageMatrix(Utils.getMatrixFromExifOrientation(exifRotation));
                        imageView.setImageBitmap(
                                Utils.decodeSampledBitmapFromUri(context, uri, requestSize)
                        );

                    }
                });
            } catch (OutOfMemoryError | Exception e) {
                e.printStackTrace();
            }
        }
    }
}
