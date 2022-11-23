package com.example.resume.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.resume.activities.Constants.FOLDER_LOCATION;

import com.example.resume.R;


public class OpenShareActivity extends AppCompatActivity {

    private static final String TAG = "OpenShareActivity";

//    private final List<File> cvList;
//    {
//        try {
//            File f = new File(FOLDER_LOCATION);
//            cvList = new ArrayList<>(Arrays.asList(f.listFiles()));
//        }catch (NullPointerException e){
//
//        }
//    }

    private List<File> movieList;


    // private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    private List<File> cvList;



    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_share);
        context = OpenShareActivity.this;



        {
            try {
                File f = new File(FOLDER_LOCATION(context));
                cvList = new ArrayList<>(Arrays.asList(f.listFiles()));
            } catch (NullPointerException e) {

            }
        }

        {
            try {
                //  String pathh=OpenShareActivity.this.getExternalFilesDir(null).getAbsolutePath();
                File f = new File(FOLDER_LOCATION(context));
                movieList = new ArrayList<>(Arrays.asList(f.listFiles()));
            } catch (NullPointerException e) {
                Log.e("file2", e.getLocalizedMessage());
            }
        }

        init_recycleview();
        // init();
    }

    private void init_recycleview() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_open_share);
        TextView tv_no = findViewById(R.id.no_cv_recycler);

        try {
            if (movieList.isEmpty()) {
                tv_no.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                mAdapter = new MoviesAdapter(movieList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                tv_no.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException e) {

            tv_no.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
//    private void init() {
//        CommonMethods.setBackButton(this, true);
//        ListView cvListView = (ListView) findViewById(R.id.cv_list_view);
//        try {
//            ArrayAdapter<File> cvListAdapter = new CvListAdapter(this, R.layout.item_open_share, cvList);
//            cvListView.setAdapter(cvListAdapter);
//        }catch (NullPointerException e){
//
//        }
//    }

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

    private class CvListAdapter extends ArrayAdapter<File> {

        private Context context;
        private LayoutInflater layoutInflater;

        CvListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<File> objects) {
            super(context, resource, objects);
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE
            );
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_open_share, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.cv_name)).setText(
                    cvList.get(position).getName()
            );
            convertView.findViewById(R.id.button_open).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//
//                                CommonMethods.openCV(context, cvList.get(position));
//                            }

                        }
                    }
            );
            convertView.findViewById(R.id.button_share).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



//
//                                CommonMethods.shareCV(context, cvList.get(position));
//                                ;
//                            }

                        }
                    }
            );


            convertView.findViewById(R.id.button_delete).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Delete"); // title bar string
                            builder.setMessage("Confirm delete"); // message to display
                            // provide an OK button that simply dismisses the dialog
                            builder.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int button) {
                                            if (cvList.get(position).delete()) {
                                                cvList.remove(position);
                                                Toast.makeText(
                                                        context, "Deleted successfully", Toast.LENGTH_SHORT
                                                ).show();
                                            } else
                                                Toast.makeText(
                                                        context, "Cannot delete file", Toast.LENGTH_SHORT
                                                ).show();
                                            notifyDataSetChanged();
                                        }
                                    }
                            );
                            builder.setNegativeButton("No", null);
                            builder.show();

                        }
                    }
            );


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                        CommonMethods.openCV(context, cvList.get(position));
//                    }

                }
            });
            return convertView;
        }
    }


    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

        private List<File> moviesList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageButton b_delete, b_open, b_share;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.cv_name);
                b_delete = view.findViewById(R.id.button_delete);
                b_share = view.findViewById(R.id.button_share);
                b_open = view.findViewById(R.id.button_open);
//                genre = (TextView) view.findViewById(R.id.genre);
//                year = (TextView) view.findViewById(R.id.year);
            }
        }


        public MoviesAdapter(List<File> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_open_share, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder,  int position) {
            // Movie movie = moviesList.get(position);
            holder.title.setText(movieList.get(position).getName());
            holder.b_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//
//                        CommonMethods.openCV(context, cvList.get(position));
//                    }
                }
            });
//            holder.genre.setText(movie.getGenre());
//            holder.year.setText(movie.getYear());

            holder.b_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//
//                        CommonMethods.shareCV(context, cvList.get(position));
//                    }

                }
            });

            holder.b_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete"); // title bar string
                    builder.setIcon(R.drawable.ic_delete_black_24dp);

                    builder.setMessage("Confirm delete"); // message to display
                    // provide an OK button that simply dismisses the dialog
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int button) {
                                    if (cvList.get(position).delete()) {
                                        cvList.remove(position);
                                        Toast.makeText(
                                                context, "Deleted successfullyy", Toast.LENGTH_SHORT
                                        ).show();
                                        notifyItemRemoved(position);
                                        notifyDataSetChanged();

                                        init_recycleview();

                                    } else
                                        Toast.makeText(
                                                context, "Cannot delete file", Toast.LENGTH_SHORT
                                        ).show();
                                    notifyDataSetChanged();
                                }
                            }
                    );
                    builder.setNegativeButton("No", null);
                    builder.show();
                }
            });
        }


        @Override
        public int getItemCount() {
            return moviesList.size();
        }
    }



}
