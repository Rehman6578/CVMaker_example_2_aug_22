package com.example.resume.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.resume.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnGenerateCVListener} interface
 * to handle interaction events.
 */
public class CVFormatsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final List<Integer> formatImagesIds;

//    SharedPreferences sharedprefs_in_app_billing;
//    boolean bol_purchased_info;
//    BillingProcessor bp;

    private OnGenerateCVListener mListener;
    private View root;
    private ListView formatsListView;

    {
        formatImagesIds = new ArrayList<>();
        formatImagesIds.add(R.drawable.cv_1);
        formatImagesIds.add(R.drawable.cv_2);
        formatImagesIds.add(R.drawable.cv_3);
        formatImagesIds.add(R.drawable.cv_4);
        formatImagesIds.add(R.drawable.cv_5);
        formatImagesIds.add(R.drawable.cv_6);
        formatImagesIds.add(R.drawable.cv_7_first_one);
        formatImagesIds.add(R.drawable.cv_8);
        formatImagesIds.add(R.drawable.cv_7);
        formatImagesIds.add(R.drawable.cv_9);

    }

    public CVFormatsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_cv_formats_list, container, false);
//        sharedprefs_in_app_billing = getActivity().getSharedPreferences("userdetails", getActivity().MODE_PRIVATE);
//        bol_purchased_info = sharedprefs_in_app_billing.getBoolean("app_purchased", false);
//
//        bp = new BillingProcessor(getActivity(), null, this);

        init();
        setListeners();
        return root;
    }

    private void init() {
        formatsListView = (ListView) root.findViewById(R.id.list_view_cv_format);
        setAdapter();
    }


    private void setAdapter() {
        formatsListView.setAdapter(
                new ImageAdapter(getContext(), R.layout.cv_format, formatImagesIds)
        );
    }

    private void setListeners() {
        formatsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                    mListener.onNextClicked(position);



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

    private class ImageAdapter extends ArrayAdapter<Integer> {

        ImageAdapter(Context context, int textViewResourceId, List<Integer> stringArrayList) {
            super(context, textViewResourceId, stringArrayList);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        CVFormatsListFragment.this.getContext()
                ).inflate(
                        R.layout.cv_format, parent, false
                );
            }
            Holder holder = new Holder(convertView);
//            holder.imageViewFormat.setImageResource(formatImagesIds.get(position));

            Glide.with(getActivity())
                    .load(formatImagesIds.get(position))
                    .into(holder.imageViewFormat);

//            Toast.makeText(getContext(), "cv format list", Toast.LENGTH_SHORT).show();
            return convertView;
        }

        class Holder {
            View root;
            ImageView imageViewFormat;

            Holder(View root) {
                this.root = root;
                root.setTag(this);
                this.imageViewFormat = (ImageView) root.findViewById(R.id.image_view_format);
            }
        }
    }


//    @Override
//    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
//
//        SharedPreferences.Editor edit = sharedprefs_in_app_billing.edit();
//        edit.putBoolean("app_purchased", true);
//        edit.apply();
//        Toast.makeText(getActivity(), "purchased Done", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onPurchaseHistoryRestored() {
//
//    }
//
//    @Override
//    public void onBillingError(int errorCode, @Nullable Throwable error) {
//        Toast.makeText(getActivity(), "purchased Error", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onBillingInitialized() {
//
//    }
}
