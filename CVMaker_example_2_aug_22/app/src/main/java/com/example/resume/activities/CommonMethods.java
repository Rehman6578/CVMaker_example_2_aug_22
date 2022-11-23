package com.example.resume.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.resume.utils.Logger;

import java.io.File;


public class CommonMethods {

    static final String FILE_LOCATION = "FILE_LOCATION";

    private static final String TAG = CommonMethods.class.getName();

    static void openCV(Context context, File file) {
        Intent intent = new Intent(context, ViewCvActivity.class);
        intent.putExtra(FILE_LOCATION, file.getPath());
        context.startActivity(intent);
    }

    public static void openGallery(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    public static void openGallery(Fragment fragment, int requestCode) {
        Intent intent = new Intent();
       // intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_PICK);
        //fragment.startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
        fragment.startActivityForResult(intent,  requestCode);
    }

    static void shareCV(Context context, File file) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        Uri apkURI = FileProvider.getUriForFile(
                context,
                context.getApplicationContext()
                        .getPackageName() + ".provider", file);
        shareIntent.setType("itemValue/pdf");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        } else shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_STREAM, apkURI);
        context.startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    static void setBackButton(AppCompatActivity context, Boolean showHomeAsUp) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().setDisplayShowHomeEnabled(showHomeAsUp);
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

//    public static void checkAppDir() {
//        File imageDir = null;
//        File extStorageDir = Environment.getExternalStorageDirectory();
//        if (extStorageDir.canWrite()) {
//            imageDir = new File(FOLDER_LOCATION);
//        }
//        if (imageDir != null) {
//            if (!imageDir.exists()) {
//                imageDir.mkdirs();
//            }
//        }
//    }

    public static Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    public static void showAlertDialog(Context context, String message) {
        CommonMethods.showAlertDialog(context, "", message);
    }

    public static void showAlertDialog(Context context, String title, String message) {
//        new AlertDialog.Builder(context)
//                .setMessage(message)
//                .setTitle(title)
//                .setNeutralButton("OK", null)
//                .create()
//                .show();
        Logger.i(TAG, "Show alert dialog");
    }
}
