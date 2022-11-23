package com.example.resume.fragments.infocategories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;
import com.example.resume.fragments.infocategories.InfoCategoryContent.InfoCategoryItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link InfoCategoryItem} and makes a call to the
 * specified {@link InfoCategoryFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
class InfoCategoryAdapter extends RecyclerView.Adapter<InfoCategoryAdapter.ViewHolder> {

    private final List<InfoCategoryItem> mValues;
    private final InfoCategoryFragment.OnListFragmentInteractionListener mListener;

    InfoCategoryAdapter(List<InfoCategoryItem> items,
                        InfoCategoryFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_info_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getContent());
        holder.mCategoryIcon.setImageResource(mValues.get(position).categoryIconID);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListItemClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mIdView;
        final TextView mContentView;
        final ImageView mCategoryIcon;
        InfoCategoryItem mItem;

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mCategoryIcon = (ImageView) view.findViewById(R.id.category_icon);
        }
    }
}
