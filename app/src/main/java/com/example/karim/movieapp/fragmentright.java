package com.example.karim.movieapp;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

/**
 * Created by karim on 4/1/2016.
 */
public class fragmentright extends Fragment implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    TextView tv,tv1,tv2,tv3,reviews,trailer_text;
    CheckBox cb;
    ImageView iv;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    ListView trailers;
    trailer_list trailer_list;
    LinkedList<String> urls;
    ScrollView detail_scroll_view;

    Bundle bundle;
    myfavorite_database myfavorite_database;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.details2,container,false);

            bundle = this.getArguments();
      //  Log.i("karim", container.toString());
            if(container!=null) {
                myfavorite_database=new myfavorite_database(container.getContext());
                detail_scroll_view = (ScrollView) view.findViewById(R.id.detail_scrollview);
                tv = (TextView) view.findViewById(R.id.textView3);
                tv1 = (TextView) view.findViewById(R.id.name);
                tv2 = (TextView) view.findViewById(R.id.release);
                tv3 = (TextView) view.findViewById(R.id.votes);
                trailer_text = (TextView) view.findViewById(R.id.trail);
                trailers = (ListView) view.findViewById(R.id.trailers_links);
                reviews = (TextView) view.findViewById(R.id.reviews_data);
                iv = (ImageView) view.findViewById(R.id.imageView2);
                cb = (CheckBox) view.findViewById(R.id.checkBox);

                trailers = (ListView) view.findViewById(R.id.trailers_links);
                trailers.setOnItemClickListener(this);
                //trailer_list=new trailer_list(this,trailer);
                //trailers.setAdapter(trailer_list);
                String name = bundle.getString("name");
//        Toast.makeText(container.getContext(),name,Toast.LENGTH_SHORT).show();
                tv.setText(bundle.getString("overview"));
                tv1.setText(name);
                tv2.setText("Release date: " + bundle.getString("release"));
                tv3.setText(bundle.getString("votes") + "/10");
                Picasso.with(container.getContext()).load("http://image.tmdb.org/t/p/w185" + bundle.getString("poster")).resize(200, 300).into(iv);

                urls = new LinkedList<>();
                // reviews.setText(getIntent().getStringExtra("id"));


                reviewTask reviewTask = new reviewTask(container.getContext(), reviews,detail_scroll_view);
                reviewTask.execute(bundle.getString("id"));

                trailers_task trReviewTask1 = new trailers_task(container.getContext(), trailers, urls, detail_scroll_view, trailer_text);
                trReviewTask1.execute(bundle.getString("id"));

                //details=this.getIntent().("detals");
                if(myfavorite_database.is_exist(2, bundle.getString("id"))==1){
                    cb.setChecked(true);
                }else
                    cb.setChecked(false);


                cb.setOnCheckedChangeListener(this);
               // detail_scroll_view.scrollTo(0,0);
            }

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            myfavorite_database.insert_data(bundle.getString("name")+","+bundle.getString("id")+","+bundle.getString("poster"));
        }else{
            myfavorite_database.delete_data("movie_id",bundle.getString("id"));
        }
        //Toast.makeText(this,sp.getString("favorites", "none"),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,position+"", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), video.class).putExtra("site", urls.get(position)));
    }

   }
