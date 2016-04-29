package com.example.karim.movieapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Created by karim on 4/1/2016.
 */
public class fragmentleft extends Fragment implements AdapterView.OnItemClickListener {

    myarrayadapter adapter;
    //ProgressBar pb;

    GridView g;
    LinkedList<String[]> details;
    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    mySync mysync;
    ProgressBar sp;
    boolean frag=false;
    Bundle bundle;
    fragmentright fragment_class2=new fragmentright();
    public fragmentleft(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.main,container,false);

        context=container.getContext();
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
        bundle=this.getArguments();
        frag = bundle.getBoolean("frag");
        editor = prefs.edit();
        g= (GridView) view.findViewById(R.id.grid);
        details=new LinkedList<>();
        sp=(ProgressBar)view.findViewById(R.id.progressBar);
        refresh();
        g.setOnItemClickListener(this);
        return view;
    }
    public void refresh(){
        //if(context.getResources().f(R.id.screen2)!=null)
          //  frag=true;
       mysync =new mySync(context,adapter,g,details,sp,frag);
        mysync.execute(getFragmentManager());


        //getLoaderManager().initLoader(R.id.myid, null, loaderCallbacks);
    }

    @Override
    public void onDestroy() {
        //Log.i("karim","destroyed");
        if(mysync!=null)
        mysync.cancel=true;
        super.onDestroy();
    }
    public LoaderManager.LoaderCallbacks<LinkedList<String[]>> loaderCallbacks=new LoaderManager.LoaderCallbacks<LinkedList<String[]>>() {
        @Override
        public Loader<LinkedList<String[]>> onCreateLoader(int id, Bundle args) {
            Toast.makeText(context,"created",Toast.LENGTH_SHORT).show();
            return new loadmovies_loader(context);
        }

        @Override
        public void onLoadFinished(Loader<LinkedList<String[]>> loader, LinkedList<String[]> data) {
            Toast.makeText(context,"finished",Toast.LENGTH_SHORT).show();
            String w="";
            int y=0;
            //  Toast.makeText(con,details.size()+"",Toast.LENGTH_SHORT).show();
            LinkedList<grid_Images> linkedList=new LinkedList<>();
            String sort=prefs.getString("sort","popularity");
            String movie_name=prefs.getString(sort, "popularity");
            Log.i("stored",movie_name);
            for(int i=0;i<data.size();i++){
                if(movie_name.equals(details.get(i)[0]))
                    y=i;
                linkedList.addLast(new grid_Images(details.get(i)[3]));
            }
           myarrayadapter arrayAdapter=new myarrayadapter(context,linkedList);

            g.setAdapter(arrayAdapter);

            // Log.i("position", prefs.getInt("position", 0) + "");
            g.setSelection(y);
            if(context.getApplicationContext().getResources().getConfiguration().orientation==2) {




                Bundle bundle = new Bundle();
                bundle.putString("poster", details.get(y)[3]);
                bundle.putString("overview", details.get(y)[1]);
                bundle.putString("name", details.get(y)[0]);
                bundle.putString("release", details.get(y)[2]);
                bundle.putString("id", details.get(y)[6]);
                bundle.putString("votes", details.get(y)[5]);

                FragmentTransaction ft=getFragmentManager().beginTransaction();
                Fragment f=getFragmentManager().findFragmentByTag("right");
                if(f!=null){
                    ft.remove(f);
                }
                fragmentright fragmentright= new fragmentright();
                fragmentright.setArguments(bundle);
                ft.add(R.id.screen2, fragmentright, "right");
                //Log.i("karim", con+"");
                //if(cancel==false)
                    ft.commit();

            }

        }

        @Override
        public void onLoaderReset(Loader<LinkedList<String[]>> loader) {

        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(context, details.size()+"",Toast.LENGTH_SHORT).show();
        String sort=prefs.getString("sort","popularity");
        editor.putString(sort, details.get(position)[0]);
        //Log.i("stored", details.get(position)[0]);
        editor.commit();
        if(frag) {

            Bundle bundle = new Bundle();
            bundle.putString("poster", details.get(position)[3]);
            bundle.putString("overview", details.get(position)[1]);
            bundle.putString("name", details.get(position)[0]);
            bundle.putString("release", details.get(position)[2]);
            bundle.putString("id", details.get(position)[6]);
            bundle.putString("votes", details.get(position)[5]);

            FragmentTransaction ft=getFragmentManager().beginTransaction();
            Fragment f=getFragmentManager().findFragmentByTag("right");
            if(f!=null){
                ft.remove(f);
            }

            fragmentright fragmentright= new fragmentright();
            fragmentright.setArguments(bundle);
            ft.add(R.id.screen2, fragmentright, "right");
            ft.commit();



        }else{

                startActivity(new Intent(getActivity(), details.class)
                        .putExtra("poster", details.get(position)[3])
                        .putExtra("overview", details.get(position)[1])
                        .putExtra("name", details.get(position)[0])
                        .putExtra("release", details.get(position)[2])
                        .putExtra("id", details.get(position)[6])
                        .putExtra("votes", details.get(position)[5]));
        }

    }
}
