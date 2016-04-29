package com.example.karim.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class details extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener, View.OnClickListener {
    TextView tv,tv1,tv2,tv3,reviews,trailer_text;
    CheckBox cb;
    ImageView iv;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    ListView trailers;
    com.example.karim.movieapp.trailer_list trailer_list;
    LinkedList<String>urls;
    ScrollView detail_scroll_view;
    myfavorite_database myfavorite_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details2);

        myfavorite_database=new myfavorite_database(this);

        detail_scroll_view=(ScrollView)findViewById(R.id.detail_scrollview);
        tv=(TextView)findViewById(R.id.textView3);
        tv1=(TextView)findViewById(R.id.name);
        tv2=(TextView)findViewById(R.id.release);
        tv3=(TextView)findViewById(R.id.votes);
        trailer_text=(TextView)findViewById(R.id.trail);
        trailers=(ListView)findViewById(R.id.trailers_links);
       // buttonrev=(Button)findViewById(R.id.buttonrev);
       // buttontra=(Button)findViewById(R.id.buttontra);

        //trailer_text.setText("no railers");

        reviews=(TextView)findViewById(R.id.reviews_data);
        iv=(ImageView)findViewById(R.id.imageView2);
        cb=(CheckBox)findViewById(R.id.checkBox);

        trailers=(ListView)findViewById(R.id.trailers_links);
        trailers.setOnItemClickListener(this);
        //trailer_list=new trailer_list(this,trailer);
        //trailers.setAdapter(trailer_list);
        String name=getIntent().getStringExtra("name");
        tv.setText(getIntent().getStringExtra("overview"));
        tv1.setText(name);
        tv2.setText("Release date: "+getIntent().getStringExtra("release"));
        tv3.setText(getIntent().getStringExtra("votes") + "/10");
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185" + getIntent().getStringExtra("poster")).resize(200,300).into(iv);

        urls=new LinkedList<>();
       // reviews.setText(getIntent().getStringExtra("id"));


        reviewTask reviewTask=new reviewTask(this,reviews,detail_scroll_view);
        reviewTask.execute(getIntent().getStringExtra("id"));

        trailers_task trReviewTask1=new trailers_task(this,trailers,urls,detail_scroll_view,trailer_text);
        trReviewTask1.execute(getIntent().getStringExtra("id"));


        if(myfavorite_database.is_exist(2, getIntent().getStringExtra("id"))==1){
            cb.setChecked(true);
        }else
            cb.setChecked(false);
        cb.setOnCheckedChangeListener(this);

        //detail_scroll_view.scrollTo(0,0);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        if(isChecked){
           myfavorite_database.insert_data(getIntent().getStringExtra("name")+","+getIntent().getStringExtra("id")+","+getIntent().getStringExtra("poster"));
        }else{
            myfavorite_database.delete_data("movie_id",getIntent().getStringExtra("id"));
        }
        //Log.i("data",myfavorite_database.get_all());
        //Toast.makeText(this,sp.getString("favorites", "none"),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,position+"", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(details.this, video.class).putExtra("site", urls.get(position)));
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();


    }
}
