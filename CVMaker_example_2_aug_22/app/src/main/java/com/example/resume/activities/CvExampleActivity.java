package com.example.resume.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.resume.R;

public class CvExampleActivity extends AppCompatActivity {


    Context context;
//    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_cv_example);
        context=CvExampleActivity.this;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void setListAdapter() {
//        listViewCvExamples.setAdapter(new ImageAdapter(this, R.layout.cv_example, cvExampleImages));
//        Utility.setListViewHeightBasedOnChildren(listViewCvExamples);
//    }

//    private class ImageAdapter extends ArrayAdapter<Integer> {
//
//        private List<Integer> cvExampleImages;
//        private LayoutInflater layoutInflater;
//
//        ImageAdapter(@NonNull Context context,
//                     @LayoutRes int resource,
//                     @NonNull List<Integer> cvExampleImages) {
//            super(context, resource, cvExampleImages);
//            this.cvExampleImages = cvExampleImages;
//            this.layoutInflater = (LayoutInflater) context.getSystemService(
//                    Context.LAYOUT_INFLATER_SERVICE
//            );
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            if (convertView == null) {
//                convertView = layoutInflater.inflate(R.layout.cv_example, parent, false);
//            }
//            ((ImageView) convertView.findViewById(R.id.image_view_cv_example))
//                    .setImageResource(cvExampleImages.get(position));
//            return convertView;
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}