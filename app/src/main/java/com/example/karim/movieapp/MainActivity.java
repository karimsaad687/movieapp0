package com.example.karim.movieapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    FragmentManager fragmentManager=getSupportFragmentManager();
    FragmentTransaction ft=fragmentManager.beginTransaction();
    fragmentleft fragment_class=new fragmentleft();
    boolean frag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part);
        prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        Log.i("ge",findViewById(R.id.screen2)+"");
        if(findViewById(R.id.screen2)!=null)
            frag=true;
        Fragment f=getSupportFragmentManager().findFragmentByTag("left");
        if(f!=null){
            ft.remove(f);
        }
        Fragment fr=getSupportFragmentManager().findFragmentByTag("right");
        if(fr!=null){
            Log.i("hello","found");
            ft.remove(fr);
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("frag", frag);
        fragmentleft fragmentleft1= new fragmentleft();
        fragmentleft1.setArguments(bundle);
        ft.add(R.id.screen, fragmentleft1, "left");
        ft.commit();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this,newConfig.orientation+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        if (id == R.id.top) {
            //startActivity(new Intent(this,SettingsActivity.class));

            String sort=prefs.getString("sort", "popularity");

            if(sort.equals("none")||sort.equals("popularity")) {
                editor.putString("sort","vote_average");
                editor.commit();
            }
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Fragment f=getSupportFragmentManager().findFragmentByTag("left");
            Fragment fr=getSupportFragmentManager().findFragmentByTag("right");
            if(fr!=null){
                Log.i("hello","found");
                ft.remove(fr);
            }
            if(f!=null){
                ft.remove(f);
            }

            Bundle bundle = new Bundle();
            bundle.putBoolean("frag", frag);
            fragmentleft fragmentleft1= new fragmentleft();
            fragmentleft1.setArguments(bundle);
            ft.add(R.id.screen, fragmentleft1, "left");
            ft.commit();
            return true;
        }
        else if (id == R.id.pop) {
            //startActivity(new Intent(this,SettingsActivity.class));
            String sort=prefs.getString("sort", "none");

            if(sort.equals("none")||sort.equals("vote_average")) {
                //  SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this);
                editor.putString("sort","popularity");
                editor.commit();
            }
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Fragment f=getSupportFragmentManager().findFragmentByTag("left");
            Fragment fr=getSupportFragmentManager().findFragmentByTag("right");
            if(fr!=null){
                Log.i("hello","found");
                ft.remove(fr);
            }
            if(f!=null){
                ft.remove(f);
            }

            Bundle bundle = new Bundle();
            bundle.putBoolean("frag", frag);
            fragmentleft fragmentleft1= new fragmentleft();
            fragmentleft1.setArguments(bundle);
            ft.add(R.id.screen, fragmentleft1, "left");
            ft.commit();

            return true;

        }else if(id==R.id.favorite){
            startActivity(new Intent(MainActivity.this, favoriteMovies.class));
            // startActivity(new Intent(MainActivity.this,video.class));

        }

        return super.onOptionsItemSelected(item);
    }
}
