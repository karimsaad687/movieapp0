package com.example.karim.movieapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class favoriteMovies extends Activity implements AdapterView.OnItemClickListener {
    ArrayAdapter adapter;
    GridView listView;
    TextView t;
    myfavorite_database myfavorite_database;
    LinkedList<grid_Images> linkedList;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);
        t=(TextView)findViewById(R.id.nof);

        listView=(GridView)findViewById(R.id.gridView);

         myfavorite_database=new myfavorite_database(this);
        String word=myfavorite_database.get_all(3);
        if(word.length()>2) {

            String[] strings = word.split(",");
                t.setText("Your Favorite Movies");
                linkedList = new LinkedList<>();
                for (int i = 0; i < strings.length; i++) {
                    linkedList.addLast(new grid_Images(strings[i]));
                    //Log.i("url", strings[i]);
                }
                myarrayadapter myarrayadapter = new myarrayadapter(this, linkedList);
                listView.setAdapter(myarrayadapter);

                listView.setOnItemClickListener(this);
            listView.setVisibility(View.VISIBLE);
        }else {
            t.setText("No Favorite Movies Yet");
            listView.setVisibility(View.GONE);
        }
        //Log.i("all",myfavorite_database.get_all(3));
      //  Log.i("database insert", myfavorite_database.insert_data("karim,1234,poster") + "");
        //Log.i("database read",myfavorite_database.IsInDB(1)+"");

    }
    @Override
    protected void onResume() {

        super.onResume();
        String word=myfavorite_database.get_all(3);
        if(word.length()>2) {

            String[] strings = word.split(",");
            t.setText("Your Favorite Movies");
            linkedList = new LinkedList<>();
            for (int i = 0; i < strings.length; i++) {
                linkedList.addLast(new grid_Images(strings[i]));
                //Log.i("url", strings[i]);
            }
            myarrayadapter myarrayadapter = new myarrayadapter(this, linkedList);
            listView.setAdapter(myarrayadapter);

            listView.setOnItemClickListener(this);
            listView.setVisibility(View.VISIBLE);
        }else {
            t.setText("No Favorite Movies Yet");
            listView.setVisibility(View.GONE);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final String[]strings=myfavorite_database.get_all(1).split(",");
        new AlertDialog.Builder(this).setTitle(strings[position])
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Log.i("state", strings[position] + " " + myfavorite_database.delete_data("name", strings[position]) + "");
                                String word = myfavorite_database.get_all(3);
                                if (word.length() > 2) {
                                    String[] strings = word.split(",");

                                    t.setText("Your Favorite Movies");
                                    linkedList = new LinkedList<>();
                                    for (int i = 0; i < strings.length; i++) {
                                        linkedList.addLast(new grid_Images(strings[i]));
                                        //Log.i("url", strings[i]);
                                    }
                                    myarrayadapter myarrayadapter = new myarrayadapter(getBaseContext(), linkedList);
                                    listView.setAdapter(myarrayadapter);
                                    listView.setVisibility(View.VISIBLE);
                                } else {
                                    t.setText("No Favorite Movies Yet");
                                    listView.setVisibility(View.GONE);
                                }
                            }
                        }

                ).

            setNegativeButton("View", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(),"Viewing",Toast.LENGTH_SHORT).show();
                            String[] strings1 = myfavorite_database.get_all(2).split(",");

                            Log.i("id", strings1[position]);
                            favoritetask favoritetask = new favoritetask(favoriteMovies.this, getBaseContext());
                            favoritetask.execute(strings1[position]);
                        }
                    }

            )

            .show();
        }
    }
