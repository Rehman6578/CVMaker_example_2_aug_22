package com.example.resume.fragments.infocategories;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;
import com.example.resume.fragments.infocategories.InfoCategoryContent.InfoCategoryItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class InfoCategoryFragment extends Fragment {

    InfoCategoryContent infoCategoryContent;
    // TODO: Customize parameters
    private OnListFragmentInteractionListener onCategoryItemClickListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InfoCategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_category_list, container, false);

        infoCategoryContent = new InfoCategoryContent(getContext());

        // Set the adapter
        RecyclerView categoryList = (RecyclerView) view.findViewById(R.id.list);

        Context context = categoryList.getContext();
        categoryList.setLayoutManager(new LinearLayoutManager(context));
        categoryList.setAdapter(new InfoCategoryAdapter(
                infoCategoryContent.getInfoCategoryItems(), onCategoryItemClickListener
        ));

        view.findViewById(R.id.button_select_format).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryItemClickListener.onSelectFormatClick();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            onCategoryItemClickListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //onCategoryItemClickListener.onDetach(this);
        onCategoryItemClickListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListItemClick(InfoCategoryItem item);

        void onSelectFormatClick();

        void onDetach(Fragment fragment);
    }
}
